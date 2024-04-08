package BusDemo.Backend.Data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import BusDemo.Backend.Service.Reservation;

@DataJpaTest
class ReservationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void whenFindById_thenReturnReservation() {
        // arrange a new reservation and insert into db
        Reservation reservation = new Reservation("nome", "telefone", "endereco", "cidade", "cartaoCredito", "ViagemID");
        entityManager.persistAndFlush(reservation);

        // test the query method of interest
        Optional<Reservation> found = reservationRepository.findById(reservation.getId());
        assertThat(found).isPresent().contains(reservation);
    }

    @Test
    void whenInvalidId_thenReturnEmpty() {
        Optional<Reservation> fromDb = reservationRepository.findById("invalidId");
        assertThat(fromDb).isEmpty();
    }

    @Test
    void givenSetOfReservations_whenFindAll_thenReturnAllReservations() {
        Reservation res1 = new Reservation("nome1", "telefone1", "endereco1", "cidade1", "cartaoCredito1", "ViagemID1");
        Reservation res2 = new Reservation("nome2", "telefone2", "endereco2", "cidade2", "cartaoCredito2", "ViagemID2");
        Reservation res3 = new Reservation("nome3", "telefone3", "endereco3", "cidade3", "cartaoCredito3", "ViagemID3");

        entityManager.persist(res1);
        entityManager.persist(res2);
        entityManager.persist(res3);
        entityManager.flush();

        List<Reservation> allReservations = reservationRepository.findAll();

        assertThat(allReservations).hasSize(3).containsExactlyInAnyOrder(res1, res2, res3);
    }
}
