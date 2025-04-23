package br.com.aula.fecapwallet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import br.com.aula.fecapwallet.security.BiometricAuthHelper;
import br.com.aula.fecapwallet.security.CryptoHelper;
import br.com.aula.fecapwallet.security.KeyStoreHelper;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.crypto.SecretKey;

public class Activity_Entrar extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private EditText emailLogin, senhaLogin;
    private Button btnEntrar, btnVoltarCadastro;
    private SharedPreferences preferences;
    private SecretKey secretKey;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);

        inicializarComponentes();
        configurarBiometria();
        configurarListeners();

        Button btnBiometria = findViewById(R.id.btnBiometria);

        if (BiometricAuthHelper.isBiometricAvailable(this)) {
            btnBiometria.setVisibility(View.VISIBLE);
            btnBiometria.setOnClickListener(v -> autenticarComBiometria());
        }
    }

private void inicializarComponentes() {
    emailLogin = findViewById(R.id.email_login);
    senhaLogin = findViewById(R.id.senha_login);
    btnEntrar = findViewById(R.id.btn_entrar);
    btnVoltarCadastro = findViewById(R.id.btnVoltarCadastro);
    preferences = getSharedPreferences("user_data", MODE_PRIVATE);

    try {
        secretKey = KeyStoreHelper.getOrCreateKey(this);
    } catch (Exception e) {
        Log.e(TAG, "Erro ao obter chave de criptografia", e);
        Toast.makeText(this, "Erro de configuração de segurança", Toast.LENGTH_SHORT).show();
    }
}

private void configurarBiometria() {
    executor = Executors.newSingleThreadExecutor();

    biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
            runOnUiThread(() -> {
                if (errorCode != BiometricPrompt.ERROR_USER_CANCELED) {
                    Toast.makeText(Activity_Entrar.this,
                            "Erro: " + errString, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            runOnUiThread(() -> processarLoginBiometrico());
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            runOnUiThread(() ->
                    Toast.makeText(Activity_Entrar.this,
                            "Autenticação falhou", Toast.LENGTH_SHORT).show());
        }
    });

    promptInfo = new BiometricPrompt.PromptInfo.Builder()
            .setTitle("Login FecapWallet")
            .setSubtitle("Use sua biometria para acessar")
            .setNegativeButtonText("Usar senha")
            .setConfirmationRequired(false)
            .build();
}

private void configurarListeners() {
    btnEntrar.setOnClickListener(v -> processarLoginManual());

    btnVoltarCadastro.setOnClickListener(v -> {
        startActivity(new Intent(this, Activity_Cadastro.class));
        finish();
    });
}

private void autenticarComBiometria() {
    if (BiometricAuthHelper.isBiometricAvailable(this)) {
        biometricPrompt.authenticate(promptInfo);
    }
}

    private void processarLoginBiometrico() {
        try {
            String savedEmail = preferences.getString("email", null);
            String savedSenha = preferences.getString("senha", null);

            if (savedEmail == null || savedSenha == null) {
                runOnUiThread(() ->
                        Toast.makeText(this, "Nenhuma credencial salva encontrada", Toast.LENGTH_SHORT).show());
                return;
            }

            // Verificar formato antes de descriptografar
            if (!savedEmail.contains("==") || !savedSenha.contains("==")) {
                runOnUiThread(() ->
                        Toast.makeText(this, "Credenciais corrompidas", Toast.LENGTH_SHORT).show());
                return;
            }

            String decryptedEmail = CryptoHelper.decrypt(savedEmail, secretKey);
            String decryptedSenha = CryptoHelper.decrypt(savedSenha, secretKey);

            if (decryptedEmail == null || decryptedSenha == null) {
                runOnUiThread(() ->
                        Toast.makeText(this, "Falha na autenticação biométrica", Toast.LENGTH_SHORT).show());
                return;
            }

            // Preenche automaticamente
            runOnUiThread(() -> {
                emailLogin.setText(decryptedEmail);
                senhaLogin.setText(decryptedSenha);
                realizarLogin(decryptedEmail, decryptedSenha);
            });

        } catch (Exception e) {
            Log.e(TAG, "Erro ao descriptografar com biometria", e);
            runOnUiThread(() ->
                    Toast.makeText(this, "Falha na autenticação: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void processarLoginManual() {
        String emailInput = emailLogin.getText().toString().trim();
        String senhaInput = senhaLogin.getText().toString();

        if (emailInput.isEmpty() || senhaInput.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            if (secretKey == null) {
                Toast.makeText(this, "Erro de segurança. Reinicie o app.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Recupera dados criptografados
            String savedEmail = preferences.getString("email", null);
            String savedSenha = preferences.getString("senha", null);

            if (savedEmail == null || savedSenha == null) {
                Toast.makeText(this, "Nenhum usuário cadastrado", Toast.LENGTH_SHORT).show();
                return;
            }

            // DEBUG: Mostra os dados salvos
            Log.d(TAG, "Tentando descriptografar email: " + savedEmail);
            Log.d(TAG, "Tentando descriptografar senha: " + savedSenha);

            // Descriptografa
            String decryptedEmail = CryptoHelper.decrypt(savedEmail, secretKey);
            String decryptedSenha = CryptoHelper.decrypt(savedSenha, secretKey);

            // Verifica se a descriptografia foi bem-sucedida
            if (decryptedEmail == null || decryptedSenha == null) {
                Log.e(TAG, "Falha na descriptografia - resultado nulo");
                Toast.makeText(this, "Erro ao ler credenciais salvas", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d(TAG, "Email descriptografado: " + decryptedEmail);
            Log.d(TAG, "Senha descriptografada: " + decryptedSenha);

            // Compara credenciais
            if (emailInput.equals(decryptedEmail) && senhaInput.equals(decryptedSenha)) {
                realizarLogin(emailInput, senhaInput);
            } else {
                Toast.makeText(this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e(TAG, "Erro no login: " + e.getMessage(), e);
            Toast.makeText(this, "Erro durante o login: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void realizarLogin(String email, String senha) {
        Log.d(TAG, "Login bem-sucedido para: " + email);
    // Salvar estado de login
    SharedPreferences.Editor editor = preferences.edit();
    editor.putBoolean("esta_logado", true);
    editor.apply();

    // Navegar para home
    startActivity(new Intent(this, Activity_Home.class));
    finish();
}

@Override
protected void onDestroy() {
    super.onDestroy();
    // Limpar recursos da biometria
    if (biometricPrompt != null) {
        try {
            biometricPrompt.cancelAuthentication();
        } catch (Exception e) {
            Log.e(TAG, "Erro ao limpar biometria", e);
        }
    }
}
}

