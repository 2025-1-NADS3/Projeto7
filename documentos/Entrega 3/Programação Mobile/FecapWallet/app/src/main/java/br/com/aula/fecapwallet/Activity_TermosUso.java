package br.com.aula.fecapwallet;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class Activity_TermosUso extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termos_uso);

        // Configuração do botão voltar
        findViewById(R.id.btnVoltar).setOnClickListener(v -> finish());
    }
}