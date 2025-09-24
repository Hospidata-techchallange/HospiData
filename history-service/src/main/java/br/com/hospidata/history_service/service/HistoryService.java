package br.com.hospidata.history_service.service;

import br.com.hospidata.history_service.entity.MedicalHistory;
import br.com.hospidata.history_service.repository.MedicalHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class HistoryService {

    private final MedicalHistoryRepository repository;

    public HistoryService(MedicalHistoryRepository repository) {
        this.repository = repository;
    }

    public MedicalHistory createMedicalHistory(MedicalHistory medicalHistory) {
        medicalHistory.setCreatedAt(LocalDateTime.now());
        return repository.save(medicalHistory);
    }

    public List<MedicalHistory> getMedicalHistoryByPatientId(UUID patientId) {
        return repository.findByPatientId(patientId);
    }
}