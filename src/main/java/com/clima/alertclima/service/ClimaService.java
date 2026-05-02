package com.clima.alertclima.service;

import com.clima.alertclima.client.ClimaClient;
import com.clima.alertclima.model.ClimaActualDTO;
import com.clima.alertclima.model.ClimaHistoricoDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ClimaService {

    private final ClimaClient climaClient;

    public ClimaService(ClimaClient climaClient) {
        this.climaClient = climaClient;
    }

    // key combina lat y lon para que cada ubicación tenga su propia entrada en caché
    @Cacheable(value = "climaActual", key = "#lat + '-' + #lon")
    public ClimaActualDTO obtenerClimaActual(Double lat, Double lon) {
        System.out.println(">>> Llamando a Open-Meteo para lat=" + lat + " lon=" + lon);
        return climaClient.obtenerClimaActual(lat, lon);
    }

    public ClimaHistoricoDTO obtenerClimaHistorico(Double lat, Double lon,
                                                   String fechaInicio, String fechaFin) {
        return climaClient.obtenerClimaHistorico(lat, lon, fechaInicio, fechaFin);
    }
}