package br.com.hospidata.history_service.service;

import br.com.hospidata.history_service.controller.dto.MedicalHistoryRequest;
import br.com.hospidata.history_service.entity.MedicalHistory;
import br.com.hospidata.history_service.repository.MedicalHistoryRepository;
import br.com.hospidata.history_service.service.exceptions.ResourceNotFoundException;
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

    public MedicalHistory createMedicalHistory(MedicalHistoryRequest request) {
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setPatientId(request.patientId());
        medicalHistory.setPatientName(request.patientName());
        medicalHistory.setPatientEmail(request.patientEmail());
        medicalHistory.setDoctorId(request.doctorId());
        medicalHistory.setDoctorName(request.doctorName());
        medicalHistory.setDoctorEmail(request.doctorEmail());
        medicalHistory.setDescription(request.description());
        medicalHistory.setCreatedAt(LocalDateTime.now());
        return repository.save(medicalHistory);
    }

    public List<MedicalHistory> getMedicalHistoryByPatientId(UUID patientId) {
        List<MedicalHistory> history = repository.findByPatientId(patientId);
        if (history.isEmpty()) {
            throw new ResourceNotFoundException("No medical history found for patient with ID: " + patientId);
        }
        return history;
    }

    public List<MedicalHistory> getAllMedicalHistory() {
        return repository.findAll();
    }
}