package RegrasDeNegocio;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private boolean isEmailValido(String email) {
        // Expressão regular para validar emails comuns
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /*Necessário adicionar esse método no botão de cadastro no front de form separada:
    * sugestão:
    *
    * if (sistema.isEmailValido(email)) {
        sistema.cadastrarCliente(novoCliente);
        Toast.makeText(this, "Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
    } else {
        Toast.makeText(this, "Erro: Email inválido", Toast.LENGTH_SHORT).show();
    }
    *
    * mas claro no lugar dos toasts talvez colocar a mudança na caixa de texto a baixo do
    * campo de entrada de email para indicar erro.*/
}

