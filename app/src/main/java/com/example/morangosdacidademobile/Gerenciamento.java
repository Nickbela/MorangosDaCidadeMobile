package com.example.morangosdacidademobile;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.morangosdacidademobile.Services.ClienteApiService;

import RegrasDeNegocio.Entity.Cliente;

public class Gerenciamento extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciamento);

        // Obtendo as referências dos campos
        EditText Nome = findViewById(R.id.Nome);
        EditText Email = findViewById(R.id.Email);
        EditText Telefone = findViewById(R.id.Telefone);
        EditText CPF = findViewById(R.id.CPF);
        EditText CEP = findViewById(R.id.CEP);
        EditText Rua = findViewById(R.id.Rua);
        EditText Numero = findViewById(R.id.Numero);
        EditText Cidade = findViewById(R.id.Cidade);
        EditText Estado = findViewById(R.id.Estado);
        EditText Senha = findViewById(R.id.Senha);

        // Botão único para salvar
        Button btnSalvar = findViewById(R.id.btn_Salvar);

        // Recuperar o email do cliente armazenado
        SharedPreferences sharedPreferences = getSharedPreferences("ClientePreferences", Context.MODE_PRIVATE);
        String emailCliente = sharedPreferences.getString("emailCliente", null);

        // Carregar dados do cliente usando API
        new Thread(() -> {
            try {
                if (emailCliente != null) {
                    // Recuperar os dados do cliente pela API
                    Cliente cliente = ClienteApiService.getClienteDataByEmail(emailCliente);

                    // Atualizar os campos na UI (necessário usar runOnUiThread)
                    runOnUiThread(() -> {
                        Nome.setText(cliente.getNome());
                        Email.setText(cliente.getEmail());
                        Telefone.setText(cliente.getTelefone());
                        CPF.setText(cliente.getCpf());
                        CEP.setText(cliente.getCep());
                        Rua.setText(cliente.getRua());
                        Numero.setText(String.valueOf(cliente.getNumero()));
                        Cidade.setText(cliente.getCidade());
                        Estado.setText(cliente.getEstado());
                        Senha.setText(cliente.getSenha());
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Erro ao carregar dados", Toast.LENGTH_SHORT).show());
            }
        }).start();

        // Salvar dados pessoais e endereço
        btnSalvar.setOnClickListener(v -> {
            new Thread(() -> {
                try {
                    Cliente clienteAtualizado = new Cliente();
                    clienteAtualizado.setNome(Nome.getText().toString());
                    clienteAtualizado.setEmail(Email.getText().toString());
                    clienteAtualizado.setTelefone(Telefone.getText().toString());
                    clienteAtualizado.setCpf(CPF.getText().toString());
                    clienteAtualizado.setCep(CEP.getText().toString());
                    clienteAtualizado.setRua(Rua.getText().toString());
                    clienteAtualizado.setNumero(Integer.parseInt(Numero.getText().toString()));
                    clienteAtualizado.setCidade(Cidade.getText().toString());
                    clienteAtualizado.setEstado(Estado.getText().toString());
                    clienteAtualizado.setSenha(Senha.getText().toString());

                    // Atualiza os dados do cliente pela API
                    String response = ClienteApiService.updateCliente(emailCliente, clienteAtualizado);

                    runOnUiThread(() -> Toast.makeText(this, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show());

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(this, "Erro ao salvar dados", Toast.LENGTH_SHORT).show());
                }
            }).start();
        });
    }
}
