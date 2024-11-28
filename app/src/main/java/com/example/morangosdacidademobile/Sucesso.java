package com.example.morangosdacidademobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.morangosdacidademobile.ui.home.HomeFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Sucesso extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucesso);

        // Referências aos elementos da interface
        TextView NumPedido = findViewById(R.id.NumPedido);
        TextView TotalPedido = findViewById(R.id.TotalPedido);
        TextView DataCompra = findViewById(R.id.DataCompra);
        Button btnVoltarHome = findViewById(R.id.btnReturnHome);


        // Gera número de pedido aleatório
        int numeroPedido = new Random().nextInt(999999) + 100000; // Gera um número entre 100000 e 999999
        String numeroPedidoTexto = "Pedido #" + numeroPedido;

        // Obtém a data atual
        String dataAtual = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        // Recebe o total da compra da tela anterior
        Intent intent = getIntent();
        String totalCompra = intent.getStringExtra("TOTAL_COMPRA");

        // Configura os textos
        NumPedido.setText(numeroPedidoTexto);
        TotalPedido.setText(getString(R.string.total_compra_, totalCompra));
        DataCompra.setText(getString(R.string.data_compra, dataAtual));

        // Voltar à tela inicial ao clicar no botão
        btnVoltarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(Sucesso.this, HomeFragment.class);
                startActivity(homeIntent);
                finish(); // Finaliza a Activity para não voltar ao sucesso
            }
        });
    }
}
