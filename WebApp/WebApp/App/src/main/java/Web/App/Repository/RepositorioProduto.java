package Web.App.Repository;

import Web.App.Model.DTO.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositorioProduto extends JpaRepository<Produto, Integer> {
    int countBy();
    @Query("SELECT COUNT(p) FROM Produto p WHERE p.quantidade = 0")
    int countByQuantidadeEqualsZero();
    @Query("SELECT COUNT(p) FROM Produto p WHERE p.quantidade > 0")
    int countByQuantidadeGreaterThanZero();

    List<Produto> findByNomeContaining(String nome);
}
