window.onload = async function(){

   //para pagina de erro, caso nao encontre a peca
    const paginaAtual = window.location.pathname.split('/').pop();

    if (paginaAtual === 'erroPeca.html') {
        return; 
    }

	try {
	    const params = new URLSearchParams(window.location.search);
	    const id = params.get("id"); //pega id da url
	
	    if(!id){
            //erro
		    throw new Error("ID da peça não encontrado na URL.");
	    }
	
	    //carregar os detalhes da peca
        const res = await fetch(`/api/peca/${id}`); 
        
        if (!res.ok) {
			//erro
            throw new Error("Peça não encontrada no banco de dados.");
        }

        const item = await res.json();

        //Preenche os campos da pagina
        document.querySelector('.image-section img').src = item.fotoBase64
            ? `data:image/jpeg;base64,${item.fotoBase64}`
            : "imgs/imagem-padrao.png";
        
        document.getElementById('titulo-peca').textContent = item.nome;
        document.getElementById('cor').textContent = item.cor;
        document.getElementById('tipo').textContent = item.categoria;
        document.getElementById('ocasiao').textContent = item.ocasiao;
        document.getElementById('descricao').textContent = item.descricao;

	        
        //Botao excluir 
        const botaoExcluir = document.getElementById("excluir");
        botaoExcluir.addEventListener("click", async () => {
            
            if (!confirm("Tem certeza que deseja excluir esta peça? Esta ação não pode ser desfeita.")) {
                return;
            }

            try {
                const response = await fetch(`/peca/${id}`, {
                    method: 'DELETE'
                });
                const result = await response.json();

                if (response.ok) {
                    alert(result.message);
                    window.location.href = 'perfil.html'; 
                } else {
                    throw new Error(result.message);
                }

            } catch (error) {
                console.error("Erro em fetch DELETE:", error);
                alert("Erro ao tentar excluir a peça: " + error.message);
            }
        });

    } catch (error) {
        console.error("Erro ao carregar detalhes:", error.message);
        
        alert("Ocorreu um erro: " + error.message);
        
        window.location.href = 'erroPeca.html';
    }
};