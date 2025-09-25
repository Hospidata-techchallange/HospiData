package br.com.hospidata.gateway_service.controller.gateway;

import br.com.hospidata.gateway_service.controller.dto.HistoryResponse;
import br.com.hospidata.gateway_service.service.HistoryGatewayService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    private final HistoryGatewayService service;

    public HistoryController(HistoryGatewayService service) {
        this.service = service;
    }

    @GetMapping
    public List<HistoryResponse> getHistory() {
        return service.getAllHistory();
    }

    @QueryMapping("allHistories")
    public List<HistoryResponse> getAllHistory() {
        return service.getAllHistory();
    }

}
