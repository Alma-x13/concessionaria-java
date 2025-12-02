package Projeto.Concessionaria.NathanLucas;

import jakarta.persistence.*;

@Entity
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Pedido pedido;

    @ManyToOne
    private Veiculo veiculo;

    private double preco;

    public ItemPedido() {}

    public ItemPedido(Pedido pedido, Veiculo veiculo) {
        this.pedido = pedido;
        this.veiculo = veiculo;
        this.preco = veiculo.getPreco();
    }

    public Long getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
        this.preco = veiculo != null ? veiculo.getPreco() : 0;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
