package br.com.aula.fecapwallet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
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
                // Solicitar autenticação biométrica para ativar
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

        // Listener para alterar senha
        findViewById(R.id.alterarSenhaLayout).setOnClickListener(v -> {
            startActivity(new Intent(this, Activity_AlterarSenha.class));
        });
    }
}