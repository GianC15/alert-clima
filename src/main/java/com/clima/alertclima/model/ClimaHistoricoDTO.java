package com.clima.alertclima.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class ClimaHistoricoDTO {

    private Double latitude;
    private Double longitude;

    @JsonProperty("daily")
    private DatosHistoricos datosHistoricos;

    @Data
    public static class DatosHistoricos {

        @JsonProperty("time")
        private List<String> fechas;

        @JsonProperty("temperature_2m_max")
        private List<Double> temperaturaMaxima;

        @JsonProperty("precipitation_sum")
        private List<Double> precipitacion;
    }
}