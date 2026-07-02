package com.upc.pre.urbanvoiceapp.reports.application.services;

import com.upc.pre.urbanvoiceapp.reports.domain.entities.IncidentCategory;
import com.upc.pre.urbanvoiceapp.reports.domain.repositories.IncidentCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IncidentCategoryApplicationService {

    private final IncidentCategoryRepository categoryRepository;

    public IncidentCategoryApplicationService(IncidentCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public IncidentCategory createCategory(String name, String description) {
        IncidentCategory category = new IncidentCategory(name, description);
        return categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public IncidentCategory findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<IncidentCategory> findAll() {
        return categoryRepository.findAll();
    }

    public IncidentCategory updateCategory(Long id, String name, String description) {
        IncidentCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));
        if (name != null) category.setName(name);
        if (description != null) category.setDescription(description);
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
