package Projeto.Concessionaria.NathanLucas;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByRole(String role);
}
