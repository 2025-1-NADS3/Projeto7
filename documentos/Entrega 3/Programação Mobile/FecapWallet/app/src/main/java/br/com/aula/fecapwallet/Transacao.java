// Declara o pacote onde essa classe está localizada
package br.com.aula.fecapwallet;

// Define a classe Transacao que representa uma transação (modelo de dados)
public class Transacao {

    // Atributos privados da classe: título, valor, categoria e ícone
    private String titulo;
    private String valor;
    private String categoria;
    private int icone;

    // Construtor da classe que inicializa os atributos com os valores recebidos
    public Transacao(String titulo, String valor, String categoria, int icone) {
        this.titulo = titulo;
        this.valor = valor;
        this.categoria = categoria;
        this.icone = icone;
    }

    // Métodos getters que retornam os valores dos atributos
    public String getTitulo() { return titulo; }
    public String getValor() { return valor; }
    public String getCategoria() { return categoria; }
    public int getIcone() { return icone; }
}