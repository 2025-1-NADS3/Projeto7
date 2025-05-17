package br.com.aula.fecapwallet.security;

import android.content.Context;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BiometricAuthHelper {

    public interface BiometricAuthCallback {
        void onAuthenticationSuccess();
        void onAuthenticationError(int errorCode, CharSequence errString);
    }

    public static boolean isBiometricAvailable(Context context) {
        BiometricManager biometricManager = BiometricManager.from(context);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                return true;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(context, "Nenhum sensor biométrico disponível", Toast.LENGTH_SHORT).show();
                return false;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(context, "Sensor biométrico indisponível", Toast.LENGTH_SHORT).show();
                return false;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(context, "Nenhuma biometria cadastrada", Toast.LENGTH_SHORT).show();
                return false;
            default:
                return false;
        }
    }

    public static void showBiometricPrompt(
            FragmentActivity activity,
            String title,
            String subtitle,
            BiometricAuthCallback callback) {

        Executor executor = Executors.newSingleThreadExecutor();

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(title)
                .setSubtitle(subtitle)
                .setNegativeButtonText("Usar senha")
                .setConfirmationRequired(false)
                .build();

        BiometricPrompt biometricPrompt = new BiometricPrompt(activity, executor,
                new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        activity.runOnUiThread(() ->
                                callback.onAuthenticationError(errorCode, errString));
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        activity.runOnUiThread(callback::onAuthenticationSuccess);
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        activity.runOnUiThread(() ->
                                Toast.makeText(activity, "Autenticação falhou", Toast.LENGTH_SHORT).show());
                    }
                });

        biometricPrompt.authenticate(promptInfo);
    }
}