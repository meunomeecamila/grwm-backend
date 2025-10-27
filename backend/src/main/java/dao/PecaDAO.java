package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Peca;

public class PecaDAO {

    // =========================================================
    // INSERIR NOVA PEÇA
    // =========================================================
    public boolean inserir(Peca p) {
        // Adicionei a coluna 'id_usuario' para associar ao dono da peça
        String sql = "INSERT INTO peca (nome, cor, ocasiao, descricao, categoria, foto, id_usuario) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNome());
            ps.setString(2, p.getCor());
            ps.setString(3, p.getOcasiao());
            ps.setString(4, p.getDescricao());
            ps.setString(5, p.getCategoria());
            ps.setBytes(6, p.getFoto()); // salva a foto como byte[]
            ps.setInt(7, p.getIdUsuario()); // id do usuário logado

            ps.executeUpdate();
            System.out.println("✅ Peça inserida no banco com sucesso!");
            return true;

        } catch (Exception e) {
            System.out.println("❌ Erro ao inserir peça no banco:");
            e.printStackTrace();
            return false;
        }
    }

    // =========================================================
    // BUSCAR PEÇA POR ID
    // =========================================================
    public Peca getById(int id) {
        String sql = "SELECT * FROM peca WHERE id = ?";
        Peca peca = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    peca = new Peca();
                    peca.setId(rs.getInt("id"));
                    peca.setNome(rs.getString("nome"));
                    peca.setCor(rs.getString("cor"));
                    peca.setOcasiao(rs.getString("ocasiao"));
                    peca.setDescricao(rs.getString("descricao"));
                    peca.setCategoria(rs.getString("categoria"));
                    peca.setFoto(rs.getBytes("foto"));
                    peca.setIdUsuario(rs.getInt("id_usuario"));
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Erro ao buscar peça no banco:");
            e.printStackTrace();
        }
        return peca;
    }

    // =========================================================
    // LISTAR TODAS AS PEÇAS DE UM USUÁRIO
    // =========================================================
    public List<Peca> listarPorUsuario(int idUsuario) {
        List<Peca> lista = new ArrayList<>();

        String sql = "SELECT * FROM peca WHERE id_usuario = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Peca p = new Peca();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setCor(rs.getString("cor"));
                p.setOcasiao(rs.getString("ocasiao"));
                p.setDescricao(rs.getString("descricao"));
                p.setCategoria(rs.getString("categoria"));

                // ESSENCIAL: ler a imagem salva no banco
                p.setFoto(rs.getBytes("foto"));

                // id do usuário dono da peça
                p.setIdUsuario(rs.getInt("id_usuario"));

                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar peças do usuário:");
            e.printStackTrace();
        }

        return lista;
    }
    
    public List<Peca> listarTodas() {
        List<Peca> lista = new ArrayList<>();
        String sql = "SELECT * FROM peca";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Peca p = new Peca();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setCor(rs.getString("cor"));
                p.setOcasiao(rs.getString("ocasiao"));
                p.setDescricao(rs.getString("descricao"));
                p.setCategoria(rs.getString("categoria"));
                p.setFoto(rs.getBytes("foto"));
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
   
    // =========================================================
    // EXCLUIR PEÇA POR ID
    // =========================================================
    
    public boolean excluir(int id) {
    	String sql = "DELETE FROM peca WHERE id = ?";
    	
    	try(Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)){
    		ps.setInt(1, id);
            int linhasAfetadas = ps.executeUpdate(); 

            // Verifica se alguma linha foi realmente deletada
            if (linhasAfetadas > 0) {
                System.out.println("Peça com ID " + id + " excluída com sucesso!");
                return true;
            } else {
                System.out.println("Nenhuma peça encontrada com ID " + id + " para excluir.");
                return false; 
            }

        } catch (Exception e) {
            System.out.println("Erro ao excluir peça no banco:");
            e.printStackTrace();
            return false;
        }
    }
    

}
