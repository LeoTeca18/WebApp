package Web.App.Service;

import Web.App.Model.DTO.Avaliacao;
import Web.App.Model.DTO.Produto;
import Web.App.Model.DTO.Usuario;
import Web.App.Repository.RepositorioAvaliacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço para gerenciamento de avaliações de produtos.
 * Contém lógica de negócio para operações com avaliações.
 * 
 * @author Sistema E-commerce
 * @version 1.0
 * @since 2026-02-01
 */
@Service
public class AvaliacaoService {

    @Autowired
    private RepositorioAvaliacao repositorioAvaliacao;

    /**
     * Cria uma nova avaliação para um produto
     * 
     * @param avaliacao Avaliação a ser criada
     * @return Avaliação salva
     */
    public Avaliacao criarAvaliacao(Avaliacao avaliacao) {
        // Validações
        if (avaliacao.getNota() < 1 || avaliacao.getNota() > 5) {
            throw new IllegalArgumentException("Nota deve estar entre 1 e 5");
        }
        if (avaliacao.getProduto() == null || avaliacao.getUsuario() == null) {
            throw new IllegalArgumentException("Produto e usuário são obrigatórios");
        }

        return repositorioAvaliacao.save(avaliacao);
    }

    /**
     * Busca todas as avaliações de um produto
     * 
     * @param produtoId ID do produto
     * @return Lista de avaliações
     */
    public List<Avaliacao> buscarAvaliacoesPorProduto(int produtoId) {
        return repositorioAvaliacao.findByProdutoId(produtoId);
    }

    /**
     * Busca avaliações verificadas de um produto
     * 
     * @param produtoId ID do produto
     * @return Lista de avaliações verificadas
     */
    public List<Avaliacao> buscarAvaliacoesVerificadas(int produtoId) {
        return repositorioAvaliacao.findByProdutoIdAndVerificada(produtoId, true);
    }

    /**
     * Calcula a média de notas de um produto
     * 
     * @param produtoId ID do produto
     * @return Média das notas
     */
    public double calcularMediaAvaliacoes(int produtoId) {
        Double media = repositorioAvaliacao.calcularMediaNotas(produtoId);
        return media != null ? media : 0.0;
    }

    /**
     * Verifica uma avaliação (ação do admin)
     * 
     * @param avaliacaoId ID da avaliação
     * @return Avaliação atualizada
     */
    public Avaliacao verificarAvaliacao(int avaliacaoId) {
        Optional<Avaliacao> optionalAvaliacao = repositorioAvaliacao.findById(avaliacaoId);
        if (optionalAvaliacao.isPresent()) {
            Avaliacao avaliacao = optionalAvaliacao.get();
            avaliacao.setVerificada(true);
            return repositorioAvaliacao.save(avaliacao);
        }
        throw new RuntimeException("Avaliação não encontrada: " + avaliacaoId);
    }

    /**
     * Remove uma avaliação
     * 
     * @param avaliacaoId ID da avaliação
     */
    public void removerAvaliacao(int avaliacaoId) {
        repositorioAvaliacao.deleteById(avaliacaoId);
    }

    /**
     * Lista todas as avaliações pendentes de verificação
     * 
     * @return Lista de avaliações não verificadas
     */
    public List<Avaliacao> listarAvaliacoesPendentes() {
        return repositorioAvaliacao.findAll().stream()
                .filter(a -> !a.isVerificada())
                .toList();
    }
}
