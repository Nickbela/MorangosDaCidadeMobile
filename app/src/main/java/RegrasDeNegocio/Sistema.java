package RegrasDeNegocio;

import java.util.ArrayList;
import java.util.List;

public class Sistema {
    private List<Cliente> clientes;

    public Sistema() {
        this.clientes = new ArrayList<>();
    }

    public void cadastrarCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }

    public Cliente login(String identificador, String senha) {
        for (Cliente cliente : clientes) {
            // Verifica se o identificador é o CPF ou o email
            if ((cliente.getCpf().equals(identificador) || cliente.getEmail().equals(identificador))
                    && cliente.getSenha().equals(senha)) {
                System.out.println("Login realizado com sucesso! Bem-vindo, " + cliente.getNome() + "!");
                return cliente;
            }
        }
        System.out.println("Identificador (CPF ou email) ou senha inválidos.");
        return null;
    }
}

