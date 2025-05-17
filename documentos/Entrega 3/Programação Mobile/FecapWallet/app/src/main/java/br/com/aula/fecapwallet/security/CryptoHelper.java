package br.com.aula.fecapwallet.security;

import android.util.Base64;
import android.util.Log;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class CryptoHelper {
    private static final String TAG = "CryptoHelper";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128; // bits

    public static String encrypt(String plaintext, SecretKey secretKey) {
        try {
            Log.d("CRIPTO_DEBUG", "Iniciando criptografia...");
            Log.d("CRIPTO_DEBUG", "Texto original: " + plaintext);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            byte[] iv = cipher.getIV();

            Log.d("CRIPTO_DEBUG", "IV gerado: " + Base64.encodeToString(iv, Base64.NO_WRAP));
            Log.d("CRIPTO_DEBUG", "Dados criptografados (sem IV): " + Base64.encodeToString(encryptedBytes, Base64.NO_WRAP));

            // Combine IV + dados criptografados
            byte[] combined = new byte[iv.length + encryptedBytes.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);

            String result = Base64.encodeToString(combined, Base64.NO_WRAP);
            Log.d("CRIPTO_DEBUG", "Resultado final (IV + dados): " + result);

            return result;
        } catch (Exception e) {
            Log.e("CRIPTO_DEBUG", "Erro durante criptografia: " + e.getMessage(), e);
            return null;
        }
    }

    public static String decrypt(String encryptedData, SecretKey secretKey) {
        if (encryptedData == null || encryptedData.length() < 16) { // Mínimo para IV + 1 byte
            Log.e(TAG, "Dados inválidos para descriptografia");
            return null;
        }

        try {
            // Verifica se o Base64 é válido
            if (!isBase64(encryptedData)) {
                Log.e(TAG, "Dados não estão em Base64 válido");
                return null;
            }

            byte[] combined = Base64.decode(encryptedData, Base64.NO_WRAP);

            // Verifica tamanho mínimo (IV + dados)
            if (combined.length <= 12) {
                Log.e(TAG, "Dados criptografados corrompidos (tamanho insuficiente)");
                return null;
            }

            // Extrai IV (primeiros 12 bytes)
            byte[] iv = new byte[12];
            System.arraycopy(combined, 0, iv, 0, 12);

            // Extrai dados criptografados
            byte[] encryptedBytes = new byte[combined.length - 12];
            System.arraycopy(combined, 12, encryptedBytes, 0, encryptedBytes.length);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(128, iv));

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            Log.e(TAG, "Falha na descriptografia: " + e.getMessage());
            return null;
        }
    }

    // Método auxiliar para verificar Base64
    private static boolean isBase64(String str) {
        try {
            Base64.decode(str, Base64.DEFAULT);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}