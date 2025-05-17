package br.com.aula.fecapwallet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class Activity_Suporte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suporte);

        // Configuração do botão voltar
        findViewById(R.id.btnVoltar).setOnClickListener(v -> finish());

        EditText edtMensagem = findViewById(R.id.edtMensagem);
        Button btnEnviar = findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(v -> {
            String mensagem = edtMensagem.getText().toString();
            if (mensagem.isEmpty()) {
                edtMensagem.setError("Digite sua mensagem");
                return;
            }

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:suporte@fecapwallet.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Contato pelo App");
            intent.putExtra(Intent.EXTRA_TEXT, mensagem);
            startActivity(Intent.createChooser(intent, "Enviar e-mail"));
        });
    }
}