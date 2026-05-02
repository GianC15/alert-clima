package com.clima.alertclima.scheduler;

import com.clima.alertclima.entity.Alerta;
import com.clima.alertclima.model.ClimaActualDTO;
import com.clima.alertclima.service.AlertaService;
import com.clima.alertclima.service.ClimaService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlertaScheduler {

    private final AlertaService alertaService;
    private final ClimaService climaService;

    public AlertaScheduler(AlertaService alertaService, ClimaService climaService) {
        this.alertaService = alertaService;
        this.climaService = climaService;
    }

    // Se ejecuta cada 60 segundos (en producción sería cada 10-30 min)
    @Scheduled(fixedDelay = 60000)
    public void revisarAlertas() {
        System.out.println(">>> [Scheduler] Revisando alertas pendientes...");

        List<Alerta> pendientes = alertaService.obtenerAlertasPendientes();

        if (pendientes.isEmpty()) {
            System.out.println(">>> [Scheduler] No hay alertas pendientes.");
            return;
        }

        for (Alerta alerta : pendientes) {
            ClimaActualDTO clima = climaService.obtenerClimaActual(alerta.getLat(), alerta.getLon());
            Double valorActual = obtenerValorSegunCondicion(alerta, clima);

            if (valorActual != null && valorActual >= alerta.getUmbral()) {
                notificar(alerta, valorActual);
                alertaService.marcarComoNotificada(alerta);
            } else {
                System.out.println(">>> [Scheduler] Alerta id=" + alerta.getId()
                        + " no cumple condición. Valor actual: " + valorActual
                        + " | Umbral: " + alerta.getUmbral());
            }
        }
    }

    private Double obtenerValorSegunCondicion(Alerta alerta, ClimaActualDTO clima) {
        return switch (alerta.getTipoCondicion().toUpperCase()) {
            case "LLUVIA"      -> clima.getDatosActuales().getPrecipitacion();
            case "TEMPERATURA" -> clima.getDatosActuales().getTemperatura();
            case "VIENTO"      -> clima.getDatosActuales().getVelocidadViento();
            default -> {
                System.out.println(">>> [Scheduler] Condición desconocida: " + alerta.getTipoCondicion());
                yield null;
            }
        };
    }

    private void notificar(Alerta alerta, Double valorActual) {
        System.out.println("==================================================");
        System.out.println("🚨 ALERTA DISPARADA");
        System.out.println("   Email:      " + alerta.getEmail());
        System.out.println("   Condición:  " + alerta.getTipoCondicion());
        System.out.println("   Umbral:     " + alerta.getUmbral());
        System.out.println("   Valor real: " + valorActual);
        System.out.println("   Ubicación:  lat=" + alerta.getLat() + " lon=" + alerta.getLon());
        System.out.println("==================================================");
    }
}