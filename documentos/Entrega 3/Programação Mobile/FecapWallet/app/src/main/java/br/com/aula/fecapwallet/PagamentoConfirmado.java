package br.com.aula.fecapwallet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PagamentoConfirmado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagamento_confirmado);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Recebe valor da intent
        Intent intent = getIntent();
        String valor = intent.getStringExtra("valor_enviado");

// Atualiza TextView com o valor
        TextView txtValor = findViewById(R.id.txt_valor_confirmado);
        if (valor != null && !valor.isEmpty()) {
            txtValor.setText("R$ " + valor);
        } else {
            txtValor.setText("R$ 0,00");
        }

// Atualiza TextView com a chave Pix
        TextView txtChavePix = findViewById(R.id.textValorRecebido);
        String chavePix = intent.getStringExtra("chave_pix"); // <- Novo campo
        if (chavePix != null && !chavePix.isEmpty()) {
            txtChavePix.setText("Chave Pix: " + chavePix);
        } else {
            txtChavePix.setText("Chave Pix não informada");
        }

        // Botão de voltar ao início
        findViewById(R.id.btnVoltarInicio).setOnClickListener(v -> {
            Intent intentHome = new Intent(PagamentoConfirmado.this, Activity_Home.class);
            intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentHome);
            finish();
        });
    }
}
