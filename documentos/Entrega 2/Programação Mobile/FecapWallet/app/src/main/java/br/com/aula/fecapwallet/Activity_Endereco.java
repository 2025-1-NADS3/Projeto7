package br.com.aula.fecapwallet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class Activity_Endereco extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endereco);

        // Configuração do botão voltar
        findViewById(R.id.btnVoltar).setOnClickListener(v -> finish());

        EditText edtCep = findViewById(R.id.edtCep);
        EditText edtEndereco = findViewById(R.id.edtEndereco);
        EditText edtNumero = findViewById(R.id.edtNumero);
        EditText edtComplemento = findViewById(R.id.edtComplemento);
        EditText edtCidade = findViewById(R.id.edtCidade);
        EditText edtEstado = findViewById(R.id.edtEstado);
        Button btnSalvar = findViewById(R.id.btnSalvar);

        // Carrega dados existentes
        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        edtCep.setText(preferences.getString("cep", ""));
        edtEndereco.setText(preferences.getString("endereco", ""));
        edtNumero.setText(preferences.getString("numero", ""));
        edtComplemento.setText(preferences.getString("complemento", ""));
        edtCidade.setText(preferences.getString("cidade", ""));
        edtEstado.setText(preferences.getString("estado", ""));

        btnSalvar.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("cep", edtCep.getText().toString());
            editor.putString("endereco", edtEndereco.getText().toString());
            editor.putString("numero", edtNumero.getText().toString());
            editor.putString("complemento", edtComplemento.getText().toString());
            editor.putString("cidade", edtCidade.getText().toString());
            editor.putString("estado", edtEstado.getText().toString());
            editor.apply();

            finish();
        });
    }
}