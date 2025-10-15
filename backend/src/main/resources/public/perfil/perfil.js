// ===================================================================
// LÓGICA PARA BUSCAR E EXIBIR OS DADOS DO USUÁRIO
// ===================================================================

// Executa o código quando a página HTML terminar de carregar
window.onload = function() {
    // Pega os parâmetros da URL
    const params = new URLSearchParams(window.location.search);
    const userId = params.get('id'); 

    //Onde irá substituir
    const nomeUsuarioEl = document.querySelector('.user-name'); 

    // Se não houver um ID na URL
    if (!userId) {
        nomeUsuarioEl.textContent = 'Erro: Usuário não especificado.';
        return;
    }

    // Chama a API do backend para buscar os dados do usuário
    fetch(`/usuario/${userId}`)
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => { throw new Error(err.message) });
            }
            // Se a resposta for OK converte para JSON
            return response.json();
        })
        .then(data => {
            //Atualiza username
            nomeUsuarioEl.textContent = data.username;
        })
        .catch(error => {
            console.error('Erro ao buscar dados do perfil:', error);
			alert('Usuário não encontrado!');
			window.location.href = '/login/index.html';
        });
};


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