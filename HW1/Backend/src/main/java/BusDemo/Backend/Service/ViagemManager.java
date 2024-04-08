package BusDemo.Backend.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import BusDemo.Backend.Data.ViagemRepository;

@Service
public class ViagemManager{

    @Autowired
    private ViagemRepository viagemRepository;

    public List<Viagem> buscarViagens(String origem, String destino, String dia) {
        return viagemRepository.buscarViagens(origem, destino, dia);
    }

    public Optional<Viagem> buscarViagemPorId(String viagemId) {
        return viagemRepository.findViagemById(viagemId);
    }
}