package com.example.morangosdacidademobile.ui;

import static com.example.morangosdacidademobile.adapters.CarrinhoAdapter.recuperarCarrinho;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.morangosdacidademobile.Confirmacao;
import com.example.morangosdacidademobile.ProdutoFragment;
import com.example.morangosdacidademobile.R;
import com.example.morangosdacidademobile.adapters.CarrinhoAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import RegrasDeNegocio.Entity.ProdutoEntity;

public class CarrinhoFragment extends Fragment {

    private RecyclerView recyclerViewCarrinho;
    private CarrinhoAdapter carrinhoAdapter;
    private static List<ProdutoEntity> carrinho; // Lista de produtos no carrinho
    private static TextView textTotalCompra; // Exibe o total da compra

    public CarrinhoFragment() {
        // Construtor público vazio obrigatório para fragments
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar o layout do fragmento
        return inflater.inflate(R.layout.fragment_carrinho, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        // Inicializa a lista de produtos do carrinho
        carrinho = recuperarCarrinho(getContext()); // Método para obter os itens do carrinho

        Log.d("CarrinhoFragment", "Itens no carrinho: " + carrinho.size());

        // Configura o RecyclerView
        recyclerViewCarrinho = view.findViewById(R.id.recyclerViewCarrinho);
        recyclerViewCarrinho.setLayoutManager(new LinearLayoutManager(getContext()));

        // Passa a lista de produtos para o Adapter
        carrinhoAdapter = new CarrinhoAdapter(carrinho, carrinho, true, this); // isCarrinhoPage = true
        recyclerViewCarrinho.setAdapter(carrinhoAdapter);

        // Exibe o total da compra
        textTotalCompra = view.findViewById(R.id.tvTotal);
        atualizarTotalCompra();

        // Configuração do botão "Finalizar Compra"
        Button btnFinalizarCompra = view.findViewById(R.id.btnFinalizarCompra);
        btnFinalizarCompra.setOnClickListener(v -> {
            if (carrinho.isEmpty()) {
                Toast.makeText(getContext(), "Seu carrinho está vazio!", Toast.LENGTH_SHORT).show();
            } else {
                // Criar uma Intent para navegar para a tela de confirmação
                Intent intent = new Intent(getContext(), Confirmacao.class);

                // Passar os dados do cliente
                intent.putExtra("nomeCliente", "Fulano da Silva"); // Substitua pelo nome real do cliente
                intent.putExtra("telefoneCliente", "(11) 99999-9999"); // Substitua pelo telefone real do cliente

                // Passar os itens do pedido (como uma lista serializável)
                intent.putExtra("itensPedido", (Serializable) carrinho);

                // Passar o total da compra
                intent.putExtra("totalCompra", calcularTotalCompra());

                // Iniciar a nova atividade
                startActivity(intent);
            }
        });

        Button btnContinuarComprando = view.findViewById(R.id.btnContinuarComprando);
        btnContinuarComprando.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ProdutoFragment()) // Substitua o ID pelo container do fragment
                    .addToBackStack(null) // Opcional: permite retornar ao fragment anterior
                    .commit();
        });

    }
    @Override
    public void onResume() {
        super.onResume();

        carrinho = recuperarCarrinho(getContext());

        if (carrinhoAdapter != null) {
            Log.d("CarrinhoFragment", "Atualizando o RecyclerView...");
            carrinhoAdapter.notifyDataSetChanged(); // Atualize o RecyclerView
        }else{

            Log.d("CarrinhoFragment", "CarrinhoAdapter não foi inicializado ainda.");
        }
    }


    /**
     * Método para recuperar os itens adicionados ao carrinho.
     */
    /*private List<ProdutoEntity> recuperarCarrinho() {
        Log.d("CarrinhoFragment", "Recuperando itens do carrinho...");
        // Simula a recuperação do carrinho (pode ser ajustado para usar banco de dados ou argumentos)
        return new ArrayList<>();
    }*/

    /**
     * Atualiza o valor total da compra exibido na tela.
     */
    public static void atualizarTotalCompra() {
        double total = calcularTotalCompra();
        textTotalCompra.setText(String.format("Total: R$ %.2f", total));
    }

    /**
     * Calcula o valor total dos produtos no carrinho.
     */
    private static double calcularTotalCompra() {
        double total = 0.0;
        for (ProdutoEntity produto : carrinho) {
            total += produto.getPreco() * produto.getQuantidade();
        }
        return total;
    }
}
