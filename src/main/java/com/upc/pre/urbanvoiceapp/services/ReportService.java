package com.upc.pre.urbanvoiceapp.services;

import com.upc.pre.urbanvoiceapp.models.Report;
import com.upc.pre.urbanvoiceapp.repositories.ReportRepository;
import com.upc.pre.urbanvoiceapp.schemas.ReportSchema;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    private final ReportRepository repository;

    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    public Report saveReport(ReportSchema report) {
        Report newReport = repository.save(new Report(report.title(), report.detail(), report.type(), report.user_id(), report.image(), report.address()));
        return newReport;
    }

    public List<Report> findAll() {
        return repository.findAll();
    }

    public Report findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Report> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Report save(Report report) {
        return repository.save(report);
    }

}
