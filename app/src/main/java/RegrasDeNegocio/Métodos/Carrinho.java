package RegrasDeNegocio.Métodos;

    import java.util.ArrayList;
    import java.util.List;
    import RegrasDeNegocio.Entity.ItemCarrinho;
    import RegrasDeNegocio.Entity.Produto;

public class Carrinho {
        private List<ItemCarrinho> itens;

        public Carrinho() {
            this.itens = new ArrayList<>();
        }

        // Método para adicionar um produto ao carrinho
        public void adicionarProduto(Produto produto, int quantidade) {
            for (ItemCarrinho item : itens) {
                if (item.getProduto().getNome().equals(produto.getNome())) {
                    item.setQuantidade(item.getQuantidade() + quantidade);
                    return;
                }
            }
            itens.add(new ItemCarrinho(produto, quantidade));
        }

        // Método para remover um produto do carrinho
        public void removerProduto(String nomeProduto) {
            itens.removeIf(item -> item.getProduto().getNome().equals(nomeProduto));
        }

        // Método para recuperar itens no carrinho
        public List<ItemCarrinho> getItens() {
            return itens;
        }

        // Método para calcular o valor total do carrinho
        public double calcularTotal() {
            double total = 0.0;
            for (ItemCarrinho item : itens) {
                total += item.calcularTotalItem();
            }
            return total;
        }
    }


