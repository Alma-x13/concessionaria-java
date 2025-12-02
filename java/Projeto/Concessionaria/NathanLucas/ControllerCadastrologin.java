package Projeto.Concessionaria.NathanLucas;

import java.io.IOException;
import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@Controller
public class ControllerCadastrologin {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private ConcessionariaRepository concessionariaRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdministradorRepository administradorRepository;

    // CADASTRAR USUÁRIO 
    @GetMapping("/register")
    public String formCadastro() {
        return "cadastro";
    }

    @PostMapping("/register")
    public String cadastrar(
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String telefone,
            @RequestParam String senha,
            Model model) {

        if (usuarioDAO.existsByEmail(email)) {
            model.addAttribute("erro", "Já existe uma conta com esse e-mail!");
            return "cadastro";
        }
        if (usuarioDAO.existsByNome(nome)) {
            model.addAttribute("erro", "Já existe alguém com esse nome de usuário!");
            return "cadastro";
        }

        Usuario u = new Usuario();
        u.setNome(nome);
        u.setEmail(email);
        u.setTelefone(telefone);
        u.setSenha(passwordEncoder.encode(senha));
        u.setRole("USER");

        usuarioDAO.save(u);

        model.addAttribute("comSucesso", "Conta criada com sucesso! Faça login.");
        return "cadastro";
    }


        @PostMapping("/atualizarPerfil")
    public String atualizarPerfil(@RequestParam String campo, @RequestParam String valor, Authentication auth) {
        if (auth == null) return "redirect:/longi";

        String email = auth.getName();
        Usuario usuario = usuarioDAO.findByEmail(email).orElse(null);
        Administrador admin = null;

        if (usuario == null) {
            admin = administradorRepository.findByEmail(email);
            if (admin == null) return "redirect:/longi";
        }

        if (usuario != null) {
            switch(campo) {
                case "nome": usuario.setNome(valor); break;
                case "email": usuario.setEmail(valor); break;
                case "telefone": usuario.setTelefone(valor); break;
                case "senha": usuario.setSenha(passwordEncoder.encode(valor)); break;
            }
            usuarioDAO.save(usuario);
        } else {
            switch(campo) {
                case "nome": admin.setNome(valor); break;
                case "email": admin.setEmail(valor); break;
                case "telefone": admin.setTelefone(valor); break;
                case "senha": admin.setSenha(passwordEncoder.encode(valor)); break;
            }
            administradorRepository.save(admin);
        }

        return "redirect:/perfil";
    }




    @GetMapping("/perfil")
    public String perfil(Model model, Authentication auth) {
        if (auth == null) return "redirect:/longi";

        String email = auth.getName();
        Usuario usuario = usuarioDAO.findByEmail(email).orElse(null);

        if (usuario != null) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("tipo", "usuario");
            return "perfil";
        }

        Administrador admin = administradorRepository.findByEmail(email);
        if (admin != null) {
            model.addAttribute("usuario", admin);
            model.addAttribute("tipo", "admin");
            return "perfil";
        }

        return "redirect:/longi";
    }

        @PostMapping("/atualizarFoto")
    public String atualizarFoto(@RequestParam("foto") MultipartFile foto, Authentication auth) throws IOException {
        if (auth == null) return "redirect:/longi";

        String email = auth.getName();

        Usuario usuario = usuarioDAO.findByEmail(email).orElse(null);
        Administrador admin = null;

        if (usuario == null) {
            admin = administradorRepository.findByEmail(email);
            if (admin == null) {
                return "redirect:/longi"; 
            }
        }

       
        String pastaFotos = "C:/NathanLucas/fotos_perfil/";
        File diretorio = new File(pastaFotos);
        if (!diretorio.exists()) diretorio.mkdirs();

        
        String nomeArquivo = System.currentTimeMillis() + "_" + foto.getOriginalFilename();
        File arquivoDestino = new File(diretorio, nomeArquivo);
        foto.transferTo(arquivoDestino);

        if (usuario != null) {
            usuario.setFotoPerfil("/fotos_perfil/" + nomeArquivo);
            usuarioDAO.save(usuario);
        } else {
            admin.setFotoPerfil("/fotos_perfil/" + nomeArquivo);
            administradorRepository.save(admin);
        }

        return "redirect:/perfil";
    }



    // REDIRECIONAR POR TIPO 
    @GetMapping("/redirecionar")
    public String redirecionar(Authentication auth) {

        Usuario u = usuarioDAO.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        if ("ADMIN".equals(u.getRole())) return "redirect:/admin";

        return "redirect:/loja";
    }


    @GetMapping("/criar-admin")
    @ResponseBody
    public String criarAdmin() {

    if (administradorRepository.findByEmail("admin@root.com") != null) {
        return "Admin já existe!";
    }

    

    Administrador admin = new Administrador();
    admin.setNome("Administrador Principal");
    admin.setEmail("admin@root.com");
    admin.setSenha(passwordEncoder.encode("123456"));
    admin.setTelefone("15000000");

    administradorRepository.save(admin);

    return "Admin criado com sucesso!";
}

        @Controller
        public class PaginasController {

            @GetMapping("/informacoes")
            public String informacoes() {
        
                return "informacoes"; 
            }
        }

            

    // PÁGINAS NORMAL
    @GetMapping("/longi") public String loginPage() { return "longi"; }
    @GetMapping("/carros") public String carros() { return "carros"; }
    @GetMapping("/motos") public String motos() { return "motos"; }
    @GetMapping("/caminhoes") public String caminhoes() { return "caminhoes"; }
    @GetMapping("/civic") public String civic() { return "civic"; }
    @GetMapping("/fusion") public String fusion() { return "fusion"; }
    @GetMapping("/lexus") public String lexus() { return "lexus"; }
    @GetMapping("/harley") public String harley() { return "harley"; }
    @GetMapping("/kawasaki") public String kawasaki() { return "kawasaki"; }
    @GetMapping("/vespa") public String vespa() { return "vespa"; }
    @GetMapping("/daf") public String daf() { return "daf"; }
    @GetMapping("/scania") public String scania() { return "scania"; }
    @GetMapping("/mercedes") public String mercedes() { return "mercedes"; }




    @GetMapping("/apagar-concessionaria")
    @ResponseBody
    public String apagarConcessionaria() {
        concessionariaRepository.deleteAll();  // Deleta todos os registros
        return "Concessionária apagada!";
    }
}
