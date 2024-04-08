package BusDemo.Backend.Service;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import BusDemo.Backend.Data.ReservationRepository;


@ExtendWith(MockitoExtension.class)
public class ReservationManagerTest {

    @Mock(lenient = true)
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationManager reservationManager;

    @BeforeEach
    public void setUp() {
        Reservation reservation = new Reservation("1", "João", "123456789", "Rua 1", "Cidade 1", "123456789", "1");

        Mockito.when(reservationRepository.findById(reservation .getId())).thenReturn(Optional.of(reservation));
    }

    @Test
    void whenValidId_thenReservationShouldBeFound() {
        String id = "1";
        Reservation found = reservationManager.getReservationById(id).get();

        assertThat(found.getId()).isEqualTo(id);
        assertThat(found.getNome()).isEqualTo("João");
        assertThat(found.getTelefone()).isEqualTo("123456789");
        assertThat(found.getEndereco()).isEqualTo("Rua 1");
        assertThat(found.getCidade()).isEqualTo("Cidade 1");
        assertThat(found.getCartaoCredito()).isEqualTo("123456789");
        assertThat(found.getViagemID()).isEqualTo("1");
    }

    @Test
    void whenInvalidId_thenReservationShouldNotBeFound() {
        String id = "99";
        Optional<Reservation> found = reservationManager.getReservationById(id);

        assertThat(found).isEmpty();
    }

    @Test
    void whengivenReservation_thenSaveReservationShouldReturnSavedReservation() {
        Reservation reservation = new Reservation("1", "João", "123456789", "Rua 1", "Cidade 1", "123456789", "1");
        Mockito.when(reservationRepository.save(reservation)).thenReturn(reservation);
        Reservation saved = reservationManager.criarReservation(reservation);
        assertThat(saved).isEqualTo(reservation);
    }
}