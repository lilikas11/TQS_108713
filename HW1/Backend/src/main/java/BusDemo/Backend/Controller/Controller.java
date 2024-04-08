package BusDemo.Backend.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import BusDemo.Backend.Service.ExchangeRateClient;
import BusDemo.Backend.Service.Reservation;
import BusDemo.Backend.Service.ReservationManager;
import BusDemo.Backend.Service.Viagem;
import BusDemo.Backend.Service.ViagemManager;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private ViagemManager ViagemManager;

    @Autowired
    private ReservationManager ReservationManager;

    @Autowired
    private ExchangeRateClient exchangeRateClient;

    @CrossOrigin
    @GetMapping("/viagens/{origem}/{destino}/{dia}")
    public ResponseEntity<List<Viagem>> buscarViagens(@PathVariable String origem, @PathVariable String destino, @PathVariable String dia) {
        List<Viagem> viagens = ViagemManager.buscarViagens(origem, destino, dia);
        return new ResponseEntity<>(viagens, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/reservation")
    public ResponseEntity<Reservation> criarNewReservation(@RequestBody Reservation cliente) {
        Reservation newreservation = ReservationManager.criarReservation(cliente);
        return new ResponseEntity<>(newreservation, HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable String id) {
        Optional<Reservation> reservation = ReservationManager.getReservationById(id);
        
        if (reservation.isPresent()) {
            return ResponseEntity.ok(reservation.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @CrossOrigin
    @GetMapping("/convert")
    public Double convertFromEuro(@RequestParam Double amount, @RequestParam String currency) {
        Double rate = exchangeRateClient.getExchangeRate(currency);
        return amount * rate;
    }

    @CrossOrigin
    @GetMapping("/viagens/{viagemId}")
    public ResponseEntity<Viagem> buscarViagemPorId(@PathVariable String viagemId) {
        Optional<Viagem> viagem = ViagemManager.buscarViagemPorId(viagemId);

        return viagem
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    

}
