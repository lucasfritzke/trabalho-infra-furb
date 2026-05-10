package br.com.furb.pontomed.medicalrecord.model;


import br.com.furb.pontomed.patient.model.Patient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "medical_records")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Paciente é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "Diagnóstico é obrigatório")
    @Column(nullable = false)
    private String diagnosis;

    @Column(nullable = false)
    private LocalDateTime recordDate;

    public MedicalRecord() {}

    public MedicalRecord(Patient patient, String description, String diagnosis, LocalDateTime recordDate) {
        this.patient = patient;
        this.description = description;
        this.diagnosis = diagnosis;
        this.recordDate = recordDate;
    }

    @PrePersist
    public void prePersist() {
        if (this.recordDate == null) {
            this.recordDate = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public LocalDateTime getRecordDate() { return recordDate; }
    public void setRecordDate(LocalDateTime recordDate) { this.recordDate = recordDate; }
}
