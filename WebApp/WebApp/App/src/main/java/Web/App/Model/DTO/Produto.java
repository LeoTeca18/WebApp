package Web.App.Model.DTO;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um produto no sistema de e-commerce.
 * Contém todas as informações necessárias sobre um produto,
 * incluindo preço, quantidade em estoque, imagens e avaliações.
 * 
 * @author Sistema E-commerce
 * @version 2.0
 * @since 2026-02-01
 */
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Nome do produto
     */
    @Column(name = "nome", nullable = false, length = 200)
    private String nome;

    /**
     * Preço unitário do produto
     */
    @Column(name = "preco", nullable = false)
    private double preco;

    /**
     * Quantidade disponível em estoque
     */
    @Column(name = "quantidade", nullable = false)
    private int quantidade;

    /**
     * Caminho da imagem do produto
     */
    @Column(length = 64)
    private String images;

    /**
     * Informações detalhadas sobre o produto
     */
    @Column(name = "informacao", columnDefinition = "TEXT")
    private String info;

    /**
     * Categoria do produto
     */
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    /**
     * Data de cadastro do produto
     */
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    /**
     * Indica se o produto está ativo/disponível
     */
    @Column(name = "ativo")
    private boolean ativo = true;

    /**
     * Lista de avaliações deste produto
     */
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<Avaliacao> avaliacoes = new ArrayList<>();

    /**
     * Desconto percentual aplicado ao produto
     */
    @Column(name = "desconto")
    private double desconto = 0.0;

    /**
     * Marca/fabricante do produto
     */
    @Column(name = "marca", length = 100)
    private String marca;

    /**
     * Construtor padrão
     */
    public Produto() {
        this.dataCadastro = LocalDateTime.now();
    }

    // Getters e Setters

    /**
     * Obtém o ID do produto
     * 
     * @return ID do produto
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID do produto
     * 
     * @param id ID do produto
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém o nome do produto
     * 
     * @return Nome do produto
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do produto
     * 
     * @param nome Nome do produto
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o preço do produto
     * 
     * @return Preço do produto
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Define o preço do produto
     * 
     * @param preco Preço do produto
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }

    /**
     * Obtém a quantidade em estoque
     * 
     * @return Quantidade disponível
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade em estoque
     * 
     * @param quantidade Quantidade disponível
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Obtém o caminho da imagem
     * 
     * @return Caminho da imagem
     */
    public String getImages() {
        return images;
    }

    /**
     * Define o caminho da imagem
     * 
     * @param images Caminho da imagem
     */
    public void setImages(String images) {
        this.images = images;
    }

    /**
     * Obtém as informações do produto
     * 
     * @return Informações detalhadas
     */
    public String getInfo() {
        return info;
    }

    /**
     * Define as informações do produto
     * 
     * @param info Informações detalhadas
     */
    public void setInfo(String info) {
        this.info = info;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Calcula o preço final do produto com desconto aplicado
     * 
     * @return Preço final após desconto
     */
    public double getPrecoComDesconto() {
        return preco - (preco * desconto / 100);
    }

    /**
     * Calcula a média de avaliações do produto
     * 
     * @return Média das notas (0 se não houver avaliações)
     */
    public double getMediaAvaliacoes() {
        if (avaliacoes == null || avaliacoes.isEmpty()) {
            return 0.0;
        }
        return avaliacoes.stream()
                .mapToInt(Avaliacao::getNota)
                .average()
                .orElse(0.0);
    }

    /**
     * Verifica se o produto está disponível em estoque
     * 
     * @return true se houver quantidade disponível
     */
    public boolean isDisponivel() {
        return quantidade > 0 && ativo;
    }
}
