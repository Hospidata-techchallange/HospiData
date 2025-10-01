package br.com.hospidata.appointment_service.controller;

import br.com.hospidata.appointment_service.controller.docs.AppointmentControllerDoc;
import br.com.hospidata.appointment_service.controller.dto.AppointmentRequest;
import br.com.hospidata.appointment_service.controller.dto.AppointmentResponse;
import br.com.hospidata.appointment_service.controller.dto.AppointmentUpdateRequest;
import br.com.hospidata.appointment_service.entity.Appointment;
import br.com.hospidata.appointment_service.mapper.AppointmentMapper;
import br.com.hospidata.appointment_service.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointments")
public class AppointmentController implements AppointmentControllerDoc {

    private final AppointmentService appointmentService;
    private final AppointmentMapper mapper;

    public AppointmentController(AppointmentService appointmentService, AppointmentMapper mapper) {
        this.appointmentService = appointmentService;
        this.mapper = mapper;
    }

    @Override
    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(
            @Valid @RequestBody AppointmentRequest appointmentRequest
    ) {
        Appointment appointmentCreated = appointmentService.createAppointment(mapper.toEntity(appointmentRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(appointmentCreated));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> updateAppointment(
            @Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest,
            @PathVariable UUID id) {
        Appointment appointmentUpdate = appointmentService.updateAppointment(id , appointmentUpdateRequest);
        return ResponseEntity.ok(mapper.toResponse(appointmentUpdate));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments(Pageable pageable) {
        return ResponseEntity.ok(mapper.toResponseList(appointmentService.getAllAppointments(pageable)));
    }
}