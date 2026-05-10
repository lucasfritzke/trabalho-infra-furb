package br.com.furb.pontomed.medicalrecord.service;
import br.com.furb.pontomed.patient.model.Patient;
import br.com.furb.pontomed.patient.service.PatientService;
import br.com.furb.pontomed.medicalrecord.model.MedicalRecord;
import br.com.furb.pontomed.medicalrecord.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository repository;
    private final PatientService patientService;

    public MedicalRecordService(MedicalRecordRepository repository, PatientService patientService) {
        this.repository = repository;
        this.patientService = patientService;
    }

    public MedicalRecord create(Long patientId, MedicalRecord record) {
        Patient patient = patientService.findById(patientId);
        record.setPatient(patient);
        return repository.save(record);
    }

    public List<MedicalRecord> findAll() {
        return repository.findAll();
    }

    public MedicalRecord findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prontuário não encontrado com ID: " + id));
    }

    public List<MedicalRecord> findByPatientId(Long patientId) {
        patientService.findById(patientId);
        return repository.findByPatientIdOrderByRecordDateDesc(patientId);
    }

    public MedicalRecord update(Long id, MedicalRecord updated) {
        MedicalRecord existing = findById(id);
        existing.setDescription(updated.getDescription());
        existing.setDiagnosis(updated.getDiagnosis());
        existing.setRecordDate(updated.getRecordDate());
        return repository.save(existing);
    }

    public void delete(Long id) {
        MedicalRecord record = findById(id);
        repository.delete(record);
    }
}
