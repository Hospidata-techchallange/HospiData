package br.com.hospidata.gateway_service.service;

import br.com.hospidata.gateway_service.client.HistoryClient;
import br.com.hospidata.gateway_service.controller.dto.HistoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryGatewayService {

    private HistoryClient client;

    public HistoryGatewayService(HistoryClient client) {
        this.client = client;
    }

    public List<HistoryResponse> getAllHistory() {
        System.out.println(client.getAllHistory());
        return client.getAllHistory();
    }


}
