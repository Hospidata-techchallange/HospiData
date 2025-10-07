package br.com.hospidata.gateway_service.client;

import br.com.hospidata.gateway_service.config.FeignClientConfig;
import br.com.hospidata.gateway_service.controller.dto.AppointmentRequest;
import br.com.hospidata.gateway_service.controller.dto.AppointmentResponse;
import br.com.hospidata.gateway_service.controller.dto.AppointmentUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@FeignClient(
        name = "appointment-service",
        url = "${clients.appointment-service.url}",
        configuration = FeignClientConfig.class
)
public interface AppointmentClient {


    @PostMapping("/appointments")
    AppointmentResponse createAppointment(@RequestBody AppointmentRequest request);


    @PutMapping("/appointments/{id}")
    AppointmentResponse updateAppointment(
            @PathVariable("id") UUID id,
            @RequestBody AppointmentUpdateRequest updateRequest);

    @GetMapping("/appointments")
    List<AppointmentResponse> getAllAppointments();

    @GetMapping("/appointments/search")
    List<AppointmentResponse> getAppointmentsSearch(
            @RequestParam(value = "doctorId", required = false) UUID doctorId,
            @RequestParam(value = "patientId", required = false) UUID patientId
    );

    @GetMapping("/appointments/{id}")
    AppointmentResponse findAppointmentById(
            @PathVariable("id") UUID id
    );
}
