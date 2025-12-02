package Projeto.Concessionaria.NathanLucas;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CarrinhoController {

    private final VeiculoRepository veiculoRepository;
    private final PedidoRepository pedidoRepository;

    public CarrinhoController(VeiculoRepository veiculoRepository, PedidoRepository pedidoRepository) {
    this.veiculoRepository = veiculoRepository;
    this.pedidoRepository = pedidoRepository;
}

    // ADICIONAR ITEM
    @PostMapping("/adicionarCarrinho")
    public String adicionarItem(Long id, HttpServletRequest request, HttpServletResponse response) {

        CarrinhoUtils.adicionarItem(id, request, response);
        return "redirect:/carrinho";
    }

    // MOSTRAR CARRINHO
    @GetMapping("/carrinho")
    public String mostrarCarrinho(HttpServletRequest request, Model model) {

        
        List<Long> ids = CarrinhoUtils.lerIds(request);
        List<Veiculo> itens = new ArrayList<>();
        double total = 0;

        for (Long id : ids) {
            veiculoRepository.findById(id).ifPresent(itens::add);
        }

        for (Veiculo v : itens) {
            total += v.getPreco();
        }

        model.addAttribute("itens", itens);
        model.addAttribute("total", total);
        System.out.println("IDs lidos do cookie: " + ids);
        System.out.println("Itens encontrados no banco: " + itens.size());

        return "carrinho";
    }

        @GetMapping("/remover")
        public String removerItem(Long id, HttpServletRequest request, HttpServletResponse response) {
            CarrinhoUtils.removerItem(id, request, response);
            return "redirect:/carrinho";
        }


    // LIMPAR CARRINHO
    @GetMapping("/limparCarrinho")
    public String limpar(HttpServletResponse response) {
        CarrinhoUtils.limparCarrinho(response);
        return "redirect:/carrinho";
    }

    // FINALIZAR COMPRA
    @PostMapping("/finalizarCompra")
    public String finalizarCompra(HttpServletRequest request) {

        List<Long> ids = CarrinhoUtils.lerIds(request);

        if (ids.isEmpty()) {
            return "redirect:/carrinho?erroVazio=true";
        }

        return "redirect:/pagamento";
    }

    // PAGAMENTO
    @GetMapping("/pagamento")
    public String mostrarPagamento(HttpServletRequest request, Model model) {

        List<Long> ids = CarrinhoUtils.lerIds(request);
        List<Veiculo> itens = new ArrayList<>();
        double total = 0;

        for (Long id : ids) {
            veiculoRepository.findById(id).ifPresent(itens::add);
        }

        for (Veiculo v : itens) {
            total += v.getPreco();
        }

        model.addAttribute("itens", itens);
        model.addAttribute("total", total);

        return "pagamento";
    }

        @PostMapping("/pagar")
    public String pagar(
            @RequestParam String formaPagamento,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {

        
        List<Long> ids = CarrinhoUtils.lerIds(request);
        List<Veiculo> itens = veiculoRepository.findAllById(ids);

        double total = itens.stream().mapToDouble(Veiculo::getPreco).sum();

        
        Pedido pedido = new Pedido();
        pedido.setItens(itens);
        pedido.setTotal(total);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setFormaPagamento(formaPagamento); 
        pedidoRepository.save(pedido);

        // Limpa o carrinho
        CarrinhoUtils.limparCarrinho(response);

        model.addAttribute("pedido", pedido);
        return "confirmacao";
    }







}
