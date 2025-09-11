package br.com.hospidata.appointment_service.mapper;

import br.com.hospidata.appointment_service.controller.dto.AppointmentNotification;
import br.com.hospidata.appointment_service.controller.dto.AppointmentRequest;
import br.com.hospidata.appointment_service.entity.Appointment;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AppointmentMapper {

    public Appointment toEntity(AppointmentRequest appointmentRequest) {
        Appointment appointment = new Appointment();
        appointment.setDoctorId(appointmentRequest.doctorId());
        appointment.setPatientId(appointmentRequest.patientId());
        appointment.setScheduledDate(appointmentRequest.scheduledDate());
        appointment.setFirstScheduledDate(appointmentRequest.scheduledDate());
        appointment.setScheduledDate(appointmentRequest.scheduledDate());
        return appointment;
    }

    public AppointmentNotification toNotification(AppointmentRequest appointmentRequest, UUID id) {
        return new AppointmentNotification(
                id,
                appointmentRequest.scheduledDate(),
                appointmentRequest.patientId(),
                appointmentRequest.patientName(),
                appointmentRequest.patientEmail(),
                appointmentRequest.doctorId(),
                appointmentRequest.doctorName(),
                appointmentRequest.doctorEmail()
        );
    }
}
