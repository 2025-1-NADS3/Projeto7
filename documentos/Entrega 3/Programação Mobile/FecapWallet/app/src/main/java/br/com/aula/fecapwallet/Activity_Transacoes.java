package br.com.aula.fecapwallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity_Transacoes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomNavigationHelper.setupBottomNavigation(this);


        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transacoes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Bottom Navigation
        LinearLayout btnMeuCartao = findViewById(R.id.btnMeuCartaoLayout);
        LinearLayout btnHome = findViewById(R.id.btnHomeLayout);
        LinearLayout btnConfiguracoes = findViewById(R.id.btnConfiguracoesLayout);

        btnMeuCartao.setOnClickListener(v -> {
            startActivity(new Intent(this, Activity_My_card.class));
        });

        btnHome.setOnClickListener(v -> {
            startActivity(new Intent(this, Activity_Home.class));
        });

        btnConfiguracoes.setOnClickListener(v -> {
            startActivity(new Intent(this, Activity_Configuracoes.class));
        });
    }

    // Metodo chamado pelo botão via android:onClick no XML
    public void voltarHome(View view) {
        Intent intent = new Intent(Activity_Transacoes.this, Activity_Home.class);
        startActivity(intent);
        finish();
    }
}