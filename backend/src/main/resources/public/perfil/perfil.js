// ===================================================================
// PERFIL.JS — BUSCA E EXIBE OS DADOS DO USUÁRIO
// ===================================================================

const idUsuario = localStorage.getItem("idUsuario");
const username  = localStorage.getItem("username");

if (!idUsuario || !username) {
  alert("Nenhum usuário logado!");
  window.location.href = "/login/login.html";
} else {
  const nomeEl = document.querySelector(".user-name");
  const emailEl = document.querySelector(".user-email");

  if (nomeEl)  nomeEl.textContent  = username;
  if (emailEl) emailEl.textContent = username + "@email.com";
}

// ===================================================================
// SIDEBAR E CLIQUES
// ===================================================================

const sidebar = document.getElementById('sidebar');
const logoBtn = document.getElementById('openSideBar'); // botão do logo

// Configurações (bata com seu CSS)
const SIDEBAR_WIDTH = 250; // mesma largura definida na .sidebar
const PADDING_LEFT  = 35;  // distância inicial (left: 35px)

// Verifica se os elementos existem antes de aplicar o script
if (logoBtn && sidebar) {
  logoBtn.style.transition = 'left 0.3s ease';
  logoBtn.style.zIndex = 1101;

  // Toggle: abre/fecha a sidebar e move a logo
  logoBtn.addEventListener('click', () => {
    sidebar.classList.toggle('active');

    if (sidebar.classList.contains('active')) {
      // empurra a logo para a direita quando a barra abre
      logoBtn.style.left = (SIDEBAR_WIDTH + PADDING_LEFT) + 'px';
    } else {
      // volta a logo para a posição original
      logoBtn.style.left = PADDING_LEFT + 'px';
    }
  });
}

// ===================================================================
// NAVEGAÇÃO PARA A PÁGINA DE DETALHE DE PEÇA
// ===================================================================

const blusa = document.getElementById("blusa1");
if (blusa) {
  blusa.addEventListener('click', () => {
    window.location.href = "detalheperfil.html";
  });
}
