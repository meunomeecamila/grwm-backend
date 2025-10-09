package model;

public class Usuario {
    private int id;
    private String username;
    private String senha;

    // Construtor vazio
    public Usuario() {}

    // Construtor completo
    public Usuario(String username, String senha) {
        this.username = username;
        this.senha = senha;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
