package Web.App.Controller;

import Web.App.Model.DTO.Pedido;
import Web.App.Model.DTO.Produto;
import Web.App.Model.DTO.Usuario;
import Web.App.Service.PedidoService;
import Web.App.Global.GlobalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller para gerenciamento de pedidos.
 * Permite criar, visualizar e acompanhar pedidos.
 * 
 * @author Sistema E-commerce
 * @version 1.0
 * @since 2026-02-01
 */
@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    /**
     * Exibe o formulário de checkout
     * @param model Model do Spring
     * @return Nome da view
     */
    @GetMapping("/checkout")
    public String checkout(Model model) {
        if (GlobalData.usuarios.isEmpty()) {
            return "redirect:/login";
        }

        if (GlobalData.produtos.isEmpty()) {
            return "redirect:/cart";
        }

        Usuario usuario = GlobalData.usuarios.get(0);
        double total = GlobalData.produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();

        model.addAttribute("usuario", usuario);
        model.addAttribute("produtos", GlobalData.produtos);
        model.addAttribute("total", total);

        return "pedidos/checkout";
    }

    /**
     * Finaliza a compra criando um novo pedido
     * @param endereco Endereço de entrega
     * @param formaPagamento Forma de pagamento escolhida
     * @param redirectAttributes Atributos de redirecionamento
     * @return Redirecionamento
     */
    @PostMapping("/finalizar")
    public String finalizarPedido(
            @RequestParam String endereco,
            @RequestParam String formaPagamento,
            RedirectAttributes redirectAttributes) {
        
        if (GlobalData.usuarios.isEmpty()) {
            return "redirect:/login";
        }

        try {
            Usuario usuario = GlobalData.usuarios.get(0);
            Pedido pedido = pedidoService.criarPedido(
                usuario, 
                GlobalData.produtos, 
                endereco, 
                formaPagamento
            );

            // Limpa o carrinho
            GlobalData.produtos.clear();

            redirectAttributes.addFlashAttribute("mensagem", 
                "Pedido #" + pedido.getId() + " realizado com sucesso!");
            return "redirect:/pedidos/" + pedido.getId();
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/pedidos/checkout";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao processar pedido");
            return "redirect:/pedidos/checkout";
        }
    }

    /**
     * Exibe detalhes de um pedido específico
     * @param id ID do pedido
     * @param model Model do Spring
     * @return Nome da view
     */
    @GetMapping("/{id}")
    public String detalhesPedido(@PathVariable int id, Model model) {
        if (GlobalData.usuarios.isEmpty()) {
            return "redirect:/login";
        }

        try {
            Pedido pedido = pedidoService.buscarPorId(id);
            Usuario usuario = GlobalData.usuarios.get(0);

            // Verifica se o pedido pertence ao usuário ou se é admin
            if (pedido.getUsuario().getId() != usuario.getId() && 
                !usuario.getPapel().equals("admin")) {
                return "redirect:/pedidos/meus";
            }

            model.addAttribute("pedido", pedido);
            return "pedidos/detalhes";
        } catch (Exception e) {
            return "redirect:/pedidos/meus";
        }
    }

    /**
     * Lista todos os pedidos do usuário logado
     * @param model Model do Spring
     * @return Nome da view
     */
    @GetMapping("/meus")
    public String meusPedidos(Model model) {
        if (GlobalData.usuarios.isEmpty()) {
            return "redirect:/login";
        }

        Usuario usuario = GlobalData.usuarios.get(0);
        List<Pedido> pedidos = pedidoService.buscarPedidosUsuario(usuario.getId());

        model.addAttribute("pedidos", pedidos);
        return "pedidos/meus_pedidos";
    }

    /**
     * Cancela um pedido (usuário ou admin)
     * @param id ID do pedido
     * @param redirectAttributes Atributos de redirecionamento
     * @return Redirecionamento
     */
    @GetMapping("/cancelar/{id}")
    public String cancelarPedido(@PathVariable int id, RedirectAttributes redirectAttributes) {
        if (GlobalData.usuarios.isEmpty()) {
            return "redirect:/login";
        }

        try {
            Pedido pedido = pedidoService.buscarPorId(id);
            Usuario usuario = GlobalData.usuarios.get(0);

            // Verifica permissão
            if (pedido.getUsuario().getId() != usuario.getId() && 
                !usuario.getPapel().equals("admin")) {
                redirectAttributes.addFlashAttribute("erro", "Sem permissão");
                return "redirect:/pedidos/meus";
            }

            pedidoService.cancelarPedido(id);
            redirectAttributes.addFlashAttribute("mensagem", "Pedido cancelado com sucesso!");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao cancelar pedido");
        }

        return "redirect:/pedidos/meus";
    }

    /**
     * Lista todos os pedidos (apenas admin)
     * @param model Model do Spring
     * @return Nome da view
     */
    @GetMapping("/admin/listar")
    public String listarTodosPedidos(Model model) {
        if (GlobalData.usuarios.isEmpty() || !GlobalData.usuarios.get(0).getPapel().equals("admin")) {
            return "redirect:/login";
        }

        List<Pedido> pedidos = pedidoService.listarTodosPedidos();
        model.addAttribute("pedidos", pedidos);
        return "admin/pedidos_lista";
    }

    /**
     * Atualiza o status de um pedido (apenas admin)
     * @param id ID do pedido
     * @param status Novo status
     * @param redirectAttributes Atributos de redirecionamento
     * @return Redirecionamento
     */
    @PostMapping("/admin/status/{id}")
    public String atualizarStatus(
            @PathVariable int id,
            @RequestParam String status,
            RedirectAttributes redirectAttributes) {
        
        if (GlobalData.usuarios.isEmpty() || !GlobalData.usuarios.get(0).getPapel().equals("admin")) {
            return "redirect:/login";
        }

        try {
            pedidoService.atualizarStatus(id, status);
            redirectAttributes.addFlashAttribute("mensagem", "Status atualizado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar status");
        }

        return "redirect:/pedidos/admin/listar";
    }
}
