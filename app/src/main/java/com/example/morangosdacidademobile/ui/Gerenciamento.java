package com.example.morangosdacidademobile.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.morangosdacidademobile.R;
import com.example.morangosdacidademobile.Services.ClienteApiService;
import com.example.morangosdacidademobile.ui.Gerenciamento;

import RegrasDeNegocio.Entity.Cliente;

import androidx.fragment.app.Fragment;

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

        Button btnSalvarDados = findViewById(R.id.btn_SalvarDadosPessoais);
        Button btnSalvarEndereco = findViewById(R.id.btnSalvarEndereco);

        // Carregar dados do cliente usando API
        new Thread(() -> {
            try {
                String login = "usuario_login"; // Substituir pelo login real
                String senha = "usuario_senha"; // Substituir pela senha real

                Cliente cliente = ClienteApiService.getClienteByLogin(login, senha);

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
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(this, "Erro ao carregar dados", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();

        // Salvar dados pessoais
        btnSalvarDados.setOnClickListener(v -> {
            new Thread(() -> {
                try {
                    Cliente clienteAtualizado = new Cliente();
                    clienteAtualizado.setNome(Nome.getText().toString());
                    clienteAtualizado.setEmail(Email.getText().toString());
                    clienteAtualizado.setTelefone(Telefone.getText().toString());
                    clienteAtualizado.setCpf(CPF.getText().toString());

                    String response = ClienteApiService.updateCliente(clienteAtualizado.getCpf(), clienteAtualizado);
                    runOnUiThread(() ->
                            Toast.makeText(this, "Dados pessoais salvos com sucesso!", Toast.LENGTH_SHORT).show()
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() ->
                            Toast.makeText(this, "Erro ao salvar dados pessoais", Toast.LENGTH_SHORT).show()
                    );
                }
            }).start();
        });

        // Salvar endereço
        btnSalvarEndereco.setOnClickListener(v -> {
            new Thread(() -> {
                try {
                    Cliente clienteAtualizado = new Cliente();
                    clienteAtualizado.setCep(CEP.getText().toString());
                    clienteAtualizado.setRua(Rua.getText().toString());
                    clienteAtualizado.setNumero(Integer.parseInt(Numero.getText().toString()));
                    clienteAtualizado.setCidade(Cidade.getText().toString());
                    clienteAtualizado.setEstado(Estado.getText().toString());

                    String response = ClienteApiService.updateCliente(clienteAtualizado.getCpf(), clienteAtualizado);
                    runOnUiThread(() ->
                            Toast.makeText(this, "Endereço salvo com sucesso!", Toast.LENGTH_SHORT).show()
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() ->
                            Toast.makeText(this, "Erro ao salvar endereço", Toast.LENGTH_SHORT).show()
                    );
                }
            }).start();
        });
    }


}
