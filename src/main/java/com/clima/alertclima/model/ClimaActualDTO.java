package com.clima.alertclima.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClimaActualDTO {

    private Double latitude;
    private Double longitude;

    @JsonProperty("current")  // mapea el campo "current" del JSON
    private DatosActuales datosActuales;

    @Data
    public static class DatosActuales {

        @JsonProperty("temperature_2m")
        private Double temperatura;

        @JsonProperty("precipitation")
        private Double precipitacion;

        @JsonProperty("windspeed_10m")
        private Double velocidadViento;
    }
}