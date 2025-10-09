package app;

import static spark.Spark.*;

import model.Doacao;
import model.Peca;
import model.Usuario;
import service.DoacaoService;
import service.PecaService;
import service.UsuarioService;


public class Aplicacao {
    public static void main(String[] args) {
        // Porta padrão e local dos arquivos estáticos (HTML, CSS, JS)
        port(8081);
        staticFileLocation("/public");

        // ===================== ROTA DE DOAÇÃO =====================
        DoacaoService doacaoService = new DoacaoService();

        post("/doacao", (req, res) -> {
            String nome = req.queryParams("nome");
            String descricao = req.queryParams("descricao");
            String tamanho = req.queryParams("tamanho");
            String categoria = req.queryParams("categoria");

            Doacao d = new Doacao(nome, descricao, tamanho, categoria);
            boolean ok = doacaoService.cadastrar(d);

            res.type("application/json");
            res.status(ok ? 200 : 500);

            return ok
                ? "{\"message\":\"Doação cadastrada com sucesso!\"}"
                : "{\"message\":\"Erro ao cadastrar doação.\"}";
        });

        // ===================== ROTA DE PEÇA (PERFIL) =====================
        PecaService pecaService = new PecaService();

        post("/peca", (req, res) -> {
            String nome = req.queryParams("nome");
            String cor = req.queryParams("cor");
            String ocasiao = req.queryParams("ocasiao");
            String descricao = req.queryParams("descricao");
            String categoria = req.queryParams("categoria");

            Peca p = new Peca(nome, cor, ocasiao, descricao, categoria);
            boolean ok = pecaService.cadastrar(p);

            res.type("application/json");
            res.status(ok ? 200 : 500);

            return ok
                ? "{\"message\":\"Peça cadastrada com sucesso!\"}"
                : "{\"message\":\"Erro ao cadastrar peça.\"}";
        });
        
     // ===================== ROTA DE USUÁRIO (LOGIN) =====================
        
        UsuarioService usuarioService = new UsuarioService();

        post("/usuario", (req, res) -> {
            String username = req.queryParams("username");
            String senha = req.queryParams("senha");

            Usuario u = new Usuario(username, senha);
            boolean ok = usuarioService.cadastrar(u);

            if (ok) {
                // Se deu certo → redireciona para a tela de login
                res.redirect("/login.html");
                return null; // encerra a execução da rota
            } else {
                res.status(500);
                return "Erro ao cadastrar usuário.";
            }
        });


    }
}
