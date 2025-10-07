package br.com.hospidata.appointment_service.controller.docs;

import br.com.hospidata.appointment_service.controller.dto.AppointmentRequest;
import br.com.hospidata.appointment_service.controller.dto.AppointmentResponse;
import br.com.hospidata.appointment_service.controller.dto.AppointmentUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Operation(summary = "Busca um agendamento pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    ResponseEntity<AppointmentResponse> getAppointmentById(
            @Parameter(description = "ID do agendamento", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
            @PathVariable UUID id
    );

    @Operation(
            summary = "Busca agendamentos filtrando por paciente ou médico",
            description = """
                Retorna uma lista de agendamentos de acordo com os filtros informados.
                Todos os parâmetros são opcionais e podem ser combinados.
                """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum agendamento encontrado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    ResponseEntity<List<AppointmentResponse>> searchAppointments(
            @Parameter(description = "ID do paciente", example = "a21bdbde-66ad-4cc9-8889-76f7cf5a93f8")
            @RequestParam(required = false) UUID patientId,

            @Parameter(description = "ID do médico", example = "b58c1d9d-3ab9-49d0-b59f-d34f0f1bfe61")
            @RequestParam(required = false) UUID doctorId
    );

}