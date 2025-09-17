package br.com.hospidata.gateway_service.service;

import br.com.hospidata.gateway_service.client.AppointmentClient;
import br.com.hospidata.gateway_service.controller.dto.AppointmentRequestGateway;
import br.com.hospidata.gateway_service.controller.dto.AppointmentResponse;
import br.com.hospidata.gateway_service.controller.dto.AppointmentUpdateRequestGateway;
import br.com.hospidata.gateway_service.entity.User;
import br.com.hospidata.gateway_service.mapper.gateway.AppointmentMapper;
import br.com.hospidata.gateway_service.repository.UserRepository;
import br.com.hospidata.gateway_service.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppointmentGatewayService {

    private final UserRepository repository;
    private final AppointmentMapper mapper;
    private final AppointmentClient client;

    public AppointmentGatewayService(UserRepository repository, AppointmentMapper mapper , AppointmentClient client) {
        this.repository = repository;
        this.mapper = mapper;
        this.client = client;
    }

    public AppointmentResponse createAppointment(AppointmentRequestGateway appointment) {
        User patient = repository.findById(appointment.patientId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", appointment.patientId().toString()));
        User doctor = repository.findById(appointment.doctorId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", appointment.doctorId().toString()));
        return client.createAppointment(mapper.toRequest(appointment , patient , doctor));
    }

    public AppointmentResponse updateAppointment(UUID id, AppointmentUpdateRequestGateway appointment) {
        User patient = repository.findById(appointment.patientId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", appointment.patientId().toString()));
        User doctor = repository.findById(appointment.doctorId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", appointment.doctorId().toString()));

        return client.updateAppointment(id, mapper.toUpdateRequest(appointment, patient , doctor));
    }

    public List<AppointmentResponse> getAllAppointments() {
        return client.getAllAppointments();
    }
}
