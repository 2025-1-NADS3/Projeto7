package br.com.aula.fecapwallet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Activity_Perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Initialize components
        ImageButton btnVoltar = findViewById(R.id.btnVoltar);
        TextView txtNomeUsuario = findViewById(R.id.txtNomeUsuario);
        TextView txtTipoUsuario = findViewById(R.id.txtTipoUsuario);

        // Back button configuration
        btnVoltar.setOnClickListener(v -> finish());

        // Load user data
        loadUserData(txtNomeUsuario, txtTipoUsuario);

        // Configure menu item clicks
        setupMenuNavigation();

        // Optional: Verify item states
        checkItemStates();
    }

    private void loadUserData(TextView txtNome, TextView txtTipo) {
        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        txtNome.setText(preferences.getString("nome", "Lucas Almeida"));
        txtTipo.setText(preferences.getString("tipo", "Estudante"));
    }

    private void setupMenuNavigation() {
        // Personal Information
        findViewById(R.id.btnInformacoes).setOnClickListener(v ->
                startActivity(new Intent(this, Activity_EditarPerfil.class)));

        // Payment Preferences
        findViewById(R.id.btnPagamentos).setOnClickListener(v ->
                startActivity(new Intent(this, Activity_Home.class)));

        // Banks and Cards
        findViewById(R.id.btnBancos).setOnClickListener(v ->
                startActivity(new Intent(this, Activity_My_card.class)));

        // Notifications
        findViewById(R.id.btnNotificacoes).setOnClickListener(v ->
                startActivity(new Intent(this, Activity_Historico.class)));

        // Message Center
        findViewById(R.id.btnMensagens).setOnClickListener(v ->
                startActivity(new Intent(this, Activity_Suporte.class)));

        // Address
        findViewById(R.id.btnEndereco).setOnClickListener(v ->
                startActivity(new Intent(this, Activity_Endereco.class)));

        // Settings
        findViewById(R.id.btnConfiguracoes).setOnClickListener(v ->
                startActivity(new Intent(this, Activity_Configuracoes.class)));
    }

    private void checkItemStates() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);

        configureItemState(
                R.id.btnInformacoes,
                R.id.iconeInformacoes,
                "info_completa",
                R.drawable.ic_check_circle,
                R.drawable.ic_person
        );

        configureItemState(
                R.id.btnPagamentos,
                R.id.iconePagamentos,
                "pagamento_configurado",
                R.drawable.ic_check_circle,
                R.drawable.ic_payment
        );
    }

    private void configureItemState(int itemId, int iconeId, String prefKey,
                                    int iconeConcluido, int iconePadrao) {
        try {
            View item = findViewById(itemId);
            ImageView icon = item.findViewById(iconeId);

            boolean isComplete = getSharedPreferences("app_prefs", MODE_PRIVATE)
                    .getBoolean(prefKey, false);

            icon.setImageResource(isComplete ? iconeConcluido : iconePadrao);
            icon.setColorFilter(ContextCompat.getColor(this,
                            isComplete ? R.color.verde_fecap : R.color.cinza_medio),
                    PorterDuff.Mode.SRC_IN);

        } catch (Exception e) {
            Log.e("Perfil", "Error configuring item: " + e.getMessage());
        }
    }
}