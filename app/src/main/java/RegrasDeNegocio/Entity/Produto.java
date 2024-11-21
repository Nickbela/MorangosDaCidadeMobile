package RegrasDeNegocio.Entity;

public class Produto {
    private String nome;
    private double preco;
    private int quantidade;
    private int imagemId;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getImagemId(){
        return imagemId;
    }
    public void setImagemId(){
        this.imagemId = imagemId;
    }
}
