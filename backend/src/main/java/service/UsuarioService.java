package service;

import dao.UsuarioDAO;
import model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioService {
    private UsuarioDAO dao = new UsuarioDAO();

    // ======================================================
    // CADASTRAR USUÁRIO COM SENHA CRIPTOGRAFADA
    // ======================================================
    public boolean cadastrar(Usuario u) {
        System.out.println(">>> [DEBUG] Entrou no UsuarioService.cadastrar()");
        String senhaCriptografada = BCrypt.hashpw(u.getSenha(), BCrypt.gensalt());
        System.out.println(">>> [DEBUG] Senha original: " + u.getSenha());
        System.out.println(">>> [DEBUG] Senha criptografada: " + senhaCriptografada);

        u.setSenha(senhaCriptografada);
        return dao.inserir(u);
    }


    // ======================================================
    // AUTENTICAR USUÁRIO (LOGIN)
    // ======================================================
    public boolean autenticar(String username, String senhaDigitada) {
        // Busca o usuário pelo username
        Usuario usuarioBanco = dao.getByUsername(username);

        if (usuarioBanco != null) {
            String senhaBanco = usuarioBanco.getSenha();

            // Verifica se a senha digitada corresponde ao hash armazenado
            if (BCrypt.checkpw(senhaDigitada, senhaBanco)) {
                return true; // login bem-sucedido
            }
        }
        return false; // usuário não existe ou senha incorreta
    }

    // ======================================================
    // BUSCAR USUÁRIO PELO USERNAME (para rota de login)
    // ======================================================
    public Usuario getByUsername(String username) {
        return dao.getByUsername(username);
    }

    // ======================================================
    // OUTROS MÉTODOS MANTIDOS
    // ======================================================
    public Usuario getById(int id) {
        return dao.getById(id);
    }

    public Usuario getByUsernameAndSenha(String username, String senha) {
        // Esse método deixa de ser útil para login,
        // já que a comparação não pode mais ser feita no SQL.
        // Mantenha apenas se for usado em outras partes do sistema.
        return dao.getByUsernameAndSenha(username, senha);
    }
}
