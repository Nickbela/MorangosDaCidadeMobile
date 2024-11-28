package com.example.morangosdacidademobile;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Pagamento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);

        // Conectar os elementos da interface
        EditText inputNomeTitular = findViewById(R.id.input_NomeTitular);
        EditText inputNumeroCartao = findViewById(R.id.input_NumeroCartao);
        EditText inputMes = findViewById(R.id.Mes);
        EditText inputAno = findViewById(R.id.Ano);
        EditText inputCvc = findViewById(R.id.cvc);
        Button btnPagamento = findViewById(R.id.btn_pagamento);

        // Ação do botão "Finalizar Pagamento"
        btnPagamento.setOnClickListener(view -> {
            // Obter os dados inseridos pelo usuário
            String nomeTitular = inputNomeTitular.getText().toString().trim();
            String numeroCartao = inputNumeroCartao.getText().toString().trim();
            String mesValidade = inputMes.getText().toString().trim();
            String anoValidade = inputAno.getText().toString().trim();
            String cvc = inputCvc.getText().toString().trim();

            // Validações dos campos
            if (TextUtils.isEmpty(nomeTitular)) {
                inputNomeTitular.setError("Por favor, insira o nome do titular.");
                return;
            }
            if (TextUtils.isEmpty(numeroCartao) || numeroCartao.length() != 16) {
                inputNumeroCartao.setError("Por favor, insira um número de cartão válido (16 dígitos).");
                return;
            }
            if (TextUtils.isEmpty(mesValidade) || mesValidade.length() != 2) {
                inputMes.setError("Por favor, insira um mês válido (2 dígitos).");
                return;
            }
            if (TextUtils.isEmpty(anoValidade) || anoValidade.length() != 4) {
                inputAno.setError("Por favor, insira um ano válido (4 dígitos).");
                return;
            }
            if (TextUtils.isEmpty(cvc) || cvc.length() != 3) {
                inputCvc.setError("Por favor, insira um CVC válido (3 dígitos).");
                return;
            }

            // Armazenar os dados temporariamente
            String dadosPagamento = "Nome: " + nomeTitular + "\n" +
                    "Número do Cartão: " + numeroCartao + "\n" +
                    "Validade: " + mesValidade + "/" + anoValidade + "\n" +
                    "CVC: " + cvc;

            // Simular finalização do pagamento
            Toast.makeText(Pagamento.this, "Pagamento realizad." +
                    "o com sucesso!", Toast.LENGTH_LONG).show();

            // Exibir os dados para fins de depuração (pode ser removido em produção)
            System.out.println("Dados do pagamento:\n" + dadosPagamento);


            //  Direcionar para a tela de sucesso
            Intent intent = new Intent(Pagamento.this, Sucesso.class);
            startActivity(intent);
               finish();startActivity(new Intent(this, Sucesso.class));
        });
    }
}
