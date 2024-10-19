package RegrasDeNegocio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sistema {
    private List<Cliente> clientes;
    private Cliente clienteLogado;

    public Sistema() {
        this.clientes = new ArrayList<>();
    }

    public void cadastrarCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }

    public boolean login(String identificador, String senha) {
        for (Cliente cliente : clientes) {
            // Verifica se o identificador é o CPF ou o email
            if ((cliente.getCpf().equals(identificador) || cliente.getEmail().equals(identificador))
                    && cliente.getSenha().equals(senha)) {
                System.out.println("Login realizado com sucesso! Bem-vindo, " + cliente.getNome() + "!");
                clienteLogado = cliente; // Armazena o cliente logado
                return true; // Login bem-sucedido
            }
        }
        System.out.println("Identificador (CPF ou email) ou senha inválidos.");
        return false;
    }

    // Método para obter os dados do cliente logado
    public Cliente getClienteLogado() {
        return clienteLogado;
    }

    /*chamar o método para verificar se estar logado antes de fazer as chamadas de dados para a tela:
    *
    * Cliente cliente = sistema.getClienteLogado();
    *
    * isso ira fazer com que puxe do banco de dados o cliente correto para apresentação do dados.*/

    private boolean isEmailValido(String email) {
        // Expressão regular para validar emails comuns
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /*Necessário adicionar esse método no botão de cadastro no front de forma separada:
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

    public boolean editarCliente(String cpfOuEmail, String novoNome, String novoEmail, String novaSenha) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpfOuEmail) || cliente.getEmail().equals(cpfOuEmail)) {
                boolean dadosAtualizados = false;

                if (novoNome != null && !novoNome.isEmpty()) {
                    cliente.setNome(novoNome);
                    dadosAtualizados = true;
                }

                if (novoEmail != null && !novoEmail.isEmpty()) {
                    if (isEmailValido(novoEmail)) {
                        cliente.setEmail(novoEmail);
                        dadosAtualizados = true;
                    } else {
                        System.out.println("Erro: Email inválido. Atualização falhou.");
                        return false; // Retorna falso se o email é inválido
                    }
                }

                if (novaSenha != null && !novaSenha.isEmpty()) {
                    cliente.setSenha(novaSenha);
                    dadosAtualizados = true;
                }

                if (dadosAtualizados) {
                    System.out.println("Dados do cliente atualizados com sucesso!");
                    return true;
                } else {
                    System.out.println("Nenhum dado foi alterado.");
                    return false;
                }
            }
        }
        System.out.println("Erro: Cliente não encontrado.");
        return false;
    }

    /*
    * Observação para boa usabilidade configurar no front para ao entrar nessa área os dados
    * cadastrados anteriormente sejam apresentados.
    *
    * Sugestão de lógica para carregar os dados na tela:
    *
    * private void carregarDadosDoCliente(String cpfOuEmail) {
    // Aqui você deve buscar os dados do cliente e preencher os campos
    // Simulando com dados fictícios. Na prática, você buscaria os dados do cliente real.
    Cliente cliente = buscarClientePorCpfOuEmail(cpfOuEmail); // Método para buscar o cliente

        if (cliente != null) {
            editTextNome.setText(cliente.getNome());
            editTextEmail.setText(cliente.getEmail());
            editTextSenha.setText(cliente.getSenha());
        }
    }

    private Cliente buscarClientePorCpfOuEmail(String cpfOuEmail) {
        for (Cliente cliente : sistema.getClientes()) { // Presumindo que você tem um método para obter clientes
            if (cliente.getCpf().equals(cpfOuEmail) || cliente.getEmail().equals(cpfOuEmail)) {
                return cliente;
            }
        }
        return null; // Retorna null se não encontrar o cliente
    }
    *
    * Além disso para melhor segurança acho legal colocar uma verificação de identidade antes de realizar a edição
    * como por exemplo chamar o método de login novamente.
*/

    public boolean excluirCliente(String cpfOuEmail) {
        Iterator<Cliente> iterator = clientes.iterator();
        while (iterator.hasNext()) {
            Cliente cliente = iterator.next();
            if (cliente.getCpf().equals(cpfOuEmail) || cliente.getEmail().equals(cpfOuEmail)) {
                iterator.remove(); // Remove o cliente da lista
                System.out.println("Cliente excluído com sucesso!");
                return true; // Retorna verdadeiro se a exclusão for bem-sucedida
            }
        }
        System.out.println("Erro: Cliente não encontrado.");
        return false; // Retorna falso se o cliente não for encontrado
    }
    public boolean removerTelefoneDoClienteLogado(String telefone) {
        if (clienteLogado != null) {
            return clienteLogado.removerTelefone(telefone);
        }
        return false; // Retorna false se não houver cliente logado
    }
}

