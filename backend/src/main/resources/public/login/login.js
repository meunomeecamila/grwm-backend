// /public/login/login.js
const form = document.querySelector("#form-login");

form.addEventListener("submit", async (event) => {
  event.preventDefault(); // impede o envio tradicional do form

  const username = document.querySelector("#username").value.trim();
  const senha = document.querySelector("#senha").value.trim();

  try {
    const response = await fetch("/login", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: `username=${encodeURIComponent(username)}&senha=${encodeURIComponent(senha)}`
    });

    if (!response.ok) {
      alert("Usuário ou senha incorretos!");
      return;
    }

    const usuario = await response.json();

    // salva o usuário logado no navegador
    localStorage.setItem("idUsuario", usuario.id);
    localStorage.setItem("username", usuario.username);

    // redireciona manualmente
    window.location.href = "/inicio/inicio.html";

  } catch (error) {
    console.error("Erro no login:", error);
    alert("Erro de conexão com o servidor.");
  }
});
