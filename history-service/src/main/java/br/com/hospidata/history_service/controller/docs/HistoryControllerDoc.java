package br.com.hospidata.history_service.controller.docs;


import br.com.hospidata.history_service.controller.dto.MedicalHistoryRequest;
import br.com.hospidata.history_service.entity.MedicalHistory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Tag(name = "History", description = "Endpoints para visualizar e criar o histórico médico")
public interface HistoryControllerDoc {

    @Operation(summary = "Cria um novo registro no histórico médico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    ResponseEntity<MedicalHistory> createHistory(@RequestBody MedicalHistoryRequest request);

    @Operation(summary = "Retorna todo o histórico médico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico retornado com sucesso")
    })
    ResponseEntity<List<MedicalHistory>> getAll();

    ResponseEntity<List<MedicalHistory>> searchHistory(
            @Parameter(description = "ID do agendamento", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
            @RequestParam(required = false) UUID appointmentId,

            @Parameter(description = "ID do paciente", example = "a21bdbde-66ad-4cc9-8889-76f7cf5a93f8")
            @RequestParam(required = false) UUID patientId,

            @Parameter(description = "ID do médico", example = "b58c1d9d-3ab9-49d0-b59f-d34f0f1bfe61")
            @RequestParam(required = false) UUID doctorId
    );
}