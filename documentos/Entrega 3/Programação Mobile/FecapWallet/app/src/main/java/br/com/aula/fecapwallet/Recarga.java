package br.com.aula.fecapwallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Recarga extends AppCompatActivity {

    private EditText inputValorRecarga;
    private Button btnContinuar;
    private Button btn10, btn20, btn50, btn100;
    private RadioGroup radioGroupMetodo;
    private RadioButton radioPix, radioCartao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga);

        inputValorRecarga = findViewById(R.id.input_valor_recarga);
        btnContinuar = findViewById(R.id.btn_continuar);

        btn10 = findViewById(R.id.btn10);
        btn20 = findViewById(R.id.btn20);
        btn50 = findViewById(R.id.btn50);
        btn100 = findViewById(R.id.btn100);

        radioGroupMetodo = findViewById(R.id.radioGroupMetodo);
        radioPix = findViewById(R.id.radioPix);
        radioCartao = findViewById(R.id.radioCartao);

        ImageView btnVoltar = findViewById(R.id.img_voltar);
        btnVoltar.setOnClickListener(v -> finish());

        // Formatação automática com R$ ao digitar
        inputValorRecarga.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    inputValorRecarga.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[R$,.\\s]", "");
                    if (cleanString.isEmpty()) {
                        current = "";
                        inputValorRecarga.setText("");
                        inputValorRecarga.setSelection(0);
                        inputValorRecarga.addTextChangedListener(this);
                        return;
                    }

                    double parsed = Double.parseDouble(cleanString) / 100.0;
                    String formatted = String.format("R$ %.2f", parsed);

                    current = formatted;
                    inputValorRecarga.setText(formatted);
                    inputValorRecarga.setSelection(formatted.length());

                    inputValorRecarga.addTextChangedListener(this);
                }
            }
        });

        // Botões de valor pré-definido
        View.OnClickListener listenerValores = v -> {
            Button botao = (Button) v;
            String valor = botao.getText().toString().replaceAll("[R$\\s]", "").replace(",", ".");
            double valorDouble = Double.parseDouble(valor);
            String formatado = String.format("R$ %.2f", valorDouble);
            inputValorRecarga.setText(formatado);
        };

        btn10.setOnClickListener(listenerValores);
        btn20.setOnClickListener(listenerValores);
        btn50.setOnClickListener(listenerValores);
        btn100.setOnClickListener(listenerValores);

        btnContinuar.setOnClickListener(v -> {
            String valorBruto = inputValorRecarga.getText().toString().trim();

            if (valorBruto.isEmpty()) {
                Toast.makeText(Recarga.this, "Digite um valor para recarga", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedId = radioGroupMetodo.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(Recarga.this, "Selecione um método de pagamento", Toast.LENGTH_SHORT).show();
                return;
            }

            // Remove R$, espaço e converte vírgula para ponto
            String valor = valorBruto.replaceAll("[R$\\s]", "").replace(",", ".");

            Intent intent;

            if (selectedId == radioPix.getId()) {
                intent = new Intent(Recarga.this, RecargaPix.class);
            } else if (selectedId == radioCartao.getId()) {
                intent = new Intent(Recarga.this, RecargaCartao.class);
            } else {
                Toast.makeText(Recarga.this, "Método de pagamento inválido", Toast.LENGTH_SHORT).show();
                return;
            }

            intent.putExtra("valorRecarga", valor);
            startActivity(intent);
        });
    }
}
