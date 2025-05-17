package br.com.aula.fecapwallet;

import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class Activity_Historico extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        BottomNavigationHelper.setupBottomNavigation(this);

        // Botão voltar
        ImageButton btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> finish());

        // Configura RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvTransacoes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Transacao> transacoes = new ArrayList<>();
        // Adicione suas transações aqui...

        TransacaoAdapter adapter = new TransacaoAdapter(transacoes);
        recyclerView.setAdapter(adapter);
    }
}