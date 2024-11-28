package com.example.morangosdacidademobile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.morangosdacidademobile.adapters.CarrinhoAdapter;
import RegrasDeNegocio.Entity.ProdutoEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Produto extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CarrinhoAdapter adapter;
    private List<ProdutoEntity> listaProdutos; // Lista de produtos completa
    private List<ProdutoEntity> listaFiltrada;  // Lista de produtos filtrados
    private List<ProdutoEntity> carrinho;      // Lista do carrinho de compras

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        // Inicializando a lista de produtos
        listaProdutos = Arrays.asList(
                new ProdutoEntity("Morango Albion", 150.00, 0, R.drawable.albion),
                new ProdutoEntity("Morango Capri", 185.00, 0, R.drawable.capri),
                new ProdutoEntity("Morango Diamante", 190.00, 0, R.drawable.diamante),
                new ProdutoEntity("Morango Bourbon", 210.00, 0, R.drawable.bourbon)
        );

        carrinho = new ArrayList<>();
        listaFiltrada = new ArrayList<>(listaProdutos); // Inicializando a lista filtrada

        recyclerView = findViewById(R.id.recyclerViewProdutos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializando o adapter com a lista do carrinho
        adapter = new CarrinhoAdapter(listaProdutos, carrinho, false);  // isCarrinhoPage = false
        recyclerView.setAdapter(adapter);

        // EditText de pesquisa
        EditText editTextSearch = findViewById(R.id.BuscarProdutos);
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
