package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Doacao;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;



public class DoacaoDAO {

    public boolean inserir(Doacao d) {
        // Adicionei a coluna 'foto' na query
        String sql = "INSERT INTO Doacao (nome, descricao, tamanho, categoria, foto) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getNome());
            ps.setString(2, d.getDescricao());
            ps.setString(3, d.getTamanho());
            ps.setString(4, d.getCategoria());
            ps.setBytes(5, d.getFoto()); // aqui salvamos a foto como byte array

            ps.executeUpdate();
            System.out.println("✅ Doação inserida no banco com sucesso!");
            return true;

        } catch (Exception e) {
            System.out.println("❌ Erro ao inserir doação no banco:");
            e.printStackTrace();
            return false;
        }
    }

    public Doacao getById(int id){
        String sql = "SELECT * FROM Doacao WHERE id = ?";
        Doacao doacao = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    doacao = new Doacao();
                    doacao.setId(rs.getInt("id"));
                    doacao.setNome(rs.getString("nome"));
                    doacao.setDescricao(rs.getString("descricao"));
                    doacao.setTamanho(rs.getString("tamanho"));
                    doacao.setCategoria(rs.getString("categoria"));
                    doacao.setFoto(rs.getBytes("foto")); // Pega os bytes da foto
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Erro ao buscar peça no banco:");
            e.printStackTrace();
        }
        return doacao;

    }
    
    public List<Doacao> listarTodas() {
        List<Doacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM doacao";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Doacao d = new Doacao();
                d.setId(rs.getInt("id"));
                d.setNome(rs.getString("nome"));
                d.setDescricao(rs.getString("descricao"));
                d.setTamanho(rs.getString("tamanho"));
                d.setCategoria(rs.getString("categoria"));
                d.setFoto(rs.getBytes("foto")); // caso tenha imagem
                lista.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}
