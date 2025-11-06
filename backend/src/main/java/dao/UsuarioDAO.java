package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Usuario;

public class UsuarioDAO {

    // ======================================================
    // INSERIR NOVO USUÁRIO (CADASTRO)
    // ======================================================
    public boolean inserir(Usuario u) {
        String sql = "INSERT INTO usuario (username, senha) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getSenha()); // já vem criptografada do Service

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ======================================================
    // BUSCAR USUÁRIO POR USERNAME (para login)
    // ======================================================
    public Usuario getByUsername(String username) {
        Usuario u = null;
        String sql = "SELECT * FROM usuario WHERE username = ?";

        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setSenha(rs.getString("senha")); // hash armazenado no banco
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    // ======================================================
    // NÃO USAR MAIS ESSA VERSÃO DE LOGIN (deixe apenas se o código antigo depende)
    // ======================================================
    @Deprecated
    public boolean autenticar(String username, String senha) {
        // ❌ Essa abordagem não funciona mais com senha criptografada
        // Mantida apenas por compatibilidade temporária
        return false;
    }

    // ======================================================
    // BUSCAR USUÁRIO PELO ID (ex.: perfil)
    // ======================================================
    public Usuario getById(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        Usuario usuario = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setUsername(rs.getString("username"));
                usuario.setSenha(rs.getString("senha"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuario;
    }

    // ======================================================
    // ANTIGO GET BY USERNAME AND SENHA — AGORA OBSOLETO
    // ======================================================
    @Deprecated
    public Usuario getByUsernameAndSenha(String username, String senha) {
        // ❌ Não faz mais sentido comparar senhas no SQL.
        // Use getByUsername() + BCrypt.checkpw() no Service.
        return null;
    }
}
