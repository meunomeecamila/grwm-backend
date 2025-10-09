package service;

import dao.PecaDAO;
import model.Peca;

public class PecaService {
    private final PecaDAO dao = new PecaDAO();

    public boolean cadastrar(Peca p) {
        return dao.inserir(p);
    }
}
