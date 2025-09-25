package br.com.hospidata.gateway_service.client;

import br.com.hospidata.gateway_service.config.FeignClientConfig;
import br.com.hospidata.gateway_service.controller.dto.HistoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
        name = "history-service",
        url = "${clients.history-service.url}",
        configuration = FeignClientConfig.class
)
public interface HistoryClient {

    @GetMapping("/history")
    List<HistoryResponse> getAllHistory();

}
