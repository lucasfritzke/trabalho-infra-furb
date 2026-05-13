package br.com.furb.pontomed.medicalrecord.handler;

import br.com.furb.pontomed.medicalrecord.model.MedicalRecord;
import br.com.furb.pontomed.medicalrecord.service.MedicalRecordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordHandler {

    private final MedicalRecordService service;

    public MedicalRecordHandler(MedicalRecordService service) {
        this.service = service;
    }

    @PostMapping("/patient/{patientId}")
    public ResponseEntity<MedicalRecord> create(
            @PathVariable Long patientId,
            @Valid @RequestBody MedicalRecord record) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(patientId, record));
    }

    @GetMapping
    public ResponseEntity<List<MedicalRecord>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecord> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalRecord>> findByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(service.findByPatientId(patientId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecord> update(
            @PathVariable Long id,
            @Valid @RequestBody MedicalRecord record) {
        return ResponseEntity.ok(service.update(id, record));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
