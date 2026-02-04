package Web.App.Repository;

import Web.App.Model.DTO.Avaliacao;
import Web.App.Model.DTO.Produto;
import Web.App.Model.DTO.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para operações de banco de dados relacionadas a Avaliacao.
 * Extende JpaRepository para fornecer operações CRUD básicas.
 * 
 * @author Sistema E-commerce
 * @version 1.0
 * @since 2026-02-01
 */
@Repository
public interface RepositorioAvaliacao extends JpaRepository<Avaliacao, Integer> {

    /**
     * Busca todas as avaliações de um produto
     * 
     * @param produto Produto a ser consultado
     * @return Lista de avaliações do produto
     */
    List<Avaliacao> findByProduto(Produto produto);

    /**
     * Busca todas as avaliações de um produto pelo ID
     * 
     * @param produtoId ID do produto
     * @return Lista de avaliações
     */
    @Query("SELECT a FROM Avaliacao a WHERE a.produto.id = ?1")
    List<Avaliacao> findByProdutoId(int produtoId);

    /**
     * Busca todas as avaliações de um usuário
     * 
     * @param usuario Usuário a ser consultado
     * @return Lista de avaliações do usuário
     */
    List<Avaliacao> findByUsuario(Usuario usuario);

    /**
     * Busca avaliações verificadas de um produto
     * 
     * @param produtoId  ID do produto
     * @param verificada Status de verificação
     * @return Lista de avaliações verificadas
     */
    @Query("SELECT a FROM Avaliacao a WHERE a.produto.id = ?1 AND a.verificada = ?2")
    List<Avaliacao> findByProdutoIdAndVerificada(int produtoId, boolean verificada);

    /**
     * Calcula a média de notas de um produto
     * 
     * @param produtoId ID do produto
     * @return Média das notas ou null se não houver avaliações
     */
    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.produto.id = ?1")
    Double calcularMediaNotas(int produtoId);

    /**
     * Conta quantas avaliações um produto tem
     * 
     * @param produtoId ID do produto
     * @return Número de avaliações
     */
    @Query("SELECT COUNT(a) FROM Avaliacao a WHERE a.produto.id = ?1")
    long contarAvaliacoesProduto(int produtoId);
}
