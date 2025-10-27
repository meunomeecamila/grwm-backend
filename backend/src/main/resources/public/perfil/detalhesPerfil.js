window.onload = async function(){
	const params = new URLSearchParams(window.location.search);
	const id = params.get("id"); //pega id da url
	
	if(!id){
		alert("Peça não encontrada!");
		window.location.href = '/inicio/inicio.html';
		return;
	}
	
	//carregar os detalhes da peca
	try {
	            // Chama a nova rota GET
	            const res = await fetch(`/peca/${id}`); 
	            
	            if (!res.ok) {
					window.location.href = 'erroPeca.html'
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


	        } catch (error) {
	            console.error(error);
	            document.querySelector('.details-section').innerHTML = `<h1>${error.message}</h1>`;
	        }
	        
	        //Botao excluir
	        const botaoExcluir = document.getElementById("excluir");
	        botaoExcluir.addEventListener("click", async () => {
	            
	            //Confirmar exclusão
	            if (!confirm("Tem certeza que deseja excluir esta peça? Esta ação não pode ser desfeita.")) {
	                return; // Caso o usuario nao queira excluir
	            }

	            try {
	                // Chama a nova rota DELETE
	                const response = await fetch(`/peca/${id}`, {
	                    method: 'DELETE'
	                });

	                const result = await response.json(); // Lê a resposta JSON do backend

	                if (response.ok) {
	                    alert(result.message); // Exibe "Peça excluída com sucesso!"
	                    // Redireciona para a página de início ou perfil
	                    window.location.href = 'perfil.html'; 
	                } else {
	                    throw new Error(result.message); // Exibe "Erro ao excluir peça."
	                }

	            } catch (error) {
	                console.error("Erro em fetch DELETE:", error);
	                alert("Erro ao tentar excluir a peça: " + error.message);
	            }
	        });
};