package Web.App.Service;

import Web.App.Model.DTO.Categoria;
import Web.App.Repository.RepositorioCategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço para gerenciamento de categorias de produtos.
 * Contém lógica de negócio para operações com categorias.
 * 
 * @author Sistema E-commerce
 * @version 1.0
 * @since 2026-02-01
 */
@Service
public class CategoriaService {

    @Autowired
    private RepositorioCategoria repositorioCategoria;

    /**
     * Cria uma nova categoria
     * @param categoria Categoria a ser criada
     * @return Categoria salva
     */
    public Categoria criarCategoria(Categoria categoria) {
        // Validação: verifica se já existe categoria com o mesmo nome
        Categoria existente = repositorioCategoria.findByNome(categoria.getNome());
        if (existente != null) {
            throw new IllegalArgumentException("Já existe uma categoria com este nome");
        }
        return repositorioCategoria.save(categoria);
    }

    /**
     * Busca todas as categorias ativas
     * @return Lista de categorias ativas
     */
    public List<Categoria> listarCategoriasAtivas() {
        return repositorioCategoria.findByAtiva(true);
    }

    /**
     * Busca todas as categorias
     * @return Lista de todas as categorias
     */
    public List<Categoria> listarTodasCategorias() {
        return repositorioCategoria.findAll();
    }

    /**
     * Busca categoria por ID
     * @param id ID da categoria
     * @return Categoria encontrada
     */
    public Categoria buscarPorId(int id) {
        Optional<Categoria> optional = repositorioCategoria.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException("Categoria não encontrada: " + id);
    }

    /**
     * Atualiza uma categoria existente
     * @param categoria Categoria com dados atualizados
     * @return Categoria atualizada
     */
    public Categoria atualizarCategoria(Categoria categoria) {
        if (!repositorioCategoria.existsById(categoria.getId())) {
            throw new RuntimeException("Categoria não encontrada: " + categoria.getId());
        }
        return repositorioCategoria.save(categoria);
    }

    /**
     * Ativa ou desativa uma categoria
     * @param id ID da categoria
     * @param ativa Status a ser definido
     * @return Categoria atualizada
     */
    public Categoria alterarStatus(int id, boolean ativa) {
        Categoria categoria = buscarPorId(id);
        categoria.setAtiva(ativa);
        return repositorioCategoria.save(categoria);
    }

    /**
     * Busca categorias por nome (pesquisa parcial)
     * @param nome Nome ou parte do nome
     * @return Lista de categorias encontradas
     */
    public List<Categoria> buscarPorNome(String nome) {
        return repositorioCategoria.findByNomeContainingIgnoreCase(nome);
    }

    /**
     * Remove uma categoria
     * @param id ID da categoria
     */
    public void removerCategoria(int id) {
        Categoria categoria = buscarPorId(id);
        if (!categoria.getProdutos().isEmpty()) {
            throw new IllegalStateException("Não é possível remover categoria com produtos associados");
        }
        repositorioCategoria.deleteById(id);
    }
}
