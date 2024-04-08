import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './Passagens.css';
import ConversorDeMoeda from './ConversorDeMoeda';

function Passagens() {
  const location = useLocation();
  const { viagens } = location.state;
  const navigate = useNavigate();
  const [moeda, setMoeda] = useState('EUR'); // Estado para a moeda

  const handleSelecionarViagem = (viagem) => {
    navigate('/reserva', { state: { viagem } });
  };

  return (
    <div className="passagens-container">
      <h2>Resultados das Viagens</h2>
      <div>
        <label>Escolha a moeda: </label>
        <select value={moeda} onChange={(e) => setMoeda(e.target.value)}>
          <option value="EUR">Euro (EUR)</option>
          <option value="USD">Dólar Americano (USD)</option>
          <option value="GBP">Libra Esterlina (GBP)</option>
          <option value="JPY">Iene Japonês (JPY)</option>
          <option value="BRL">Real Brasileiro (BRL)</option>
          <option value="CAD">Dólar Canadense (CAD)</option>
          <option value="AUD">Dólar Australiano (AUD)</option>
          <option value="CNY">Yuan Chinês (CNY)</option>
          <option value="INR">Rupia Indiana (INR)</option>
          {/* Adicione mais opções de moeda conforme necessário */}
        </select>
      </div>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Partida</th>
            <th>Chegada</th>
            <th>Preço</th>
            <th>Escolher Viagem</th>
          </tr>
        </thead>
        <tbody>
          {viagens.map(viagem => (
            <tr key={viagem.viagemID}>
              <td>{viagem.ViagemID}</td>
              <td>{viagem['Dia de partida Hour']}</td>
              <td>{viagem['Dia de chegada Hour']}</td>
              <td>
                <ConversorDeMoeda precoEuro={viagem.Preço} moeda={moeda} />
              </td>
              <td>
                <button onClick={() => handleSelecionarViagem(viagem)}>Selecionar</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Passagens;
