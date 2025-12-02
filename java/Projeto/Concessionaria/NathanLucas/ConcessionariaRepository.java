package Projeto.Concessionaria.NathanLucas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcessionariaRepository extends JpaRepository<Concessionaria, Long> {
    }
