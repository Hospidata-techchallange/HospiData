package br.com.hospidata.gateway_service.service;

import br.com.hospidata.gateway_service.client.AppointmentClient;
import br.com.hospidata.gateway_service.client.HistoryClient;
import br.com.hospidata.gateway_service.controller.dto.AppointmentRequestGateway;
import br.com.hospidata.gateway_service.controller.dto.AppointmentResponse;
import br.com.hospidata.gateway_service.controller.dto.AppointmentUpdateRequestGateway;
import br.com.hospidata.gateway_service.controller.dto.HistoryRequest;
import br.com.hospidata.gateway_service.entity.User;
import br.com.hospidata.gateway_service.entity.enums.AppointmentStatus;
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
    private final AppointmentClient appointmentClient;
    private final HistoryClient historyClient;

    public AppointmentGatewayService(UserRepository repository, AppointmentMapper mapper, AppointmentClient appointmentClient, HistoryClient historyClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.appointmentClient = appointmentClient;
        this.historyClient = historyClient;
    }

    public AppointmentResponse createAppointment(AppointmentRequestGateway appointment) {
        User patient = repository.findById(appointment.patientId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", appointment.patientId().toString()));
        User doctor = repository.findById(appointment.doctorId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", appointment.doctorId().toString()));
        return appointmentClient.createAppointment(mapper.toRequest(appointment, patient, doctor));
    }

    public AppointmentResponse updateAppointment(UUID id, AppointmentUpdateRequestGateway appointmentUpdate) {
        User patient = repository.findById(appointmentUpdate.patientId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", appointmentUpdate.patientId().toString()));
        User doctor = repository.findById(appointmentUpdate.doctorId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", appointmentUpdate.doctorId().toString()));

        AppointmentResponse updatedAppointment = appointmentClient.updateAppointment(id, mapper.toUpdateRequest(appointmentUpdate, patient, doctor));

        if (updatedAppointment.status() == AppointmentStatus.COMPLETED || updatedAppointment.status() == AppointmentStatus.CANCELED) {

            String historyDescription = String.format("Consulta %s. Motivo: %s",
                    updatedAppointment.status().toString().toLowerCase(),
                    updatedAppointment.description()
            );

            HistoryRequest historyRequest = new HistoryRequest(
                    id,
                    updatedAppointment.patientId(),
                    updatedAppointment.patientName(),
                    updatedAppointment.patientEmail(),
                    updatedAppointment.doctorId(),
                    updatedAppointment.doctorName(),
                    updatedAppointment.doctorEmail(),
                    historyDescription
            );

            historyClient.createHistory(historyRequest);
        }

        return updatedAppointment;
    }

    public List<AppointmentResponse> getAllAppointments() {
        return appointmentClient.getAllAppointments();
    }

    public List<AppointmentResponse> getAppointmentsSearch(UUID doctorId, UUID patientId) {
        return appointmentClient.getAppointmentsSearch(doctorId, patientId);
    }

    public AppointmentResponse findAppointmentById(UUID id) {
        return appointmentClient.findAppointmentById(id);
    }
}