package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Peca;

public class PecaDAO {

    public boolean inserir(Peca p) {
        // Adicionei a coluna 'foto' na query
        String sql = "INSERT INTO Peca (nome, cor, ocasiao, descricao, categoria, foto) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNome());
            ps.setString(2, p.getCor());
            ps.setString(3, p.getOcasiao());
            ps.setString(4, p.getDescricao());
            ps.setString(5, p.getCategoria());
            ps.setBytes(6, p.getFoto()); // aqui salvamos a foto como byte array

            ps.executeUpdate();
            System.out.println("✅ Peça inserida no banco com sucesso!");
            return true;

        } catch (Exception e) {
            System.out.println("❌ Erro ao inserir peça no banco:");
            e.printStackTrace();
            return false;
        }
    }
    
    public Peca getById(int id) {
        String sql = "SELECT * FROM Peca WHERE id = ?";
        Peca peca = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    peca = new Peca();
                    peca.setId(rs.getInt("id"));
                    peca.setNome(rs.getString("nome"));
                    peca.setCor(rs.getString("cor"));
                    peca.setOcasiao(rs.getString("ocasiao"));
                    peca.setDescricao(rs.getString("descricao"));
                    peca.setCategoria(rs.getString("categoria"));
                    peca.setFoto(rs.getBytes("foto")); // Pega os bytes da foto
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Erro ao buscar peça no banco:");
            e.printStackTrace();
        }
        return peca;
    }
}