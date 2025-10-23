package service;

import dao.DoacaoDAO;
import model.Doacao;
import java.util.List;

public class DoacaoService {

    private DoacaoDAO dao = new DoacaoDAO();

    public boolean cadastrar(Doacao d) {
        // Podemos fazer um if para validar os dados 
        return dao.inserir(d);
    }

    public Doacao getById(int id) {
        return dao.getById(id);
    }

    // NOVO MÉTODO ↓↓↓
    public List<Doacao> listarTodas() {
        return dao.listarTodas();
    }
}
