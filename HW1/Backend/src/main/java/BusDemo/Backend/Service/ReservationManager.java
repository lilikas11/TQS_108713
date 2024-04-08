package BusDemo.Backend.Service;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import BusDemo.Backend.Data.ReservationRepository;

@Service
public class ReservationManager {
    
    @Autowired
    private ReservationRepository ReservationRepository;

    public Reservation criarReservation(Reservation cliente) {
        return this.ReservationRepository.save(cliente);
    }


    public Optional<Reservation> getReservationById(String id) {
        return ReservationRepository.findById(id);
    }

}
