package com.example.morangosdacidademobile;

import static RegrasDeNegocio.Métodos.CadastroLogin.isEmailValido;

import android.content.Intent;
import android.os.Bundle;
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

        EditText input_nome = findViewById(R.id.etNome);
        EditText input_email = findViewById(R.id.etEmail);
        EditText input_senha = findViewById(R.id.etSenha);
        EditText input_cpf = findViewById(R.id.etCpf);
        Button btn_salvar = findViewById(R.id.btnCadastrar);
        EditText input_telefone = findViewById(R.id.etTelefone);
        EditText input_rua = findViewById(R.id.etRua);
        EditText input_cidade = findViewById(R.id.etCidade);
        EditText input_estado = findViewById(R.id.etEstado);
        EditText input_cep = findViewById(R.id.etCEP);
        EditText input_numero = findViewById(R.id.etNumero);

        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = input_nome.getText().toString();
                String email = input_email.getText().toString();
                String cpf = input_cpf.getText().toString();
                String senha = input_senha.getText().toString();
                String telefone = input_telefone.getText().toString();
                String rua = input_rua.getText().toString();
                String cidade = input_cidade.getText().toString();
                String estado = input_estado.getText().toString();
                String cep = input_cep.getText().toString();
                int numero = Integer.parseInt(input_numero.getText().toString());


                // Validar o email
                if (isEmailValido(email)) {
                    input_email.setError("Email inválido");
                } else {
                    // Cria um novo cliente
                    Cliente novoCliente = new Cliente(
                            nome,            // Nome do cliente
                            cpf,             // CPF do cliente
                            email,           // Email do cliente
                            telefone,        // Telefone do cliente
                            senha,           // Senha do cliente
                            rua,             // Rua do cliente
                            numero,          // Número da casa ou apartamento
                            cidade,          // Cidade do cliente
                            estado,          // Estado do cliente
                            cep              // CEP do cliente
                    );

// Cadastra o cliente através do método da API
                    try {
                        String resposta = ClienteApiService.addCliente(novoCliente); // Chama o método para adicionar o cliente
                        if (resposta.contains("sucesso")) {  // Verifica se a resposta da API é um sucesso
                            Toast.makeText(Cadastro.this, "Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Cadastro.this, "Falha ao cadastrar cliente. Tente novamente.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        // Tratamento de exceções caso ocorra um erro na requisição
                        e.printStackTrace();
                        Toast.makeText(Cadastro.this, "Erro ao conectar com a API. Tente novamente.", Toast.LENGTH_SHORT).show();
                    }

                    // Opcional: Navegar para outra tela após o cadastro
                    Intent intent = new Intent(Cadastro.this, Homepage.class);
                    startActivity(intent);
                    finish(); // Opcional: encerra a tela de cadastro
                }
            }
        });


    }
}