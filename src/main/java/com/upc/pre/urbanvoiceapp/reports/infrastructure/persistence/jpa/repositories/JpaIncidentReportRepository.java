package com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.reports.domain.entities.IncidentReport;
import com.upc.pre.urbanvoiceapp.reports.domain.repositories.IncidentReportRepository;
import com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.mappers.IncidentReportJpaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementacion de {@link IncidentReportRepository} utilizando Spring Data JPA.
 *
 * <p>Adapta el contrato de dominio al repositorio Spring Data y aplica el mapper
 * para mantener separadas las representaciones de dominio y persistencia.</p>
 */
@Repository
public class JpaIncidentReportRepository implements IncidentReportRepository {

    private final IncidentReportSpringDataRepository springDataRepository;
    private final IncidentReportJpaMapper mapper;

    /**
     * Construye el adaptador de persistencia.
     *
     * @param springDataRepository repositorio Spring Data de entidades JPA.
     * @param mapper conversor entre entidades JPA y agregados de dominio.
     */
    public JpaIncidentReportRepository(IncidentReportSpringDataRepository springDataRepository, IncidentReportJpaMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    /**
     * Guarda un agregado despues de validar sus invariantes.
     *
     * @param report agregado a persistir.
     * @return agregado persistido reconstruido desde la entidad guardada.
     */
    @Override
    public IncidentReport save(IncidentReport report) {
        report.validate();
        var jpaEntity = mapper.toJpa(report);
        var savedEntity = springDataRepository.save(jpaEntity);
        return mapper.toDomain(savedEntity);
    }

    /**
     * Busca un reporte por identificador.
     *
     * @param id identificador del reporte.
     * @return reporte encontrado o vacio si no existe.
     */
    @Override
    public Optional<IncidentReport> findById(Long id) {
        return springDataRepository.findById(id).map(mapper::toDomain);
    }

    /**
     * Busca reportes asociados a un usuario.
     *
     * @param userId identificador del usuario.
     * @return reportes del usuario indicado.
     */
    @Override
    public List<IncidentReport> findByUserId(Long userId) {
        return springDataRepository.findByUserId(userId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Busca reportes cercanos a una coordenada.
     *
     * @param latitude latitud del punto central.
     * @param longitude longitud del punto central.
     * @param radiusInKm radio maximo en kilometros.
     * @return reportes dentro del radio indicado.
     */
    @Override
    public List<IncidentReport> findNearby(double latitude, double longitude, double radiusInKm) {
        return springDataRepository.findNearby(latitude, longitude, radiusInKm).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Elimina el reporte indicado.
     *
     * @param id identificador del reporte a eliminar.
     */
    @Override
    public void deleteById(Long id) {
        springDataRepository.deleteById(id);
    }

    /**
     * Recupera todos los reportes persistidos.
     *
     * @return lista completa de reportes.
     */
    @Override
    public List<IncidentReport> findAll() {
        return springDataRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
