package com.example.morangosdacidademobile.Services;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import RegrasDeNegocio.Entity.Cliente;
import java.io.BufferedReader;
import com.example.morangosdacidademobile.Services.CadastroService;

public class ClienteApiService {

    private static final String BASE_URL = "http://192.168.0.105:8085/api/clientes";
    //private static final String BASE_URL = "http://localhost:8085/api/clientes";

    public static Cliente getClienteByLogin(String email, String senha) throws Exception {
        // Verificar se os parâmetros não estão vazios
        if (email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("Login e senha não podem ser nulos ou vazios.");
        }

        // Criar a URL com parâmetros
        URL url = new URL(BASE_URL + "/login?login=" + email + "&senha=" + senha);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET"); // Define o método da requisição como GET.

        // Verificar o código de resposta
        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new Exception("Erro ao buscar cliente. Código HTTP: " + responseCode);
        }

        // Buffer para ler a resposta da API
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();

        // Converter a resposta para um objeto JSON e criar o cliente
        JSONObject jsonObject = new JSONObject(result.toString());

        // Verificar se os campos esperados existem na resposta JSON
        Cliente cliente = new Cliente(); // Requer um construtor vazio na classe Cliente.

        cliente.setNome(jsonObject.optString("nome", null));
        cliente.setCpf(jsonObject.optString("cpf", null));
        cliente.setEmail(jsonObject.optString("email", null));
        cliente.setTelefone(jsonObject.optString("telefone", null));
        cliente.setSenha(jsonObject.optString("senha", null));
        cliente.setRua(jsonObject.optString("rua", null));
        cliente.setNumero(jsonObject.optInt("numero", 0));
        cliente.setCidade(jsonObject.optString("cidade", null));
        cliente.setEstado(jsonObject.optString("estado", null));
        cliente.setCep(jsonObject.optString("cep", null));

        return cliente;
    }


    public static String addCliente(Cliente cliente) throws Exception {
        URL url = new URL(BASE_URL + "/adicionar");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true); // Permitir envio de dados

        // Criar JSON do cliente
        JSONObject jsonCliente = new JSONObject();
        jsonCliente.put("nome", cliente.getNome());
        jsonCliente.put("email", cliente.getEmail());
        jsonCliente.put("cpf", cliente.getCpf());
        jsonCliente.put("telefone", cliente.getTelefone());
        jsonCliente.put("senha", cliente.getSenha());
        jsonCliente.put("rua", cliente.getRua());
        jsonCliente.put("numero", cliente.getNumero());
        jsonCliente.put("cidade", cliente.getCidade());
        jsonCliente.put("estado", cliente.getEstado());
        jsonCliente.put("cep", cliente.getCep());

        // Log para depuração
        Log.d("JSON Enviado", jsonCliente.toString());

        // Enviar dados
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {
            writer.write(jsonCliente.toString());
            writer.flush();
        }

        // Ler resposta
        int responseCode = conn.getResponseCode();
        InputStream is = (responseCode >= 200 && responseCode < 300)
                ? conn.getInputStream()
                : conn.getErrorStream();

        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }

        // Verificar resposta
        if (responseCode >= 200 && responseCode < 300) {
            return result.toString();
        } else {
            throw new Exception("Erro na API: " + responseCode + " - " + result.toString());
        }
    }


    public static String updateCliente(CharSequence id, Cliente cliente) throws Exception {
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
