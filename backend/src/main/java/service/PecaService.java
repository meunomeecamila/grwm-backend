package service;

import java.util.List; // <-- IMPORT NECESSÁRIO

import dao.PecaDAO;
import model.Peca;

public class PecaService {

    private final PecaDAO dao = new PecaDAO();

    // ======================================================
    // Cadastrar nova peça
    // ======================================================
    public boolean cadastrar(Peca p) {
        return dao.inserir(p);
    }

    // ======================================================
    // Buscar peça por ID
    // ======================================================
    public Peca getById(int id) {
        return dao.getById(id);
    }

    // ======================================================
    // Listar peças por usuário (para exibir no perfil)
    // ======================================================
    public List<Peca> listarPorUsuario(int idUsuario) {
        return dao.listarPorUsuario(idUsuario);
    }
    
    public List<Peca> listarTodas() {
        return dao.listarTodas();
    }
    
    // ======================================================
    // Excluir peça por ID
    // ======================================================
    public boolean excluir(int id) {
        return dao.excluir(id);
    }

}
