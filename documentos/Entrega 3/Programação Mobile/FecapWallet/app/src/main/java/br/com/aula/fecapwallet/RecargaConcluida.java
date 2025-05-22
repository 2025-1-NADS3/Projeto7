package br.com.aula.fecapwallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecargaConcluida extends AppCompatActivity {

    private Button btnVoltarHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga_concluida);

        btnVoltarHome = findViewById(R.id.btn_voltar_home);

        // Se quiser pegar o valor da recarga, descomente abaixo
        /*
        String valor = getIntent().getStringExtra("valorRecarga");
        if (valor != null) {
            Toast.makeText(this, "Valor recarregado: R$ " + valor, Toast.LENGTH_SHORT).show();
        }
        */

        // Ação ao clicar em "Voltar ao Início"
        btnVoltarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Volta à tela principal (MainActivity ou outra que você definir)
                Intent intent = new Intent(RecargaConcluida.this, Activity_Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Encerra a tela atual
            }
        });
    }
}
