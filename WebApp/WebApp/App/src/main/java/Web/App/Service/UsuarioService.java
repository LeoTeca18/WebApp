package Web.App.Service;

import Web.App.Model.DTO.Usuario;
import Web.App.Repository.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    RepositorioUsuario repositorioUsuario;
    public Usuario autenticar(String email, String senha){
        Usuario usuario = repositorioUsuario.findByEmail(email);
        if(usuario != null && usuario.getSenha().equals(senha) && usuario.isStatus()){
            return usuario;
        }
        return null;
    }

    public Usuario getUserByID(int id) {
        Optional<Usuario> optional = repositorioUsuario.findById(id);
        Usuario usuario = null;
        if (optional.isPresent()) {
             usuario = optional.get();
        } else {
            throw new RuntimeException(" Product not found by id :: " + id);
        }
        return usuario;
    }
}
