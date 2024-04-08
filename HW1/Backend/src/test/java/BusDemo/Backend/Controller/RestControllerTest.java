package BusDemo.Backend.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import BusDemo.Backend.Service.ExchangeRateClient;
import BusDemo.Backend.Service.Reservation;
import BusDemo.Backend.Service.Viagem;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class RestControllerTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ExchangeRateClient exchangeRateClient;

    private String reservationId;

    @BeforeEach
    void setUp() {
        // Cria uma reserva para teste
        Reservation reservationRequest = new Reservation(null, "nome", "telefone", "endereco", "cidade", "cartaoCredito", "ViagemID");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Reservation> request = new HttpEntity<>(reservationRequest, headers);

        ResponseEntity<Reservation> createdResponse = restTemplate.postForEntity("/api/reservation", request, Reservation.class);
        assertThat(createdResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        reservationId = createdResponse.getBody().getId();
    }

    // @CrossOrigin
    // @GetMapping("/viagens/{origem}/{destino}/{dia}")
    // public ResponseEntity<List<Viagem>> buscarViagens(@PathVariable String origem, @PathVariable String destino, @PathVariable String dia) {
    //     List<Viagem> viagens = ViagemManager.buscarViagens(origem, destino, dia);
    //     return new ResponseEntity<>(viagens, HttpStatus.OK);
    // }

    @Test
    void givenViagens_whenGetViagens_thenStatus200()  {
        String origem = "Aveiro";
        String destino = "Faro";
        String dia = "2024-04-25";

        String url = String.format("/api/viagens/%s/%s/%s", origem, destino, dia);
        ResponseEntity<List<Viagem>> response = restTemplate
            .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Viagem>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody().get(0).getViagemID()).isEqualTo("072");
        assertThat(response.getBody().get(0).getDia()).isEqualTo("2024-04-25");
        assertThat(response.getBody().get(0).getDiaDePartidaHour()).isEqualTo("10:00");
        assertThat(response.getBody().get(0).getDiaDeChegadaHour()).isEqualTo("12:00");
        assertThat(response.getBody().get(0).getPreço()).isEqualTo("30");

        assertThat(response.getBody().get(1).getViagemID()).isEqualTo("075");
        assertThat(response.getBody().get(1).getDia()).isEqualTo("2024-04-25");
        assertThat(response.getBody().get(1).getDiaDePartidaHour()).isEqualTo("13:00");
        assertThat(response.getBody().get(1).getDiaDeChegadaHour()).isEqualTo("15:00");
        assertThat(response.getBody().get(1).getPreço()).isEqualTo("45");
    }



    // @CrossOrigin
    // @PostMapping("/reservation")
    // public ResponseEntity<Reservation> criarNewReservation(@RequestBody Reservation cliente) {
    //     Reservation newreservation = ReservationManager.criarReservation(cliente);
    //     return new ResponseEntity<>(newreservation, HttpStatus.CREATED);
    // }

    @Test
    void whenCreateReservation_thenStatus201AndDataMatches()  {
        Reservation reservationRequest = new Reservation(null, "nome", "telefone", "endereco", "cidade", "cartaoCredito", "ViagemID");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Reservation> request = new HttpEntity<>(reservationRequest, headers);

        ResponseEntity<Reservation> response = restTemplate
            .postForEntity("/api/reservation", request, Reservation.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        
        // Verifica se os dados da resposta correspondem aos dados enviados, exceto o 'id' que deve ser gerado pelo servidor
        Reservation reservationResponse = response.getBody();
        assertThat(reservationResponse).isNotNull();
        assertThat(reservationResponse.getId()).isNotNull(); // Verifica se o 'id' não é nulo
        assertThat(reservationResponse.getNome()).isEqualTo(reservationRequest.getNome());
        assertThat(reservationResponse.getTelefone()).isEqualTo(reservationRequest.getTelefone());
        assertThat(reservationResponse.getEndereco()).isEqualTo(reservationRequest.getEndereco());
        assertThat(reservationResponse.getCidade()).isEqualTo(reservationRequest.getCidade());
        assertThat(reservationResponse.getCartaoCredito()).isEqualTo(reservationRequest.getCartaoCredito());
        assertThat(reservationResponse.getViagemID()).isEqualTo(reservationRequest.getViagemID());
    }


    // @CrossOrigin
    // @GetMapping("/{id}")
    // public ResponseEntity<Reservation> getReservationById(@PathVariable String id) {
    //     Optional<Reservation> reservation = ReservationManager.getReservationById(id);
        
    //     if (reservation.isPresent()) {
    //         return ResponseEntity.ok(reservation.get());
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    @Test
    void givenReservationId_whenGetReservationById_thenStatus200AndDataMatches(){
        ResponseEntity<Reservation> response = restTemplate.getForEntity("/api/" + reservationId, Reservation.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        Reservation reservationResponse = response.getBody();
        assertThat(reservationResponse).isNotNull();
        assertThat(reservationResponse.getId()).isEqualTo(reservationId);
    }

    // @CrossOrigin
    // @GetMapping("/viagens/{viagemId}")
    // public ResponseEntity<Viagem> buscarViagemPorId(@PathVariable String viagemId) {
    //     Optional<Viagem> viagem = ViagemManager.buscarViagemPorId(viagemId);

    //     return viagem
    //             .map(ResponseEntity::ok)
    //             .orElseGet(() -> ResponseEntity.notFound().build());
    // }

    @Test
    void whenBuscarViagemPorId_thenStatus200AndDataMatches() {
        String viagemId = "001";
        ResponseEntity<Viagem> response = restTemplate.getForEntity("/api/viagens/" + viagemId, Viagem.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        Viagem viagemResponse = response.getBody();
        assertThat(viagemResponse).isNotNull();
        assertThat(viagemResponse.getViagemID()).isEqualTo(viagemId);
    }

    

}
