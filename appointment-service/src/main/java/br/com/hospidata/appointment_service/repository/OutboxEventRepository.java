package br.com.hospidata.appointment_service.repository;

import br.com.hospidata.appointment_service.entity.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {

    @Query(value = "SELECT * FROM tb_outbox WHERE processed = false ORDER BY created_at LIMIT :size", nativeQuery = true)
    List<OutboxEvent> findUnprocessed(@Param("size") int size);
}
