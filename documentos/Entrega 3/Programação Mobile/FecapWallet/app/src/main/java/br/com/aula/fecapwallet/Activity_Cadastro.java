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
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inicializarComponentes();
        usuarioDAO = new UsuarioDAO(this);
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
            if (!validarCampos()) return;

            String nomeStr = nome.getText().toString().trim();
            String telefoneStr = formatarTelefone(telefone.getText().toString().trim());
            String cpfStr = formatarCPF(cpf.getText().toString().trim());
            String emailStr = email.getText().toString().trim();
            String senhaStr = senha.getText().toString();

            boolean sucesso = salvarDadosUsuario(nomeStr, telefoneStr, cpfStr, emailStr, senhaStr);

            if (sucesso) {
                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                navegarParaLogin();
            } else {
                mostrarErro("Erro ao cadastrar no banco local");
            }

        } catch (Exception e) {
            Log.e(TAG, "Erro no cadastro: ", e);
            mostrarErro("Erro durante o cadastro: " + e.getMessage());
        }
    }

    private boolean salvarDadosUsuario(String nome, String telefone, String cpf,
                                       String email, String senha) {
        String sobrenome = "";
        String ra = "000000";

        boolean cadastrado = usuarioDAO.cadastrarUsuario(nome, sobrenome, ra, telefone, cpf, email, senha);

        if (cadastrado) {
            try {
                SecretKey key = KeyStoreHelper.getOrCreateKey(this);
                String emailCripto = CryptoHelper.encrypt(email, key);
                String senhaCripto = CryptoHelper.encrypt(senha, key);
                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("email", emailCripto);
                editor.putString("senha", senhaCripto);
                editor.apply();
            } catch (Exception e) {
                Log.e(TAG, "Erro ao salvar dados para biometria", e);
            }
        }

        return cadastrado;
    }

    private boolean validarCampos() {
        boolean valido = true;
        if (nome.getText().toString().trim().isEmpty()) {
            nome.setError("Nome completo é obrigatório");
            valido = false;
        }
        String telefoneStr = telefone.getText().toString().trim();
        if (telefoneStr.isEmpty() || telefoneStr.length() < 11) {
            telefone.setError("Telefone inválido");
            valido = false;
        }
        String cpfStr = cpf.getText().toString().trim();
        if (cpfStr.isEmpty() || cpfStr.length() != 11) {
            cpf.setError("CPF inválido");
            valido = false;
        }
        String emailStr = email.getText().toString().trim();
        if (emailStr.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            email.setError("Email inválido");
            valido = false;
        }
        if (senha.getText().toString().length() < 6) {
            senha.setError("Senha deve ter no mínimo 6 caracteres");
            valido = false;
        }
        return valido;
    }

    private String formatarTelefone(String telefone) {
        return telefone.replaceAll("[^0-9]", "");
    }

    private String formatarCPF(String cpf) {
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
}