package Web.App.Controller;

import Web.App.Global.GlobalData;
import Web.App.Model.DTO.ListaDesejo;
import Web.App.Model.DTO.Produto;
import Web.App.Repository.RepositorioListaDesejo;
import Web.App.Repository.RepositorioProduto;
import Web.App.Service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller principal da aplicação.
 * Gerencia a página inicial, carrinho de compras, pesquisa e lista de desejos.
 * 
 * @author Sistema E-commerce
 * @version 2.0
 * @since 2026-02-01
 */
@Controller
public class MainController {

    @Autowired
    RepositorioListaDesejo repositorioListaDesejo;
    @Autowired
    RepositorioProduto repositortioProduto;
    @Autowired
    ProdutoService produtoService;

    /**
     * Exibe a página inicial com produtos em destaque
     * @param model Model do Spring
     * @return Nome da view
     */
    @GetMapping("/")
    public String index(Model model) {
        try {
            var produtos = repositortioProduto.findAll();
            model.addAttribute("produto", produtos);
            
            // Produtos em destaque (primeiros 6)
            for (int i = 1; i <= 6; i++) {
                Produto produto = repositortioProduto.findById(i).orElse(null);
                model.addAttribute("produto" + i, produto);
            }
            
            model.addAttribute("cartCount", GlobalData.produtos.size());
            model.addAttribute("total", GlobalData.produtos.stream()
                    .mapToDouble(Produto::getPreco).sum());
            model.addAttribute("cart", GlobalData.produtos);
            
            return "index";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao carregar produtos");
            return "index";
        }
    }

    /**
     * Adiciona um produto ao carrinho
     * @param id ID do produto
     * @param redirectAttributes Atributos de redirecionamento
     * @return Redirecionamento
     */
    @GetMapping("adicionarCart/{id}")
    public String adicionarCart(@PathVariable(value = "id") int id, 
                               RedirectAttributes redirectAttributes) {
        if (GlobalData.usuarios.isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "Faça login para adicionar ao carrinho");
            return "redirect:/login";
        }

        try {
            Produto produto = produtoService.getProductByID(id);
            
            if (!produto.isDisponivel()) {
                redirectAttributes.addFlashAttribute("erro", "Produto indisponível");
                return "redirect:/";
            }
            
            GlobalData.produtos.add(produto);
            redirectAttributes.addFlashAttribute("mensagem", 
                    "Produto adicionado ao carrinho com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao adicionar produto");
        }

        return "redirect:/";
    }

    /**
     * Remove um produto específico do carrinho
     * @param id ID do produto a remover
     * @param redirectAttributes Atributos de redirecionamento
     * @return Redirecionamento
     */
    @GetMapping("removerCart/{id}")
    public String removerItemCart(@PathVariable(value = "id") int id,
                                  RedirectAttributes redirectAttributes) {
        try {
            GlobalData.produtos.removeIf(p -> p.getId() == id);
            redirectAttributes.addFlashAttribute("mensagem", "Produto removido do carrinho");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao remover produto");
        }
        return "redirect:/cart";
    }

    /**
     * Limpa todo o carrinho
     * @param redirectAttributes Atributos de redirecionamento
     * @return Redirecionamento
     */
    @GetMapping("removerCart")
    public String removerCart(RedirectAttributes redirectAttributes) {
        GlobalData.produtos.clear();
        redirectAttributes.addFlashAttribute("mensagem", "Carrinho limpo com sucesso");
        return "redirect:/";
    }

    /**
     * Realiza pesquisa de produtos por nome
     * @param nome Nome ou parte do nome do produto
     * @param model Model do Spring
     * @return Nome da view
     */
    @GetMapping("pesquisa")
    public String pesquisa(@RequestParam("pesquisa") String nome, Model model) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                model.addAttribute("erro", "Digite algo para pesquisar");
                model.addAttribute("produto", repositortioProduto.findAll());
            } else {
                var produtos = repositortioProduto.findByNomeContaining(nome);
                model.addAttribute("produto", produtos);
                model.addAttribute("termoPesquisa", nome);
                
                if (produtos.isEmpty()) {
                    model.addAttribute("mensagem", "Nenhum produto encontrado");
                }
            }
            
            model.addAttribute("cartCount", GlobalData.produtos.size());
            return "pesquisa";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao realizar pesquisa");
            return "pesquisa";
        }
    }

    /**
     * Exibe detalhes de um produto específico
     * @param id ID do produto
     * @param model Model do Spring
     * @return Nome da view
     */
    @GetMapping("detalhe/{id}")
    public String detalhe(@PathVariable(value = "id") int id, Model model) {
        try {
            var produto = this.produtoService.getProductByID(id);
            model.addAttribute("produto", produto);
            model.addAttribute("cartCount", GlobalData.produtos.size());
            model.addAttribute("mediaAvaliacoes", produto.getMediaAvaliacoes());
            return "detalhe";
        } catch (Exception e) {
            model.addAttribute("erro", "Produto não encontrado");
            return "redirect:/";
        }
    }

    /**
     * Adiciona um produto à lista de desejos do usuário
     * @param id ID do produto
     * @param listaDesejo Objeto lista de desejos
     * @param redirectAttributes Atributos de redirecionamento
     * @return Redirecionamento
     */
    @GetMapping("desejo/{id}")
    public String desejo(@PathVariable(value = "id") int id, 
                        ListaDesejo listaDesejo,
                        RedirectAttributes redirectAttributes) {
        if (GlobalData.usuarios.isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "Faça login para adicionar à lista de desejos");
            return "redirect:/login";
        }

        try {
            var produto = produtoService.getProductByID(id);
            listaDesejo.setProduto(produto);
            listaDesejo.setUsuario(GlobalData.usuarios.get(0));
            repositorioListaDesejo.save(listaDesejo);
            redirectAttributes.addFlashAttribute("mensagem", 
                    "Produto adicionado à lista de desejos!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao adicionar à lista de desejos");
        }

        return "redirect:/";
    }

    /**
     * Exibe a lista de desejos do usuário
     * @param model Model do Spring
     * @return Nome da view
     */
    @GetMapping("desejo")
    public String desejo(Model model) {
        if (GlobalData.usuarios.isEmpty()) {
            return "redirect:/login";
        }

        try {
            var produtos = repositorioListaDesejo.findProdutosByUsuarioId(
                    GlobalData.usuarios.get(0).getId());
            model.addAttribute("produto", produtos);
            model.addAttribute("cartCount", GlobalData.produtos.size());
            return "lista_desejo";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao carregar lista de desejos");
            return "lista_desejo";
        }
    }

    /**
     * Remove um produto da lista de desejos
     * @param id ID do produto
     * @param redirectAttributes Atributos de redirecionamento
     * @return Redirecionamento
     */
    @GetMapping("desejo/remover/{id}")
    public String removerDesejo(@PathVariable(value = "id") int id,
                                RedirectAttributes redirectAttributes) {
        if (GlobalData.usuarios.isEmpty()) {
            return "redirect:/login";
        }

        try {
            repositorioListaDesejo.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagem", 
                    "Produto removido da lista de desejos");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao remover produto");
        }

        return "redirect:/desejo";
    }
}
