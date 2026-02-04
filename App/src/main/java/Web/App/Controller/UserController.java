package Web.App.Controller;

import Web.App.Global.GlobalData;
import Web.App.Model.DTO.Usuario;
import Web.App.Repository.RepositorioProduto;
import Web.App.Repository.RepositorioUsuario;
import Web.App.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    RepositorioUsuario repositorioUsuario;
    @GetMapping("login")
    public String paginaLogin(){
        return "login";
    }
    @GetMapping("registrar")
    public String registrar(Model model){
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        return "registrar";
    }
    @PostMapping("salvarUsuario")
    public String registrarUsuario(Usuario usuario){
        usuario.setPapel("user");
        usuario.setStatus(false);
        repositorioUsuario.save(usuario);
        return "redirect:login";
    }

    @PostMapping("login")
    public String login(@RequestParam String email, @RequestParam String senha, Model model){
        Usuario usuario = usuarioService.autenticar(email, senha);
        if(usuario != null){
            if(usuario.getPapel().equals("admin")){
                GlobalData.usuarios.add(usuario);
                return "redirect:/admin";
            }else {
                GlobalData.usuarios.add(usuario);
                model.addAttribute("usuario", usuario);
                return "redirect:/";
            }
        }else {
            model.addAttribute("error", "Senha inválida ou conta desativada");
            return "login";
        }
    }
    @GetMapping("cart")
    public String cart(){
         return "cart";
    }

  }
