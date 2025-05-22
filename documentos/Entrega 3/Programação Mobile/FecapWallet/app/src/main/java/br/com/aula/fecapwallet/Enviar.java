package br.com.aula.fecapwallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Enviar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enviar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnEnviar = findViewById(R.id.btn_enviar);
        EditText inputValor = findViewById(R.id.input_valor_enviar);
        EditText inputChavePix = findViewById(R.id.input_chave_pix);

        // Botão voltar
        ImageView btnVoltar = findViewById(R.id.img_voltar);
        btnVoltar.setOnClickListener(v -> finish());

        // Formatação com "R$" ao digitar o valor
        inputValor.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    inputValor.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[R$,.\\s]", "");
                    if (cleanString.isEmpty()) {
                        current = "";
                        inputValor.setText("");
                        inputValor.setSelection(0);
                        inputValor.addTextChangedListener(this);
                        return;
                    }

                    double parsed = Double.parseDouble(cleanString) / 100.0;
                    String formatted = String.format("R$ %.2f", parsed);

                    current = formatted;
                    inputValor.setText(formatted);
                    inputValor.setSelection(formatted.length());

                    inputValor.addTextChangedListener(this);
                }
            }
        });

        btnEnviar.setOnClickListener(v -> {
            String valorBruto = inputValor.getText().toString().trim();
            String chavePix = inputChavePix.getText().toString().trim();

            // Sanitiza o valor para passar como dado puro (sem R$ e com ponto decimal)
            String valor = valorBruto.replaceAll("[R$\\s]", "").replace(",", ".");

            Intent intent = new Intent(Enviar.this, PagamentoConfirmado.class);
            intent.putExtra("valor_enviado", valor);
            intent.putExtra("chave_pix", chavePix);
            startActivity(intent);
        });
    }
}
