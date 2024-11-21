package com.example.morangosdacidademobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.morangosdacidademobile.R;
import RegrasDeNegocio.Entity.Produto;

import java.util.List;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.CarrinhoViewHolder> {

    private List<Produto> produtos;

    // Construtor para receber a lista de produtos
    public CarrinhoAdapter(List<Produto> produtos) {
        this.produtos = produtos;
    }

    // ViewHolder para vincular os dados às views
    public static class CarrinhoViewHolder extends RecyclerView.ViewHolder {
        TextView nome, preco, quantidade;
        ImageView imagem, btnRemover;

        public CarrinhoViewHolder(@NonNull View itemView) {
            super(itemView);

            // Vincule os IDs das views no layout do item
            nome = itemView.findViewById(R.id.tvNomeProduto);
            preco = itemView.findViewById(R.id.tvPrecoProduto);
            quantidade = itemView.findViewById(R.id.tvQuantidadeProduto);
            imagem = itemView.findViewById(R.id.ivProduto);
            btnRemover = itemView.findViewById(R.id.ivRemoverProduto);
        }
    }

    @NonNull
    @Override
    public CarrinhoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar o layout do item do carrinho
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carrinho, parent, false);
        return new CarrinhoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarrinhoViewHolder holder, int position) {
        // Obter o produto na posição atual
        Produto produto = produtos.get(position);

        // Configurar os dados nas views
        holder.nome.setText(produto.getNome());
        holder.preco.setText(String.format("R$ %.2f", produto.getPreco()));
        holder.quantidade.setText(String.valueOf(produto.getQuantidade()));
        holder.imagem.setImageResource(produto.getImagemId());

        // Ação de remover item do carrinho
        holder.btnRemover.setOnClickListener(v -> {
            produtos.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, produtos.size());
        });
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }
}
