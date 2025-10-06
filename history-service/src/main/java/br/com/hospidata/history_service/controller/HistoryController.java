package br.com.hospidata.history_service.controller;

import br.com.hospidata.history_service.controller.docs.HistoryControllerDoc;
import br.com.hospidata.history_service.controller.dto.MedicalHistoryRequest;
import br.com.hospidata.history_service.entity.MedicalHistory;
import br.com.hospidata.history_service.service.HistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/history")
public class HistoryController implements HistoryControllerDoc {

    private final HistoryService service;

    public HistoryController(HistoryService service) {
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<MedicalHistory> createHistory(@RequestBody MedicalHistoryRequest request) {
        var newHistory = service.createMedicalHistory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newHistory);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<MedicalHistory>> getAll() {
        return ResponseEntity.ok(service.getAllMedicalHistory());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MedicalHistory>> searchHistory(
            @RequestParam(required = false) UUID appointmentId,
            @RequestParam(required = false) UUID patientId,
            @RequestParam(required = false) UUID doctorId
    ) {
        var result = service.searchMedicalHistory(appointmentId, patientId, doctorId);
        return ResponseEntity.ok(result);
    }

}