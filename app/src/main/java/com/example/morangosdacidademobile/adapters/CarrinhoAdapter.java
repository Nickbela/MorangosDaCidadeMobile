package com.example.morangosdacidademobile.adapters;

import static com.example.morangosdacidademobile.ui.CarrinhoFragment.atualizarTotalCompra;
import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.morangosdacidademobile.R;
import RegrasDeNegocio.Entity.ProdutoEntity;

import com.example.morangosdacidademobile.ui.CarrinhoFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.CarrinhoViewHolder> {

    private List<ProdutoEntity> produtos;
    private List<ProdutoEntity> carrinho;  // Lista do carrinho
    private boolean isCarrinhoPage;
    private CarrinhoFragment carrinhoFragment;

    // Construtor
    public CarrinhoAdapter(List<ProdutoEntity> produtos, List<ProdutoEntity> carrinho, boolean isCarrinhoPage, CarrinhoFragment carrinhoFragment) {
        this.produtos = produtos;
        this.carrinho = carrinho;
        this.isCarrinhoPage = isCarrinhoPage;
        this.carrinhoFragment = carrinhoFragment;  // Referência ao CarrinhoFragment
    }

    public static class CarrinhoViewHolder extends RecyclerView.ViewHolder {
        TextView nome, preco, quantidade;
        ImageView imagem;
        Button btnAdicionar;  // Botão "Comprar"
        ImageButton btnExcluir;    // Botão "Excluir"
        ImageButton iconAumentarQuantidade, iconDiminuirQuantidade;

        public CarrinhoViewHolder(@NonNull View itemView) {
            super(itemView);

            // Atribuir os elementos do XML ao ViewHolder
            nome = itemView.findViewById(R.id.textNomeProduto);
            preco = itemView.findViewById(R.id.textValorProduto);
            quantidade = itemView.findViewById(R.id.editQuantidadeProduto);
            imagem = itemView.findViewById(R.id.imageProduto);
            btnAdicionar = itemView.findViewById(R.id.buttonComprarProduto);
            btnExcluir = itemView.findViewById(R.id.iconLixeira);
            iconAumentarQuantidade = itemView.findViewById(R.id.iconAumentarQuantidade);
            iconDiminuirQuantidade = itemView.findViewById(R.id.iconDiminuirQuantidade);
        }
    }

    @NonNull
    @Override
    public CarrinhoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar o layout do item do RecyclerView
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_produto, parent, false);
        return new CarrinhoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarrinhoViewHolder holder, int position) {
        ProdutoEntity produto = produtos.get(position);

        Set<String> idsVistos = new HashSet<>();
        carrinho.removeIf(item -> !idsVistos.add(item.getId()));

        // Preenchendo os campos com os dados do produto
        holder.nome.setText(produto.getNome());
        holder.preco.setText(String.format("R$ %.2f", produto.getPreco()));
        holder.quantidade.setText(String.valueOf(produto.getQuantidade()));
        holder.imagem.setImageResource(produto.getImagemId());

        if (isCarrinhoPage) {
            // Configuração da página do carrinho
            holder.btnAdicionar.setVisibility(View.GONE);
            holder.btnExcluir.setVisibility(View.VISIBLE);

            holder.btnExcluir.setOnClickListener(v -> {
                if (position >= 0 && position < carrinho.size()) {
                    carrinho.remove(position); // Remove o produto do carrinho
                    notifyItemRemoved(position); // Atualiza a interface

                    // Salva a lista de carrinho atualizada nos SharedPreferences
                    salvarCarrinhoNoSharedPreferences(v.getContext(), carrinho);

                    // Atualiza o total da compra
                    if (carrinhoFragment != null) {
                        carrinhoFragment.atualizarTotalCompra();
                    }
                }
            });

            holder.iconAumentarQuantidade.setOnClickListener(v -> {
                produto.setQuantidade(produto.getQuantidade() + 1);
                holder.quantidade.setText(String.valueOf(produto.getQuantidade()));

                // Atualiza o carrinho no SharedPreferences após a alteração
                atualizarCarrinhoNoSharedPreferences(v.getContext(), carrinho);

                // Atualiza o total da compra
                if (carrinhoFragment != null) {
                    carrinhoFragment.atualizarTotalCompra();
                }
            });

            holder.iconDiminuirQuantidade.setOnClickListener(v -> {
                if (produto.getQuantidade() > 1) {
                    produto.setQuantidade(produto.getQuantidade() - 1);
                    holder.quantidade.setText(String.valueOf(produto.getQuantidade()));

                    // Atualiza o carrinho no SharedPreferences após a alteração
                    atualizarCarrinhoNoSharedPreferences(v.getContext(), carrinho);

                    // Atualiza o total da compra
                    if (carrinhoFragment != null) {
                        carrinhoFragment.atualizarTotalCompra();
                    }
                }
            });
        } else {
            // Configuração da página de produtos
            holder.btnAdicionar.setVisibility(View.VISIBLE);
            holder.btnExcluir.setVisibility(View.GONE);

            holder.btnAdicionar.setOnClickListener(v -> {
                Log.d("CarrinhoAdapter", "Tentando adicionar produto com ID: " + produto.getId());

                // Verifica se o produto já está no carrinho
                boolean produtoExistente = false;
                for (ProdutoEntity item : carrinho) {
                    Log.d("CarrinhoAdapter", "Verificando produto no carrinho com ID: " + item.getId());
                    if (item.getId() == produto.getId() && item.getNome().equals(produto.getNome())) {
                        produtoExistente = true;
                        item.setQuantidade(item.getQuantidade() + produto.getQuantidade());
                        break;
                    }
                }

                if (!produtoExistente) {
                    ProdutoEntity novoProduto = new ProdutoEntity(
                            produto.getNome(),
                            produto.getPreco(),
                            produto.getQuantidade(),
                            produto.getImagemId(),
                            produto.getId()
                    );
                    carrinho.add(novoProduto);
                }


                salvarCarrinhoNoSharedPreferences(v.getContext(), carrinho);
                notifyDataSetChanged();
                Toast.makeText(v.getContext(), "Produto adicionado ao carrinho", Toast.LENGTH_SHORT).show();

                if (carrinhoFragment != null) {
                    carrinhoFragment.atualizarTotalCompra();
                }
            });
            holder.iconAumentarQuantidade.setOnClickListener(v -> {
                produto.setQuantidade(produto.getQuantidade() + 1);
                holder.quantidade.setText(String.valueOf(produto.getQuantidade()));

                // Atualiza o carrinho no SharedPreferences após a alteração
                atualizarCarrinhoNoSharedPreferences(v.getContext(), carrinho);

                // Atualiza o total da compra
                if (carrinhoFragment != null) {
                    carrinhoFragment.atualizarTotalCompra();
                }
            });

            holder.iconDiminuirQuantidade.setOnClickListener(v -> {
                if (produto.getQuantidade() > 1) {
                    produto.setQuantidade(produto.getQuantidade() - 1);
                    holder.quantidade.setText(String.valueOf(produto.getQuantidade()));

                    // Atualiza o carrinho no SharedPreferences após a alteração
                    atualizarCarrinhoNoSharedPreferences(v.getContext(), carrinho);

                    // Atualiza o total da compra
                    if (carrinhoFragment != null) {
                        carrinhoFragment.atualizarTotalCompra();
                    }
                }
            });

        }
    }

    private void adicionarProdutoAoCarrinho(Context context, ProdutoEntity produto) {
        // Recuperar carrinho existente
        List<ProdutoEntity> carrinho = recuperarCarrinho(context);

        // Verificar se o produto já está no carrinho
        boolean produtoExistente = false;
        for (ProdutoEntity p : carrinho) {
            if (p.getId() == produto.getId()) {
                produtoExistente = true;
                break;
            }
        }

        if (!produtoExistente) {
            // Adicionar novo produto ao carrinho
            carrinho.add(produto);
            Log.d("CarrinhoAdapter", "Produto não encontrado no carrinho. Adicionando: " + produto.getId());
        } else {
            Log.d("CarrinhoAdapter", "Produto já existe no carrinho. ID: " + produto.getId());
        }

        // Salvar o carrinho atualizado
        salvarCarrinhoNoSharedPreferences(context, carrinho);
    }


    private void salvarCarrinhoNoSharedPreferences(Context context, List<ProdutoEntity> carrinho) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("carrinho", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String carrinhoJson = gson.toJson(carrinho);
        editor.putString("carrinho", carrinhoJson);
        editor.apply();

        Log.d("CarrinhoAdapter", "Carrinho salvo no SharedPreferences: " + carrinho.size() + " itens.");
    }



    private void atualizarCarrinhoNoSharedPreferences(Context context, List<ProdutoEntity> carrinho) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("carrinho", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Convertendo a lista de produtos em uma string JSON usando o Gson
        Gson gson = new Gson();
        String carrinhoJson = gson.toJson(carrinho);
        editor.putString("carrinho", carrinhoJson);  // Salvando a string JSON
        editor.apply();  // Aplicando a alteração

        Log.d("CarrinhoFragment", "Carrinho atualizado nos SharedPreferences.");
    }

    public static List<ProdutoEntity> recuperarCarrinho(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("carrinho", Context.MODE_PRIVATE);
        String carrinhoJson = sharedPreferences.getString("carrinho", "[]");

        Gson gson = new Gson();
        Type type = new TypeToken<List<ProdutoEntity>>() {}.getType();
        List<ProdutoEntity> carrinho = gson.fromJson(carrinhoJson, type);

        if (carrinho == null) {
            carrinho = new ArrayList<>();
        }

        // Remove duplicados, caso existam
        Set<String> idsVistos = new HashSet<>();
        carrinho.removeIf(produto -> !idsVistos.add(produto.getId()));

        Log.d("CarrinhoAdapter", "Carrinho recuperado com " + carrinho.size() + " itens.");
        return carrinho;
    }


    @Override
    public int getItemCount() {
        return produtos.size();
    }
}
