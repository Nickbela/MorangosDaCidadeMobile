package com.example.morangosdacidademobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

import RegrasDeNegocio.Entity.ProdutoEntity;

public class Confirmacao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao);

        // Recuperando o nome do cliente do SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("ClientePreferences", MODE_PRIVATE);
        String nomeCliente = sharedPreferences.getString("nomeCliente", null); // Recupera o nome ou null se não encontrado

        if (nomeCliente != null) {
            // Usar o nome do cliente (exibindo na UI, por exemplo)
            TextView nomeClienteTextView = findViewById(R.id.NomeCliente);
            nomeClienteTextView.setText("Nome: " + nomeCliente);
        } else {
            // Se não encontrou o nome no SharedPreferences, você pode fazer uma nova requisição ou exibir uma mensagem de erro
            Log.e("ConfirmacaoActivity", "Nome do cliente não encontrado no SharedPreferences");
        }


        // Receber lista de itens do pedido
        List<ProdutoEntity> itensPedido = (List<ProdutoEntity>) getIntent().getSerializableExtra("itensPedido");

        // Receber total da compra
        double totalCompra = getIntent().getDoubleExtra("totalCompra", 0.0);

        // Exibir detalhes do pedido
        TextView tvDetalhesPedido = findViewById(R.id.DetalhesPedido);
        StringBuilder detalhesPedido = new StringBuilder();
        for (ProdutoEntity produto : itensPedido) {
            detalhesPedido.append(produto.getNome())
                    .append(" - ")
                    .append(produto.getQuantidade())
                    .append(" unidade(s)\n");
        }
        tvDetalhesPedido.setText(detalhesPedido.toString());

        // Exibir total
        TextView tvTotal = findViewById(R.id.Total);
        tvTotal.setText(String.format("Total: R$ %.2f", totalCompra));

        // Configurar botões
        Button btnConfirmarPedido = findViewById(R.id.btnConfirmarPedido);
        Button btnCancelarPedido = findViewById(R.id.btnCancelarPedido);

        // Ação ao confirmar pedido
        btnConfirmarPedido.setOnClickListener(v -> {
            // Ir para a tela de pagamento
            Intent intent = new Intent(this, Pagamento.class);
            intent.putExtra("nomeCliente", nomeCliente);
            intent.putExtra("itensPedido", (Serializable) itensPedido);
            intent.putExtra("totalCompra", totalCompra);
            startActivity(intent);
        });

        // Ação ao cancelar pedido
        btnCancelarPedido.setOnClickListener(v -> {
            // Voltar para o carrinho
            finish();
        });
    }
}
