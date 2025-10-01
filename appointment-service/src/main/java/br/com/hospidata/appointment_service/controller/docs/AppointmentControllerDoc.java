package br.com.hospidata.appointment_service.controller.docs;

import br.com.hospidata.appointment_service.controller.dto.AppointmentRequest;
import br.com.hospidata.appointment_service.controller.dto.AppointmentResponse;
import br.com.hospidata.appointment_service.controller.dto.AppointmentUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Tag(name = "Appointments", description = "Endpoints para gerenciar agendamentos")
public interface AppointmentControllerDoc {

    @Operation(summary = "Cria um novo agendamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    ResponseEntity<AppointmentResponse> createAppointment(@RequestBody AppointmentRequest appointmentRequest);

    @Operation(summary = "Atualiza um agendamento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    ResponseEntity<AppointmentResponse> updateAppointment(@RequestBody AppointmentUpdateRequest appointmentUpdateRequest, @PathVariable UUID id);

    @Operation(summary = "Lista todos os agendamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de agendamentos retornada com sucesso")
    })
    ResponseEntity<List<AppointmentResponse>> getAllAppointments(Pageable pageable);
}