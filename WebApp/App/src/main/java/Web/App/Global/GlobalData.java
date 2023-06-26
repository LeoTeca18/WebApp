package Web.App.Global;

import Web.App.Model.DTO.Produto;
import Web.App.Model.DTO.Usuario;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    public static List<Produto> produtos;

    static {
            produtos = new ArrayList<Produto>();
    }

    public static List<Usuario> usuarios;

    static {
        usuarios = new ArrayList<Usuario>();
    }

}
