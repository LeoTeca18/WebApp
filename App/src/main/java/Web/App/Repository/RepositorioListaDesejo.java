package Web.App.Repository;

import Web.App.Model.DTO.ListaDesejo;
import Web.App.Model.DTO.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositorioListaDesejo extends JpaRepository<ListaDesejo, Integer> {

    @Query("SELECT l.produto FROM ListaDesejo l WHERE l.usuario.id = :id")
    List<Produto> findProdutosByUsuarioId(@Param("id")int usuarioId);
}
