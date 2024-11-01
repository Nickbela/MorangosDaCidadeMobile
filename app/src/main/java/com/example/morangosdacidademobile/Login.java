package com.example.morangosdacidademobile;

import static RegrasDeNegocio.Métodos.CadastroLogin.isEmailValido;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.morangosdacidademobile.R.id;
import RegrasDeNegocio.Entity.Cliente;
import RegrasDeNegocio.Métodos.CadastroLogin;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText input_email = findViewById(R.id.input_email);
        EditText input_senha = findViewById(R.id.input_senha);
        Button btn_login = findViewById(R.id.btn_login);
        Button btn_cadastro = findViewById(R.id.btn_cadastro);

        // Funcionalidades
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarLogin(input_email, input_senha);
            }
        });

        // Configure o botão de cadastro, se necessário
        btn_cadastro.setOnClickListener(v -> {
            // Implementação para abrir a tela de cadastro
        });
    }

    // Método para realizar o login
    private void realizarLogin(EditText input_email, EditText input_senha) {
        String email = input_email.getText().toString().trim();
        String senha = input_senha.getText().toString().trim();

        // Validação de formato de e-mail (opcional, apenas para feedback visual)
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
