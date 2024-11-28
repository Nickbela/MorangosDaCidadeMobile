package com.example.morangosdacidademobile.adapters;

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

import java.util.List;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.CarrinhoViewHolder> {

    private List<ProdutoEntity> produtos;
    private List<ProdutoEntity> carrinho;  // Lista para o carrinho
    private boolean isCarrinhoPage;

    // Construtor
    public CarrinhoAdapter(List<ProdutoEntity> produtos, List<ProdutoEntity> carrinho, boolean isCarrinhoPage) {
        this.produtos = produtos;
        this.carrinho = carrinho;
        this.isCarrinhoPage = isCarrinhoPage;
    }

    public static class CarrinhoViewHolder extends RecyclerView.ViewHolder {
        TextView nome, preco, quantidade;
        ImageView imagem;
        Button btnAdicionar;  // Botão "Comprar"
        ImageButton btnExcluir;    // Botão "Excluir"
        ImageButton iconAumentarQuantidade, iconDiminuirQuantidade;

        public CarrinhoViewHolder(@NonNull View itemView) {
            super(itemView);
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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_produto, parent, false);
        return new CarrinhoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarrinhoViewHolder holder, int position) {
        ProdutoEntity produto = produtos.get(position);

        holder.nome.setText(produto.getNome());
        holder.preco.setText(String.format("R$ %.2f", produto.getPreco()));
        holder.quantidade.setText(String.valueOf(produto.getQuantidade()));
        holder.imagem.setImageResource(produto.getImagemId());

        if (isCarrinhoPage) {
            // Exibir e controlar o botão de excluir
            holder.btnAdicionar.setVisibility(View.GONE);
            holder.btnExcluir.setVisibility(View.VISIBLE);

            // Excluir produto do carrinho
            holder.btnExcluir.setOnClickListener(v -> {
                if (position >= 0 && position < produtos.size()) {
                    produtos.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, produtos.size());
                }
            });

            // Controlar a quantidade
            holder.iconDiminuirQuantidade.setOnClickListener(v -> {
                if (produto.getQuantidade() > 1) {  // Impede que a quantidade seja menor que 1
                    produto.setQuantidade(produto.getQuantidade() - 1);
                    holder.quantidade.setText(String.valueOf(produto.getQuantidade()));
                }
            });

            holder.iconAumentarQuantidade.setOnClickListener(v -> {
                produto.setQuantidade(produto.getQuantidade() + 1);
                holder.quantidade.setText(String.valueOf(produto.getQuantidade()));
            });

        } else {
            // Lógica para a página de produtos
            holder.btnAdicionar.setVisibility(View.VISIBLE);
            holder.btnExcluir.setVisibility(View.GONE);

            holder.btnAdicionar.setOnClickListener(v -> {
                // Adiciona o produto ao carrinho
                if (!carrinho.contains(produto)) {
                    carrinho.add(produto);
                    Toast.makeText(v.getContext(), "Produto adicionado ao carrinho", Toast.LENGTH_SHORT).show();
                }
            });
            holder.iconDiminuirQuantidade.setOnClickListener(v -> {
                if (produto.getQuantidade() > 1) {  // Impede que a quantidade seja menor que 1
                    produto.setQuantidade(produto.getQuantidade() - 1);
                    holder.quantidade.setText(String.valueOf(produto.getQuantidade()));
                }
            });

            holder.iconAumentarQuantidade.setOnClickListener(v -> {
                produto.setQuantidade(produto.getQuantidade() + 1);
                holder.quantidade.setText(String.valueOf(produto.getQuantidade()));
            });
        }
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }
}
