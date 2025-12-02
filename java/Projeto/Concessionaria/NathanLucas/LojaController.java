package Projeto.Concessionaria.NathanLucas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LojaController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @GetMapping("/loja")
    public String loja(Model model) {
        model.addAttribute("veiculos", veiculoRepository.findAll());
        return "Loja"; 
    }
}
