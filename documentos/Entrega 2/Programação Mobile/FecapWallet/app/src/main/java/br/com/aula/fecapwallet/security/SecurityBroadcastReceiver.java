package br.com.aula.fecapwallet.security;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SecurityBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
                // Tratar inicialização do sistema
            }
        }
    }
}