import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './TicketPurchased.css'; // Certifique-se de que o caminho para o CSS está correto

function TicketPurchased() {
  const location = useLocation();
  const navigate = useNavigate();
  const { dadosReserva } = location.state;
  const [detalhesViagem, setDetalhesViagem] = useState(null); // Estado para armazenar os detalhes da viagem

  const handleHomePage = () => {
    // Aqui você pode lidar com a seleção da viagem, talvez armazenando o ID da viagem selecionada ou navegando para outra página
    navigate('/');
  };

  useEffect(() => {
    const fetchViagemDetalhes = async () => {
      try {
        const resposta = await fetch(`http://localhost:8080/api/viagens/${dadosReserva.viagemID}`);
        if (!resposta.ok) {
          throw new Error('Falha ao buscar detalhes da viagem');
        }
        const dadosViagem = await resposta.json();
        setDetalhesViagem(dadosViagem);
      } catch (erro) {
        console.error('Erro ao buscar detalhes da viagem:', erro);
        // Você pode lidar com o erro aqui, talvez mostrando uma mensagem para o usuário
      }
    };

    fetchViagemDetalhes();
  }, [dadosReserva.viagemID]); // Dependência do useEffect para que ele seja executado apenas quando o viagemID mudar


  return (
    <div className="ticket-purchased-container">
      <div className="ticket-purchased-header">
        <h2>Obrigado por Comprar o seu Ticket Connosco!</h2>
      </div>

      <div className="ticket-purchased-details">
        <h3>Detalhes da Reserva</h3>
        <p className="detail-row"><span className="detail-label">Nome:</span> {dadosReserva.nome}</p>
        <p className="detail-row"><span className="detail-label">Telefone:</span> {dadosReserva.telefone}</p>
        <p className="detail-row"><span className="detail-label">Endereço:</span> {dadosReserva.endereco}</p>
        <p className="detail-row"><span className="detail-label">Cidade:</span> {dadosReserva.cidade}</p>
        <p className="detail-row"><span className="detail-label">Número do Cartão de Crédito:</span> {dadosReserva.cartaoCredito}</p>
        <h3>Detalhes da Viagem</h3>
        {detalhesViagem ? (
          <>
            <p className="detail-row"><span className="detail-label">Dia:</span> {detalhesViagem.Dia}</p>
            <p className="detail-row"><span className="detail-label">Partida:</span> {detalhesViagem['Dia de partida Hour']}</p>
            <p className="detail-row"><span className="detail-label">Chegada:</span> {detalhesViagem['Dia de chegada Hour']}</p>
            <p className="detail-row"><span className="detail-label">Preço:</span> {detalhesViagem.Preço}</p>
          </>
        ) : (
          <p>Carregando detalhes da viagem...</p>
        )}
      </div>

      <div className="reservation-id">
        <h3>Guarde este token para procurar a sua viagem futuramente!</h3>
        <p>ID da Reserva: {dadosReserva.id}</p>
      </div>
      <button onClick={handleHomePage}>Voltar à HomePage</button>
    </div>
  );
}

export default TicketPurchased;
