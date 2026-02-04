package Web.App.Repository;

import Web.App.Model.DTO.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para operações de banco de dados relacionadas a Categoria.
 * Extende JpaRepository para fornecer operações CRUD básicas.
 * 
 * @author Sistema E-commerce
 * @version 1.0
 * @since 2026-02-01
 */
@Repository
public interface RepositorioCategoria extends JpaRepository<Categoria, Integer> {

    /**
     * Busca categorias pelo nome (parcial, case-insensitive)
     * 
     * @param nome Nome ou parte do nome da categoria
     * @return Lista de categorias encontradas
     */
    List<Categoria> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca categorias ativas
     * 
     * @param ativa Status da categoria
     * @return Lista de categorias ativas
     */
    List<Categoria> findByAtiva(boolean ativa);

    /**
     * Busca categoria por nome exato
     * 
     * @param nome Nome da categoria
     * @return Categoria encontrada ou null
     */
    Categoria findByNome(String nome);
}
