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
import com.example.morangosdacidademobile.R.id;
import RegrasDeNegocio.Entity.Cliente;
import RegrasDeNegocio.Métodos.CadastroLogin;


public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        EditText input_email = findViewById(R.id.input_email);
        EditText input_senha = findViewById(R.id.input_senha);
        Button btn_login = findViewById(R.id.btn_login);
        Button btn_cadastro = findViewById(R.id.btn_cadastro);

        //Funcionalidades
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        // Captura os valores digitados nos campos.
                String email = input_email.getText().toString();
                String senha = input_senha.getText().toString();
                String cpf = input_email.getText().toString();


                private void realizarLogin() {
                    String email = input_email.getText().toString().trim();
                    String senha = input_senha.getText().toString().trim();

                    // Validação de formato de e-mail (opcional, apenas para feedback visual)
                    if (isEmailValido(email)) {
                        // Mostrar feedback de e-mail válido (opcional)
                        input_email.setError(null); // Limpa erros se o formato do e-mail é válido
                    } else {
                        // Feedback de erro se o identificador for inserido como um e-mail incorreto
                        input_email.setError("Formato de e-mail inválido!");
                    }

                    // Chama o método de login do backend (Classe Sistema)
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
        });

}}
