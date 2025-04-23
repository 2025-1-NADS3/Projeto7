// Declara o pacote da aplicação
package br.com.aula.fecapwallet;

// Importações necessárias para manipular atividades, intenções, views e logs
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

// Classe utilitária responsável por configurar a navegação inferior (Bottom Navigation)
public class BottomNavigationHelper {

    // Metodo principal para configurar os itens da navegação inferior
    public static void setupBottomNavigation(Activity activity) {
        try {
            // Define os listeners de clique para cada item do menu inferior
            setupNavItem(activity, R.id.btnHomeLayout, Activity_Home.class);
            setupNavItem(activity, R.id.btnMeuCartaoLayout, Activity_My_card.class);
            setupNavItem(activity, R.id.btnEstatisticasLayout, Activity_Estatisticas.class);
            setupNavItem(activity, R.id.btnConfiguracoesLayout, Activity_Configuracoes.class);

            // Atualiza visualmente qual aba está ativa
            updateActiveTab(activity);
        } catch (Exception e) {
            // Loga um erro caso ocorra falha ao configurar a navegação
            Log.e("BottomNav", "Error setting up navigation", e);
        }
    }

    // Metodo auxiliar que define a ação de clique de um item da barra de navegação
    private static void setupNavItem(Activity activity, int layoutId, Class<?> target) {
        View layout = activity.findViewById(layoutId);
        if (layout != null) {
            layout.setOnClickListener(v -> {
                // Abre uma nova activity apenas se ela ainda não estiver ativa
                if (!activity.getClass().equals(target)) {
                    activity.startActivity(new Intent(activity, target));
                }
            });
        }
    }

    // Metodo que altera visualmente qual aba está ativa na navegação
    static void updateActiveTab(Activity activity) {
        int colorActive = activity.getResources().getColor(R.color.verde_fecap);
        int colorInactive = activity.getResources().getColor(R.color.cinza_medio);

        // Reseta o estado de todos os botões para inativo
        setTabState(activity, R.id.imageButton2, R.id.textView10, colorInactive, false);
        setTabState(activity, R.id.meucartaoicon, R.id.meucartao, colorInactive, false);
        setTabState(activity, R.id.estatisticasbutton, R.id.estatisticas, colorInactive, false);
        setTabState(activity, R.id.configbutton, R.id.configuracoes, colorInactive, false);

        // Ativa visualmente o botão correspondente à activity atual
        if (activity instanceof Activity_Home) {
            setTabState(activity, R.id.imageButton2, R.id.textView10, colorActive, true);
        } else if (activity instanceof Activity_My_card) {
            setTabState(activity, R.id.meucartaoicon, R.id.meucartao, colorActive, true);
        } else if (activity instanceof Activity_Estatisticas) {
            setTabState(activity, R.id.estatisticasbutton, R.id.estatisticas, colorActive, true);
        } else if (activity instanceof Activity_Configuracoes) {
            setTabState(activity, R.id.configbutton, R.id.configuracoes, colorActive, true);
        }
    }

    // Metodo que aplica o estado visual (cor e seleção) ao ícone e texto do item selecionado
    private static void setTabState(Activity activity, int iconId, int textId, int color, boolean active) {
        ImageButton icon = activity.findViewById(iconId);
        TextView text = activity.findViewById(textId);

        if (icon != null) {
            icon.setColorFilter(color); // Aplica a cor ao ícone
            icon.setSelected(active);   // Define se o botão está selecionado
        }
        if (text != null) {
            text.setTextColor(color);   // Aplica a cor ao texto do item
        }
    }
}