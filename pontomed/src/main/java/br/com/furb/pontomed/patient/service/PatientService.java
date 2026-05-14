package br.com.furb.pontomed.patient.service;

import br.com.furb.pontomed.patient.model.Patient;
import br.com.furb.pontomed.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public Patient create(Patient patient) {
        if (repository.existsByCpf(patient.getCpf())) {
            throw new IllegalArgumentException("Já existe um paciente com o CPF : " + patient.getCpf());
        }
        return repository.save(patient);
    }

    public List<Patient> findAll() {
        return repository.findAll();
    }

    public Patient findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado com ID: " + id));
    }

    public Patient findByCpf(String cpf) {
        return repository.findByCpf(cpf)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado com CPF: " + cpf));
    }

    public Patient update(Long id, Patient updated) {
        Patient existing = findById(id);
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setBirthDate(updated.getBirthDate());
        return repository.save(existing);
    }

    public void delete(Long id) {
        Patient patient = findById(id);
        repository.delete(patient);
    }
}
