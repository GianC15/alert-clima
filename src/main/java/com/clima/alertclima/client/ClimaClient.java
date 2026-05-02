package com.clima.alertclima.client;

import com.clima.alertclima.model.ClimaActualDTO;
import com.clima.alertclima.model.ClimaHistoricoDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component  // Spring lo registra como un bean (componente manejado por Spring)
public class ClimaClient {

    // RestClient es la forma moderna de hacer llamadas HTTP en Spring Boot 3.2+
    private final RestClient restClient;

    public ClimaClient() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.open-meteo.com")
                .build();
    }

    public ClimaActualDTO obtenerClimaActual(Double lat, Double lon) {
        return restClient.get()
                .uri("/v1/forecast?latitude={lat}&longitude={lon}&current=temperature_2m,precipitation,windspeed_10m",
                        lat, lon)
                .retrieve()
                .body(ClimaActualDTO.class);  // deserializa el JSON → ClimaActualDTO
    }

    public ClimaHistoricoDTO obtenerClimaHistorico(Double lat, Double lon,
                                                   String fechaInicio, String fechaFin) {
        return restClient.get()
                .uri("https://archive-api.open-meteo.com/v1/archive" +
                                "?latitude={lat}&longitude={lon}" +
                                "&start_date={inicio}&end_date={fin}" +
                                "&daily=temperature_2m_max,precipitation_sum",
                        lat, lon, fechaInicio, fechaFin)
                .retrieve()
                .body(ClimaHistoricoDTO.class);
    }
}