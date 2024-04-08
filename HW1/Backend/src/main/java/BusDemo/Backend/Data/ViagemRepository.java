package BusDemo.Backend.Data;

import com.fasterxml.jackson.databind.ObjectMapper;

import BusDemo.Backend.Service.Viagem;
import BusDemo.Backend.Service.Connection;
import BusDemo.Backend.Service.BusDemo;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class ViagemRepository {
    private List<Connection> connections;

    public ViagemRepository() {
        // Carregar viagens do JSON
        ObjectMapper mapper = new ObjectMapper();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("bus.json");
            BusDemo busDemo = mapper.readValue(inputStream, BusDemo.class);
            this.connections = busDemo.getBusdemo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar viagens por origem, destino e dia
    public List<Viagem> buscarViagens(String origem, String destino, String dia) {
        return connections.stream()
                          .filter(conn -> conn.getOrigin().equals(origem) && conn.getDestination().equals(destino))
                          .flatMap(conn -> conn.getViagens().stream())
                          .filter(viagem -> viagem.getDia().equals(dia))
                          .collect(Collectors.toList());
    }

    // Método para buscar uma viagem pelo ID
    public Optional<Viagem> findViagemById(String viagemId) {
        for (Connection conn : connections) {
            for (Viagem viagem : conn.getViagens()) {
                if (viagem.getViagemID().equals(viagemId)) {
                    return Optional.of(viagem);
                }
            }
        }
        return Optional.empty();
    }
}
