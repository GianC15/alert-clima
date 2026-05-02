package com.clima.alertclima.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "alertas")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double lat;
    private Double lon;

    // Condición: "LLUVIA" o "TEMPERATURA"
    private String tipoCondicion;

    // Umbral: ej. 20.0 (mm de lluvia) o 35.0 (°C)
    private Double umbral;

    // Email donde se notifica (simulado por ahora)
    private String email;

    // Para saber si ya fue notificada y no spamear
    private Boolean notificada = false;
}