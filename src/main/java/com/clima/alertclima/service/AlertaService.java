package com.clima.alertclima.service;

import com.clima.alertclima.entity.Alerta;
import com.clima.alertclima.repository.AlertaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AlertaService {

    private final AlertaRepository alertaRepository;

    public AlertaService(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    public Alerta guardarAlerta(Alerta alerta) {
        return alertaRepository.save(alerta);
    }

    public List<Alerta> obtenerAlertasPendientes() {
        return alertaRepository.findByNotificadaFalse();
    }

    public void marcarComoNotificada(Alerta alerta) {
        alerta.setNotificada(true);
        alertaRepository.save(alerta);
    }
}