package br.com.aula.fecapwallet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Activity_Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationHelper.setupBottomNavigation(this);


        // Configuração do usuário
        TextView textViewNome = findViewById(R.id.textView2);
        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        textViewNome.setText("Bem-vindo, " + preferences.getString("nome", "Usuário") + "!");

        // Configuração dos botões da bottom navigation
        ImageButton btnPerfil = findViewById(R.id.imageButton); // Botão do perfil no header
        btnPerfil.setOnClickListener(v -> {
            startActivity(new Intent(this, Activity_Perfil.class));
        });

        // Bottom Navigation
        LinearLayout btnMeuCartao = findViewById(R.id.btnMeuCartaoLayout);
        LinearLayout btnEstatisticas = findViewById(R.id.btnEstatisticasLayout);
        LinearLayout btnConfiguracoes = findViewById(R.id.btnConfiguracoesLayout);

        btnMeuCartao.setOnClickListener(v -> {
            startActivity(new Intent(this, Activity_My_card.class));
        });

        btnEstatisticas.setOnClickListener(v -> {
            startActivity(new Intent(this, Activity_Estatisticas.class));
        });

        btnConfiguracoes.setOnClickListener(v -> {
            startActivity(new Intent(this, Activity_Configuracoes.class));
        });

        // Ativa o ícone da Home na bottom bar
        ImageButton btnHomeIcon = findViewById(R.id.imageButton2);
        btnHomeIcon.setImageResource(R.drawable.homeverde);
        TextView txtHome = findViewById(R.id.textView10);
        txtHome.setTextColor(getResources().getColor(R.color.verde_fecap));
    }
}