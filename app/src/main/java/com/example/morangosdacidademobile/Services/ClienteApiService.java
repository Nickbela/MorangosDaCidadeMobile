package com.example.morangosdacidademobile.Services;

import org.json.JSONObject;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import RegrasDeNegocio.Entity.Cliente;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class ClienteApiService {

    private static final String BASE_URL = "http://10.0.2.2:8085/api/clientes";
    //private static final String BASE_URL = "http://localhost:8085/api/clientes";

    public static Cliente getClienteByLogin(String login, String senha) throws Exception {
        URL url = new URL(BASE_URL + "/listar?login=" + login + "&senha=" + senha); // Cria a URL com parâmetros de login e senha.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Abre uma conexão HTTP.
        conn.setRequestMethod("GET"); // Define o método da requisição como GET.
        conn.setRequestProperty("Content-Type", "application/json"); // Define o tipo de conteúdo como JSON.

        // Buffer para ler a resposta da API.
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) { // Lê cada linha da resposta.
            result.append(line);
        }
        reader.close();

        // Converte a resposta para um objeto JSON e cria um cliente.
        JSONObject jsonObject = new JSONObject(result.toString()); // Converte a resposta JSON.
        Cliente cliente = new Cliente();
        cliente.setNome(jsonObject.getString("nome"));
        cliente.setCpf(jsonObject.getString("cpf"));
        cliente.setEmail(jsonObject.getString("email"));
        cliente.setTelefone(jsonObject.getString("telefone"));
        cliente.setSenha(jsonObject.getString("senha"));
        cliente.setRua(jsonObject.getString("rua"));
        cliente.setNumero(jsonObject.getInt("numero"));
        cliente.setCidade(jsonObject.getString("cidade"));
        cliente.setEstado(jsonObject.getString("estado"));
        cliente.setCep(jsonObject.getString("cep"));

        return cliente; // Retorna o cliente.
    }

    public static String addCliente(Cliente cliente) throws Exception {
        URL url = new URL(BASE_URL + "/adicionar"); // Cria a URL completa para a operação de adicionar.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Abre uma conexão HTTP.
        conn.setRequestMethod("POST"); // Define o método da requisição como POST.
        conn.setRequestProperty("Content-Type", "application/json"); // Define o tipo de conteúdo como JSON.
        conn.setDoOutput(true); // Permite enviar dados na requisição.

        // Cria um objeto JSON com os dados do cliente.
        JSONObject jsonCliente = new JSONObject();
        jsonCliente.put("nome", cliente.getNome()); // Adiciona o nome ao objeto JSON.
        jsonCliente.put("email", cliente.getEmail()); // Adiciona o email ao objeto JSON.
        jsonCliente.put("cpf", cliente.getCpf()); // Adiciona o CPF ao objeto JSON.
        jsonCliente.put("telefone", cliente.getTelefone()); // Adiciona o telefone ao objeto JSON.
        jsonCliente.put("senha", cliente.getSenha()); // Adiciona a senha ao objeto JSON.
        jsonCliente.put("rua", cliente.getRua()); // Adiciona a rua ao objeto JSON.
        jsonCliente.put("numero", cliente.getNumero()); // Adiciona o número ao objeto JSON.
        jsonCliente.put("cidade", cliente.getCidade()); // Adiciona a cidade ao objeto JSON.
        jsonCliente.put("estado", cliente.getEstado()); // Adiciona o estado ao objeto JSON.
        jsonCliente.put("cep", cliente.getCep()); // Adiciona o CEP ao objeto JSON.

        // Envia o JSON com os dados do cliente.
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        writer.write(jsonCliente.toString()); // Escreve o JSON no corpo da requisição.
        writer.flush();
        writer.close();

        // Lê a resposta da API.
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) { // Lê cada linha da resposta.
            result.append(line);
        }
        reader.close();
        return result.toString(); // Retorna a resposta da API.
    }

    public static String updateCliente(long id, Cliente cliente) throws Exception {
        URL url = new URL(BASE_URL + "/atualizar/" + id); // Cria a URL completa para a operação de atualização.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Abre uma conexão HTTP.
        conn.setRequestMethod("PUT"); // Define o método da requisição como PUT.
        conn.setRequestProperty("Content-Type", "application/json"); // Define o tipo de conteúdo como JSON.
        conn.setDoOutput(true); // Permite enviar dados na requisição.

        // Cria um objeto JSON com os dados atualizados do cliente.
        JSONObject jsonCliente = new JSONObject();
        jsonCliente.put("nome", cliente.getNome()); // Adiciona o nome atualizado ao objeto JSON.
        jsonCliente.put("email", cliente.getEmail()); // Adiciona o email atualizado ao objeto JSON.
        jsonCliente.put("cpf", cliente.getCpf()); // Adiciona o CPF atualizado ao objeto JSON.
        jsonCliente.put("telefone", cliente.getTelefone()); // Adiciona o telefone atualizado ao objeto JSON.
        jsonCliente.put("senha", cliente.getSenha()); // Adiciona a senha ao objeto JSON.
        jsonCliente.put("rua", cliente.getRua()); // Adiciona a rua ao objeto JSON.
        jsonCliente.put("numero", cliente.getNumero()); // Adiciona o número ao objeto JSON.
        jsonCliente.put("cidade", cliente.getCidade()); // Adiciona a cidade ao objeto JSON.
        jsonCliente.put("estado", cliente.getEstado()); // Adiciona o estado ao objeto JSON.
        jsonCliente.put("cep", cliente.getCep()); // Adiciona o CEP ao objeto JSON.

        // Envia o JSON com os dados atualizados do cliente.
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        writer.write(jsonCliente.toString()); // Escreve o JSON no corpo da requisição.
        writer.flush();
        writer.close();

        // Lê a resposta da API.
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) { // Lê cada linha da resposta.
            result.append(line);
        }
        reader.close();
        return result.toString(); // Retorna a resposta da API.
    }

    public static String deleteCliente(long id) throws Exception {
        URL url = new URL(BASE_URL + "/deletar/" + id); // Cria a URL completa para a operação de exclusão.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Abre uma conexão HTTP.
        conn.setRequestMethod("DELETE"); // Define o método da requisição como DELETE.
        conn.setRequestProperty("Content-Type", "application/json"); // Define o tipo de conteúdo como JSON.

        // Lê a resposta da API.
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) { // Lê cada linha da resposta.
            result.append(line);
        }
        reader.close();
        return result.toString(); // Retorna a resposta da API.
    }

}
