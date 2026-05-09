package br.com.furb.pontomed.medicalrecord.repository;

import br.com.furb.pontomed.medicalrecord.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> findByPatientId(Long patientId);

    List<MedicalRecord> findByPatientIdOrderByRecordDateDesc(Long patientId);
}
