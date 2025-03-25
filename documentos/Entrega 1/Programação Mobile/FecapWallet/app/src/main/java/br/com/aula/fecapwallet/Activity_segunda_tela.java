// Define o pacote da aplicação
package br.com.aula.fecapwallet;

// Importações necessárias para o funcionamento
import android.content.Intent;  // Para navegação entre telas
import android.os.Bundle;       // Para gerenciar estado da Activity
import android.widget.Button;    // Componente de interface para botões

// Classe base para Activities com suporte a recursos modernos
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity que representa a segunda tela do fluxo de introdução do aplicativo.
 * Permite a navegação para a tela de login.
 */
public class Activity_segunda_tela extends AppCompatActivity {

    /**
     * Método chamado quando a Activity é criada.
     * @param savedInstanceState Estado salvo da Activity (se houver)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Define o layout XML para esta Activity
        setContentView(R.layout.activity_segunda_tela);

        // Obtém referência ao botão "Continuar" do layout
        Button btnContinuar = findViewById(R.id.bt_inscreversecadastro);

        // Configura ação ao clicar no botão Continuar
        btnContinuar.setOnClickListener(v -> {
            // Cria Intent para navegar para tela de login
            Intent intent = new Intent(Activity_segunda_tela.this, Activity_Entrar.class);
            // Inicia a Activity de login
            startActivity(intent);
        });
    }
}