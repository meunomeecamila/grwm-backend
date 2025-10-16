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
