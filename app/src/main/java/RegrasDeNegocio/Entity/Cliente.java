package RegrasDeNegocio.Entity;

import java.io.Serializable;

public class Cliente {
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String rua;
    private String cidade;
    private String estado;
    private String cep;
    private int numero;
    private String telefone;

    public Cliente(String nome, String cpf, String senha, String rua, String cidade, String estado, String cep, int numero, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.rua = rua;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.numero = numero;
        this.telefone = telefone;
    }

    public Cliente() {

    }

    public Cliente(String nome, String cpf, String email, String telefone, String senha, String rua, int numero, String cidade, String estado, String cep) {
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}

