import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import Entrada from './components/Entrada/Entrada';
import Viagens from './components/Viagens/Viagens';
import Passagens from './components/Passagens/Passagens';
import Reservation from './components/Reservation/Reservation';
import TicketPurchased from './components/TicketPurchased/TicketPurchased';

function App() {
  return (
  <Router>
      <Routes>
        <Route path="/" exact element={<Entrada/>} />
        <Route path="/viagens" element={<Viagens/>} />
        <Route path="/passagens" element={<Passagens/>} />
        <Route path="/reserva" element={<Reservation/>} />
        <Route path="/ticket-comprado" element={<TicketPurchased/>} />
        {/* Adicione mais rotas conforme necessário */}
      </Routes>
    </Router>
  );
}

export default App;
