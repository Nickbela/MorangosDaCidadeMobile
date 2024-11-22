package com.example.morangosdacidademobile;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.morangosdacidademobile.databinding.ActivityConfirmacaoBinding;

public class Confirmacao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao);

        // Referências aos componentes do layout
        TextView tvNomeCliente = findViewById(R.id.tvNomeCliente);
        TextView tvDetalhesPedido = findViewById(R.id.tvDetalhesPedido);
        TextView tvTotal = findViewById(R.id.tvTotal);

        // Dados do pedido (normalmente vindos de outra Activity ou banco de dados)
        String nomeCliente = getIntent().getStringExtra("nomeCliente");
        String detalhesPedido = getIntent().getStringExtra("detalhesPedido");
        String total = getIntent().getStringExtra("total");

        // Atualizando os textos
        tvNomeCliente.setText("Nome do Cliente: " + nomeCliente);
        tvDetalhesPedido.setText("Detalhes do Pedido: " + detalhesPedido);
        tvTotal.setText("Total: " + total);
    }

}