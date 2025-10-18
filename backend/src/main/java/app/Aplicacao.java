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
        port(8081);
        staticFileLocation("/public");

        DoacaoService doacaoService = new DoacaoService();
        PecaService pecaService = new PecaService();
        UsuarioService usuarioService = new UsuarioService();

        // ===================== ROTA DE DOAÇÃO =====================
        post("/doacao", (req, res) -> {
            String nome = req.queryParams("nome");
            String descricao = req.queryParams("descricao");
            String tamanho = req.queryParams("tamanho");
            String categoria = req.queryParams("categoria");

            Doacao d = new Doacao(nome, descricao, tamanho, categoria);
            boolean ok = doacaoService.cadastrar(d);

            res.type("application/json");

            if (ok) {
                // Redireciona para a página de sucesso
                res.redirect("/doacao/sucesso.html");
                return null; // precisa retornar null após o redirect
            } else {
                // Retorna mensagem de erro sem sair da página
                res.status(400);
                return "<script>alert('Erro ao cadastrar peça.'); history.back();</script>";
            }
        });

        // ===================== ROTA DE PEÇA =====================
        post("/peca", (req, res) -> {
            String nome = req.queryParams("nome");
            String cor = req.queryParams("cor");
            String ocasiao = req.queryParams("ocasiao");
            String descricao = req.queryParams("descricao");
            String categoria = req.queryParams("categoria");

            Peca p = new Peca(nome, cor, ocasiao, descricao, categoria);
            boolean ok = pecaService.cadastrar(p);

            if (ok) {
                // Se cadastrou com sucesso → redireciona para sucesso.html
                res.redirect("/doacao/sucesso.html");
                return null; // Spark exige um retorno nulo após redirect
            } else {
                // Se deu erro → mostra alerta e permanece na página
                res.status(400);
                res.type("text/html");
                return "<script>alert('Erro ao cadastrar peça.'); history.back();</script>";
            }
        });


        // ===================== ROTA DE USUÁRIO =====================
        post("/usuario", (req, res) -> {
            String username = req.queryParams("username");
            String senha = req.queryParams("senha");

            Usuario u = new Usuario(username, senha);
            boolean ok = usuarioService.cadastrar(u);

            if (ok) {
                res.redirect("/login/login.html");
                return null;
            } else {
                res.status(500);
                return "Erro ao cadastrar usuário.";
            }
        });

        // ===================== ROTA DE LOGIN (funcionava antes) =====================
        post("/login", (req, res) -> {
            String username = req.queryParams("username");
            String senha = req.queryParams("senha");

            Usuario usuario = usuarioService.getByUsernameAndSenha(username, senha);

            res.type("application/json");

            if (usuario != null) {
                res.status(200);
                return "{\"id\": " + usuario.getId() + ", \"username\": \"" + usuario.getUsername() + "\"}";
            } else {
                res.status(401);
                return "{\"message\": \"Usuário ou senha incorretos.\"}";
            }
        });

        // ===================== ROTA DE PERFIL =====================
        get("/usuario/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Usuario usuario = usuarioService.getById(id);

            res.type("application/json");

            if (usuario != null) {
                res.status(200);
                return "{\"id\": " + usuario.getId() +
                       ", \"username\": \"" + usuario.getUsername() + "\"}";
            } else {
                res.status(404);
                return "{\"message\":\"Usuário não encontrado.\"}";
            }
        });
    }
}
