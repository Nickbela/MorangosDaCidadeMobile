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

import RegrasDeNegocio.Entity.Cliente;
import RegrasDeNegocio.Entity.Endereco;
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

        EditText input_nome = findViewById(R.id.input_nome);
        EditText input_email = findViewById(R.id.input_email);
        EditText input_senha = findViewById(R.id.input_senha);
        EditText input_cpf = findViewById(R.id.input_cpf);
        Button btn_salvar = findViewById(R.id.btn_cadastrar);
        EditText input_telefone = findViewById(R.id.input_telefone);
        EditText input_rua = findViewById(R.id.input_rua);
        EditText input_cidade = findViewById(R.id.input_cidade);
        EditText input_estado = findViewById(R.id.input_estado);
        EditText input_cep = findViewById(R.id.input_cep);
        EditText input_numero = findViewById(R.id.input_numero);

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
                String numero = input_numero.getText().toString();

                Endereco endereco = new Endereco(rua, cidade, estado, cep, numero);

                // Validar o email
                if (isEmailValido(email)) {
                    input_email.setError("Email inválido");
                } else {
                    // Cria um novo cliente
                    Cliente novoCliente = new Cliente( nome, cpf, senha, endereco, telefone);

                    // Cadastra o cliente
                    CadastroLogin.cadastrarCliente(novoCliente);
                    Toast.makeText(Cadastro.this, "Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                    // Opcional: Navegar para outra tela após o cadastro
                    Intent intent = new Intent(Cadastro.this, Homepage.class);
                    startActivity(intent);
                    finish(); // Opcional: encerra a tela de cadastro
                }
            }
        });


    }
}