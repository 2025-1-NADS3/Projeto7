package br.com.aula.fecapwallet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmarRecarga extends AppCompatActivity {

    private TextView txtValor, txtDadosRecarga;
    private Button btnConfirmar, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_recarga);

        txtValor = findViewById(R.id.txt_valor);
        txtDadosRecarga = findViewById(R.id.txt_dados_recarga);
        btnConfirmar = findViewById(R.id.btn_confirmar);
        btnCancelar = findViewById(R.id.btn_cancelar);

        ImageView btnVoltar = findViewById(R.id.img_voltar);
        btnVoltar.setOnClickListener(v -> finish());

        String valor = getIntent().getStringExtra("valorRecarga");
        String formaPagamento = getIntent().getStringExtra("formaPagamento");
        String dadosExtras = getIntent().getStringExtra("dadosExtras");

        if (valor != null && !valor.isEmpty()) {
            txtValor.setText("Valor: R$ " + valor);
        } else {
            txtValor.setText("Valor não informado");
            Toast.makeText(this, "Valor da recarga não recebido", Toast.LENGTH_SHORT).show();
        }

        StringBuilder dadosTexto = new StringBuilder();

        if (formaPagamento != null) {
            dadosTexto.append("Forma de Pagamento: ").append(formaPagamento).append("\n");
        }

        if (dadosExtras != null && !dadosExtras.isEmpty()) {
            dadosTexto.append(dadosExtras);
        }

        txtDadosRecarga.setText(dadosTexto.toString());

        btnConfirmar.setOnClickListener(v -> {
            Intent intent = new Intent(ConfirmarRecarga.this, RecargaConcluida.class);
            intent.putExtra("valorRecarga", valor);
            intent.putExtra("formaPagamento", formaPagamento);
            intent.putExtra("dadosExtras", dadosExtras);
            startActivity(intent);
        });

        btnCancelar.setOnClickListener(v -> finish());
    }
}
