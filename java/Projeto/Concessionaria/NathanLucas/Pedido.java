package Projeto.Concessionaria.NathanLucas;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double total;

    private String formaPagamento;
    private LocalDateTime dataHora;

    @ManyToMany
    private List<Veiculo> itens;

    public Pedido() {}

    public Long getId() { return id; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public List<Veiculo> getItens() { return itens; }
    public void setItens(List<Veiculo> itens) { this.itens = itens; }

    public String getFormaPagamento() {
        return formaPagamento;
    }
    
    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    
}


