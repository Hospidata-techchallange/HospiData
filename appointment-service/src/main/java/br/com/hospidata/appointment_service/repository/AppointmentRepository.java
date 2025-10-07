package br.com.hospidata.appointment_service.repository;

import br.com.hospidata.appointment_service.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByPatientId(UUID patientId);

    List<Appointment> findByPatientIdAndDoctorId(UUID patientId, UUID doctorId);

    List<Appointment> findByDoctorId(UUID doctorId);
}