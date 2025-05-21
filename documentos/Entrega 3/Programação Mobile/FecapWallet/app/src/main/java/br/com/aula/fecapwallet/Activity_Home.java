package br.com.aula.fecapwallet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.aula.fecapwallet.security.CryptoHelper;
import br.com.aula.fecapwallet.security.KeyStoreHelper;

public class Activity_Home extends AppCompatActivity {

    private UsuarioDAO usuarioDAO;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationHelper.setupBottomNavigation(this);

        preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        usuarioDAO = new UsuarioDAO(this);

        TextView textViewNome = findViewById(R.id.textView2);
        TextView textViewTitular = findViewById(R.id.card_holder);

        try {
            String emailLogado = preferences.getString("email_logado", null);

            if (emailLogado != null) {
                String nomeUsuario = usuarioDAO.getNomePorEmail(emailLogado);

                if (nomeUsuario != null) {
                    textViewNome.setText(nomeUsuario + "!");
                    textViewTitular.setText(nomeUsuario);
                } else {
                    textViewNome.setText("Usuário!");
                    textViewTitular.setText("Usuário");
                }
            } else {
                Log.e("Activity_Home", "email_logado está nulo.");
                textViewNome.setText("Usuário!");
                textViewTitular.setText("Usuário");
            }

        } catch (Exception e) {
            Log.e("Activity_Home", "Erro ao recuperar nome do usuário", e);
            textViewNome.setText("Usuário!");
            textViewTitular.setText("Usuário");
        }

        // Navegação dos botões inferiores
        ImageButton btnPerfil = findViewById(R.id.imageButton);
        btnPerfil.setOnClickListener(v -> {
            startActivity(new Intent(this, Activity_Perfil.class));
        });

        LinearLayout btnMeuCartao = findViewById(R.id.btnMeuCartaoLayout);
        LinearLayout btnTransacoes = findViewById(R.id.btnTransacoesLayout);
        LinearLayout btnConfiguracoes = findViewById(R.id.btnConfiguracoesLayout);

        btnMeuCartao.setOnClickListener(v -> {
            startActivity(new Intent(this, Activity_My_card.class));
        });

        btnTransacoes.setOnClickListener(v -> {
            startActivity(new Intent(this, Activity_Transacoes.class));
        });

        btnConfiguracoes.setOnClickListener(v -> {
            startActivity(new Intent(this, Activity_Configuracoes.class));
        });

        // Ativa o ícone da Home na barra inferior
        ImageButton btnHomeIcon = findViewById(R.id.imageButton2);
        btnHomeIcon.setImageResource(R.drawable.homeverde);
        TextView txtHome = findViewById(R.id.textView10);
        txtHome.setTextColor(getResources().getColor(R.color.verde_fecap));

        // === NOVOS BOTÕES FUNCIONAIS ===

        // Botão "Enviar"
        ImageButton btnEnviar = findViewById(R.id.imageButton6);
        btnEnviar.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_Home.this, Enviar.class);
            startActivity(intent);
        });

        // Botão "Recarga"
        ImageButton btnRecarga = findViewById(R.id.imageButton8);
        btnRecarga.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_Home.this, Recarga.class);
            startActivity(intent);
        });
    }
}