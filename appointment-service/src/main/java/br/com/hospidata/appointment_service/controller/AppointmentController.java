package br.com.hospidata.appointment_service.controller;

import br.com.hospidata.appointment_service.controller.dto.AppointmentRequest;
import br.com.hospidata.appointment_service.controller.dto.AppointmentResponse;
import br.com.hospidata.appointment_service.controller.dto.AppointmentUpdateRequest;
import br.com.hospidata.appointment_service.entity.Appointment;
import br.com.hospidata.appointment_service.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    private AppointmentResponse toResponse(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(), appointment.getPatientId(), appointment.getDoctorId(),
                appointment.getStatus(), appointment.getScheduledDate(), appointment.getCreatedAt()
        );
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody AppointmentRequest request) {
        Appointment appointment = new Appointment();
        appointment.setPatientId(request.patientId());
        appointment.setDoctorId(request.doctorId());
        appointment.setScheduledDate(request.scheduledDate());

        Appointment createdAppointment = appointmentService.createAppointment(appointment);
        return new ResponseEntity<>(toResponse(createdAppointment), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> findAppointmentById(@PathVariable Long id) {
        return appointmentService.findAppointmentById(id)
                .map(appointment -> ResponseEntity.ok(toResponse(appointment)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> findAllAppointments() {
        List<AppointmentResponse> appointments = appointmentService.findAllAppointments().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable Long id, @Valid @RequestBody AppointmentUpdateRequest request) {
        Appointment appointmentDetails = new Appointment();
        appointmentDetails.setStatus(request.status());
        appointmentDetails.setScheduledDate(request.scheduledDate());

        Appointment updatedAppointment = appointmentService.updateAppointment(id, appointmentDetails);
        return ResponseEntity.ok(toResponse(updatedAppointment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }
}