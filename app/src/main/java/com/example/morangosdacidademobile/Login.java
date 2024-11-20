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

import RegrasDeNegocio.Métodos.CadastroLogin;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText input_email = findViewById(R.id.input_email);
        EditText input_senha = findViewById(R.id.input_senha);
        Button btn_login = findViewById(R.id.btn_login);
        TextView textViewSignUp = findViewById(R.id.textViewSignUp);

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
                Intent intent = new Intent(Login.this, CadastroActivity.class);
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
            input_email.setError(null); // Limpa erros se o formato do e-mail é válido
        } else {
            input_email.setError("Formato de e-mail inválido!");
            return; // Interrompe a execução se o e-mail é inválido
        }

        // Chama o método de login do backend (Classe CadastroLogin)
        if (CadastroLogin.login(email, senha)) {
            // Login bem-sucedido, prossiga para a próxima tela
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Login falhou, exiba uma mensagem de erro
            Toast.makeText(Login.this, "Identificador (CPF ou email) ou senha inválidos.", Toast.LENGTH_SHORT).show();
        }
    }
}
