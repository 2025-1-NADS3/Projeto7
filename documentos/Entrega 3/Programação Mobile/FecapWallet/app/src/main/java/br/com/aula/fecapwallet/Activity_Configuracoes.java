package br.com.aula.fecapwallet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

import br.com.aula.fecapwallet.security.BiometricAuthHelper;

public class Activity_Configuracoes extends AppCompatActivity {

    private Switch switchBiometria;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        // Inicialização dos componentes
        switchBiometria = findViewById(R.id.switchBiometria);
        preferences = getSharedPreferences("app_prefs", MODE_PRIVATE);

        // Configuração inicial do switch
        boolean isBiometricAvailable = BiometricAuthHelper.isBiometricAvailable(this);
        boolean biometriaAtiva = preferences.getBoolean("biometria_ativa", false) && isBiometricAvailable;
        switchBiometria.setChecked(biometriaAtiva);
        switchBiometria.setEnabled(isBiometricAvailable);

        // Listener para o switch de biometria
        switchBiometria.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                BiometricAuthHelper.showBiometricPrompt(
                        this,
                        "Ativar autenticação biométrica",
                        "Autentique-se para ativar o login com biometria",
                        new BiometricAuthHelper.BiometricAuthCallback() {
                            @Override
                            public void onAuthenticationSuccess() {
                                preferences.edit()
                                        .putBoolean("biometria_ativa", true)
                                        .apply();
                            }
                            @Override
                            public void onAuthenticationError(int errorCode, CharSequence errString) {
                                switchBiometria.setChecked(false);
                            }
                        }
                );
            } else {
                preferences.edit()
                        .putBoolean("biometria_ativa", false)
                        .apply();
            }
        });

        // Bottom Navigation
        LinearLayout btnMeuCartao = findViewById(R.id.btnMeuCartaoLayout);
        LinearLayout btnTransacoes = findViewById(R.id.btnTransacoesLayout);
        LinearLayout btnHome = findViewById(R.id.btnHomeLayout);

        btnMeuCartao.setOnClickListener(v -> {
            startActivity(new Intent(this, Activity_My_card.class));
        });

        btnTransacoes.setOnClickListener(v -> {
            startActivity(new Intent(this, Activity_Transacoes.class));
        });

        btnHome.setOnClickListener(v -> {
            startActivity(new Intent(this, Activity_Home.class));
        });

        // Listener para Linguagem
        findViewById(R.id.linguagemLayout).setOnClickListener(v -> {
            startActivity(new Intent(this, Activity_Linguagem.class));
        });

        // Listener para Meu Perfil
        findViewById(R.id.perfilLayout).setOnClickListener(v -> {
            startActivity(new Intent(this, Activity_EditarPerfil.class));
        });

        // Listener para contate-nos
        findViewById(R.id.contatoLayout).setOnClickListener(v -> {
            startActivity(new Intent(this, Activity_Suporte.class));
        });

        // Listener para alterar senha
        findViewById(R.id.alterarSenhaLayout).setOnClickListener(v -> {
            startActivity(new Intent(this, Activity_AlterarSenha.class));
        });

        // Listener para Politica de Privacidade
        findViewById(R.id.privacidadeLayout).setOnClickListener(v -> {
            startActivity(new Intent(this, Activity_TermosUso.class));
        });


    }

    // Metodo chamado pelo botão via android:onClick no XML
    public void voltarHome(View view) {
        Intent intent = new Intent(Activity_Configuracoes.this, Activity_Home.class);
        startActivity(intent);
    }
}