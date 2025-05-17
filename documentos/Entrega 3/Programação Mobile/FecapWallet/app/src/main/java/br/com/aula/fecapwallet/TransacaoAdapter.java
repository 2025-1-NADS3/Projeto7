// Declara o pacote da aplicação
package br.com.aula.fecapwallet;

// Importações necessárias para a criação do Adapter, manipulação de Views e lista
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

// Define a classe do Adapter que liga os dados da transação com a interface visual (RecyclerView)
public class TransacaoAdapter extends RecyclerView.Adapter<TransacaoAdapter.TransacaoViewHolder> {

    // Lista de transações a serem exibidas no RecyclerView
    private List<Transacao> transacoes;

    // Construtor da classe, que recebe a lista de transações
    public TransacaoAdapter(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    // Cria um novo ViewHolder, inflando o layout de item da transação
    @NonNull
    @Override
    public TransacaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transacao, parent, false);
        return new TransacaoViewHolder(view);
    }

    // Responsável por preencher os dados de cada item da lista com base na posição
    @Override
    public void onBindViewHolder(@NonNull TransacaoViewHolder holder, int position) {
        Transacao transacao = transacoes.get(position);
        holder.imgIcone.setImageResource(transacao.getIcone());
        holder.txtTitulo.setText(transacao.getTitulo());
        holder.txtValor.setText(transacao.getValor());
        holder.txtCategoria.setText(transacao.getCategoria());
    }

    // Retorna a quantidade total de itens da lista
    @Override
    public int getItemCount() {
        return transacoes.size();
    }

    // Classe interna que representa o ViewHolder, responsável por armazenar as views de cada item
    static class TransacaoViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcone;
        TextView txtTitulo;
        TextView txtValor;
        TextView txtCategoria;

        // Construtor do ViewHolder que inicializa as views com base nos IDs definidos no XML
        public TransacaoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcone = itemView.findViewById(R.id.imgIcone);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtValor = itemView.findViewById(R.id.txtValor);
            txtCategoria = itemView.findViewById(R.id.txtCategoria);
        }
    }
}