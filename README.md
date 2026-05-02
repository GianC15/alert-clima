# 🌾 Dashboard de Clima

API REST desarrollada con **Spring Boot 3.5** que permite a interesados consultar el clima actual, revisar datos históricos y configurar alertas automáticas basadas en condiciones meteorológicas.

## ¿Qué problema resuelve?

Para toda aquel que necesita tomar decisiones diarias basadas en el clima: si regar, si cosechar, si sacar los animales. Las apps de clima genéricas no están pensadas para el agro. Este sistema permite:

- Consultar el clima en tiempo real para cualquier coordenada geográfica
- Revisar datos históricos de temperatura y precipitaciones por rango de fechas
- Configurar alertas personalizadas que se disparan automáticamente cuando se supera un umbral (ej: "avisame si llueve más de 20mm")

## ¿Cómo funciona?

El backend actúa como intermediario inteligente entre el agricultor y la API meteorológica [Open-Meteo](https://open-meteo.com/). El agricultor configura sus alertas una sola vez, y el sistema las revisa automáticamente en segundo plano cada 60 segundos.

```
Agricultor → POST /alertas (configura condición)
                    ↓
           Alerta guardada en BD
                    ↓
    Scheduler revisa cada 60 segundos
                    ↓
    Consulta clima real a Open-Meteo
                    ↓
    ¿Se cumple la condición? → Notificación
```

## Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| Java 21 | Lenguaje principal |
| Spring Boot 3.5 | Framework base |
| Spring Web + RestClient | Consumo de API externa |
| Spring Cache + Caffeine | Caché con expiración de 10 minutos |
| Spring Data JPA | Persistencia de alertas |
| Spring Scheduler | Revisión periódica de alertas |
| H2 Database | Base de datos en memoria |
| Lombok | Reducción de código repetitivo |
| Open-Meteo API | Datos meteorológicos (gratuita, sin API key) |

## Endpoints disponibles

### GET /clima/actual
Devuelve el clima actual para una ubicación. El resultado se cachea por 10 minutos.

```
GET /clima/actual?lat=-34.6&lon=-58.4
```

**Respuesta:**
```json
{
  "latitude": -34.622143,
  "longitude": -58.40909,
  "current": {
    "temperature_2m": 13.0,
    "precipitation": 0.0,
    "windspeed_10m": 17.8
  }
}
```

---

### GET /clima/historico
Devuelve temperatura máxima y precipitaciones diarias para un rango de fechas.

```
GET /clima/historico?lat=-34.6&lon=-58.4&fechaInicio=2025-01-01&fechaFin=2025-01-07
```

**Respuesta:**
```json
{
  "latitude": -34.622143,
  "longitude": -58.40909,
  "daily": {
    "time": ["2025-01-01", "2025-01-02"],
    "temperature_2m_max": [31.4, 31.2],
    "precipitation_sum": [8.8, 0.0]
  }
}
```

---

### POST /alertas
Registra una alerta. El sistema la revisará automáticamente cada 60 segundos.

```
POST /alertas
Content-Type: application/json
```

**Body:**
```json
{
  "lat": -34.6,
  "lon": -58.4,
  "tipoCondicion": "LLUVIA",
  "umbral": 20.0,
  "email": "agricultor@email.com"
}
```

**Condiciones disponibles:** `LLUVIA`, `TEMPERATURA`, `VIENTO`

**Respuesta (201 Created):**
```json
{
  "id": 1,
  "lat": -34.6,
  "lon": -58.4,
  "tipoCondicion": "LLUVIA",
  "umbral": 20.0,
  "email": "agricultor@email.com",
  "notificada": false
}
```

## Cómo correr el proyecto

### Prerequisitos
- Java 21
- Maven 3.x

### Pasos

```bash
# Clonar el repositorio
git clone https://github.com/tu-usuario/alert-clima.git
cd alert-clima

# Compilar y correr
./mvnw spring-boot:run
```

La app arranca en `http://localhost:8080`.

### Consola H2 (base de datos)
Podés ver los datos guardados en `http://localhost:8080/h2-console` con los siguientes datos:

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:mem:alertadb` |
| User Name | `sa` |
| Password | (vacío) |

## Arquitectura del proyecto

```
src/main/java/com/clima/alertclima/
├── controller/        # Recibe las requests HTTP
│   ├── ClimaController.java
│   └── AlertaController.java
├── service/           # Lógica de negocio
│   ├── ClimaService.java
│   └── AlertaService.java
├── client/            # Llama a la API externa
│   └── ClimaClient.java
├── scheduler/         # Tarea programada
│   └── AlertaScheduler.java
├── repository/        # Acceso a base de datos
│   └── AlertaRepository.java
├── entity/            # Entidades JPA
│   └── Alerta.java
├── model/             # DTOs
│   ├── ClimaActualDTO.java
│   └── ClimaHistoricoDTO.java
└── config/            # Configuración de caché
    └── CacheConfig.java
```

## Conceptos demostrados

- **RestClient**: llamadas HTTP a APIs externas de forma moderna (Spring Boot 3.2+)
- **@Cacheable + Caffeine**: caché con expiración configurable para reducir llamadas a la API
- **Spring Data JPA**: persistencia con queries generadas automáticamente por nombre de método
- **@Scheduled**: tareas programadas que corren en segundo plano de forma asíncrona
- **Arquitectura en capas**: separación de responsabilidades entre Controller, Service, Client y Repository
- **Inyección de dependencias por constructor**: buena práctica recomendada sobre @Autowired

## Mejoras futuras

- [ ] Reemplazar H2 por PostgreSQL para persistencia real
- [ ] Envío de emails reales con JavaMailSender en lugar de logs en consola
- [ ] Endpoint `GET /alertas` para listar todas las alertas de un usuario
- [ ] Usar precipitación acumulada diaria del endpoint histórico para alertas más precisas
- [ ] Agregar autenticación con Spring Security + JWT
- [ ] Tests unitarios con JUnit y Mockito
- [ ] Dockerizar la aplicación

## Autor

**Gian Castro Colicigno**  
[LinkedIn](https://www.linkedin.com/in/gian-colicigno-921ba21b0/) · [Portfolio](https://portfoliogianc.netlify.app/)
