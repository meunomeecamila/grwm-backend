// ===================================================================
// LÓGICA PARA BUSCAR E EXIBIR OS DADOS DO USUÁRIO
// ===================================================================

// Executa o código quando a página HTML terminar de carregar
// src/main/resources/public/perfil/perfil.js

// Exemplo: usuário com id=1
// (depois podemos deixar dinâmico com session ou localStorage)
fetch("/usuario/1")
  .then(response => {
    if (!response.ok) {
      throw new Error("Usuário não encontrado");
    }
    return response.json();
  })
  .then(usuario => {
    document.querySelector(".user-name").textContent = usuario.username;
    document.querySelector(".user-email").textContent = usuario.username + "@email.com"; // fictício
  })
  .catch(error => console.error(error));


// ===================================================================
// SIDEBAR E CLIQUES
// ===================================================================

// Pega os elementos da sidebar
const sidebar = document.getElementById('sidebar');
const logoBtn  = document.getElementById('openSideBar');

// Animação suave da logo
logoBtn.style.transition = 'left 0.3s ease';
logoBtn.style.zIndex = 1101;

// Toggle: abre/fecha a sidebar
logoBtn.addEventListener('click', () => {
  sidebar.classList.toggle('active');

  if (sidebar.classList.contains('active')) {
    logoBtn.style.left = '285px'; 
  } else {
    logoBtn.style.left = '35px';
  }
});

// Navegação para a página de detalhe
document.getElementById("blusa1").addEventListener('click', function() {
  window.location.href = "detalheperfil.html";
});