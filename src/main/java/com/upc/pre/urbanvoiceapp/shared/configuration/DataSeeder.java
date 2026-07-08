package com.upc.pre.urbanvoiceapp.shared.configuration;

import com.upc.pre.urbanvoiceapp.profiles.application.commands.CreateUserProfileCommand;
import com.upc.pre.urbanvoiceapp.profiles.application.services.UserProfileApplicationService;
import com.upc.pre.urbanvoiceapp.profiles.domain.repositories.UserProfileRepository;
import com.upc.pre.urbanvoiceapp.reports.application.commands.CreateIncidentReportCommand;
import com.upc.pre.urbanvoiceapp.reports.application.services.IncidentReportApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class DataSeeder {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private static final DateTimeFormatter FMT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    record ReportSeed(String title, String description, String incidentType, double lat, double lng, String address) {}

    @Bean
    CommandLineRunner seedDatabase(
            UserProfileRepository userProfileRepository,
            UserProfileApplicationService userService,
            IncidentReportApplicationService reportService
    ) {
        return args -> {
            if (userProfileRepository.existsByEmail("seed@urbanvoice.test")) {
                log.info("Seed data already exists — skipping.");
                return;
            }

            log.info("Seeding database with test data...");

            var user = userService.handle(new CreateUserProfileCommand(
                    "Seed", "User", 30,
                    "seed@urbanvoice.test", "999000000",
                    null, "Seed1234!"
            ));
            Long userId = user.getId();
            log.info("Created seed user with id={}", userId);

            var now = LocalDateTime.now();
            var offsetHours = ThreadLocalRandom.current();

            List<ReportSeed> reports = buildReports();
            for (int i = 0; i < reports.size(); i++) {
                var r = reports.get(i);
                var reportedAt = now.minusHours(offsetHours.nextInt(1, 49))
                        .minusMinutes(offsetHours.nextInt(0, 60));
                reportService.handle(new CreateIncidentReportCommand(
                        userId, r.incidentType(), r.title(), r.description(),
                        r.lat(), r.lng(), r.address(), null, false,
                        reportedAt.format(FMT)
                ));
            }

            log.info("Seeded {} incident reports successfully.", reports.size());
        };
    }

    private static List<ReportSeed> buildReports() {
        return List.of(
                // ========== MIRAFLORES — 15 reportes (alta concentración) ==========
                new ReportSeed("Robo en Larcomar", "Sujeto armado sustrajo pertenencias a turistas.", "ROBBERY", -12.1205, -77.0295, "Larcomar, Miraflores"),
                new ReportSeed("Asalto en Av. Larco", "Dos moto-taxistas asaltaron a transeúnte.", "ASSAULT", -12.1218, -77.0312, "Av. Larco 450, Miraflores"),
                new ReportSeed("Acoso en el Malecón", "Hombre siguió a una mujer por 3 cuadras.", "HARASSMENT", -12.1230, -77.0350, "Malecón Cisneros, Miraflores"),
                new ReportSeed("Vandalismo en parque", "Rompieron bancas y farolas del parque Kennedy.", "VANDALISM", -12.1242, -77.0325, "Parque Kennedy, Miraflores"),
                new ReportSeed("Accidente de tránsito", "Choque entre dos autos en Av. Pardo.", "ACCIDENT", -12.1190, -77.0280, "Av. Pardo cdra 3, Miraflores"),
                new ReportSeed("Robo de bicicleta", "Bicicleta robada en estacionamiento público.", "ROBBERY", -12.1220, -77.0340, "Calle Bellavista 210, Miraflores"),
                new ReportSeed("Asalto a delivery", "Repartidor asaltado en Ovalo de Miraflores.", "ASSAULT", -12.1210, -77.0300, "Óvalo Miraflores"),
                new ReportSeed("Acoso en microbús", "Pasajera denuncia tocamientos indebidos.", "HARASSMENT", -12.1250, -77.0360, "Av. Arequipa cdra 45, Miraflores"),
                new ReportSeed("Cristalazo a auto", "Rompió luneta para robar mochila.", "VANDALISM", -12.1185, -77.0275, "Calle Schell 250, Miraflores"),
                new ReportSeed("Accidente moto-lineal", "Motociclista derrapó en Av. La Paz.", "ACCIDENT", -12.1260, -77.0330, "Av. La Paz 600, Miraflores"),
                new ReportSeed("Robo en tienda", "Dos encapuchados robaron en boutique.", "ROBBERY", -12.1200, -77.0318, "Av. Larco 720, Miraflores"),
                new ReportSeed("Asalto con cuchillo", "Victimario amenazó con arma blanca.", "ASSAULT", -12.1235, -77.0290, "Calle Porta 180, Miraflores"),
                new ReportSeed("Intento de secuestro", "Falso taxi intentó subir a menor.", "OTHER", -12.1245, -77.0370, "Av. Del Ejército 300, Miraflores"),
                new ReportSeed("Robo de celular", "Arrebato en plena calle durante la noche.", "ROBBERY", -12.1215, -77.0355, "Malecón 28 de Julio, Miraflores"),
                new ReportSeed("Vandalismo en monumento", "Pintaron grafitis en el Huaca Pucllana.", "VANDALISM", -12.1180, -77.0320, "Huaca Pucllana, Miraflores"),

                // ========== SAN ISIDRO — 10 reportes (concentración media-alta) ==========
                new ReportSeed("Robo en oficina", "Ingresaron a consultorio y robaron laptop.", "ROBBERY", -12.0930, -77.0430, "Av. Pardo y Aliaga 350, San Isidro"),
                new ReportSeed("Asalto en Camino Real", "Asalto a mano armada en centro comercial.", "ASSAULT", -12.0945, -77.0450, "Camino Real 111, San Isidro"),
                new ReportSeed("Robo de auto", "Vehículo estacionado fue robado de madrugada.", "ROBBERY", -12.0960, -77.0465, "Calle Las Begonias 450, San Isidro"),
                new ReportSeed("Asalto a ejecutivo", "Ejecutivo asaltado al salir del banco.", "ASSAULT", -12.0950, -77.0440, "Av. Rivera Navarrete 250, San Isidro"),
                new ReportSeed("Accidente en Javier Prado", "Múltiple choque por exceso de velocidad.", "ACCIDENT", -12.0920, -77.0480, "Av. Javier Prado Este 500, San Isidro"),
                new ReportSeed("Robo en restaurante", "Carterista robó en concurrido restaurante.", "ROBBERY", -12.0970, -77.0435, "Calle Los Libertadores 180, San Isidro"),
                new ReportSeed("Asalto con arma de fuego", "Disparo al aire durante asalto a farmacia.", "ASSAULT", -12.0980, -77.0470, "Av. Arenales 1200, San Isidro"),
                new ReportSeed("Vandalismo en parque", "Destruyeron juegos infantiles del parque.", "VANDALISM", -12.0940, -77.0420, "Parque El Olivar, San Isidro"),
                new ReportSeed("Robo en construcción", "Materiales de obra fueron sustraídos.", "ROBBERY", -12.0990, -77.0490, "Calle Manuel Olguín 500, San Isidro"),
                new ReportSeed("Acoso en el Metropolitano", "Hombre grabó bajo la falda a pasajera.", "HARASSMENT", -12.0955, -77.0445, "Estación Javier Prado, San Isidro"),

                // ========== BARRANCO — 8 reportes (concentración media) ==========
                new ReportSeed("Robo en Barranco", "Robo a mano armada en puente de los Suspiros.", "ROBBERY", -12.1400, -77.0190, "Puente de los Suspiros, Barranco"),
                new ReportSeed("Asalto en playa", "Bañistas asaltados en la Costa Verde.", "ASSAULT", -12.1420, -77.0230, "Playa Barranquito, Barranco"),
                new ReportSeed("Vandalismo en galería", "Rayaron paredes de galería de arte.", "VANDALISM", -12.1435, -77.0210, "Av. Pedro de Osma 250, Barranco"),
                new ReportSeed("Robo en discoteca", "Pertenencias robadas en evento nocturno.", "ROBBERY", -12.1410, -77.0200, "Calle San Martín 300, Barranco"),
                new ReportSeed("Acoso callejero", "Hombre realizó tocimientos en la vía pública.", "HARASSMENT", -12.1440, -77.0240, "Malecón Barranco cdra 2"),
                new ReportSeed("Accidente en bajada", "Auto perdió control en bajada de Armendáriz.", "ACCIDENT", -12.1395, -77.0180, "Bajada Armendáriz, Barranco"),
                new ReportSeed("Robo en hostal", "Huespedes reportan robo de pertenencias.", "ROBBERY", -12.1425, -77.0220, "Calle Cajamarca 150, Barranco"),
                new ReportSeed("Intento de robo", "Sujeto intentó romper vidrio de auto.", "VANDALISM", -12.1450, -77.0250, "Av. Grau 500, Barranco"),

                // ========== LOS OLIVOS — 5 reportes (concentración media-baja) ==========
                new ReportSeed("Robo en mercado", "Sustrajeron dinero de puesto en mercado.", "ROBBERY", -11.9730, -77.0730, "Mercado Los Olivos cdra 4"),
                new ReportSeed("Asalto en paradero", "Asalto colectivo a pasajeros en paradero.", "ASSAULT", -11.9745, -77.0750, "Av. Universitaria cdra 52, Los Olivos"),
                new ReportSeed("Accidente en Panamericana", "Choque por niebla en la Panamericana Norte.", "ACCIDENT", -11.9760, -77.0770, "Panamericana Norte km 18, Los Olivos"),
                new ReportSeed("Robo en vivienda", "Casa fue forzada y robaron electrodomésticos.", "ROBBERY", -11.9720, -77.0740, "Calle Los Alamos 300, Los Olivos"),
                new ReportSeed("Vandalismo en loza deportiva", "Quemaron mallas de la losa deportiva.", "VANDALISM", -11.9755, -77.0760, "Loza Deportiva Los Olivos"),

                // ========== COMAS — 3 reportes (concentración baja) ==========
                new ReportSeed("Robo en bus", "Carterista robó dentro de bus interprovincial.", "ROBBERY", -11.9240, -77.0610, "Av. Túpac Amaru cdra 22, Comas"),
                new ReportSeed("Asalto en mercado", "Dos sujetos asaltaron a comerciante.", "ASSAULT", -11.9260, -77.0630, "Mercado Unicachi, Comas"),
                new ReportSeed("Accidente en óvalo", "Choque entre combi y mototaxi en óvalo.", "ACCIDENT", -11.9235, -77.0605, "Óvalo Comas"),

                // ========== SAN JUAN DE LURIGANCHO — 2 reportes (muy baja) ==========
                new ReportSeed("Robo en obra", "Materiales robados de construcción.", "ROBBERY", -12.0160, -77.0040, "Av. Las Flores de Primavera, SJL"),
                new ReportSeed("Vandalismo en paradero", "Destruyeron paradero de bus.", "VANDALISM", -12.0180, -77.0060, "Av. Prolongación San Martín, SJL"),

                // ========== VILLA MARÍA DEL TRIUNFO — 2 reportes (muy baja) ==========
                new ReportSeed("Robo en vivienda", "Casa allanada en hora de madrugada.", "ROBBERY", -12.1710, -76.9370, "Av. Pachacútec cdra 15, VMT"),
                new ReportSeed("Accidente de tránsito", "Volcadura de camión en carretera.", "ACCIDENT", -12.1730, -76.9400, "Carretera Panamericana Sur, VMT"),

                // ========== PUNTA NEGRA — 1 reporte ==========
                new ReportSeed("Robo en playa", "Bañista robado mientras nadaba.", "ROBBERY", -12.3500, -76.8000, "Playa Punta Negra"),

                // ========== CIENEGUILLA — 1 reporte ==========
                new ReportSeed("Accidente en carretera", "Auto despistó en curva peligrosa.", "ACCIDENT", -12.0880, -76.8600, "Carretera Cieneguilla km 25"),

                // ========== SANTA ROSA — 1 reporte ==========
                new ReportSeed("Acoso en playa", "Hombre acosó a bañistas en la orilla.", "HARASSMENT", -11.8000, -77.1700, "Playa Santa Rosa")
        );
    }
}
