package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Doacao;

public class DoacaoDAO {

    public boolean inserir(Doacao d) {
        String sql = "INSERT INTO Doacao (nome, descricao, tamanho, categoria) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getNome());
            ps.setString(2, d.getDescricao());
            ps.setString(3, d.getTamanho());
            ps.setString(4, d.getCategoria());

            ps.executeUpdate();
            System.out.println("✅ Doação inserida no banco com sucesso!");
            return true;

        } catch (Exception e) {
            System.out.println("❌ Erro ao inserir doação no banco:");
            e.printStackTrace();
            return false;
        }
    }
}
