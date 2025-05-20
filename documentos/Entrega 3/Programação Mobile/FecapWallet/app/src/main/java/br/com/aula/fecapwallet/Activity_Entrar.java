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
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);

        emailLogin = findViewById(R.id.email_login);
        senhaLogin = findViewById(R.id.senha_login);
        btnEntrar = findViewById(R.id.btn_entrar);
        btnVoltarCadastro = findViewById(R.id.btnVoltarCadastro);
        preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        usuarioDAO = new UsuarioDAO(this);

        try {
            secretKey = KeyStoreHelper.getOrCreateKey(this);
        } catch (Exception e) {
            Log.e(TAG, "Erro ao obter chave de criptografia", e);
        }

        configurarBiometria();
        configurarListeners();

        Button btnBiometria = findViewById(R.id.btnBiometria);
        if (BiometricAuthHelper.isBiometricAvailable(this)) {
            btnBiometria.setVisibility(View.VISIBLE);
            btnBiometria.setOnClickListener(v -> autenticarComBiometria());
        }
    }

    private void configurarBiometria() {
        executor = Executors.newSingleThreadExecutor();
        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                runOnUiThread(() -> processarLoginBiometrico());
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login FecapWallet")
                .setSubtitle("Use sua biometria para acessar")
                .setNegativeButtonText("Usar senha")
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

    private void processarLoginManual() {
        String emailInput = emailLogin.getText().toString().trim();
        String senhaInput = senhaLogin.getText().toString();
        if (usuarioDAO.verificarLogin(emailInput, senhaInput)) {
            realizarLogin(emailInput, senhaInput);
        } else {
            Toast.makeText(this, "Email ou senha inválidos", Toast.LENGTH_SHORT).show();
        }
    }

    private void processarLoginBiometrico() {
        try {
            String savedEmail = preferences.getString("email", null);
            String savedSenha = preferences.getString("senha", null);
            String decryptedEmail = CryptoHelper.decrypt(savedEmail, secretKey);
            String decryptedSenha = CryptoHelper.decrypt(savedSenha, secretKey);

            if (usuarioDAO.verificarLogin(decryptedEmail, decryptedSenha)) {
                realizarLogin(decryptedEmail, decryptedSenha);
            } else {
                Toast.makeText(this, "Credenciais inválidas no banco", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro ao autenticar com biometria", e);
        }
    }

    private void realizarLogin(String email, String senha) {
        Log.d(TAG, "Login bem-sucedido para: " + email);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("esta_logado", true);
        editor.putString("email_logado", email); // <-- Aqui está o segredo
        editor.apply();

        startActivity(new Intent(this, Activity_Home.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (biometricPrompt != null) {
            biometricPrompt.cancelAuthentication();
        }
    }
}
