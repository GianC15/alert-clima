package com.clima.alertclima.controller;

import com.clima.alertclima.model.ClimaActualDTO;
import com.clima.alertclima.model.ClimaHistoricoDTO;
import com.clima.alertclima.service.ClimaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController               // combina @Controller + @ResponseBody
@RequestMapping("/clima")     // todas las rutas de esta clase arrancan con /clima
public class ClimaController {

    private final ClimaService climaService;

    public ClimaController(ClimaService climaService) {
        this.climaService = climaService;
    }

    @GetMapping("/actual")    // GET /clima/actual
    public ClimaActualDTO obtenerClimaActual(
            @RequestParam Double lat,   // ?lat=...
            @RequestParam Double lon) { // &lon=...
        return climaService.obtenerClimaActual(lat, lon);
    }

    @GetMapping("/historico")
    public ClimaHistoricoDTO obtenerClimaHistorico(
            @RequestParam Double lat,
            @RequestParam Double lon,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        return climaService.obtenerClimaHistorico(lat, lon, fechaInicio, fechaFin);
    }
}