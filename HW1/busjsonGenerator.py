import json

# Lista de cidades
cidades = ["Lisboa", "Coimbra", "Aveiro", "Porto", "Braga", "Faro"]

# Informações iniciais para gerar as viagens
viagens = []
viagem_id = 1

for origem in cidades:
    for destino in cidades:
        if origem != destino:  # Garantir que a origem e o destino sejam diferentes
            # Criar 5 viagens para cada par de origem/destino
            for i in range(1, 6):
                horarios = [{
                    "ViagemID": f"{viagem_id:03}",
                    "Dia": f"2024-04-24",  
                    "Dia de partida Hour": f"{8 + i}:00",  # Horários diferentes para cada viagem
                    "Dia de chegada Hour": f"{10 + i}:00",  # Considerando 2 horas de viagem
                    "Preço": f"{20 + i * 5}€"  # Preço diferente para cada viagem
                }]
                viagem = {
                    "origin": origem,
                    "destination": destino,
                    "viagens": horarios
                }
                viagens.append(viagem)
                viagem_id += 1

jsonbus = {
    "busdemo": viagens
}
# Converter a lista de viagens para JSON
viagens_json = json.dumps(jsonbus, indent=4)
with open("bus.json", "w") as arquivo:
    arquivo.write(viagens_json)
# Mostrar o JSON criado
print(viagens_json)
