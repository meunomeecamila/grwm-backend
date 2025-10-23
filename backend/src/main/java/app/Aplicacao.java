package app;

import static spark.Spark.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

import javax.servlet.MultipartConfigElement;

import com.google.gson.Gson;

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

        // Serviços
        DoacaoService doacaoService = new DoacaoService();
        PecaService pecaService = new PecaService();
        UsuarioService usuarioService = new UsuarioService();

        // ===================== ROTA DE DOAÇÃO =====================
        post("/doacao", (req, res) -> {
            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

            String nome = req.queryParams("nome");
            String descricao = req.queryParams("descricao");
            String tamanho = req.queryParams("tamanho");
            String categoria = req.queryParams("categoria");

            byte[] foto = null;

            try (InputStream is = req.raw().getPart("imagem").getInputStream()) {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] data = new byte[1024];

                while ((nRead = is.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }

                foto = buffer.toByteArray();

            } catch (Exception e) {
                e.printStackTrace();
                res.status(400);
                return "<script>alert('Erro ao processar a imagem :('); history.back();</script>";
            }

            Doacao d = new Doacao(nome, descricao, tamanho, categoria, foto);
            boolean ok = doacaoService.cadastrar(d);

            if (ok) {
                res.redirect("/doacao/sucesso.html");
                return null;
            } else {
                res.status(400);
                return "<script>alert('Erro ao cadastrar doação.'); history.back();</script>";
            }
        });

        // ===================== ROTA DE PEÇA =====================
        post("/peca", (req, res) -> {
            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

            String nome = req.queryParams("nome");
            String cor = req.queryParams("cor");
            String ocasiao = req.queryParams("ocasiao");
            String descricao = req.queryParams("descricao");
            String categoria = req.queryParams("categoria");
            int idUsuario = Integer.parseInt(req.queryParams("idUsuario")); // <-- vem do front

            byte[] foto = null;

            try (InputStream is = req.raw().getPart("imagem").getInputStream()) {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] data = new byte[1024];
                while ((nRead = is.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                foto = buffer.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
                res.status(400);
                return "<script>alert('Erro ao processar a imagem :('); history.back();</script>";
            }

            // Monta objeto
            Peca p = new Peca(nome, cor, ocasiao, descricao, categoria, foto);
            p.setIdUsuario(idUsuario); // associa ao dono

            boolean ok = pecaService.cadastrar(p);

            if (ok) {
                res.redirect("/perfil/sucesso.html");
                return null;
            } else {
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

        // ===================== ROTA DE LOGIN =====================
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

     // ===================================================
     // ROTA: Lista todas as peças de um usuário
     // ===================================================
        get("/pecas/:idUsuario", (req, res) -> {
            int idUsuario = Integer.parseInt(req.params(":idUsuario"));
            res.type("application/json");

            List<Peca> pecas = pecaService.listarPorUsuario(idUsuario);

            for (Peca p : pecas) {
                byte[] bytes = p.getFoto();
                if (bytes != null && bytes.length > 0) {
                    String base64 = Base64.getEncoder().encodeToString(bytes);
                    p.setFotoBase64(base64);
                    System.out.println("✅ FOTO CONVERTIDA DE " + p.getNome() + ": " + base64.substring(0, 20) + "..."); // debug
                } else {
                    p.setFotoBase64("");
                    System.out.println("⚠️ FOTO NULA DE " + p.getNome());
                }
                p.setFoto(null); // remove array bruto
            }

            String json = new Gson().toJson(pecas);
            System.out.println("📦 JSON ENVIADO: " + json.substring(0, Math.min(200, json.length())) + "...");
            return json;
        });

    }
}
