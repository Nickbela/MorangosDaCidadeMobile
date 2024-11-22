package RegrasDeNegocio.Entity;

public class ProdutoEntity {
    private String nome;
    private double preco;
    private int quantidade;
    private int imagemId;

    public ProdutoEntity(String nome, double preco, int quantidade, int imagemId) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.imagemId = imagemId;
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
    public void setImagemId(int imagemId){
        this.imagemId = imagemId;
    }

}
