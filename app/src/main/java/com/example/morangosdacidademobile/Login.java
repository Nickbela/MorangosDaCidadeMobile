package com.example.morangosdacidademobile;

import static android.content.ContentValues.TAG;
import static RegrasDeNegocio.Métodos.CadastroLogin.isEmailValido;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.morangosdacidademobile.Services.ClienteApiService;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import RegrasDeNegocio.Entity.Cliente;

public class Login extends AppCompatActivity {

    List<Cliente> cliente = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Log.d(TAG, "Verificando configurações de rede...");
        new Thread(() -> {
            try {
                URL url = new URL("http://192.168.228.16:8085/api/clientes/login"); // URL do endpoint da API.
                HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // Abre conexão HTTP.
                connection.setRequestMethod("GET"); // Define o método HTTP como GET.
                connection.connect(); // Conecta ao servidor.

                int responseCode = connection.getResponseCode(); // Código de resposta da conexão.
                if (responseCode == 200) {
                    Log.d(TAG, "Conexão HTTP com 192.168.228.16 bem-sucedida.");
                } else {
                    Log.e(TAG, "Conexão HTTP falhou. Código de resposta: " + responseCode);
                }
                connection.disconnect(); // Fecha a conexão.
            } catch (Exception e) {
                Log.e(TAG, "Erro ao verificar a conexão HTTP com 192.168.228.16: " + e.getMessage());
            }
        }).start();

        EditText input_email = findViewById(R.id.email);
        EditText input_senha = findViewById(R.id.senha);
        Button btn_entrar = findViewById(R.id.btn_entrar);
        TextView cadastrarTextView = findViewById(R.id.cadastre);

        // Configura o clique no texto "Cadastre-se aqui"
        String texto = "Cadastre-se aqui!";
        SpannableString spannable = new SpannableString(texto);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Navegar para a tela de cadastro
                Intent intent = new Intent(Login.this, Cadastro.class);
                startActivity(intent);
            }
        };

        // Define o trecho "Cadastre-se aqui" como clicável
        int start = texto.indexOf("Cadastre-se");
        int end = start + "Cadastre-se aqui".length();
        spannable.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        cadastrarTextView.setText(spannable);
        cadastrarTextView.setMovementMethod(LinkMovementMethod.getInstance());

        // Funcionalidade do botão de login
        btn_entrar.setOnClickListener(v -> realizarLogin(input_email, input_senha));
    }

    ClickableSpan clickableSpan = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            try {
                System.out.println("Clicou no texto para cadastro.");
                Intent intent = new Intent(Login.this, Cadastro.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(Login.this, "Erro ao abrir a tela de cadastro.", Toast.LENGTH_SHORT).show();
            }
        }
    };


    // Método para realizar o login
    private void realizarLogin(EditText input_email, EditText input_senha) {
        String email = input_email.getText().toString().trim();
        String senha = input_senha.getText().toString().trim();

        Log.d(TAG, "Email: " + email + ", Senha: " + senha);

        if (!isEmailValido(email)) {
            input_email.setError("Formato de e-mail inválido!");
            return;
        }

        new Thread(() -> {
            try {
                cliente = (List<Cliente>) ClienteApiService.getClienteByLogin(email, senha);

                Log.d(TAG, "Clientes retornados: " + cliente);

                if (cliente == null) {
                    // Login bem-sucedido, redirecionar para a próxima tela
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();  // Finaliza a tela de login
                } else {
                    // Login falhou, mostrar mensagem de erro
                    Toast.makeText(Login.this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();

                // Exibir Toast de erro ao conectar com a API
                runOnUiThread(() ->
                        Toast.makeText(Login.this, "Erro ao conectar com a API. Tente novamente.", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();



    }
}
