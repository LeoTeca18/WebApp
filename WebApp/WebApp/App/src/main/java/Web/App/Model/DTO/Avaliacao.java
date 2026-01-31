package Web.App.Model.DTO;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidade que representa uma avaliação de produto feita por um usuário.
 * Permite que usuários avaliem produtos com nota e comentário.
 * 
 * @author Sistema E-commerce
 * @version 1.0
 * @since 2026-02-01
 */
@Entity
@Table(name = "avaliacao")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Produto sendo avaliado
     */
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    /**
     * Usuário que fez a avaliação
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /**
     * Nota da avaliação (1 a 5 estrelas)
     */
    @Column(name = "nota", nullable = false)
    private int nota;

    /**
     * Comentário/review do usuário
     */
    @Column(name = "comentario", columnDefinition = "TEXT")
    private String comentario;

    /**
     * Data e hora em que a avaliação foi criada
     */
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    /**
     * Indica se a avaliação foi verificada pelo admin
     */
    @Column(name = "verificada")
    private boolean verificada = false;

    // Construtores
    public Avaliacao() {
        this.dataCriacao = LocalDateTime.now();
    }

    public Avaliacao(Produto produto, Usuario usuario, int nota, String comentario) {
        this.produto = produto;
        this.usuario = usuario;
        this.nota = nota;
        this.comentario = comentario;
        this.dataCriacao = LocalDateTime.now();
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("Nota deve estar entre 1 e 5");
        }
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean isVerificada() {
        return verificada;
    }

    public void setVerificada(boolean verificada) {
        this.verificada = verificada;
    }
}
