// Pega os elementos
const sidebar = document.getElementById('sidebar');
const logoBtn  = document.getElementById('openSideBar'); // mantém igual ao seu HTML

// Configurações (bata com seu CSS)
const SIDEBAR_WIDTH = 250; // mesma largura definida na .sidebar
const PADDING_LEFT  = 35;  // distância inicial (left: 16px)

// Animação suave da logo (sem tocar no CSS)
logoBtn.style.transition = 'left 0.3s ease';
// Garante que fique clicável acima da sidebar
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

//Barra de pesquisa
document.getElementById("search").addEventListener("input", function () {
  const termo = this.value.toLowerCase(); // texto digitado em minúsculo
  const cards = document.querySelectorAll("#cards-explorar .look-card"); // pega todos os cards

  cards.forEach(card => {
    const label = card.querySelector(".look-card__label").textContent.toLowerCase();

    if (label.includes(termo)) {
      card.style.display = "block"; // mostra se combinar
    } else {
      card.style.display = "none"; // esconde se não combinar
    }
  });
});

document.addEventListener("DOMContentLoaded", async () => {
  console.log("✅ inicio.js carregado!");

  const container = document.getElementById("cards-inicio");
  if (!container) return;

  container.innerHTML = `<p>Carregando looks e doações...</p>`;

  try {
    const res = await fetch("/inicio");
    const itens = await res.json();

    console.log("📦 Itens recebidos:", itens);
    container.innerHTML = "";

    if (!itens || itens.length === 0) {
      container.innerHTML = "<p>Nenhum item disponível ainda 😢</p>";
      return;
    }

    itens.forEach(item => {
      const card = document.createElement("div");
      card.classList.add("look-card");

      const media = document.createElement("div");
      media.classList.add("look-card__media");

      const img = document.createElement("img");
      img.src = item.fotoBase64 && item.fotoBase64.length > 0
        ? `data:image/*;base64,${item.fotoBase64}`
        : "imgs/imagem-padrao.png";
      img.alt = item.nome;
      media.appendChild(img);

      const label = document.createElement("div");
      label.classList.add("look-card__label");

      const tipo = item.ocasiao ? "Peça" : "Doação";
      label.textContent = `${item.nome} (${tipo})`;

      card.appendChild(media);
      card.appendChild(label);
      container.appendChild(card);
    });
  } catch (error) {
    console.error("❌ Erro ao carregar itens:", error);
    container.innerHTML = "<p>Erro ao carregar conteúdo :(</p>";
  }
});

