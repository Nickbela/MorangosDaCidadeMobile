package com.example.morangosdacidademobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.morangosdacidademobile.adapters.CarrinhoAdapter;
import RegrasDeNegocio.Entity.ProdutoEntity;

import java.util.ArrayList;
import java.util.List;

public class Carrinho extends AppCompatActivity {

    private RecyclerView recyclerViewCarrinho;
    private CarrinhoAdapter carrinhoAdapter;
    private List<ProdutoEntity> carrinho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho); // Supondo que você tenha um layout de carrinho

        // Inicializar a lista de produtos no carrinho
        carrinho = new ArrayList<>();

        // Configurar o RecyclerView do carrinho
        recyclerViewCarrinho = findViewById(R.id.RecyclerViewCarrinho);
        recyclerViewCarrinho.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar o adapter com a lista de carrinho
        carrinhoAdapter = new CarrinhoAdapter(carrinho);
        recyclerViewCarrinho.setAdapter(carrinhoAdapter);

        // Verifique se algum produto foi adicionado ao carrinho (essa parte depende de como você está gerenciando os dados)
        // Aqui, você pode adicionar produtos diretamente à lista do carrinho ou receber da `ProdutoActivity`
        // Exemplo para adicionar um produto manualmente:
        ProdutoEntity produtoExemplo = new ProdutoEntity("Morango Albion", 150.00, 10, R.mipmap.ic_albion_foreground);
        carrinho.add(produtoExemplo);
        carrinhoAdapter.notifyDataSetChanged();

        // Ou, se você está passando o carrinho entre atividades, use o Intent para obter os dados.

        // Recupera a lista de produtos do carrinho que foi passada
        carrinho = (List<ProdutoEntity>) getIntent().getSerializableExtra("carrinho");

        Button btnFinalizarCompra = findViewById(R.id.btnFinalizarCompra);
        btnFinalizarCompra.setOnClickListener(v -> {
            if (carrinho.isEmpty()) {
                Toast.makeText(this, "Seu carrinho está vazio!", Toast.LENGTH_SHORT).show();
            } else {
                // Navegar para a tela de simulação de pagamento
                Intent intent = new Intent(this, Confirmacao.class);
                startActivity(intent);
            }
        });

    }
}
