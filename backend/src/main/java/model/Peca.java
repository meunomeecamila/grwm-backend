package model;

public class Peca {
    private int id;
    private String nome;
    private String cor;
    private String ocasiao;
    private String descricao;
    private String categoria;
    private byte[] foto; // adicionando a foto como byte array

    public Peca() {}

    public Peca(String nome, String cor, String ocasiao, String descricao, String categoria, byte[] foto) {
        this.nome = nome;
        this.cor = cor;
        this.ocasiao = ocasiao;
        this.descricao = descricao;
        this.categoria = categoria;
        this.foto = foto;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    public String getOcasiao() { return ocasiao; }
    public void setOcasiao(String ocasiao) { this.ocasiao = ocasiao; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public byte[] getFoto() { return foto; }
    public void setFoto(byte[] foto) { this.foto = foto; }
}