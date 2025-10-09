package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Peca;

public class PecaDAO {

    public boolean inserir(Peca p) {
        String sql = "INSERT INTO Peca (nome, cor, ocasiao, descricao, categoria) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNome());
            ps.setString(2, p.getCor());
            ps.setString(3, p.getOcasiao());
            ps.setString(4, p.getDescricao());
            ps.setString(5, p.getCategoria());

            ps.executeUpdate();
            System.out.println("✅ Peça inserida no banco com sucesso!");
            return true;

        } catch (Exception e) {
            System.out.println("❌ Erro ao inserir peça no banco:");
            e.printStackTrace();
            return false;
        }
    }
}
