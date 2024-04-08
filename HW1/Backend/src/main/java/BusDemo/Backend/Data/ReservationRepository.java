package BusDemo.Backend.Data;

import BusDemo.Backend.Service.Reservation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, String> {


    public Optional<Reservation> findById(String id);

    public List<Reservation> findAll();

}

