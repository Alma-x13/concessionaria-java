package Projeto.Concessionaria.NathanLucas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @GetMapping("/painel")
    public String painelAdmin(Model model) {

        model.addAttribute("admins", administradorRepository.findAll());
        model.addAttribute("usuarios", usuarioDAO.findAll());

        return "painelAdmin";
    }


    @PostMapping("/cadastrar-adm")
    public String cadastrarAdm(
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String senha) {

        if (administradorRepository.findByEmail(email) != null) {
            return "redirect:/admin/painel?erroEmail=true";
        }

        Administrador admin = new Administrador();
        admin.setNome(nome);
        admin.setEmail(email);
        admin.setSenha(passwordEncoder.encode(senha));
        admin.setRole("ADMIN");

        administradorRepository.save(admin);

        return "redirect:/admin/painel?cadastroOk=true";
    }


    @PostMapping("/deletar")
    public String deletarAdmin(@RequestParam Long id) {
        administradorRepository.deleteById(id);
        return "redirect:/admin/painel?adminDeletado=true";
    }


    @PostMapping("/promover")
    public String promover(@RequestParam Long id) {

        Usuario usuario = usuarioRepository.findById(id).orElse(null);

        if (usuario != null) {

            Administrador novoAdm = new Administrador();
            novoAdm.setNome(usuario.getNome());
            novoAdm.setEmail(usuario.getEmail());
            novoAdm.setSenha(usuario.getSenha());

            administradorRepository.save(novoAdm);
            usuarioRepository.delete(usuario);
        }

        return "redirect:/admin/painel?promovido=true";
    }


    @GetMapping("/editarAdmin/{id}")
    public String editarAdminTela(@PathVariable Long id, Model model) {
        model.addAttribute("admin", administradorRepository.findById(id).orElse(null));
        return "editarAdmin";
    }


    @GetMapping("/editarUsuario/{id}")
    public String editarUsuarioTela(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioRepository.findById(id).orElse(null));
        return "editarUsuario";
    }


    @GetMapping("/listar")
    public String listarAdmins(Model model) {
        model.addAttribute("admins", administradorRepository.findAll());
        return "painel_admin/lista_admin";
    }

    
    @PostMapping("/salvarEdicaoAdmin")
    public String salvarEdicaoAdmin(
            @RequestParam Long id,
            @RequestParam String nome,
            @RequestParam String email) {

        Administrador admin = administradorRepository.findById(id).orElse(null);
        if (admin != null) {
            admin.setNome(nome);
            admin.setEmail(email);
            administradorRepository.save(admin);
        }

        return "redirect:/admin/painel?editado=true";
    }


}

