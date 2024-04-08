package BusDemo.Backend.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Component
public class ExchangeRateClient {

    private RestTemplate restTemplate = new RestTemplate();
    private final String apiKey = "a9c1f2648535c05589c45151";
    private final String baseUrl = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/EUR"; // Defina EUR como moeda base

    @Cacheable(value = "exchangeRates", key = "#toCurrency")
    public Double getExchangeRate(String toCurrency) {
        String url = baseUrl; // A URL já inclui EUR como base
        ExchangeRateResponse response = restTemplate.getForObject(url, ExchangeRateResponse.class);
        return response.getConversion_rates().get(toCurrency); // Retorna a taxa de câmbio para a moeda destino
    }

    // Classe interna para mapear a resposta da API
    public static class ExchangeRateResponse {
        private String result;
        private Map<String, Double> conversion_rates;

        // Getters e Setters
        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public Map<String, Double> getConversion_rates() {
            return conversion_rates;
        }

        public void setConversion_rates(Map<String, Double> conversion_rates) {
            this.conversion_rates = conversion_rates;
        }

    }
}
