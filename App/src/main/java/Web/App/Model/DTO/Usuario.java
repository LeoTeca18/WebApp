package Web.App.Model.DTO;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um usuário do sistema.
 * Pode ser um usuário comum ou administrador.
 * 
 * @author Sistema E-commerce
 * @version 2.0
 * @since 2026-02-01
 */
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Nome completo do usuário
     */
    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    /**
     * Email do usuário (usado para login)
     */
    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    /**
     * Senha do usuário (deve ser criptografada)
     */
    @Column(name = "senha", nullable = false, length = 255)
    private String senha;

    /**
     * Papel/role do usuário (admin ou user)
     */
    @Column(name = "papel", nullable = false, length = 20)
    private String papel;

    /**
     * Status da conta (ativa ou desativada)
     */
    @Column(name = "status", nullable = false)
    private boolean status;

    /**
     * Data de cadastro do usuário
     */
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    /**
     * Telefone do usuário
     */
    @Column(name = "telefone", length = 20)
    private String telefone;

    /**
     * Endereço do usuário
     */
    @Column(name = "endereco", length = 500)
    private String endereco;

    /**
     * CPF do usuário
     */
    @Column(name = "cpf", length = 14, unique = true)
    private String cpf;

    /**
     * Lista de pedidos realizados pelo usuário
     */
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Pedido> pedidos = new ArrayList<>();

    /**
     * Lista de avaliações feitas pelo usuário
     */
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Avaliacao> avaliacoes = new ArrayList<>();

    /**
     * Construtor padrão
     */
    public Usuario() {
        this.dataCadastro = LocalDateTime.now();
    }

    // Getters e Setters

    /**
     * Obtém o ID do usuário
     * 
     * @return ID do usuário
     */

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
