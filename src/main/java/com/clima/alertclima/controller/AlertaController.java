package com.clima.alertclima.controller;

import com.clima.alertclima.entity.Alerta;
import com.clima.alertclima.service.AlertaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

    private final AlertaService alertaService;

    public AlertaController(AlertaService alertaService) {
        this.alertaService = alertaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)  // devuelve 201 en lugar de 200
    public Alerta crearAlerta(@RequestBody Alerta alerta) {
        return alertaService.guardarAlerta(alerta);
    }
}