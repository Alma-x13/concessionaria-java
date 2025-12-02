package Projeto.Concessionaria.NathanLucas;

public class ItemCarrinho {
    private Long idVeiculo;
    private String nome;
    private String imagem;
    private double preco;

    public ItemCarrinho() {}

    public ItemCarrinho(Long idVeiculo, String nome, String imagem, double preco) {
        this.idVeiculo = idVeiculo;
        this.nome = nome;
        this.imagem = imagem;
        this.preco = preco;
    }

    public Long getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Long idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}

