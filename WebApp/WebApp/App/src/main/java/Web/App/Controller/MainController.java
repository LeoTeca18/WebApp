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

@Controller
public class MainController {

    @Autowired
    RepositorioListaDesejo repositorioListaDesejo;
    @Autowired
    RepositorioProduto repositortioProduto;
    @Autowired
    ProdutoService produtoService;

    @GetMapping("/")
    public String index(Model model) {

            var produtos = repositortioProduto.findAll();
            model.addAttribute("produto", produtos);
            Produto produto1 = repositortioProduto.findById(1).orElse(null);
            model.addAttribute("produto1", produto1);
            Produto produto2 = repositortioProduto.findById(2).orElse(null);
            model.addAttribute("produto2", produto2);
            Produto produto3 = repositortioProduto.findById(3).orElse(null);
            model.addAttribute("produto3", produto3);
            Produto produto4 = repositortioProduto.findById(4).orElse(null);
            model.addAttribute("produto4", produto4);
            Produto produto5 = repositortioProduto.findById(5).orElse(null);
            model.addAttribute("produto5", produto5);
            Produto produto6 = repositortioProduto.findById(6).orElse(null);
            model.addAttribute("produto6", produto6);
            model.addAttribute("cartCount", GlobalData.produtos.size());
            model.addAttribute("total", GlobalData.produtos.stream().mapToDouble(Produto::getPreco).sum());
            model.addAttribute("cart", GlobalData.produtos);
            return "index";
    }


    @GetMapping("adicionarCart/{id}")
    public String adicionarCart(Model model, @PathVariable(value = "id")int id){
        if(GlobalData.usuarios.isEmpty()){
            return "redirect:/login";
        }else{

                    Produto produto = produtoService.getProductByID(id);
                    GlobalData.produtos.add(produto);

            return "redirect:/";
        }

    }
    @GetMapping("removerCart")
    public String removerCart(){
            GlobalData.produtos.clear();
            return "redirect:/";
    }

    @GetMapping("pesquisa")
    public String pesquisa(@RequestParam("pesquisa") String nome, Model model){
        var produtos = repositortioProduto.findByNomeContaining(nome);
        model.addAttribute("produto", produtos);
        return "pesquisa";
    }

    @GetMapping("detalhe/{id}")
    public String detalhe(@PathVariable(value = "id")int id, Model model){
        var produto = this.produtoService.getProductByID(id);
        model.addAttribute("produto", produto);
        return "detalhe";
    }
    @GetMapping("desejo/{id}")
    public String desejo(@PathVariable(value = "id")int id, Model model, ListaDesejo listaDesejo) {
        if (GlobalData.usuarios.isEmpty()) {
            return "redirect:/login";
        } else {
            var produto = produtoService.getProductByID(id);
            listaDesejo.setProduto(produto);
            listaDesejo.setUsuario(GlobalData.usuarios.get(0));
            repositorioListaDesejo.save(listaDesejo);
            return "redirect:/";
        }
    }

    @GetMapping("desejo")
    public String desejo(Model model){
        if (GlobalData.usuarios.isEmpty()) {
            return "redirect:/login";
        } else {
        var produto = repositorioListaDesejo.findProdutosByUsuarioId(GlobalData.usuarios.get(0).getId());
        model.addAttribute("produto", produto);
        return "lista_desejo";
        }
    }
}
