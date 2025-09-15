package br.com.hospidata.appointment_service.service;

import br.com.hospidata.appointment_service.controller.dto.AppointmentRequest;
import br.com.hospidata.appointment_service.controller.dto.AppointmentUpdateRequest;
import br.com.hospidata.appointment_service.entity.Appointment;
import br.com.hospidata.appointment_service.entity.OutboxEvent;
import br.com.hospidata.appointment_service.entity.enums.AppointmentStatus;
import br.com.hospidata.appointment_service.repository.AppointmentRepository;
import br.com.hospidata.appointment_service.mapper.AppointmentMapper;
import br.com.hospidata.appointment_service.repository.OutboxEventRepository;
import br.com.hospidata.appointment_service.service.exceptions.ResourceNotFoundException;
import br.com.hospidata.appointment_service.utils.JsonUtils;
import jakarta.validation.Valid;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AppointmentService {

    private final AppointmentRepository repository;
    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, Appointment> kafkaTemplate;
    private static final String KAFKA_TOPIC = "notification-topic";
    private final AppointmentMapper mapper;

    public AppointmentService(AppointmentRepository repository, OutboxEventRepository outboxEventRepository , KafkaTemplate<String, Appointment> kafkaTemplate , AppointmentMapper mapper) {
        this.repository = repository;
        this.outboxEventRepository = outboxEventRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }

    public Appointment createAppointment( Appointment appointment) {
        LocalDateTime now = LocalDateTime.now();

        appointment.setCreatedAt(now);
        appointment.setLastUpdatedAt(now);
        appointment.setStatus(AppointmentStatus.SCHEDULED);

         var appointmentSave = repository.save(appointment);

         var appointmentNotification = mapper.toNotification(appointmentSave);

        outboxEventRepository.save(new OutboxEvent(
                "Appointment",
                appointmentSave.getId(),
                JsonUtils.toJson(appointmentNotification),
                "APPOINTMENT_CREATED",
                now,
                false
        ));

        return appointmentSave;
    }

    public Appointment updateAppointment(UUID id, @Valid AppointmentUpdateRequest appointmentUpdateRequest) {
        Appointment appointment = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id.toString()));

        appointment.setLastUpdatedAt(LocalDateTime.now());
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        updateAppointment(appointment, appointmentUpdateRequest);

        var appointmentNotification = mapper.toNotification(appointment);
        outboxEventRepository.save(new OutboxEvent(
                "Appointment",
                appointment.getId(),
                JsonUtils.toJson(appointmentNotification),
                "APPOINTMENT_UPDATED",
                appointment.getLastUpdatedAt(),
                false
        ));

        return repository.save(appointment);
    }

    public void updateAppointment(Appointment appointment, AppointmentUpdateRequest appointmentUpdateRequest) {
        appointment.setLastUpdatedAt(LocalDateTime.now());
        appointment.setStatus(appointmentUpdateRequest.status());
        appointment.setScheduledDate(appointmentUpdateRequest.scheduledDate());

        if (appointmentUpdateRequest.doctorId() != null) {
            appointment.setDoctorId(appointmentUpdateRequest.doctorId());
            appointment.setDoctorName(appointmentUpdateRequest.doctorName());
            appointment.setDoctorEmail(appointmentUpdateRequest.doctorEmail());
        }

        if (appointmentUpdateRequest.patientId() != null) {
            appointment.setPatientId(appointmentUpdateRequest.patientId());
            appointment.setPatientName(appointmentUpdateRequest.patientName());
            appointment.setPatientEmail(appointmentUpdateRequest.patientEmail());
        }

    };

}