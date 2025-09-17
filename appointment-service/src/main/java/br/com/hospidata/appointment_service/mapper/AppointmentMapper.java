package br.com.hospidata.appointment_service.mapper;

import br.com.hospidata.appointment_service.controller.dto.AppointmentNotification;
import br.com.hospidata.appointment_service.controller.dto.AppointmentRequest;
import br.com.hospidata.appointment_service.controller.dto.AppointmentResponse;
import br.com.hospidata.appointment_service.controller.dto.AppointmentUpdateRequest;
import br.com.hospidata.appointment_service.entity.Appointment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class AppointmentMapper {

    public Appointment toEntity(AppointmentRequest appointmentRequest) {
        Appointment appointment = new Appointment();
        appointment.setDescription(appointmentRequest.description());
        appointment.setDoctorId(appointmentRequest.doctorId());
        appointment.setDoctorName(appointmentRequest.doctorName());
        appointment.setDoctorEmail(appointmentRequest.doctorEmail());
        appointment.setPatientId(appointmentRequest.patientId());
        appointment.setPatientName(appointmentRequest.patientName());
        appointment.setPatientEmail(appointmentRequest.patientEmail());
        appointment.setScheduledDate(appointmentRequest.scheduledDate());
        appointment.setFirstScheduledDate(appointmentRequest.scheduledDate());
        appointment.setScheduledDate(appointmentRequest.scheduledDate());
        return appointment;
    }

    public AppointmentNotification toNotification(Appointment appointment) {
        return new AppointmentNotification(
                appointment.getId(),
                appointment.getDescription(),
                appointment.getScheduledDate(),
                appointment.getStatus(),
                appointment.getPatientId(),
                appointment.getPatientName(),
                appointment.getPatientEmail(),
                appointment.getDoctorId(),
                appointment.getDoctorName(),
                appointment.getDoctorEmail()
        );
    }

    public AppointmentResponse toResponse(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getDescription(),
                appointment.getPatientId(),
                appointment.getPatientName(),
                appointment.getPatientEmail(),
                appointment.getDoctorId(),
                appointment.getDoctorName(),
                appointment.getDoctorEmail(),
                appointment.getStatus(),
                appointment.getFirstScheduledDate(),
                appointment.getScheduledDate(),
                appointment.getCreatedAt(),
                appointment.getLastUpdatedAt()
        );
    }

    public List<AppointmentResponse> toResponseList(List<Appointment> allAppointments) {
        return allAppointments.stream()
                .map(this::toResponse)
                .toList();
    }
}
