import React, { useState, useEffect } from 'react';

function ConversorDeMoeda({ precoEuro, moeda }) {
  const [precoConvertido, setPrecoConvertido] = useState(precoEuro);

  useEffect(() => {
    if (moeda !== 'EUR') {
      const url = `http://localhost:8080/api/convert?amount=${precoEuro}&currency=${moeda}`;

      fetch(url)
        .then(response => {
          if (!response.ok) {
            throw new Error('Falha na requisição da API');
          }
          return response.json();
        })
        .then(data => setPrecoConvertido(data))
        .catch(error => console.error('Erro ao obter a taxa de câmbio:', error));
    } else {
      setPrecoConvertido(precoEuro); // Se a moeda for EUR, mantém o valor original
    }
  }, [precoEuro, moeda]);

  return <p>Preço: {precoConvertido} {moeda}</p>;
}

export default ConversorDeMoeda;
