package br.com.hospidata.gateway_service.mapper.gateway;

import br.com.hospidata.gateway_service.controller.dto.AppointmentRequest;
import br.com.hospidata.gateway_service.controller.dto.AppointmentRequestGateway;
import br.com.hospidata.gateway_service.controller.dto.AppointmentUpdateRequest;
import br.com.hospidata.gateway_service.controller.dto.AppointmentUpdateRequestGateway;
import br.com.hospidata.gateway_service.entity.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AppointmentMapper {
    public AppointmentRequest toRequest(AppointmentRequestGateway appointment, User patient, User doctor) {
        return new AppointmentRequest(
            appointment.scheduledDate(),
            appointment.patientId(),
            appointment.description(),
            patient.getName(),
            patient.getEmail(),
            appointment.doctorId(),
            doctor.getName(),
            doctor.getEmail()
        );
    }

    public AppointmentUpdateRequest toUpdateRequest(AppointmentUpdateRequestGateway appointment, User patient, User doctor) {
        return new AppointmentUpdateRequest(
            appointment.status(),
            appointment.scheduledDate(),
            appointment.doctorId(),
            appointment.description(),
            doctor.getName(),
            doctor.getEmail(),
            appointment.patientId(),
            patient.getName(),
            patient.getEmail()
        );
    }
}
