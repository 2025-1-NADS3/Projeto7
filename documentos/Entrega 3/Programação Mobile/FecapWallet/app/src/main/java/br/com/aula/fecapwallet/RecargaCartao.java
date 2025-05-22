package br.com.aula.fecapwallet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecargaCartao extends AppCompatActivity {

    private EditText edtValidade, edtNomeCartao, edtNumeroCartao;
    private Button btnConfirmarCartao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga_cartao);

        edtValidade = findViewById(R.id.input_validade_cartao);
        edtNomeCartao = findViewById(R.id.input_nome_cartao);
        edtNumeroCartao = findViewById(R.id.input_num_cartao);
        btnConfirmarCartao = findViewById(R.id.btn_confirmar_cartao);

        ImageView btnVoltar = findViewById(R.id.img_voltar);
        btnVoltar.setOnClickListener(v -> finish());

        btnConfirmarCartao.setOnClickListener(v -> {
            String nomeCartao = edtNomeCartao.getText().toString().trim();
            String numeroCartao = edtNumeroCartao.getText().toString().trim();
            String validadeCartao = edtValidade.getText().toString().trim();

            if (nomeCartao.isEmpty() || numeroCartao.isEmpty() || validadeCartao.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos do cartão", Toast.LENGTH_SHORT).show();
                return;
            }

            // Garantir pelo menos 4 dígitos para mascarar
            String numeroMascarado = numeroCartao.length() >= 4 ?
                    "**** **** **** " + numeroCartao.substring(numeroCartao.length() - 4) :
                    "**** **** **** " + numeroCartao;

            String dadosExtras = "Nome: " + nomeCartao + "\nNúmero: " + numeroMascarado;

            // Pegando o valor da recarga
            String valorRecarga = getIntent().getStringExtra("valorRecarga");

            Intent intent = new Intent(RecargaCartao.this, ConfirmarRecarga.class);
            intent.putExtra("valorRecarga", valorRecarga);
            intent.putExtra("formaPagamento", "Cartão");
            intent.putExtra("dadosExtras", dadosExtras);
            intent.putExtra("ValidadeCartao", validadeCartao);

            startActivity(intent);
        });
    }
}
