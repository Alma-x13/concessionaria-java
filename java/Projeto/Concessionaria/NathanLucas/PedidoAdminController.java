package Projeto.Concessionaria.NathanLucas;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

    @Controller
@RequestMapping("/admin")
public class PedidoAdminController {

    private final PedidoRepository pedidoRepository;

    public PedidoAdminController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @GetMapping("/pedidos")
    public String listarPedidos(Model model) {
        model.addAttribute("pedidos", pedidoRepository.findAll());
        return "admin/pedidos";
    }
}


