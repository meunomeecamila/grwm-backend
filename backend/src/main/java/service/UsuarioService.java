package service;

import dao.UsuarioDAO;
import model.Usuario;

public class UsuarioService {
    private UsuarioDAO dao = new UsuarioDAO();

    public boolean cadastrar(Usuario u) {
        return dao.inserir(u);
    }

    public boolean autenticar(String username, String senha) {
        return dao.autenticar(username, senha);
    }
}

