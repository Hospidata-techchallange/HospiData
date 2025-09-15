package br.com.hospidata.gateway_service.controller.gateway;

import br.com.hospidata.gateway_service.controller.dto.AppointmentRequest;
import br.com.hospidata.gateway_service.controller.dto.AppointmentRequestGateway;
import br.com.hospidata.gateway_service.controller.dto.AppointmentResponse;
import br.com.hospidata.gateway_service.controller.dto.AppointmentUpdateRequestGateway;
import br.com.hospidata.gateway_service.service.AppointmentGatewayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentGatewayService service;

    public AppointmentController(AppointmentGatewayService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody AppointmentRequestGateway appointmentRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createAppointment(appointmentRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> appointment(
            @PathVariable("id") UUID id,
            @RequestBody AppointmentUpdateRequestGateway appointmentRequest
    ) {
        return ResponseEntity.ok(service.updateAppointment(id, appointmentRequest));
    }

}
