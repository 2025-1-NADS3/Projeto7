// Declaração do pacote da aplicação
package br.com.aula.fecapwallet;

// Importações necessárias
import android.content.Intent;  // Para navegação entre Activities
import android.os.Bundle;       // Para passagem de estados
import android.os.Handler;      // Para execução de código com atraso
import androidx.appcompat.app.AppCompatActivity;  // Classe base para Activities

// Classe principal que representa a tela inicial (Splash Screen)
public class MainActivity extends AppCompatActivity {

    // Metodo chamado quando a Activity é criada
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Define o layout XML da tela
        setContentView(R.layout.activity_main);

        // Configura um temporizador de 2 segundos
        new Handler().postDelayed(() -> {
            // Cria uma Intent para navegar para Activity_Primeira_Tela
            Intent intent = new Intent(MainActivity.this, Activity_Primeira_Tela.class);
            // Inicia a próxima Activity
            startActivity(intent);
            // Finaliza a Activity atual
            finish();
        }, 2000);  // Delay de 2000 milissegundos (2 segundos)
    }
}