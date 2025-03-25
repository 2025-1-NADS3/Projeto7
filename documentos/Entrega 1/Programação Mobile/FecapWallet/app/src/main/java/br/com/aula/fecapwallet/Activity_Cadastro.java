package br.com.aula.fecapwallet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_Cadastro extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Botão "Entrar" para voltar para tela de login
        Button btnEntrar = findViewById(R.id.bt_entrarcadastro);
        btnEntrar.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_Cadastro.this, Activity_Entrar.class);
            startActivity(intent);
        });

        // Botão "Inscrever-se" (implemente a lógica de cadastro aqui)
        Button btnInscrever = findViewById(R.id.bt_inscreversecadastro);
        btnInscrever.setOnClickListener(v -> {
            // Lógica para cadastrar usuário
            // Após cadastro bem-sucedido, navegue para a tela principal do app
        });

        ImageView btnVoltar = findViewById(R.id.img_logovoltar);
        btnVoltar.setOnClickListener(v -> {
            onBackPressed();
        });


    }
}