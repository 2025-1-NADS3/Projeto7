package br.com.aula.fecapwallet;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.util.Log;


public class FecapWalletApplication extends Application {
    private static final String TAG = "FecapWalletApp";

    @Override
    public void onCreate() {
        super.onCreate();

        // Metodo seguro para verificar modo debug
        boolean isDebugMode = (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;

        try {
            // Inicialização segura sem depender do BuildConfig
            if (isDebugMode) {
                Log.d(TAG, "Aplicativo iniciado em modo debug");
            }

            // Suas outras inicializações aqui

        } catch (Exception e) {
            Log.e(TAG, "Erro na inicialização", e);
            if (isDebugMode) {
                throw new RuntimeException("Falha na inicialização", e);
            }
        }
    }
}