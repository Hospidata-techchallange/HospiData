package br.com.hospidata.appointment_service.service;

import br.com.hospidata.appointment_service.entity.Appointment;
import br.com.hospidata.appointment_service.entity.enums.AppointmentStatus;
import br.com.hospidata.appointment_service.repository.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final KafkaTemplate<String, Appointment> kafkaTemplate;
    private static final String KAFKA_TOPIC = "notification-topic";

    public AppointmentService(AppointmentRepository appointmentRepository, KafkaTemplate<String, Appointment> kafkaTemplate) {
        this.appointmentRepository = appointmentRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Appointment createAppointment(Appointment appointment) {
        LocalDateTime now = LocalDateTime.now();
        appointment.setCreatedAt(now);
        appointment.setLastUpdatedAt(now);
        appointment.setFirstScheduledDate(appointment.getScheduledDate());
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        kafkaTemplate.send(KAFKA_TOPIC, savedAppointment);

        return savedAppointment;
    }

    public Optional<Appointment> findAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public List<Appointment> findAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment updateAppointment(Long id, Appointment appointmentDetails) {
        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id: " + id));

        existingAppointment.setScheduledDate(appointmentDetails.getScheduledDate());
        existingAppointment.setStatus(appointmentDetails.getStatus());
        existingAppointment.setLastUpdatedAt(LocalDateTime.now());

        Appointment updatedAppointment = appointmentRepository.save(existingAppointment);

        kafkaTemplate.send(KAFKA_TOPIC, updatedAppointment);

        return updatedAppointment;
    }

    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id: " + id));

        appointment.setStatus(AppointmentStatus.CANCELED);
        appointment.setLastUpdatedAt(LocalDateTime.now());

        Appointment canceledAppointment = appointmentRepository.save(appointment);

        kafkaTemplate.send(KAFKA_TOPIC, canceledAppointment);
    }
}