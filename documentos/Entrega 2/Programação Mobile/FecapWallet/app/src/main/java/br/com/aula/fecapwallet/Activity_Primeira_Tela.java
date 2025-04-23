// Define o pacote da aplicação
package br.com.aula.fecapwallet;

// Importações necessárias para o funcionamento
import android.content.Intent;  // Para navegação entre Activities
import android.os.Bundle;       // Para gerenciar estado da Activity
import android.widget.Button;   // Componente de interface para botões

// Classe base para Activities com suporte a recursos modernos
import androidx.appcompat.app.AppCompatActivity;


public class Activity_Primeira_Tela extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Define o layout XML para esta Activity
        setContentView(R.layout.activity_primeira_tela);

        // Obtém referência ao botão "Começar" do layout
        Button btnComecar = findViewById(R.id.btn_proximo_primeira_tela);

        // Configura ação ao clicar no botão Começar
        btnComecar.setOnClickListener(v -> {
            // Cria Intent para navegar para segunda tela de introdução
            Intent intent = new Intent(Activity_Primeira_Tela.this, Activity_segunda_tela.class);
            // Inicia a próxima Activity
            startActivity(intent);
        });
    }
}