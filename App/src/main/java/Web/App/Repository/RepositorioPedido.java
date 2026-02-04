package Web.App.Repository;

import Web.App.Model.DTO.Pedido;
import Web.App.Model.DTO.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório para operações de banco de dados relacionadas a Pedido.
 * Extende JpaRepository para fornecer operações CRUD básicas.
 * 
 * @author Sistema E-commerce
 * @version 1.0
 * @since 2026-02-01
 */
@Repository
public interface RepositorioPedido extends JpaRepository<Pedido, Integer> {

    /**
     * Busca todos os pedidos de um usuário
     * 
     * @param usuario Usuário a ser consultado
     * @return Lista de pedidos do usuário
     */
    List<Pedido> findByUsuario(Usuario usuario);

    /**
     * Busca pedidos por ID do usuário
     * 
     * @param usuarioId ID do usuário
     * @return Lista de pedidos
     */
    @Query("SELECT p FROM Pedido p WHERE p.usuario.id = ?1 ORDER BY p.dataCriacao DESC")
    List<Pedido> findByUsuarioId(int usuarioId);

    /**
     * Busca pedidos por status
     * 
     * @param status Status do pedido
     * @return Lista de pedidos com o status especificado
     */
    List<Pedido> findByStatus(String status);

    /**
     * Busca pedidos por período
     * 
     * @param dataInicio Data inicial
     * @param dataFim    Data final
     * @return Lista de pedidos no período
     */
    @Query("SELECT p FROM Pedido p WHERE p.dataCriacao BETWEEN ?1 AND ?2")
    List<Pedido> findByPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim);

    /**
     * Calcula o total de vendas de um período
     * 
     * @param dataInicio Data inicial
     * @param dataFim    Data final
     * @return Soma total das vendas
     */
    @Query("SELECT SUM(p.total) FROM Pedido p WHERE p.dataCriacao BETWEEN ?1 AND ?2")
    Double calcularTotalVendas(LocalDateTime dataInicio, LocalDateTime dataFim);

    /**
     * Conta pedidos por status
     * 
     * @param status Status a ser contado
     * @return Número de pedidos
     */
    long countByStatus(String status);
}
