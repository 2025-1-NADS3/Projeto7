package br.com.aula.fecapwallet;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RecargaPix extends AppCompatActivity {

    private ImageView imgVoltarPix;
    private Button btnConfirmarPix;
    private TextView txtChavePix;
    private ImageView btnCopiarChave;
    private String valorRecarga = "0.00"; // Será atualizado com o valor real
    private String chavePix = "00020126580014BR.GOV.BCB.PIX0136chave@exemplo.com.br5204000053039865802BR5925NOME DO RECEBEDOR6009SÃO PAULO62140510ABCD1234EFGH6304B13F";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recarga_pix);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_pix), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Recupera valor real enviado pela tela de recarga
        if (getIntent().hasExtra("valorRecarga")) {
            valorRecarga = getIntent().getStringExtra("valorRecarga");
        }

        // Referências de UI
        imgVoltarPix = findViewById(R.id.img_voltar_pix);
        btnConfirmarPix = findViewById(R.id.btn_confirmar_pix);
        txtChavePix = findViewById(R.id.txt_chave_pix);
        btnCopiarChave = findViewById(R.id.btn_copiar_chave);

        // Exibir a chave Pix
        txtChavePix.setText(chavePix);

        // Ação do botão voltar
        imgVoltarPix.setOnClickListener(v -> finish());

        // Ação do botão copiar chave
        btnCopiarChave.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Chave Pix", chavePix);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(RecargaPix.this, "Chave Pix copiada!", Toast.LENGTH_SHORT).show();
        });

        // Ação do botão confirmar pagamento
        btnConfirmarPix.setOnClickListener(v -> {
            Intent intent = new Intent(RecargaPix.this, ConfirmarRecarga.class);
            intent.putExtra("valorRecarga", valorRecarga);
            intent.putExtra("formaPagamento", "Pix");
            startActivity(intent);
            finish();
        });
    }
}
