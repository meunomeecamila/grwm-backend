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

// ===================================================================
// PERFIL.JS — EXIBE AS PEÇAS DO USUÁRIO LOGADO, AGRUPADAS POR CATEGORIA
// ===================================================================

document.addEventListener("DOMContentLoaded", async () => {
  // Recupera o ID e o nome do usuário logado
  const idUsuario = localStorage.getItem("idUsuario");
  const username = localStorage.getItem("username");

  // Caso não esteja logado, redireciona
  if (!idUsuario || !username) {
    alert("Nenhum usuário logado!");
    window.location.href = "/login/login.html";
    return;
  }

  // Exibe o nome do usuário no perfil
  document.querySelector(".user-name").textContent = username;

  // Seleciona o container onde os cards serão adicionados
  const conteudo = document.getElementById("conteudo-looks");

  try {
    // Faz a requisição ao backend (retorna todas as peças do usuário logado)
    const resp = await fetch(`/pecas/${idUsuario}`);
    const pecas = await resp.json();

    // Se não houver peças cadastradas
    if (pecas.length === 0) {
      conteudo.innerHTML = "<p style='text-align:center; margin-top:50px;'>Você ainda não cadastrou nenhuma peça.</p>";
      return;
    }

    // Agrupa as peças por categoria
    const categorias = {};
    pecas.forEach(p => {
      const cat = p.categoria || "Outros";
      if (!categorias[cat]) categorias[cat] = [];
      categorias[cat].push(p);
    });

    // Limpa o conteúdo anterior (caso exista)
    conteudo.innerHTML = "";

    // Cria dinamicamente as seções de categorias
    for (const categoria in categorias) {
      // Título da categoria
      const titulo = document.createElement("h4");
      titulo.textContent = categoria;
      conteudo.appendChild(titulo);

      // Container dos cards
      const cardsDiv = document.createElement("div");
      cardsDiv.classList.add("cards");
      cardsDiv.id = `cards-${categoria.toLowerCase()}`;

      // Cria cada card dentro da categoria
      categorias[categoria].forEach(p => {
        const card = document.createElement("div");
        card.classList.add("look-card");
        card.tabIndex = 0;

        const mediaDiv = document.createElement("div");
        mediaDiv.classList.add("look-card__media");

        const img = document.createElement("img");
        img.src = `data:image/*;base64,${p.fotoBase64}`;
        img.alt = p.nome || "Peça";

        const labelDiv = document.createElement("div");
        labelDiv.classList.add("look-card__label");
        labelDiv.textContent = p.nome;

        mediaDiv.appendChild(img);
        card.appendChild(mediaDiv);
        card.appendChild(labelDiv);
        cardsDiv.appendChild(card);
      });

      conteudo.appendChild(cardsDiv);
    }
  } catch (error) {
    console.error("Erro ao carregar peças:", error);
    conteudo.innerHTML = "<p style='color:red; text-align:center;'>Erro ao carregar peças. Tente novamente mais tarde.</p>";
  }
});

document.addEventListener("DOMContentLoaded", () => {
  const idUsuario = localStorage.getItem("idUsuario");
  if (!idUsuario) {
    alert("Você precisa estar logado para cadastrar peças!");
    window.location.href = "/login/login.html";
    return;
  }

  // preenche o campo oculto com o ID do usuário logado
  document.getElementById("idUsuario").value = idUsuario;
});

