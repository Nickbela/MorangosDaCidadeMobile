package com.example.morangosdacidademobile;

import static RegrasDeNegocio.Métodos.CadastroLogin.isEmailValido;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.morangosdacidademobile.Services.ClienteApiService;

import RegrasDeNegocio.Entity.Cliente;
import RegrasDeNegocio.Métodos.CadastroLogin;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText input_email = findViewById(R.id.editTextEmail);
        EditText input_senha = findViewById(R.id.editTextPassword);
        Button btn_login = findViewById(R.id.btn_login);
        TextView textViewSignUp = findViewById(R.id.btn_cadastre);

        // Funcionalidades do botão de login
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarLogin(input_email, input_senha);
            }
        });

        // Configura o clique no texto "Cadastre-se aqui"
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redireciona para a tela de cadastro
                Intent intent = new Intent(Login.this, Cadastro.class);
                startActivity(intent);
            }
        });
    }

    // Método para realizar o login
    private void realizarLogin(EditText input_email, EditText input_senha) {
        String email = input_email.getText().toString().trim();
        String senha = input_senha.getText().toString().trim();

        // Validação de formato de e-mail
        if (isEmailValido(email)) {
            input_email.setError(null); // Limpa erros se o formato do e-mail for válido
        } else {
            input_email.setError("Formato de e-mail inválido!");
            return; // Interrompe a execução se o e-mail for inválido
        }

        // Chama o método de login no backend via API (Classe CadastroLogin)
        try {
            Cliente cliente = ClienteApiService.getClienteByLogin(email, senha); // Chama a API para autenticar o cliente

            if (cliente != null) {
                // Login bem-sucedido, prossiga para a próxima tela
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Login falhou, exiba uma mensagem de erro
                Toast.makeText(Login.this, "Identificador (e-mail) ou senha inválidos.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            // Caso haja um erro na requisição à API
            e.printStackTrace();
            Toast.makeText(Login.this, "Erro ao conectar com a API. Tente novamente.", Toast.LENGTH_SHORT).show();
        }
    }

}
