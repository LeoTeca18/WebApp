package Web.App.Repository;

import Web.App.Model.DTO.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositortioProduto extends JpaRepository<Produto, Integer> {
}
