package Web.App.Controller;

import Web.App.Model.DTO.Avaliacao;
import Web.App.Model.DTO.Produto;
import Web.App.Model.DTO.Usuario;
import Web.App.Service.AvaliacaoService;
import Web.App.Service.ProdutoService;
import Web.App.Global.GlobalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller para gerenciamento de avaliações de produtos.
 * Permite que usuários avaliem produtos e visualizem avaliações.
 * 
 * @author Sistema E-commerce
 * @version 1.0
 * @since 2026-02-01
 */
@Controller
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Autowired
    private ProdutoService produtoService;

    /**
     * Exibe o formulário para criar uma nova avaliação
     * 
     * @param produtoId ID do produto a ser avaliado
     * @param model     Model do Spring
     * @return Nome da view
     */
    @GetMapping("/nova/{produtoId}")
    public String novaAvaliacaoForm(@PathVariable int produtoId, Model model) {
        if (GlobalData.usuarios.isEmpty()) {
            return "redirect:/login";
        }

        try {
            Produto produto = produtoService.getProductByID(produtoId);
            model.addAttribute("produto", produto);
            model.addAttribute("avaliacao", new Avaliacao());
            return "avaliacoes/form";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    /**
     * Processa o envio de uma nova avaliação
     * 
     * @param produtoId          ID do produto
     * @param nota               Nota da avaliação (1-5)
     * @param comentario         Comentário do usuário
     * @param redirectAttributes Atributos de redirecionamento
     * @return Redirecionamento para a página do produto
     */
    @PostMapping("/nova/{produtoId}")
    public String criarAvaliacao(
            @PathVariable int produtoId,
            @RequestParam int nota,
            @RequestParam(required = false) String comentario,
            RedirectAttributes redirectAttributes) {

        if (GlobalData.usuarios.isEmpty()) {
            return "redirect:/login";
        }

        try {
            Usuario usuario = GlobalData.usuarios.get(0);
            Produto produto = produtoService.getProductByID(produtoId);

            Avaliacao avaliacao = new Avaliacao();
            avaliacao.setProduto(produto);
            avaliacao.setUsuario(usuario);
            avaliacao.setNota(nota);
            avaliacao.setComentario(comentario);

            avaliacaoService.criarAvaliacao(avaliacao);
            redirectAttributes.addFlashAttribute("mensagem", "Avaliação enviada com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao criar avaliação");
        }

        return "redirect:/detalhe/" + produtoId;
    }

    /**
     * Lista todas as avaliações de um produto
     * 
     * @param produtoId ID do produto
     * @param model     Model do Spring
     * @return Nome da view
     */
    @GetMapping("/produto/{produtoId}")
    public String listarAvaliacoesProduto(@PathVariable int produtoId, Model model) {
        try {
            Produto produto = produtoService.getProductByID(produtoId);
            List<Avaliacao> avaliacoes = avaliacaoService.buscarAvaliacoesVerificadas(produtoId);
            double mediaNotas = avaliacaoService.calcularMediaAvaliacoes(produtoId);

            model.addAttribute("produto", produto);
            model.addAttribute("avaliacoes", avaliacoes);
            model.addAttribute("mediaNotas", mediaNotas);
            model.addAttribute("totalAvaliacoes", avaliacoes.size());

            return "avaliacoes/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    /**
     * Remove uma avaliação (apenas o próprio usuário ou admin)
     * 
     * @param id                 ID da avaliação
     * @param redirectAttributes Atributos de redirecionamento
     * @return Redirecionamento
     */
    @GetMapping("/remover/{id}")
    public String removerAvaliacao(@PathVariable int id, RedirectAttributes redirectAttributes) {
        if (GlobalData.usuarios.isEmpty()) {
            return "redirect:/login";
        }

        try {
            avaliacaoService.removerAvaliacao(id);
            redirectAttributes.addFlashAttribute("mensagem", "Avaliação removida com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao remover avaliação");
        }

        return "redirect:/";
    }

    /**
     * Lista avaliações pendentes de verificação (apenas admin)
     * 
     * @param model Model do Spring
     * @return Nome da view
     */
    @GetMapping("/pendentes")
    public String listarAvaliacoesPendentes(Model model) {
        if (GlobalData.usuarios.isEmpty() || !GlobalData.usuarios.get(0).getPapel().equals("admin")) {
            return "redirect:/login";
        }

        List<Avaliacao> avaliacoesPendentes = avaliacaoService.listarAvaliacoesPendentes();
        model.addAttribute("avaliacoes", avaliacoesPendentes);
        return "admin/avaliacoes_pendentes";
    }

    /**
     * Verifica uma avaliação (apenas admin)
     * 
     * @param id                 ID da avaliação
     * @param redirectAttributes Atributos de redirecionamento
     * @return Redirecionamento
     */
    @GetMapping("/verificar/{id}")
    public String verificarAvaliacao(@PathVariable int id, RedirectAttributes redirectAttributes) {
        if (GlobalData.usuarios.isEmpty() || !GlobalData.usuarios.get(0).getPapel().equals("admin")) {
            return "redirect:/login";
        }

        try {
            avaliacaoService.verificarAvaliacao(id);
            redirectAttributes.addFlashAttribute("mensagem", "Avaliação verificada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao verificar avaliação");
        }

        return "redirect:/avaliacoes/pendentes";
    }
}
