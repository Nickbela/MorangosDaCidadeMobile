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
        EditText etNome = findViewById(R.id.etNome);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etTelefone = findViewById(R.id.etTelefone);
        EditText etCPF = findViewById(R.id.etCPF);
        EditText etCEP = findViewById(R.id.etCEP);
        EditText etRua = findViewById(R.id.etRua);
        EditText etNumero = findViewById(R.id.etNumero);
        EditText etCidade = findViewById(R.id.etCidade);
        EditText etEstado = findViewById(R.id.etEstado);

        Button btnSalvarDados = findViewById(R.id.btnSalvarDadosPessoais);
        Button btnSalvarEndereco = findViewById(R.id.btnSalvarEndereco);

        // Carregar dados do cliente usando API
        new Thread(() -> {
            try {
                String login = "usuario_login"; // Substituir pelo login real
                String senha = "usuario_senha"; // Substituir pela senha real

                Cliente cliente = ClienteApiService.getClienteByLogin(login, senha);

                // Atualizar os campos na UI (necessário usar runOnUiThread)
                runOnUiThread(() -> {
                    etNome.setText(cliente.getNome());
                    etEmail.setText(cliente.getEmail());
                    etTelefone.setText(cliente.getTelefone());
                    etCPF.setText(cliente.getCpf());
                    etCEP.setText(cliente.getCep());
                    etRua.setText(cliente.getRua());
                    etNumero.setText(String.valueOf(cliente.getNumero()));
                    etCidade.setText(cliente.getCidade());
                    etEstado.setText(cliente.getEstado());
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
                    clienteAtualizado.setNome(etNome.getText().toString());
                    clienteAtualizado.setEmail(etEmail.getText().toString());
                    clienteAtualizado.setTelefone(etTelefone.getText().toString());
                    clienteAtualizado.setCpf(etCPF.getText().toString());

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
                    clienteAtualizado.setCep(etCEP.getText().toString());
                    clienteAtualizado.setRua(etRua.getText().toString());
                    clienteAtualizado.setNumero(Integer.parseInt(etNumero.getText().toString()));
                    clienteAtualizado.setCidade(etCidade.getText().toString());
                    clienteAtualizado.setEstado(etEstado.getText().toString());

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
