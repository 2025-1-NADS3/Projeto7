package br.com.aula.fecapwallet.security;

import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;

import java.security.KeyStore;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class KeyStoreHelper {
    private static final String TAG = "KeyStoreHelper";
    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";
    private static final String KEY_ALIAS = "FecapWallet_AES_Key";
    private static final int KEY_SIZE = 256;

    public static SecretKey getOrCreateKey(Context context) {
        try {
            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
            keyStore.load(null);

            if (!keyStore.containsAlias(KEY_ALIAS)) {
                Log.d(TAG, "Criando nova chave no KeyStore");
                return createKey();
            }

            KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(KEY_ALIAS, null);
            if (secretKeyEntry == null) {
                Log.e(TAG, "Entrada da chave secreta é nula, criando nova");
                return createKey();
            }

            SecretKey key = secretKeyEntry.getSecretKey();
            Log.d(TAG, "Chave recuperada com sucesso: " + key.getAlgorithm());
            return key;
        } catch (Exception e) {
            Log.e(TAG, "Erro ao obter/criar chave: " + e.getMessage(), e);
            return null;
        }
    }

    private static SecretKey createKey() throws Exception {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE);

            keyGenerator.init(
                    new KeyGenParameterSpec.Builder(
                            KEY_ALIAS,
                            KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                            .setKeySize(KEY_SIZE)
                            .setRandomizedEncryptionRequired(true)
                            .build());

            SecretKey key = keyGenerator.generateKey();
            Log.d(TAG, "Nova chave criada com sucesso: " + key.getAlgorithm());
            return key;
        } catch (Exception e) {
            Log.e(TAG, "Falha ao criar chave: " + e.getMessage(), e);
            throw new Exception("Falha na criação da chave de criptografia", e);
        }
    }
}