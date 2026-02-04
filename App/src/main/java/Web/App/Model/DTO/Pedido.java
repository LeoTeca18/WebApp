package Web.App.Model.DTO;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um pedido realizado por um usuário.
 * Contém informações sobre os itens comprados, total e status.
 * 
 * @author Sistema E-commerce
 * @version 1.0
 * @since 2026-02-01
 */
@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Usuário que realizou o pedido
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /**
     * Lista de itens do pedido
     */
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    /**
     * Valor total do pedido
     */
    @Column(name = "total", nullable = false)
    private double total;

    /**
     * Status do pedido (PENDENTE, CONFIRMADO, ENVIADO, ENTREGUE, CANCELADO)
     */
    @Column(name = "status", length = 20)
    private String status;

    /**
     * Data e hora de criação do pedido
     */
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    /**
     * Endereço de entrega
     */
    @Column(name = "endereco_entrega", length = 500)
    private String enderecoEntrega;

    /**
     * Forma de pagamento
     */
    @Column(name = "forma_pagamento", length = 50)
    private String formaPagamento;

    // Construtores
    public Pedido() {
        this.dataCriacao = LocalDateTime.now();
        this.status = "PENDENTE";
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    /**
     * Adiciona um item ao pedido
     * 
     * @param item O item a ser adicionado
     */
    public void adicionarItem(ItemPedido item) {
        itens.add(item);
        item.setPedido(this);
    }

    /**
     * Calcula o total do pedido baseado nos itens
     */
    public void calcularTotal() {
        this.total = itens.stream()
                .mapToDouble(item -> item.getPrecoUnitario() * item.getQuantidade())
                .sum();
    }
}
