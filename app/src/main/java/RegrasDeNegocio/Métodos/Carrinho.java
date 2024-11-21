package RegrasDeNegocio.Métodos;

    import java.util.ArrayList;
    import java.util.List;

    import RegrasDeNegocio.Entity.Produto;

public class Carrinho {
        private List<Produto> produtos;

        public Carrinho() {
            this.produtos = new ArrayList<>();
        }

        // Método para adicionar um produto ao carrinho
        public void adicionarItem(Produto produto) {
            produtos.add(produto); // Adiciona o item à lista
            notifyItemInserted(produtos.size() - 1); // Notifica a inserção
        }


        // Método para remover um produto do carrinho
        public void removerItem(int position) {
            produtos.remove(position); // Remove o item da lista
            notifyItemRemoved(position); // Notifica a remoção
            notifyItemRangeChanged(position, produtos.size()); // Atualiza os índices
        }


    // Método para recuperar itens no carrinho
        public List<Produto> getItens() {
            return produtos;
        }

        // Método para calcular o valor total do carrinho
        private double calcularTotal(List<Produto> produtos) {
            double total = 0;
            for (Produto produto : produtos) {
                total += produto.getPreco() * produto.getQuantidade();
            }
            return total;
        }

    public void atualizarQuantidade(int position, int novaQuantidade) {
        Produto produto = produtos.get(position);
        produto.setQuantidade(novaQuantidade); // Atualiza a quantidade
        notifyItemChanged(position); // Notifica a alteração
    }


}


