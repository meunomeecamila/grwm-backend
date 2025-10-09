package app;

import java.sql.Connection;
import dao.ConnectionFactory;

public class TesteConexao {
    public static void main(String[] args) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            System.out.println((conn != null && !conn.isClosed())
                ? "Conexão OK"
                : "Falha na conexão");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
