package com.example.morangosdacidademobile;

import static RegrasDeNegocio.Métodos.CadastroLogin.isEmailValido;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.morangosdacidademobile.Services.ClienteApiService;

import RegrasDeNegocio.Entity.Cliente;
import RegrasDeNegocio.Métodos.CadastroLogin;

public class Cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText input_nome = findViewById(R.id.nome);
        EditText input_email = findViewById(R.id.email);
        EditText input_senha = findViewById(R.id.senha);
        EditText input_cpf = findViewById(R.id.cpf);
        Button btn_salvar = findViewById(R.id.btn_registrar);
        EditText input_telefone = findViewById(R.id.telefone);
        EditText input_rua = findViewById(R.id.rua);
        EditText input_cidade = findViewById(R.id.cidade);
        EditText input_estado = findViewById(R.id.estado);
        EditText input_cep = findViewById(R.id.cep);
        EditText input_numero = findViewById(R.id.numero_rua);

        btn_salvar.setOnClickListener(v -> {
            String nome = input_nome.getText().toString();
            String email = input_email.getText().toString();
            String cpf = input_cpf.getText().toString();
            String senha = input_senha.getText().toString();
            String telefone = input_telefone.getText().toString();
            String rua = input_rua.getText().toString();
            String cidade = input_cidade.getText().toString();
            String estado = input_estado.getText().toString();
            String cep = input_cep.getText().toString();
            int numero;

            // Verifica o campo de número
            try {
                numero = Integer.parseInt(input_numero.getText().toString());
            } catch (NumberFormatException e) {
                input_numero.setError("Número inválido");
                input_numero.requestFocus();
                return;
            }

            Log.d("Cadastro", "Nome: " + nome);
            Log.d("Cadastro", "Email: " + email);
            Log.d("Cadastro", "CPF: " + cpf);
            Log.d("Cadastro", "Telefone: " + telefone);
            Log.d("Cadastro", "Rua: " + rua);
            Log.d("Cadastro", "Cidade: " + cidade);
            Log.d("Cadastro", "Estado: " + estado);
            Log.d("Cadastro", "CEP: " + cep);
            Log.d("Cadastro", "Número: " + numero);

            // Validações dos campos
            if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || senha.isEmpty() || telefone.isEmpty()
                    || rua.isEmpty() || cidade.isEmpty() || estado.isEmpty() || cep.isEmpty()) {
                Toast.makeText(this, "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isEmailValido(email)) {
                input_email.setError("Email inválido");
                input_email.requestFocus();
                return;
            }

            Log.d("Cadastro", "Nome: " + nome + ", Email: " + email + ", CPF: " + cpf);


            // Cria o cliente
            Cliente novoCliente = new Cliente(
                    nome, cpf, email, telefone, senha, rua, numero, cidade, estado, cep
            );

            Log.d("Cadastro", "Cliente criado: " + novoCliente.toString());

            // Thread para chamada à API
            new Thread(() -> {
                try {
                    String response = ClienteApiService.addCliente(novoCliente);

                    Log.d("Cadastro", "Resposta da API: " + response);


                    // Toast de sucesso na UI
                    runOnUiThread(() -> {
                        Toast.makeText(Cadastro.this, response, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Cadastro.this, Login.class);
                        startActivity(intent);
                        finish();
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    // Toast de erro na UI
                    runOnUiThread(() -> Toast.makeText(Cadastro.this, "Erro ao adicionar cliente", Toast.LENGTH_SHORT).show());
                }
            }).start();
        });



                }
            }


