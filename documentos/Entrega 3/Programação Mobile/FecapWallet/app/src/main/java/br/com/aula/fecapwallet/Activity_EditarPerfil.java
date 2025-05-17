package br.com.aula.fecapwallet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class Activity_EditarPerfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        // Botão voltar
        ImageButton btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> finish());

        // Recupera dados atuais
        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        EditText edtNome = findViewById(R.id.edtNomeCompleto);
        EditText edtEmail = findViewById(R.id.edtEmail);
        EditText edtTelefone = findViewById(R.id.edtTelefone);

        edtNome.setText(preferences.getString("nome", ""));
        edtEmail.setText(preferences.getString("email", ""));
        edtTelefone.setText(preferences.getString("telefone", ""));

        // Botão salvar
        Button btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("nome", edtNome.getText().toString());
            editor.putString("telefone", edtTelefone.getText().toString());
            editor.apply();

            finish(); // Volta para a tela de perfil
        });
    }
}