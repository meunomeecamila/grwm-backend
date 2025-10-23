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

document.getElementById("formDoacao").addEventListener("submit", async (e) => {
    e.preventDefault(); // impede recarregamento automático
    const formData = new FormData(e.target);

    const response = await fetch("/doacao", {
        method: "POST",
        body: formData
    });

    const data = await response.json();

    if (response.ok) {
        // redireciona se deu certo
        window.location.href = "sucesso.html";
    } else {
        // exibe alerta e continua na página
        alert(data.message);
    }
});

// ===================================================================
// DOACAO.JS — EXIBE TODAS AS DOAÇÕES NO FEED AUTOMATICAMENTE
// ===================================================================

document.addEventListener("DOMContentLoaded", async () => {
  console.log("✅ doacao.js carregado!");

  const container = document.getElementById("cards-explorar");

  try {
    const res = await fetch("/doacoes");
    const doacoes = await res.json();

    console.log("📦 Doações recebidas:", doacoes);

    // Limpa o container
    container.innerHTML = "";

    if (!doacoes || doacoes.length === 0) {
      container.innerHTML = "<p>Nenhuma doação cadastrada ainda 😢</p>";
      return;
    }

    // Cria os cards dinamicamente
    doacoes.forEach(d => {
      console.log("🧱 Montando card de:", d.nome);

      const card = document.createElement("div");
      card.classList.add("look-card");

      const media = document.createElement("div");
      media.classList.add("look-card__media");

      const img = document.createElement("img");
      if (d.fotoBase64 && d.fotoBase64.length > 0) {
        img.src = `data:image/*;base64,${d.fotoBase64}`;
      } else {
        img.src = "imgs/imagem-padrao.png";
      }
      img.alt = d.nome;
      media.appendChild(img);

      const label = document.createElement("div");
      label.classList.add("look-card__label");
      label.textContent = `${d.nome} - ${d.categoria}`;

      card.appendChild(media);
      card.appendChild(label);
      container.appendChild(card);
    });
  } catch (error) {
    console.error("❌ Erro ao carregar doações:", error);
    container.innerHTML = "<p>Erro ao carregar doações :(</p>";
  }
});
