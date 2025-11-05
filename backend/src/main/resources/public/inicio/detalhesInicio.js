async function loadDetails() {
        const params = new URLSearchParams(window.location.search);
        const id = params.get("id");
        const tipo = params.get("tipo"); // peca ou doacao

        if (!id || !tipo) {
            alert("Erro: ID ou tipo do item não especificado.");
            window.location.href = 'erroInicio.html';
            return;
        }

        
        const url = `/api/${tipo}/${id}`; 

        try {
            const res = await fetch(url);
            if (!res.ok) {
                 throw new Error("Item não encontrado (ID: " + id + ", Tipo: " + tipo + ")");
            }

            const item = await res.json();

            // Preenche a imagem
            document.querySelector('.image-section img').src = item.fotoBase64
                ? `data:image/jpeg;base64,${item.fotoBase64}` 
                : "imgs/imagem-padrao.png";
            
            //preenche titulo
            document.querySelector('.details-section .item-nome').textContent = item.nome; 

            // Preenche os detalhes 
            document.querySelector('.item-categoria').innerHTML = `<span>Categoria:</span> ${item.categoria || "Não definida"}`;
            document.querySelector('.item-tamanho').innerHTML = `<span>Tamanho:</span> ${item.tamanho || "Não definido"}`;
            document.querySelector('.item-descricao').innerHTML = `<span>Descrição:</span> ${item.descricao || "Sem descrição"}`;

            // Seleciona somente pecas
            const corEl = document.querySelector('.item-cor');
            const ocasiaoEl = document.querySelector('.item-ocasiao');

            if (tipo === 'peca') {
                // Se for peca, preenche e mostra
                corEl.innerHTML = `<span>Cor:</span> ${item.cor || "Não definida"}`;
                ocasiaoEl.innerHTML = `<span>Ocasião:</span> ${item.ocasiao || "Não definida"}`;
                corEl.style.display = 'block';      // Garante que seja visível
                ocasiaoEl.style.display = 'block'; // Garante que seja visível
            } else {
                // Se for doacao, esconde esses campos
                corEl.style.display = 'none';
                ocasiaoEl.style.display = 'none';
            }

        } catch (error) {
            console.error("Erro ao carregar detalhes:", error);
            alert("Não foi possível carregar os detalhes do item.");
            window.location.href = 'erroInicio.html'; // Descomente para redirecionar em caso de erro
        }
    }

    window.onload = loadDetails;