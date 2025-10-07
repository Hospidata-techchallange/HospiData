package br.com.hospidata.gateway_service.client;

import br.com.hospidata.gateway_service.config.FeignClientConfig;
import br.com.hospidata.gateway_service.controller.dto.HistoryRequest;
import br.com.hospidata.gateway_service.controller.dto.HistoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(
        name = "history-service",
        url = "${clients.history-service.url}",
        configuration = FeignClientConfig.class
)
public interface HistoryClient {

    @GetMapping("/history")
    List<HistoryResponse> getAllHistory();

    @PostMapping("/history")
    HistoryResponse createHistory(@RequestBody HistoryRequest historyRequest);

    @GetMapping("/history/search")
    List<HistoryResponse> getHistorySearch(
            @RequestParam(value = "appointmentId", required = false) UUID appointmentId,
            @RequestParam(value = "doctorId", required = false) UUID doctorId,
            @RequestParam(value = "patientId", required = false) UUID patientId
    );
}