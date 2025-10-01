package br.com.hospidata.history_service.controller;

import br.com.hospidata.history_service.controller.docs.HistoryControllerDoc;
import br.com.hospidata.history_service.entity.MedicalHistory;
import br.com.hospidata.history_service.service.HistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController implements HistoryControllerDoc {

    private final HistoryService service;

    public HistoryController(HistoryService service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<MedicalHistory>> getAll() {
        return ResponseEntity.ok(service.getAllMedicalHistory());
    }
}