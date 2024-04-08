import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Viagens.css'; // Certifique-se de que o caminho para o CSS está correto


// Frontend para escolher uma origem, destino e data que faz o request à api
function Viagens() {
    const [origem, setOrigem] = useState('');
    const [destino, setDestino] = useState('');
    const [data, setData] = useState('');
    const navigate = useNavigate();
    const [moeda, setMoeda] = useState('EUR');
    const moedas = ['EUR', 'USD', 'GBP', 'BRL']; 

    const locais = [
      { id: 1, nome: 'Lisboa' },
      { id: 2, nome: 'Coimbra' },
      { id: 3, nome: 'Aveiro' },
      { id: 4, nome: 'Porto' },
      { id: 5, nome: 'Braga' },
      { id: 6, nome: 'Faro' },
      // Adicione mais locais conforme necessário
    ];
  
    const handleSubmit = async (e) => {
      e.preventDefault();
      
      // Verifica se os campos estão preenchidos
      if (!origem || !destino || !data) {
        alert("Por favor, preencha todos os campos.");
        return;
      }

      const url = `http://localhost:8080/api/viagens/${origem}/${destino}/${data}`;

      try {
        const response = await fetch(url);
        const responseData = await response.json();

        // Trate aqui a resposta como desejar
        console.log(responseData);
        navigate('/passagens', { state: { viagens: responseData } });
      } catch (error) {
        console.error("Erro ao buscar viagens:", error);
        // Trate aqui o erro como desejar
      }
    };
  
    return (
      <div className="viagens">
        < h2 > Buscar Viagens</h2>
        <p>Por favor, preencha os campos abaixo.</p>
        <form onSubmit={handleSubmit}>
          <div className="select-container">
            <label htmlFor="origem">Origem:</label>
            <select id="origem" value={origem} onChange={(e) => setOrigem(e.target.value)}>
              <option value="">Selecione a origem</option>
              {locais.map(local => (
                <option key={local.id} value={local.nome}>{local.nome}</option>
              ))}
            </select>
          </div>
  
          <div className="select-container">
            <label htmlFor="destino">Destino:</label>
            <select id="destino" value={destino} onChange={(e) => setDestino(e.target.value)}>
              <option value="">Selecione o destino</option>
              {locais.map(local => (
                <option key={local.id} value={local.nome}>{local.nome}</option>
              ))}
            </select>
          </div>
  
          <div className="select-container">
            <label htmlFor="data">Data:</label>
            <input type="date" id="data" value={data} onChange={(e) => setData(e.target.value)} />
          </div>
  
          <button type="submit">Buscar Viagens</button>
        </form>
        
      </div>
      
    );
}

export default Viagens;
