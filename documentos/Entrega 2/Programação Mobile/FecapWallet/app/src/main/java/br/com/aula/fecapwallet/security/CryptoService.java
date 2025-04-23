package br.com.aula.fecapwallet.security;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CryptoService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Inicializações aqui
    }
}