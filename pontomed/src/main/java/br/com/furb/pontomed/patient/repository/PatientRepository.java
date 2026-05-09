package br.com.furb.pontomed.patient.repository;

import br.com.furb.pontomed.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByCpf(String cpf);

    boolean existsByCpf(String cpf);
}
