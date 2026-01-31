package Web.App.Controller;

import Web.App.Model.DTO.Categoria;
import Web.App.Service.CategoriaService;
import Web.App.Service.ProdutoService;
import Web.App.Global.GlobalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller para gerenciamento de categorias de produtos.
 * Permite criar, editar, listar e remover categorias.
 * 
 * @author Sistema E-commerce
 * @version 1.0
 * @since 2026-02-01
 */
@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProdutoService produtoService;

    /**
     * Lista todas as categorias
     * @param model Model do Spring
     * @return Nome da view
     */
    @GetMapping
    public String listarCategorias(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodasCategorias());
        return "categorias/lista";
    }

    /**
     * Lista produtos de uma categoria específica
     * @param id ID da categoria
     * @param model Model do Spring
     * @return Nome da view
     */
    @GetMapping("/{id}/produtos")
    public String listarProdutosCategoria(@PathVariable int id, Model model) {
        try {
            Categoria categoria = categoriaService.buscarPorId(id);
            var produtos = produtoService.buscarPorCategoria(id);
            
            model.addAttribute("categoria", categoria);
            model.addAttribute("produtos", produtos);
            model.addAttribute("cartCount", GlobalData.produtos.size());
            
            return "categorias/produtos";
        } catch (Exception e) {
            return "redirect:/categorias";
        }
    }

    /**
     * Exibe formulário para criar nova categoria (apenas admin)
     * @param model Model do Spring
     * @return Nome da view
     */
    @GetMapping("/nova")
    public String novaCategoria(Model model) {
        if (GlobalData.usuarios.isEmpty() || !GlobalData.usuarios.get(0).getPapel().equals("admin")) {
            return "redirect:/login";
        }

        model.addAttribute("categoria", new Categoria());
        return "admin/categoria_form";
    }

    /**
     * Salva uma nova categoria (apenas admin)
     * @param categoria Dados da categoria
     * @param redirectAttributes Atributos de redirecionamento
     * @return Redirecionamento
     */
    @PostMapping("/salvar")
    public String salvarCategoria(@ModelAttribute Categoria categoria, RedirectAttributes redirectAttributes) {
        if (GlobalData.usuarios.isEmpty() || !GlobalData.usuarios.get(0).getPapel().equals("admin")) {
            return "redirect:/login";
        }

        try {
            categoriaService.criarCategoria(categoria);
            redirectAttributes.addFlashAttribute("mensagem", "Categoria criada com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/categorias/nova";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao criar categoria");
            return "redirect:/categorias/nova";
        }

        return "redirect:/admin/categorias";
    }

    /**
     * Exibe formulário para editar categoria (apenas admin)
     * @param id ID da categoria
     * @param model Model do Spring
     * @return Nome da view
     */
    @GetMapping("/editar/{id}")
    public String editarCategoria(@PathVariable int id, Model model) {
        if (GlobalData.usuarios.isEmpty() || !GlobalData.usuarios.get(0).getPapel().equals("admin")) {
            return "redirect:/login";
        }

        try {
            Categoria categoria = categoriaService.buscarPorId(id);
            model.addAttribute("categoria", categoria);
            return "admin/categoria_form";
        } catch (Exception e) {
            return "redirect:/admin/categorias";
        }
    }

    /**
     * Atualiza uma categoria existente (apenas admin)
     * @param categoria Dados da categoria
     * @param redirectAttributes Atributos de redirecionamento
     * @return Redirecionamento
     */
    @PostMapping("/atualizar")
    public String atualizarCategoria(@ModelAttribute Categoria categoria, RedirectAttributes redirectAttributes) {
        if (GlobalData.usuarios.isEmpty() || !GlobalData.usuarios.get(0).getPapel().equals("admin")) {
            return "redirect:/login";
        }

        try {
            categoriaService.atualizarCategoria(categoria);
            redirectAttributes.addFlashAttribute("mensagem", "Categoria atualizada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar categoria");
        }

        return "redirect:/admin/categorias";
    }

    /**
     * Ativa ou desativa uma categoria (apenas admin)
     * @param id ID da categoria
     * @param ativa Status a ser definido
     * @param redirectAttributes Atributos de redirecionamento
     * @return Redirecionamento
     */
    @GetMapping("/status/{id}")
    public String alterarStatus(@PathVariable int id, @RequestParam boolean ativa, RedirectAttributes redirectAttributes) {
        if (GlobalData.usuarios.isEmpty() || !GlobalData.usuarios.get(0).getPapel().equals("admin")) {
            return "redirect:/login";
        }

        try {
            categoriaService.alterarStatus(id, ativa);
            redirectAttributes.addFlashAttribute("mensagem", "Status alterado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao alterar status");
        }

        return "redirect:/admin/categorias";
    }

    /**
     * Remove uma categoria (apenas admin)
     * @param id ID da categoria
     * @param redirectAttributes Atributos de redirecionamento
     * @return Redirecionamento
     */
    @GetMapping("/remover/{id}")
    public String removerCategoria(@PathVariable int id, RedirectAttributes redirectAttributes) {
        if (GlobalData.usuarios.isEmpty() || !GlobalData.usuarios.get(0).getPapel().equals("admin")) {
            return "redirect:/login";
        }

        try {
            categoriaService.removerCategoria(id);
            redirectAttributes.addFlashAttribute("mensagem", "Categoria removida com sucesso!");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao remover categoria");
        }

        return "redirect:/admin/categorias";
    }

    /**
     * Lista categorias no painel admin (apenas admin)
     * @param model Model do Spring
     * @return Nome da view
     */
    @GetMapping("/admin/listar")
    public String listarCategoriasAdmin(Model model) {
        if (GlobalData.usuarios.isEmpty() || !GlobalData.usuarios.get(0).getPapel().equals("admin")) {
            return "redirect:/login";
        }

        model.addAttribute("categorias", categoriaService.listarTodasCategorias());
        return "admin/categorias_lista";
    }
}
