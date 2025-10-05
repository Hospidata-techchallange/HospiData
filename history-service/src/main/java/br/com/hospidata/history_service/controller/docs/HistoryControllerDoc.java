package br.com.hospidata.history_service.controller.docs;


import br.com.hospidata.history_service.controller.dto.MedicalHistoryRequest;
import br.com.hospidata.history_service.entity.MedicalHistory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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
}