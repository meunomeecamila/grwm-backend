window.onload = async function() {

    //para pagina de erro, caso nao encontre a peca
    const paginaAtual = window.location.pathname.split('/').pop();
    if (paginaAtual === 'erroDoacao.html') {
        return; 
    }

    try {
        const params = new URLSearchParams(window.location.search);
        const id = params.get("id"); //pega url
        
        if (!id) { //erro
            throw new Error("ID não encontrado na URL.");
        }

        //carregar detalhes
        const res = await fetch(`/api/doacao/${id}`); 
        
        if (!res.ok) { //erro
            throw new Error("Doação não encontrada no banco de dados.");
        }

        const item = await res.json();

        //Preenche os campos da pagina
        document.querySelector('.image-section img').src = item.fotoBase64
            ? `data:image/jpeg;base64,${item.fotoBase64}`
            : "imgs/imagem-padrao.png";
        
        document.getElementById('titulo-doacao').textContent = item.nome;
        document.getElementById('categoria').textContent = item.categoria;
        document.getElementById('tamanho').textContent = item.tamanho;
        document.getElementById('descricao').textContent = item.descricao;

    } catch (error) {
        console.error("Erro ao carregar detalhes da doação:", error.message);
        
        alert("Ocorreu um erro: " + error.message);
        
        window.location.href = 'erroInicio.html';
    }
};