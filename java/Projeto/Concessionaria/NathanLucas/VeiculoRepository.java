package Projeto.Concessionaria.NathanLucas;

import org.springframework.data.jpa.repository.JpaRepository;


public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    Veiculo findByNome(String nome);
}
