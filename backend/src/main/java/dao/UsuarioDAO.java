package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Usuario;

public class UsuarioDAO {

    // Inserir novo usuário (cadastro)
    public boolean inserir(Usuario u) {
        String sql = "INSERT INTO Usuario (username, senha) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getSenha());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Buscar usuário por username e senha (login)
    public boolean autenticar(String username, String senha) {
        String sql = "SELECT * FROM Usuario WHERE username = ? AND senha = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();
            return rs.next(); // Se achou usuário → true

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
