package Web.App.Model.DTO;

import jakarta.persistence.*;

/**
 * Entidade que representa um item dentro de um pedido.
 * Contém informações sobre o produto, quantidade e preço.
 * 
 * @author Sistema E-commerce
 * @version 1.0
 * @since 2026-02-01
 */
@Entity
@Table(name = "item_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Pedido ao qual este item pertence
     */
    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    /**
     * Produto referente a este item
     */
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    /**
     * Quantidade do produto no pedido
     */
    @Column(name = "quantidade", nullable = false)
    private int quantidade;

    /**
     * Preço unitário do produto no momento da compra
     */
    @Column(name = "preco_unitario", nullable = false)
    private double precoUnitario;

    // Construtores
    public ItemPedido() {
    }

    public ItemPedido(Produto produto, int quantidade, double precoUnitario) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    /**
     * Calcula o subtotal deste item (quantidade * preço unitário)
     * 
     * @return O valor total deste item
     */
    public double getSubtotal() {
        return quantidade * precoUnitario;
    }
}
