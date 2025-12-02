package Projeto.Concessionaria.NathanLucas;

import jakarta.persistence.*;

@Entity
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String marca;
    private String imagem;
    private double preco;

    public Veiculo() {}

    public Veiculo(String nome, String marca, String imagem, double preco) {
        this.nome = nome;
        this.marca = marca;
        this.imagem = imagem;
        this.preco = preco;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getMarca() { return marca; }
    public String getImagem() { return imagem; }
    public double getPreco() { return preco; }

    public void setNome(String nome) { this.nome = nome; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setImagem(String imagem) { this.imagem = imagem; }
    public void setPreco(double preco) { this.preco = preco; }
}
