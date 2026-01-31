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
    public String email;

    /**
     * Senha do usuário (deve ser criptografada)
     */
    @Column(name = "senha", nullable = false, length = 255)
    public String senha;

    /**
     * Papel/role do usuário (admin ou user)
     */
    @Column(name = "papel", nullable = false, length = 20)
    public String papel;

    /**
     * Status da conta (ativa ou desativada)
     */
    @Column(name = "status", nullable = false)
    public boolean status;

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

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    /**
     * Verifica se o usuário é administrador
     * 
     * @return true se for admin
     */
    public boolean isAdmin() {
        return "admin".equalsIgnoreCase(papel);
    }

    /**
     * Calcula o total gasto pelo usuário em pedidos
     * 
     * @return Total gasto
     */
    public double getTotalGasto() {
        return pedidos.stream()
                .mapToDouble(Pedido::getTotal)
                .sum();
    }
}
