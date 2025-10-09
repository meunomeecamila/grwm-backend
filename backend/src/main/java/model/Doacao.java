package model;

public class Doacao {
    private int id;
    private String nome;
    private String descricao;
    private String tamanho;
    private String categoria;

    public Doacao() {}

    public Doacao(String nome, String descricao, String tamanho, String categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.tamanho = tamanho;
        this.categoria = categoria;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getTamanho() { return tamanho; }
    public void setTamanho(String tamanho) { this.tamanho = tamanho; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
