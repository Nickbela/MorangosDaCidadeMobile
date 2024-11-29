package RegrasDeNegocio.Entity;

import java.io.Serializable;

public class ProdutoEntity implements Serializable {
    private String nome;
    private double preco;
    private int quantidade;
    private int imagemId;
    private String id;

    public ProdutoEntity(String nome, double preco, int quantidade, int imagemId,String id) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.imagemId = imagemId;
        this.id=id;
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

    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
