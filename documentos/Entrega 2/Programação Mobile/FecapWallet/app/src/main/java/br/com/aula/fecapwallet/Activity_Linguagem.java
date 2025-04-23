package br.com.aula.fecapwallet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class Activity_Linguagem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linguagem);

        // Configuração do botão voltar no header
        findViewById(R.id.btnVoltar).setOnClickListener(v -> finish());

        ListView listView = findViewById(R.id.listViewIdiomas);
        List<String> idiomas = new ArrayList<>();
        idiomas.add("Português");
        idiomas.add("Inglês");
        idiomas.add("Espanhol");
        idiomas.add("Francês");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, idiomas);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String idiomaSelecionado = idiomas.get(position);
            SharedPreferences preferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("idioma", idiomaSelecionado);
            editor.apply();

            // Atualiza a UI ou reinicia a activity conforme necessário
            finish();
        });
    }
}