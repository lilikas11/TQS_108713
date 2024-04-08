package BusDemo.Backend.Cache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import BusDemo.Backend.Service.ExchangeRateClient;

import static org.mockito.BDDMockito.given;

import java.util.Map;

@SpringBootTest
class ExchangeRateClientTest {

    @Autowired
    private ExchangeRateClient exchangeRateClient;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        ExchangeRateClient.ExchangeRateResponse mockResponse = new ExchangeRateClient.ExchangeRateResponse();
        mockResponse.setConversion_rates(Map.of("USD", 1.1, "GBP", 0.8));
        given(restTemplate.getForObject("https://v6.exchangerate-api.com/v6/a9c1f2648535c05589c45151/latest/EUR", ExchangeRateClient.ExchangeRateResponse.class))
            .willReturn(mockResponse);
    }

    @Test
    void whenCalledTwice_thenSecondCallUsesCache() {
        // Primeira chamada
        Double firstCallRate = exchangeRateClient.getExchangeRate("USD");
        assertNotNull(firstCallRate);

        // Segunda chamada
        Double secondCallRate = exchangeRateClient.getExchangeRate("USD");
        assertNotNull(secondCallRate);

        // mesmas taxas
        assertEquals(firstCallRate, secondCallRate);
    }
}
