import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './Reservation.css';


// Frontend para ver os detalhes da viagem e fazer a reserva
function Reservation() {
    const location = useLocation();
    const navigate = useNavigate();
    const { viagem } = location.state; // Recebe os detalhes da viagem selecionada
  
    // Estados para os campos do formulário
    const [nome, setNome] = useState('');
    const [endereco, setEndereco] = useState('');
    const [cidade, setCidade] = useState('');
    const [telefone, setTelefone] = useState('');
    const [cartaoCredito, setCartaoCredito] = useState('');
  
    // Função para lidar com o envio do formulário
    const handleSubmit = async (e) => {
      e.preventDefault();
    
      // Incluindo campos origem e destino extraídos do objeto viagem
      const dadosReserva = {
        nome,
        endereco,
        cidade,
        telefone,
        cartaoCredito,
        viagemID: viagem.viagemID,
      };
    
      try {
        const resposta = await fetch('http://localhost:8080/api/reservation', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(dadosReserva),
        });
    
        if (!resposta.ok) {
          throw new Error('Algo deu errado com a solicitação');
        }
    
        const dadosResposta = await resposta.json();
        // Redirecionar para a nova componente e passar a resposta como estado
        navigate('/ticket-comprado', { state: { dadosReserva: dadosResposta } });
      } catch (erro) {
        console.error('Erro ao enviar a reserva:', erro);
      }
    };

    return (
      <div className="reservation">
        <h2>Detalhes da Viagem</h2>
        <p>ID: {viagem.viagemID}</p>
        <p>Partida: {viagem['Dia de partida Hour']}</p>
        <p>Chegada: {viagem['Dia de chegada Hour']}</p>
        <p>Preço: {viagem.Preço}</p>
  
        <h3>Preencha os seus dados</h3>
        <form onSubmit={handleSubmit}>
          <input type="text" value={nome} onChange={(e) => setNome(e.target.value)} placeholder="Nome" required />
          <input type="text" value={endereco} onChange={(e) => setEndereco(e.target.value)} placeholder="Endereço" required />
          <input type="text" value={cidade} onChange={(e) => setCidade(e.target.value)} placeholder="Cidade" required />
          <input type="tel" value={telefone} onChange={(e) => setTelefone(e.target.value)} placeholder="Número de Telemóvel" required />
          <input type="text" value={cartaoCredito} onChange={(e) => setCartaoCredito(e.target.value)} placeholder="Número do Cartão de Crédito" required />
          <button type="submit">Confirmar</button>
        </form>
      </div>
    );
   
}

export default Reservation;
