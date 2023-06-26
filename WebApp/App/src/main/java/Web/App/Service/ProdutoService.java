package Web.App.Service;

import Web.App.Model.DTO.Produto;
import Web.App.Repository.RepositorioProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    RepositorioProduto repositortioProduto;

    public Produto getProductByID(int id) {
        Optional<Produto> optional = repositortioProduto.findById(id);
        Produto produto = null;
        if (optional.isPresent()) {
            produto = optional.get();
        } else {
            throw new RuntimeException(" Product not found by id :: " + id);
        }
        return produto;
    }

}
