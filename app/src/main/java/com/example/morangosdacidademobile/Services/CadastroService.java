package com.example.morangosdacidademobile.Services;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import RegrasDeNegocio.Entity.Cliente;

public class CadastroService {

    public static String cadastrarCliente(Cliente cliente) throws Exception {
        URL url = new URL("http://192.168.0.105:8085/api/clientes/adicionar");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

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

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        writer.write(jsonCliente.toString());
        writer.flush();
        writer.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();
        return result.toString();
    }
}
