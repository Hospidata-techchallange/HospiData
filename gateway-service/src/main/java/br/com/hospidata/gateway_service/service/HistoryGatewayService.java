package br.com.hospidata.gateway_service.service;

import br.com.hospidata.gateway_service.client.HistoryClient;
import br.com.hospidata.gateway_service.controller.dto.HistoryResponse;
import br.com.hospidata.gateway_service.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HistoryGatewayService {

    private HistoryClient client;

    public HistoryGatewayService(HistoryClient client) {
        this.client = client;
    }

    public List<HistoryResponse> getAllHistory() {
        return client.getAllHistory();
    }

    public List<HistoryResponse> getHistorySearch(UUID appointmentId, UUID doctorId, UUID patientId) {
        var histories = client.getHistorySearch(
                appointmentId,
                doctorId,
                patientId
        );
        return histories;
    }
}
