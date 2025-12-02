package Projeto.Concessionaria.NathanLucas;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;



@Controller
@RequestMapping("/admin/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    // FORMULÁRIO PARA ADICIONAR NOVO VEÍCULO
    @GetMapping("/novo")
    public String novoVeiculo(@RequestParam(required = false) String categoria, Model model) {

        Veiculo veiculo = new Veiculo();

        if (categoria != null) {
            veiculo.setCategoria(categoria);
        }

        model.addAttribute("veiculo", veiculo);
        return "admin/adicionarVeiculo";
    }

   @PostMapping("/salvar")
public String salvarVeiculo(
        @ModelAttribute Veiculo veiculo,
        @RequestParam("imagemFile") MultipartFile file
) throws IOException {

    if (!file.isEmpty()) {
        String uploadDir = "src/main/resources/static/uploads/";

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Path.of(uploadDir + fileName);

        // Criar pasta se não existir
        Files.createDirectories(path.getParent());

        // Salvar arquivo
        Files.write(path, file.getBytes());

        // Caminho que o HTML vai acessar
        veiculo.setImagem("/uploads/" + fileName);
    }

    veiculoRepository.save(veiculo);
    return "redirect:/admin/veiculos/lista";
}


    // LISTAR VEÍCULOS
    @GetMapping("/lista")
    public String listarVeiculos(Model model) {
        model.addAttribute("veiculos", veiculoRepository.findAll());
        return "admin/listaVeiculos";
    }

    // DELETAR VEÍCULO
    @GetMapping("/deletar/{id}")
    public String deletarVeiculo(@PathVariable Long id) {
        veiculoRepository.deleteById(id);
        return "redirect:/admin/veiculos/lista";
    }

    // EDITAR VEÍCULO
    @GetMapping("/editar/{id}")
    public String editarVeiculo(@PathVariable Long id, Model model) {

        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));

        model.addAttribute("veiculo", veiculo);

        return "admin/adicionarVeiculo"; // usa o mesmo form para editar
    }




}
