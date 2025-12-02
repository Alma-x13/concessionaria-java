package Projeto.Concessionaria.NathanLucas;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UsuarioDAO extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);
    boolean existsByNome(String nome);
    

    Optional<Usuario> findByTelefone(String telefone);
    Optional<Usuario> findByNome(String nome);
    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByRole(String role);
}
