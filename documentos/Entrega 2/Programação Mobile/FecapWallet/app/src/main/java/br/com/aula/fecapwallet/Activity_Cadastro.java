package br.com.aula.fecapwallet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import br.com.aula.fecapwallet.security.CryptoHelper;
import br.com.aula.fecapwallet.security.KeyStoreHelper;
import javax.crypto.SecretKey;

public class Activity_Cadastro extends AppCompatActivity {

    private static final String TAG = "CadastroActivity";
    private static final String PREFS_NAME = "user_data";
    private EditText nome, telefone, cpf, email, senha;
    private Button btnInscrever, btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inicializarComponentes();
        configurarListeners();
    }

    private void inicializarComponentes() {
        nome = findViewById(R.id.nomelinha);
        telefone = findViewById(R.id.telefonelinha);
        cpf = findViewById(R.id.linhacpf);
        email = findViewById(R.id.linha_email);
        senha = findViewById(R.id.linha_senha);
        btnInscrever = findViewById(R.id.btn_proximo_primeira_tela);
        btnEntrar = findViewById(R.id.bt_entrarcadastro);
    }

    private void configurarListeners() {
        btnInscrever.setOnClickListener(v -> processarCadastro());
        btnEntrar.setOnClickListener(v -> navegarParaLogin());

        ImageView btnVoltar = findViewById(R.id.img_primeira_tela);
        if (btnVoltar != null) {
            btnVoltar.setOnClickListener(v -> onBackPressed());
        }
    }

    private void processarCadastro() {
        try {
            Log.d(TAG, "Iniciando processamento de cadastro");

            if (!validarCampos()) {
                return;
            }

            String nomeStr = nome.getText().toString().trim();
            String telefoneStr = formatarTelefone(telefone.getText().toString().trim());
            String cpfStr = formatarCPF(cpf.getText().toString().trim());
            String emailStr = email.getText().toString().trim();
            String senhaStr = senha.getText().toString();

            SecretKey secretKey = KeyStoreHelper.getOrCreateKey(this);
            if (secretKey == null) {
                mostrarErro("Falha ao obter chave de criptografia");
                return;
            }

            Log.d(TAG, "Criptografando email...");
            String encryptedEmail = CryptoHelper.encrypt(emailStr, secretKey);
            Log.d(TAG, "Criptografando senha...");
            String encryptedSenha = CryptoHelper.encrypt(senhaStr, secretKey);

            // DEBUG: Mostrar dados criptografados
            Log.d(TAG, "Email criptografado: " + encryptedEmail);
            Log.d(TAG, "Senha criptografada: " + encryptedSenha);

            if (!validarDadosCriptografados(encryptedEmail, encryptedSenha)) {
                mostrarErro("Falha na validação dos dados criptografados");
                return;
            }

            if (salvarDadosUsuario(nomeStr, telefoneStr, cpfStr, encryptedEmail, encryptedSenha)) {
                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                navegarParaLogin();
            } else {
                mostrarErro("Erro ao salvar dados no dispositivo");
            }

        } catch (Exception e) {
            Log.e(TAG, "Erro no cadastro: ", e);
            mostrarErro("Erro durante o cadastro: " + e.getMessage());
        }
    }

    private boolean validarCampos() {
        boolean valido = true;

        // Validação do nome
        if (nome.getText().toString().trim().isEmpty()) {
            nome.setError("Nome completo é obrigatório");
            valido = false;
        }

        // Validação do telefone
        String telefoneStr = telefone.getText().toString().trim();
        if (telefoneStr.isEmpty()) {
            telefone.setError("Telefone é obrigatório");
            valido = false;
        } else if (telefoneStr.length() < 11) {
            telefone.setError("Telefone inválido");
            valido = false;
        }

        // Validação do CPF
        String cpfStr = cpf.getText().toString().trim();
        if (cpfStr.isEmpty()) {
            cpf.setError("CPF é obrigatório");
            valido = false;
        } else if (cpfStr.length() != 11) {
            cpf.setError("CPF inválido (deve ter 11 dígitos)");
            valido = false;
        }

        // Validação do email
        String emailStr = email.getText().toString().trim();
        if (emailStr.isEmpty()) {
            email.setError("Email é obrigatório");
            valido = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            email.setError("Email inválido");
            valido = false;
        }

        // Validação da senha
        if (senha.getText().toString().isEmpty()) {
            senha.setError("Senha é obrigatória");
            valido = false;
        } else if (senha.getText().toString().length() < 6) {
            senha.setError("Senha deve ter no mínimo 6 caracteres");
            valido = false;
        }

        return valido;
    }

    private boolean validarDadosCriptografados(String encryptedEmail, String encryptedSenha) {
        if (encryptedEmail == null || encryptedSenha == null) {
            Log.e(TAG, "Resultado da criptografia é nulo");
            return false;
        }

        // Verifica se os dados não estão vazios e têm tamanho mínimo
        if (encryptedEmail.isEmpty() || encryptedEmail.length() < 16 ||
                encryptedSenha.isEmpty() || encryptedSenha.length() < 16) {
            Log.e(TAG, "Dados criptografados muito curtos");
            return false;
        }

        // Verifica se a senha está em Base64 válido (deve conter == no final)
        if (!encryptedSenha.matches(".*={1,2}$")) {
            Log.e(TAG, "Formato de senha criptografada inválido");
            return false;
        }

        // O email pode não ter padding == pois depende do tamanho dos dados
        // Mas deve ser alfanumérico com possíveis +/=
        if (!encryptedEmail.matches("[A-Za-z0-9+/]+={0,2}$")) {
            Log.e(TAG, "Formato de email criptografado inválido");
            return false;
        }

        return true;
    }

    private boolean salvarDadosUsuario(String nome, String telefone, String cpf,
                                       String encryptedEmail, String encryptedSenha) {
        try {
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            // Armazenar dados
            editor.putString("nome", nome);
            editor.putString("telefone", telefone);
            editor.putString("cpf", cpf);
            editor.putString("email", encryptedEmail);
            editor.putString("senha", encryptedSenha);

            // Adicionar flag de primeiro cadastro
            editor.putBoolean("primeiro_acesso", true);

            // Aplicar as mudanças
            editor.apply();

            Log.d(TAG, "Dados salvos com sucesso no SharedPreferences");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Erro ao salvar dados: ", e);
            return false;
        }
    }

    private String formatarTelefone(String telefone) {
        // Remove todos os caracteres não numéricos
        return telefone.replaceAll("[^0-9]", "");
    }

    private String formatarCPF(String cpf) {
        // Remove todos os caracteres não numéricos
        return cpf.replaceAll("[^0-9]", "");
    }

    private void mostrarErro(String mensagem) {
        Log.e(TAG, mensagem);
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
    }

    private void navegarParaLogin() {
        startActivity(new Intent(this, Activity_Entrar.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}