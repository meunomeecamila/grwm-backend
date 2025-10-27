// ===================================================================
// GRWM — DOACAO.JS
// Funciona tanto em doacao.html (feed) quanto em cadastroDoacoes.html (cadastro)
// Inclui: Sidebar, Cadastro, e Feed automático
// ===================================================================

document.addEventListener("DOMContentLoaded", () => {
  console.log("✅ doacao.js carregado!");

  // ==============================
  // 🩷 1️⃣ SIDEBAR INTERATIVA
  // ==============================
  const sidebar = document.getElementById('sidebar');
  const logoBtn = document.getElementById('openSideBar');

  if (sidebar && logoBtn) {
    const SIDEBAR_WIDTH = 250; // mesma largura definida no CSS (.sidebar)
    const PADDING_LEFT = 35;   // distância inicial

    // deixa a animação mais suave
    logoBtn.style.transition = 'left 0.3s ease';
    logoBtn.style.zIndex = 1101;

    // Toggle de abrir/fechar
    logoBtn.addEventListener('click', () => {
      sidebar.classList.toggle('active');

      if (sidebar.classList.contains('active')) {
        logoBtn.style.left = (SIDEBAR_WIDTH + PADDING_LEFT) + 'px';
      } else {
        logoBtn.style.left = PADDING_LEFT + 'px';
      }
    });
  }

  // ==============================
  // 🩷 2️⃣ CADASTRO DE DOAÇÃO
  // ==============================
  const formDoacao = document.getElementById("formDoacao");
  if (formDoacao) {
    console.log("📋 Página de cadastro detectada.");

    formDoacao.addEventListener("submit", async (e) => {
      e.preventDefault();
      const formData = new FormData(e.target);

      try {
        const response = await fetch("/doacao", {
          method: "POST",
          body: formData
        });

        const data = await response.json();

        if (response.ok) {
          window.location.href = "sucesso.html";
        } else {
          alert(data.message || "Erro ao cadastrar doação.");
        }
      } catch (error) {
        console.error("❌ Erro ao enviar doação:", error);
        alert("Erro ao cadastrar a doação.");
      }
    });
  }

  // ==============================
  // 🩷 3️⃣ FEED DE DOAÇÕES AUTOMÁTICO
  // ==============================
  const container = document.getElementById("cards-explorar");
  if (container) {
    console.log("📰 Página de feed detectada.");

    // Mostra um “loading” enquanto carrega
    container.innerHTML = `
      <div class="loading" style="text-align:center; margin-top:30px;">
        <i class="bi bi-arrow-repeat" style="font-size:2rem; animation:spin 1s linear infinite;"></i>
        <p>Carregando doações...</p>
      </div>
    `;

    async function carregarDoacoes() {
      try {
        const res = await fetch("/doacoes");
        const doacoes = await res.json();

        console.log("📦 Doações recebidas:", doacoes);

        container.innerHTML = "";

        if (!doacoes || doacoes.length === 0) {
          container.innerHTML = "<p>Nenhuma doação cadastrada ainda 😢</p>";
          return;
        }

        // Cria cards dinamicamente
        doacoes.forEach(d => {
          console.log("🧱 Montando card de:", d.nome);

          const card = document.createElement("div");
          card.classList.add("look-card");

          const media = document.createElement("div");
          media.classList.add("look-card__media");

          const img = document.createElement("img");
          img.src = d.fotoBase64 && d.fotoBase64.length > 0
            ? `data:image/*;base64,${d.fotoBase64}`
            : "imgs/imagem-padrao.png";
          img.alt = d.nome;
          media.appendChild(img);

          const label = document.createElement("div");
          label.classList.add("look-card__label");
          label.textContent = `${d.nome} - ${d.categoria}`;

          card.appendChild(media);
          card.appendChild(label);
		  
		  //cards clicaveis
		  card.addEventListener('click', () => {
		              window.location.href = `detalhesDoacao.html?id=${d.id}`;
		            });
		  
          container.appendChild(card);
        });
      } catch (error) {
        console.error("❌ Erro ao carregar doações:", error);
        container.innerHTML = "<p>Erro ao carregar doações :(</p>";
      }
    }

    carregarDoacoes();
  }
});

// ===================================================================
// CSS Inline extra para o spinner
// ===================================================================
const style = document.createElement("style");
style.textContent = `
@keyframes spin { from { transform: rotate(0deg); } to { transform: rotate(360deg); } }
`;
document.head.appendChild(style);
