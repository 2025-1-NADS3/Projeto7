package br.com.aula.fecapwallet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Activity_EditarPerfil extends AppCompatActivity {

    private UsuarioDAO usuarioDAO;
    private SharedPreferences preferences;
    private EditText edtNome, edtEmail, edtTelefone;
    private TextView txtTituloUsuario;
    private String emailLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        usuarioDAO = new UsuarioDAO(this);
        preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        emailLogado = preferences.getString("email_logado", null);

        ImageButton btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> finish());

        edtNome = findViewById(R.id.edtNomeCompleto);
        edtEmail = findViewById(R.id.edtEmail);
        edtTelefone = findViewById(R.id.edtTelefone);
        txtTituloUsuario = findViewById(R.id.titulo_nome_usuario);

        carregarDadosUsuario();

        Button btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(v -> salvarAlteracoes());
    }

    private void carregarDadosUsuario() {
        if (emailLogado != null) {
            Usuario usuario = usuarioDAO.getUsuarioPorEmail(emailLogado);
            if (usuario != null) {
                edtNome.setText(usuario.getNome());
                edtEmail.setText(usuario.getEmail());
                edtTelefone.setText(usuario.getCelular());

                // Atualiza o título com o nome do usuário
                txtTituloUsuario.setText(usuario.getNome());
            }
        }
    }

    private void salvarAlteracoes() {
        String novoNome = edtNome.getText().toString().trim();
        String novoTelefone = edtTelefone.getText().toString().trim();

        if (usuarioDAO.atualizarNomeETelefone(emailLogado, novoNome, novoTelefone)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("nome", novoNome);
            editor.putString("telefone", novoTelefone);
            editor.apply();
            Toast.makeText(this, "Dados atualizados com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Erro ao atualizar os dados", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}