package br.com.hospidata.appointment_service.service;

import br.com.hospidata.appointment_service.controller.dto.AppointmentNotification;
import br.com.hospidata.appointment_service.controller.dto.AppointmentRequest;
import br.com.hospidata.appointment_service.entity.Appointment;
import br.com.hospidata.appointment_service.entity.OutboxEvent;
import br.com.hospidata.appointment_service.entity.enums.AppointmentStatus;
import br.com.hospidata.appointment_service.repository.AppointmentRepository;
import br.com.hospidata.appointment_service.mapper.AppointmentMapper;
import br.com.hospidata.appointment_service.repository.OutboxEventRepository;
import br.com.hospidata.appointment_service.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, Appointment> kafkaTemplate;
    private static final String KAFKA_TOPIC = "notification-topic";
    private final AppointmentMapper mapper;

    public AppointmentService(AppointmentRepository appointmentRepository, OutboxEventRepository outboxEventRepository , KafkaTemplate<String, Appointment> kafkaTemplate , AppointmentMapper mapper) {
        this.appointmentRepository = appointmentRepository;
        this.outboxEventRepository = outboxEventRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }


    public Appointment createAppointment( AppointmentRequest appointmentRequest) {

        Appointment appointment = mapper.toEntity(appointmentRequest);
        LocalDateTime now = LocalDateTime.now();

        appointment.setCreatedAt(now);
        appointment.setLastUpdatedAt(now);
        appointment.setStatus(AppointmentStatus.SCHEDULED);

         var appointmentSave = appointmentRepository.save(appointment);

         var appointmentNotification = mapper.toNotification(appointmentRequest , appointmentSave.getId());

        OutboxEvent outboxEvent = outboxEventRepository.save(new OutboxEvent(
                "Appointment",
                appointmentSave.getId(),
                JsonUtils.toJson(appointmentNotification),
                "APPOINTMENT_CREATED",
                now,
                false
        ));

        return appointmentSave;

    }
}