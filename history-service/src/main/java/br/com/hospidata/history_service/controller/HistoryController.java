package br.com.hospidata.history_service.controller;

import br.com.hospidata.history_service.entity.MedicalHistory;
import br.com.hospidata.history_service.service.HistoryService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class HistoryController {

    private final HistoryService service;

    public HistoryController(HistoryService service) {
        this.service = service;
    }

    @QueryMapping
    public List<MedicalHistory> medicalHistoryByPatientId(@Argument UUID patientId) {
        return service.getMedicalHistoryByPatientId(patientId);
    }
}