package br.com.hospidata.appointment_service.service;

import br.com.hospidata.appointment_service.entity.OutboxEvent;
import br.com.hospidata.appointment_service.repository.OutboxEventRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OutboxProcessorService {

    private final OutboxEventRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Logger log = LoggerFactory.getLogger(OutboxProcessorService.class);

    @Value("${appointments.batch-size}")
    private int MAX_APPOINTMENTS_TO_PROCESS;

    public OutboxProcessorService(OutboxEventRepository repository, KafkaTemplate<String, String> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    //@Scheduled(fixedDelay = 5000)
    @Transactional
    public void processOutboxEvents() {
        List<OutboxEvent> events = repository.findUnprocessed(MAX_APPOINTMENTS_TO_PROCESS);

        for (OutboxEvent event : events) {
            try {
                log.info("Processing outbox event {}", event.getPayload());
                kafkaTemplate.send("appointment-events", event.getAggregateId().toString(), event.getPayload());
                event.setProcessed(true);
                event.setProcessedAt(LocalDateTime.now());
                repository.save(event);
            } catch (Exception e) {
                // Trabalhar nas exception depois.
            }
        }
    }

}
