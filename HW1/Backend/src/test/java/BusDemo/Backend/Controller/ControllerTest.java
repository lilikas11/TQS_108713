package BusDemo.Backend.Controller;

import BusDemo.Backend.Service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@WebMvcTest(Controller.class)
public class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ViagemManager viagemManager;

    @MockBean
    private ReservationManager reservationManager;

    @MockBean
    private ExchangeRateClient exchangeRateClient;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testBuscarViagens() throws Exception {

        Viagem viagem1 = new Viagem("001", "2024-04-25", "11:00", "13:00", "30");
        Viagem viagem2 = new Viagem("002", "2024-04-24", "12:00", "14:00", "25");
    
        when(viagemManager.buscarViagens("Lisboa", "Coimbra", "2024-04-25")).thenReturn(Arrays.asList(viagem1));
        when(viagemManager.buscarViagens("Lisboa", "Coimbra", "2024-04-24")).thenReturn(Arrays.asList(viagem2));
    
        mvc.perform(get("/api/viagens/{origem}/{destino}/{dia}", "Lisboa", "Coimbra", "2024-04-25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].ViagemID", is("001")))
                .andExpect(jsonPath("$[0].Preço", is("30")));
    
        mvc.perform(get("/api/viagens/{origem}/{destino}/{dia}", "Lisboa", "Coimbra", "2024-04-24"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].ViagemID", is("002")))
                .andExpect(jsonPath("$[0].Preço", is("25")));
    
        verify(viagemManager, times(1)).buscarViagens("Lisboa", "Coimbra", "2024-04-25");
        verify(viagemManager, times(1)).buscarViagens("Lisboa", "Coimbra", "2024-04-24");
    }
    
    @Test
    public void testCriarNewReservation() throws Exception {
        Reservation reservation = new Reservation("02", "nome", "telefone", "endereco", "cidade", "cartaoCredito", "ViagemID");

        when(reservationManager.criarReservation(any(Reservation.class))).thenReturn(reservation);

        String reservationJson = "{\"clienteId\":\"02\",\"nome\":\"nome\",\"telefone\":\"telefone\",\"endereco\":\"endereco\",\"cidade\":\"cidade\",\"cartaoCredito\":\"cartaoCredito\",\"viagemId\":\"ViagemID\"}";

        mvc.perform(post("/api/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is("nome")));

        verify(reservationManager, times(1)).criarReservation(any(Reservation.class));
    }

    @Test
    public void testGetReservationById() throws Exception {
        Optional<Reservation> reservation = Optional.of(new Reservation("01", "nome", "telefone", "endereco", "cidade", "cartaoCredito", "viagemId"));

        when(reservationManager.getReservationById(anyString())).thenReturn(reservation);

        mvc.perform(get("/api/{id}", "01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cidade", is("cidade")));

        verify(reservationManager, times(1)).getReservationById(anyString());
    }

    @Test
    public void testConvertFromEuro() throws Exception {
        when(exchangeRateClient.getExchangeRate(anyString())).thenReturn(1.2);

        mvc.perform(get("/api/convert")
                        .param("amount", "100")
                        .param("currency", "USD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(120.0)));

        verify(exchangeRateClient, times(1)).getExchangeRate(anyString());
    }

    @Test
    public void testBuscarViagemPorId() throws Exception {
        Viagem viagemEsperada = new Viagem("001", "2024-04-25", "11:00", "13:00", "25");

        when(viagemManager.buscarViagemPorId("001")).thenReturn(Optional.of(viagemEsperada));

        mvc.perform(get("/api/viagens/{viagemId}", "001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ViagemID", is("001")))

                .andExpect(jsonPath("$.Dia", is("2024-04-25")))
                .andExpect(jsonPath("$.Preço", is("25")));

        verify(viagemManager, times(1)).buscarViagemPorId("001");
    }

}
