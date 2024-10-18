package RegrasDeNegocio;

public class Pedido {
    private Cliente cliente;

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }



    public void imprimirPedido() {
        System.out.println("Informações do Cliente: " + cliente.getNome());
        System.out.println("CPF: " + cliente.getCpf());
        System.out.println("Endereço: " + cliente.getEndereco().getRua() + ", "
                + cliente.getEndereco().getCidade() + ", "
                + cliente.getEndereco().getEstado() + " - "
                + cliente.getEndereco().getCep());
        System.out.println("Contatos:");
        for (Contato contato : cliente.getContatos()) {
            System.out.println("  " + contato.getTipo() + ": " + contato.getValor());
        }
    }
}
