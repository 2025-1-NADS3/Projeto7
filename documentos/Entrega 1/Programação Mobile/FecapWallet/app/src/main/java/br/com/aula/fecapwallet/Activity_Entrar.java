package br.com.aula.fecapwallet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_Entrar extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);

        // Botão "Inscrever-se" para ir para cadastro
        Button btnInscrever = findViewById(R.id.bt_entrarcadastro);
        btnInscrever.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_Entrar.this, Activity_Cadastro.class);
            startActivity(intent);
        });

        // Botão "Entrar" (implemente a lógica de login aqui)
        Button btnEntrar = findViewById(R.id.bt_inscreversecadastro);
        btnEntrar.setOnClickListener(v -> {
            // Lógica para fazer login
            // Após login bem-sucedido, navegue para a tela principal do app
        });

        ImageView btnVoltar = findViewById(R.id.img_logovoltar);
        btnVoltar.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}