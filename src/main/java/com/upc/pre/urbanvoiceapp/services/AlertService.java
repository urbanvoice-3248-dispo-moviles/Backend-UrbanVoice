package com.upc.pre.urbanvoiceapp.services;

import com.upc.pre.urbanvoiceapp.models.Alert;
import com.upc.pre.urbanvoiceapp.repositories.AlertRepository;
import com.upc.pre.urbanvoiceapp.schemas.AlertSchema;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {
    private final AlertRepository repository;

    public AlertService(AlertRepository repository) {
        this.repository = repository;
    }

    public Alert saveAlert(AlertSchema alertSchema) {
        Alert newAlert = new Alert(alertSchema.location(), alertSchema.type(), alertSchema.description(), alertSchema.idUser(), alertSchema.image_url(), alertSchema.idReport());
        return repository.save(newAlert);
    }


    public List<Alert> findByUserId(int userId) {
        return repository.findByUserId(userId);

    }

    public Alert findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Alert> findAll() {
        return repository.findAll();
    }
    public void deleteAllAlerts() {
        repository.deleteAll();
    }
}
