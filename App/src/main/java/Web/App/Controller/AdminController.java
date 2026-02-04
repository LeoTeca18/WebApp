package Web.App.Controller;

import Web.App.Global.GlobalData;
import Web.App.Model.DTO.Produto;
import Web.App.Model.DTO.Usuario;
import Web.App.Repository.RepositorioProduto;
import Web.App.Repository.RepositorioUsuario;
import Web.App.Service.ProdutoService;
import Web.App.Upload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
public class AdminController {

    private static final String url = "C:\\Users\\DELL\\IdeaProjects\\WebApp\\App\\src\\main\\resources\\static\\assets\\images\\upload";
    @Autowired
    RepositorioUsuario repositorioUsuario;
    @Autowired
    RepositorioProduto repositorioProduto;
    @Autowired
    ProdutoService produtoService;

    @GetMapping("admin")
    public String admin(Model model) {
        if (GlobalData.usuarios.isEmpty()) {
            return "login";
        } else if (GlobalData.usuarios.get(0).getPapel().equals("admin")) {
            model.addAttribute("produtoDisponivel", repositorioProduto.countByQuantidadeGreaterThanZero());
            model.addAttribute("produtoIndisponivel", repositorioProduto.countByQuantidadeEqualsZero());
            model.addAttribute("usuariosAtivo", repositorioUsuario.countByStatusTrue());
            model.addAttribute("usuarioDesactivados", repositorioUsuario.countByStatusFalse());
            model.addAttribute("produtos", repositorioProduto.countBy());
            return "admin/index";
        }
        return "login";
    }
    @GetMapping("salvar")
    public String salvar(Model model) {
        Produto produto = new Produto();
        if(GlobalData.usuarios.isEmpty()){
            return "login";
        }else if(GlobalData.usuarios.get(0).getPapel().equals("admin")) {
            model.addAttribute("produto", produto);
            return "admin/salvar_produto";
        }
        return null;
    }

    @GetMapping("listar")
    public String listar(Model model) {
        if(GlobalData.usuarios.isEmpty()){
            return "login";
        }else if(GlobalData.usuarios.get(0).getPapel().equals("admin")) {
            model.addAttribute("produto", repositorioProduto.findAll());
            return "admin/listar_produto";
        }
        return "login";
    }

    @PostMapping("/salvar")
    public String salvar(Produto produto, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String nomeFicheiro = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            produto.setImages(nomeFicheiro);
            repositorioProduto.save(produto);
            String diretorio = url;
            FileUpload.salvarFicheiro(diretorio, nomeFicheiro, multipartFile);
        } else {
            if (produto.getImages().isEmpty()) {
                produto.setImages(null);
                repositorioProduto.save(produto);
            }
        }
        repositorioProduto.save(produto);
        return "redirect:/listar";
    }

    @GetMapping("editar/{id}")
    public String editar(@PathVariable(value = "id")int id, Model model) {
        Produto produto = produtoService.getProductByID(id);
        model.addAttribute("produto", produto);
        return "editar";
    }
    @GetMapping("apagar/{id}")
    public String apagar(@PathVariable(value = "id")int id, Model model) {
        this.repositorioProduto.deleteById(id);
        return "redirect:/listar";
    }

    @GetMapping("usuarioAtivo")
    public String usuarioAtivo(Model model){
        var usuarios = repositorioUsuario.findByStatusTrue();
        model.addAttribute("usuarios", usuarios);
        return "admin/usuario_ativo";
    }
    @GetMapping("usuarioDesativado")
    public String usuarioDesativado(Model model){
        var usuarios = repositorioUsuario.findByStatusFalse();
        model.addAttribute("usuarios", usuarios);
        return "admin/usuario_desativado";
    }

    @GetMapping("ativar/{id}")
    public String ativar(@PathVariable(value = "id")int id) {
        Usuario usuario = repositorioUsuario.getReferenceById(id);
        usuario.setStatus(true);
        repositorioUsuario.save(usuario);
        return "redirect:/usuarioDesativado";
    }

    @GetMapping("desativar/{id}")
    public String desativar(@PathVariable(value = "id")int id) {
        Usuario usuario = repositorioUsuario.getReferenceById(id);
        usuario.setStatus(false);
        repositorioUsuario.save(usuario);
        return "redirect:/usuarioAtivo";
    }
}
