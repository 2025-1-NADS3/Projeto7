package br.com.aula.fecapwallet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Activity_AlterarSenha extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_senha);

        // Configuração do botão voltar
        findViewById(R.id.btnVoltar).setOnClickListener(v -> finish());

        EditText senhaAtual = findViewById(R.id.senha_atual);
        EditText novaSenha = findViewById(R.id.nova_senha);
        EditText confirmarSenha = findViewById(R.id.confirmar_senha);
        Button btnAlterar = findViewById(R.id.btn_alterar_senha);

        btnAlterar.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
            String senhaSalva = preferences.getString("senha", "");

            if (!senhaAtual.getText().toString().equals(senhaSalva)) {
                Toast.makeText(this, "Senha atual incorreta", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!novaSenha.getText().toString().equals(confirmarSenha.getText().toString())) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("senha", novaSenha.getText().toString());
            editor.apply();

            Toast.makeText(this, "Senha alterada com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}