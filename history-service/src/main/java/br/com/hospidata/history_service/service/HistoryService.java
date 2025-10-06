package br.com.hospidata.history_service.service;

import br.com.hospidata.history_service.controller.dto.MedicalHistoryRequest;
import br.com.hospidata.history_service.entity.MedicalHistory;
import br.com.hospidata.history_service.repository.MedicalHistoryRepository;
import br.com.hospidata.history_service.service.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class HistoryService {

    private final MedicalHistoryRepository repository;

    private static final Logger log = LoggerFactory.getLogger(HistoryService.class);

    public HistoryService(MedicalHistoryRepository repository) {
        this.repository = repository;
    }

    public MedicalHistory createMedicalHistory(MedicalHistoryRequest request) {
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setAppointmentId(request.appointmentId());
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

    public List<MedicalHistory> getAllMedicalHistoryByAppointmentId(UUID appointmentId) {
        return repository.findByAppointmentId(appointmentId);
    }

    public List<MedicalHistory> searchMedicalHistory(UUID appointmentId, UUID patientId, UUID doctorId) {
        if (appointmentId != null) {
            List<MedicalHistory> result = repository.findByAppointmentId(appointmentId);
            if (result.isEmpty()) {
                throw new ResourceNotFoundException("No medical history found for appointment with ID: " + appointmentId);
            }
            return result;
        }

        if (patientId != null && doctorId != null) {
            List<MedicalHistory> result = repository.findByPatientIdAndDoctorId(patientId, doctorId);
            if (result.isEmpty()) {
                throw new ResourceNotFoundException(
                        "No medical history found for patient ID: " + patientId + " and doctor ID: " + doctorId
                );
            }
            return result;
        } else if (patientId != null) {
            List<MedicalHistory> result = repository.findByPatientId(patientId);
            if (result.isEmpty()) {
                throw new ResourceNotFoundException("No medical history found for patient with ID: " + patientId);
            }
            return result;
        } else if (doctorId != null) {
            List<MedicalHistory> result = repository.findByDoctorId(doctorId);
            if (result.isEmpty()) {
                throw new ResourceNotFoundException("No medical history found for doctor with ID: " + doctorId);
            }
            return result;
        } else {
            List<MedicalHistory> result = getAllMedicalHistory();
            if (result.isEmpty()) {
                throw new ResourceNotFoundException("No medical history found");
            }
            return result;
        }

    }
}