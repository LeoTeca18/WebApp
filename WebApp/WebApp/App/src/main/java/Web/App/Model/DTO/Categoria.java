package Web.App.Model.DTO;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa uma categoria de produtos no sistema.
 * Uma categoria pode conter múltiplos produtos.
 * 
 * @author Sistema E-commerce
 * @version 1.0
 * @since 2026-02-01
 */
@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Nome da categoria (ex: Eletrônicos, Roupas, Alimentos)
     */
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    /**
     * Descrição detalhada da categoria
     */
    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    /**
     * Status ativo/inativo da categoria
     */
    @Column(name = "ativa")
    private boolean ativa = true;

    /**
     * Lista de produtos que pertencem a esta categoria
     */
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Produto> produtos = new ArrayList<>();

    // Construtores
    public Categoria() {
    }

    public Categoria(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
