package Web.App.Repository;

import Web.App.Model.DTO.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositorioUsuario extends JpaRepository<Usuario, Integer> {
    Usuario findByEmail(String email);
    int countByStatusTrue();
    int countByStatusFalse();

    List<Usuario> findByStatusTrue();
    List<Usuario> findByStatusFalse();
}
