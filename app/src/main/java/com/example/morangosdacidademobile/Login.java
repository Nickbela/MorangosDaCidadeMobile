package com.example.morangosdacidademobile;

import static RegrasDeNegocio.Métodos.CadastroLogin.isEmailValido;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.morangosdacidademobile.Services.ClienteApiService;

import RegrasDeNegocio.Entity.Cliente;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText input_email = findViewById(R.id.email);
        EditText input_senha = findViewById(R.id.senha);
        Button btn_login = findViewById(R.id.btn_entrar);
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
        btn_login.setOnClickListener(v -> realizarLogin(input_email, input_senha));
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

        if (!isEmailValido(email)) {
            input_email.setError("Formato de e-mail inválido!");
            return;
        }

        try {
            Cliente cliente = ClienteApiService.getClienteByLogin(email, senha);

            if (cliente != null) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(Login.this, "Identificador (e-mail) ou senha inválidos.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Login.this, "Erro ao conectar com a API. Tente novamente.", Toast.LENGTH_SHORT).show();
        }
    }
}
