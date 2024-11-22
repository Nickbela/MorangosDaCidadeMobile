package com.example.morangosdacidademobile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.example.morangosdacidademobile.adapters.CarrinhoAdapter;
import RegrasDeNegocio.Entity.ProdutoEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Produto extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CarrinhoAdapter adapter;
    private List<ProdutoEntity> listaProdutos; // Lista de produtos completa
    private List<ProdutoEntity> listaFiltrada;
    private List<ProdutoEntity> carrinho;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        // Inicializando a lista de produtos
        listaProdutos = Arrays.asList(
                new ProdutoEntity("Morango Albion", 150.00, 10,R.mipmap.ic_albion_foreground),
                new ProdutoEntity("Morango Capri", 185.00, 10,R.mipmap.ic_capri_foreground),
                new ProdutoEntity("Morango Diamante", 190.00,10, R.mipmap.ic_diamante_foreground),
                new ProdutoEntity("Morango Bourbon", 210.00, 10,R.mipmap.ic_bourbon_foreground)
        );

        carrinho = new ArrayList<>();

        listaFiltrada = new ArrayList<>(listaProdutos); // Inicializando a lista filtrada

        recyclerView = findViewById(R.id.recyclerViewProdutos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializando o adapter com a lista completa
        adapter = new CarrinhoAdapter(listaFiltrada);
        recyclerView.setAdapter(adapter);

        adapter = new CarrinhoAdapter(carrinho);
        recyclerView.setAdapter(adapter);

        for (ProdutoEntity produto : listaProdutos) {
            // Localiza o botão de adicionar ao carrinho
            Button btnAdicionar = findViewById(R.id.buttonComprarProduto);
            btnAdicionar.setOnClickListener(v -> {
                // Quando clicado, adiciona o produto ao carrinho
                adapter.adicionarItem(produto);
                Toast.makeText(Produto.this, "Produto adicionado ao carrinho", Toast.LENGTH_SHORT).show();
            });
        }

        // EditText de pesquisa
        EditText editTextSearch = findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Não é necessário fazer nada aqui
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Filtrar produtos conforme o texto
                filtrarProdutos(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Não é necessário fazer nada aqui
            }
        });
    }

    // Método para filtrar os produtos com base no nome
    private void filtrarProdutos(String query) {
        listaFiltrada.clear();  // Limpa a lista de resultados filtrados

        if (query.isEmpty()) {
            listaFiltrada.addAll(listaProdutos);  // Se a pesquisa estiver vazia, mostra todos os produtos
        } else {
            for (ProdutoEntity produto : listaProdutos) {
                if (produto.getNome().toLowerCase().contains(query.toLowerCase())) {
                    listaFiltrada.add(produto);  // Adiciona o produto à lista filtrada
                }
            }
        }

        // Atualiza o adapter com a lista filtrada
        adapter.notifyDataSetChanged();  // Notifica que a lista foi atualizada
    }
}