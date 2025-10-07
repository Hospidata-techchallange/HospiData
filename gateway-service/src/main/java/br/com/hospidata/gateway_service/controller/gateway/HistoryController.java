package br.com.hospidata.gateway_service.controller.gateway;

import br.com.hospidata.gateway_service.controller.dto.HistoryResponse;
import br.com.hospidata.gateway_service.entity.enums.Role;
import br.com.hospidata.gateway_service.security.aspect.CheckRole;
import br.com.hospidata.gateway_service.service.HistoryGatewayService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/history")
public class HistoryController {

    private final HistoryGatewayService service;

    public HistoryController(HistoryGatewayService service) {
        this.service = service;
    }

    @GetMapping
    @CheckRole({Role.DOCTOR, Role.NURSE , Role.ADMIN})
    public ResponseEntity<List<HistoryResponse>> getHistory() {
        return ResponseEntity.ok(service.getAllHistory());
    }

    @QueryMapping("allHistories")
    @CheckRole({Role.DOCTOR, Role.NURSE , Role.ADMIN})
    public List<HistoryResponse> getAllHistory() {
        return service.getAllHistory();
    }

    @QueryMapping("historySearch")
    @CheckRole({Role.DOCTOR, Role.NURSE, Role.ADMIN})
    public List<HistoryResponse> getHistorySearch(
            @Argument UUID appointmentId,
            @Argument UUID doctorId,
            @Argument UUID patientId
    ) {
        return service.getHistorySearch(appointmentId, doctorId, patientId);
    }

}