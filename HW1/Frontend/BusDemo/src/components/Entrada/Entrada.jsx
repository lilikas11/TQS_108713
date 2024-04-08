import React, { useState } from 'react';
import './Entrada.css'; // Certifique-se de que o caminho para o CSS está correto
import { useNavigate } from 'react-router-dom'; // Atualizado para useNavigate


// Frontend para a página de entrada, ao carregar no botão vai para a página que deixa escolher a origem, destino e data
function Entrada() {
  const navigate = useNavigate(); // Atualizado para useNavigate
  const [token, setToken] = useState('');


  const goToViagens = () => {
    navigate('/viagens'); // Atualizado para usar navigate
  };

  const procurarReserva = async (e) => {
    e.preventDefault();
  
    try {
      const url = `http://localhost:8080/api/${token}`;
  
      const response = await fetch(url);
      const dadosResposta = await response.json();
  
      // Redirecionar para a nova componente e passar a resposta como estado
      navigate('/ticket-comprado', { state: { dadosReserva: dadosResposta } });
    } catch (erro) {
      console.error('Erro ao enviar a reserva:', erro);
    }
  };


  return (
    <div className="video-background">
      <video autoPlay="autoplay" loop="loop" muted className="video">
        <source src="assets/autocarro.mp4" type="video/mp4" />
        Seu navegador não suporta vídeos HTML5.
      </video>

      <div className="content">
        <h1>Bem-vindo!</h1>
        <p>Carregue no botão para reservar as suas próximas viagens.</p>
        <button onClick={goToViagens}>Ver Viagens</button>
        <br></br>
        <p>Procure as suas reservas</p>
        <input
          type="text"
          value={token}
          onChange={(e) => setToken(e.target.value)}
          placeholder="Insira seu token de reserva"
          className="token-input"
        />
        <button onClick={procurarReserva} className="search-btn">
          Procurar
        </button>
      </div>
    </div>
  );
}

export default Entrada;
