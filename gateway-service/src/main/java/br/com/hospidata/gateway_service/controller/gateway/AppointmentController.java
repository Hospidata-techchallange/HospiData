package br.com.hospidata.gateway_service.controller.gateway;

import br.com.hospidata.gateway_service.controller.dto.AppointmentRequest;
import br.com.hospidata.gateway_service.controller.dto.AppointmentRequestGateway;
import br.com.hospidata.gateway_service.controller.dto.AppointmentResponse;
import br.com.hospidata.gateway_service.controller.dto.AppointmentUpdateRequestGateway;
import br.com.hospidata.gateway_service.entity.enums.Role;
import br.com.hospidata.gateway_service.security.aspect.CheckRole;
import br.com.hospidata.gateway_service.service.AppointmentGatewayService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentGatewayService service;

    public AppointmentController(AppointmentGatewayService service) {
        this.service = service;
    }

    @CheckRole({Role.ADMIN, Role.DOCTOR , Role.NURSE})
    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody AppointmentRequestGateway appointmentRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createAppointment(appointmentRequest));
    }

    @CheckRole({Role.ADMIN, Role.DOCTOR})
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> appointment(
            @PathVariable("id") UUID id,
            @RequestBody AppointmentUpdateRequestGateway appointmentRequest
    ) {
        return ResponseEntity.ok(service.updateAppointment(id, appointmentRequest));
    }

    @CheckRole({Role.ADMIN, Role.DOCTOR , Role.NURSE})
    @QueryMapping("appointments")
    public List<AppointmentResponse> getAllAppointmentsGraphQL() {
        return service.getAllAppointments();
    }

    @CheckRole({Role.ADMIN, Role.DOCTOR , Role.NURSE})
    @GetMapping
    public  ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
        return ResponseEntity.ok(service.getAllAppointments());
    }

}
