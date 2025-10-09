package service;

import dao.DoacaoDAO;
import model.Doacao;

public class DoacaoService {

    private DoacaoDAO dao = new DoacaoDAO();

    public boolean cadastrar(Doacao d) {
        // Podemos fazer um if para validar os dados 
        return dao.inserir(d);
    }
}
