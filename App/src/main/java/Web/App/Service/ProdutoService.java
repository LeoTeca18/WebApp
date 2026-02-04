package Web.App.Service;

import Web.App.Model.DTO.Produto;
import Web.App.Repository.RepositorioProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço para gerenciamento de produtos.
 * Contém lógica de negócio para operações com produtos.
 * 
 * @author Sistema E-commerce
 * @version 2.0
 * @since 2026-02-01
 */
@Service
public class ProdutoService {

    @Autowired
    RepositorioProduto repositortioProduto;

    /**
     * Busca um produto por ID
     * 
     * @param id ID do produto
     * @return Produto encontrado
     * @throws RuntimeException se produto não for encontrado
     */
    public Produto getProductByID(int id) {
        Optional<Produto> optional = repositortioProduto.findById(id);
        Produto produto = null;
        if (optional.isPresent()) {
            produto = optional.get();
        } else {
            throw new RuntimeException("Product not found by id :: " + id);
        }
        return produto;
    }

    /**
     * Lista todos os produtos
     * 
     * @return Lista de todos os produtos
     */
    public List<Produto> listarTodosProdutos() {
        return repositortioProduto.findAll();
    }

    /**
     * Lista produtos disponíveis (com estoque e ativos)
     * 
     * @return Lista de produtos disponíveis
     */
    public List<Produto> listarProdutosDisponiveis() {
        return repositortioProduto.findAll().stream()
                .filter(Produto::isDisponivel)
                .toList();
    }

    /**
     * Busca produtos por nome
     * 
     * @param nome Nome ou parte do nome do produto
     * @return Lista de produtos encontrados
     */
    public List<Produto> buscarPorNome(String nome) {
        return repositortioProduto.findByNomeContaining(nome);
    }

    /**
     * Salva um novo produto ou atualiza um existente
     * 
     * @param produto Produto a ser salvo
     * @return Produto salvo
     */
    public Produto salvarProduto(Produto produto) {
        // Validações básicas
        if (produto.getPreco() < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
        if (produto.getQuantidade() < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa");
        }
        return repositortioProduto.save(produto);
    }

    /**
     * Remove um produto
     * 
     * @param id ID do produto
     */
    public void removerProduto(int id) {
        repositortioProduto.deleteById(id);
    }

    /**
     * Atualiza o estoque de um produto
     * 
     * @param id         ID do produto
     * @param quantidade Nova quantidade em estoque
     * @return Produto atualizado
     */
    public Produto atualizarEstoque(int id, int quantidade) {
        Produto produto = getProductByID(id);
        produto.setQuantidade(quantidade);
        return repositortioProduto.save(produto);
    }

    /**
     * Aplica desconto a um produto
     * 
     * @param id       ID do produto
     * @param desconto Percentual de desconto (0-100)
     * @return Produto atualizado
     */
    public Produto aplicarDesconto(int id, double desconto) {
        if (desconto < 0 || desconto > 100) {
            throw new IllegalArgumentException("Desconto deve estar entre 0 e 100");
        }
        Produto produto = getProductByID(id);
        produto.setDesconto(desconto);
        return repositortioProduto.save(produto);
    }

    /**
     * Busca produtos por categoria
     * 
     * @param categoriaId ID da categoria
     * @return Lista de produtos da categoria
     */
    public List<Produto> buscarPorCategoria(int categoriaId) {
        return repositortioProduto.findAll().stream()
                .filter(p -> p.getCategoria() != null && p.getCategoria().getId() == categoriaId)
                .toList();
    }

    /**
     * Busca produtos em promoção (com desconto)
     * 
     * @return Lista de produtos com desconto
     */
    public List<Produto> listarProdutosEmPromocao() {
        return repositortioProduto.findAll().stream()
                .filter(p -> p.getDesconto() > 0)
                .toList();
    }
}
