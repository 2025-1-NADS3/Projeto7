package br.com.aula.fecapwallet.security;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {
    private static final String PREFS_NAME = "FecapWalletPrefs";
    private static final String KEY_ENCRYPTED_EMAIL = "encrypted_email";
    private static final String KEY_ENCRYPTED_PASSWORD = "encrypted_password";

    private final SharedPreferences prefs;

    public PreferencesHelper(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveEncryptedCredentials(String encryptedEmail, String encryptedPassword) {
        prefs.edit()
                .putString(KEY_ENCRYPTED_EMAIL, encryptedEmail)
                .putString(KEY_ENCRYPTED_PASSWORD, encryptedPassword)
                .apply();
    }

    public String[] getEncryptedCredentials() {
        return new String[] {
                prefs.getString(KEY_ENCRYPTED_EMAIL, null),
                prefs.getString(KEY_ENCRYPTED_PASSWORD, null)
        };
    }
}