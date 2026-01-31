package Web.App.Service;

import Web.App.Model.DTO.ItemPedido;
import Web.App.Model.DTO.Pedido;
import Web.App.Model.DTO.Produto;
import Web.App.Model.DTO.Usuario;
import Web.App.Repository.RepositorioPedido;
import Web.App.Repository.RepositorioProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Serviço para gerenciamento de pedidos.
 * Contém lógica de negócio para criação e acompanhamento de pedidos.
 * 
 * @author Sistema E-commerce
 * @version 1.0
 * @since 2026-02-01
 */
@Service
public class PedidoService {

    @Autowired
    private RepositorioPedido repositorioPedido;

    @Autowired
    private RepositorioProduto repositorioProduto;

    /**
     * Cria um novo pedido a partir do carrinho
     * @param usuario Usuário que está fazendo o pedido
     * @param produtos Lista de produtos do carrinho
     * @param endereco Endereço de entrega
     * @param formaPagamento Forma de pagamento
     * @return Pedido criado
     */
    @Transactional
    public Pedido criarPedido(Usuario usuario, List<Produto> produtos, String endereco, String formaPagamento) {
        if (produtos == null || produtos.isEmpty()) {
            throw new IllegalArgumentException("Carrinho está vazio");
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEnderecoEntrega(endereco);
        pedido.setFormaPagamento(formaPagamento);

        // Criar itens do pedido
        for (Produto produto : produtos) {
            // Verifica estoque
            if (produto.getQuantidade() <= 0) {
                throw new IllegalStateException("Produto fora de estoque: " + produto.getNome());
            }

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(1); // Por padrão, 1 unidade
            item.setPrecoUnitario(produto.getPreco());
            pedido.adicionarItem(item);

            // Atualiza estoque
            produto.setQuantidade(produto.getQuantidade() - 1);
            repositorioProduto.save(produto);
        }

        pedido.calcularTotal();
        return repositorioPedido.save(pedido);
    }

    /**
     * Busca todos os pedidos de um usuário
     * @param usuarioId ID do usuário
     * @return Lista de pedidos
     */
    public List<Pedido> buscarPedidosUsuario(int usuarioId) {
        return repositorioPedido.findByUsuarioId(usuarioId);
    }

    /**
     * Busca pedido por ID
     * @param id ID do pedido
     * @return Pedido encontrado
     */
    public Pedido buscarPorId(int id) {
        Optional<Pedido> optional = repositorioPedido.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException("Pedido não encontrado: " + id);
    }

    /**
     * Atualiza o status de um pedido
     * @param pedidoId ID do pedido
     * @param novoStatus Novo status
     * @return Pedido atualizado
     */
    public Pedido atualizarStatus(int pedidoId, String novoStatus) {
        Pedido pedido = buscarPorId(pedidoId);
        pedido.setStatus(novoStatus);
        return repositorioPedido.save(pedido);
    }

    /**
     * Lista todos os pedidos
     * @return Lista de todos os pedidos
     */
    public List<Pedido> listarTodosPedidos() {
        return repositorioPedido.findAll();
    }

    /**
     * Busca pedidos por status
     * @param status Status dos pedidos
     * @return Lista de pedidos com o status especificado
     */
    public List<Pedido> buscarPorStatus(String status) {
        return repositorioPedido.findByStatus(status);
    }

    /**
     * Calcula total de vendas em um período
     * @param dataInicio Data inicial
     * @param dataFim Data final
     * @return Total de vendas
     */
    public double calcularTotalVendas(LocalDateTime dataInicio, LocalDateTime dataFim) {
        Double total = repositorioPedido.calcularTotalVendas(dataInicio, dataFim);
        return total != null ? total : 0.0;
    }

    /**
     * Cancela um pedido
     * @param pedidoId ID do pedido
     * @return Pedido cancelado
     */
    @Transactional
    public Pedido cancelarPedido(int pedidoId) {
        Pedido pedido = buscarPorId(pedidoId);
        
        if ("ENTREGUE".equals(pedido.getStatus())) {
            throw new IllegalStateException("Não é possível cancelar um pedido já entregue");
        }

        // Devolve produtos ao estoque
        for (ItemPedido item : pedido.getItens()) {
            Produto produto = item.getProduto();
            produto.setQuantidade(produto.getQuantidade() + item.getQuantidade());
            repositorioProduto.save(produto);
        }

        pedido.setStatus("CANCELADO");
        return repositorioPedido.save(pedido);
    }
}
