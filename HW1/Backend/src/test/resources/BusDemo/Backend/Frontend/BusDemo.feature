Feature: Reserva de passagens de bus

  Scenario Outline: Reservar uma passagem de bus
    Given Estou na página inicial do sistema de reservas de bus
    When Eu navego para a seção de viagens
    And Eu seleciono "<origem>" como minha cidade de origem
    And Eu seleciono "<destino>" como meu destino
    And Eu insiro "<data>" como a data de viagem
    And Eu submeto a busca por viagens
    And Eu escolho pagar com "<moeda>"
    And Eu seleciono a primeira viagem disponível
    And Eu preencho as informações de contato e endereço com "Liliana Paula Cruz Ribeiro", "rua da conceição, nº22B, canto de calvão", "CALVÃO VGS", "912345678" e "1234567890123456"
    And Eu confirmo a reserva
    And Eu volto à HomePage
    And Eu insiro o ID da Viagem que comprei
    And Eu clico em Procurar
    Then Eu deveria ser capaz de visualizar o ticket da minha reserva

  Examples:
    | origem  | destino | data       | moeda            | nome                         | endereço                             | cidade      |
    | Aveiro  | Braga   | 04-24-2024 | Dólar Canadense (CAD) | Liliana Paula Cruz Ribeiro   | rua da conceição, nº22B, canto de calvão | CALVÃO VGS |

