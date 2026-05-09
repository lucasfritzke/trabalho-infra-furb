package br.com.furb.pontomed.medicalrecord.service;

import br.com.furb.pontomed.medicalrecord.model.MedicalRecord;
import br.com.furb.pontomed.medicalrecord.repository.MedicalRecordRepository;
import br.com.furb.pontomed.patient.model.Patient;
import br.com.furb.pontomed.patient.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceTest {

    @Mock
    private MedicalRecordRepository repository;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private MedicalRecordService service;

    private Patient patient;
    private MedicalRecord record;

    @BeforeEach
    void setUp() {
        patient = new Patient("Ana Costa", "11122233344",
                LocalDate.of(1992, 8, 15), "ana@email.com", "47977777777");
        patient.setId(1L);

        record = new MedicalRecord(patient, "Paciente com dor de cabeça",
                "Enxaqueca", LocalDateTime.now());
        record.setId(1L);
    }

    @Test
    @DisplayName("Deve criar prontuário vinculado a um paciente")
    void deveCriarProntuario() {
        when(patientService.findById(1L)).thenReturn(patient);
        when(repository.save(record)).thenReturn(record);

        MedicalRecord result = service.create(1L, record);

        assertThat(result).isNotNull();
        assertThat(result.getPatient()).isEqualTo(patient);
        verify(repository, times(1)).save(record);
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar prontuário com paciente inexistente")
    void deveLancarExcecaoParaPacienteInexistente() {
        when(patientService.findById(99L))
                .thenThrow(new IllegalArgumentException("Paciente não encontrado com ID: 99"));

        assertThatThrownBy(() -> service.create(99L, record))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("Deve retornar prontuário por ID")
    void deveRetornarProntuarioPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(record));

        MedicalRecord result = service.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getDiagnosis()).isEqualTo("Enxaqueca");
    }

    @Test
    @DisplayName("Deve retornar prontuários por ID de paciente")
    void deveRetornarProntuariosPorPaciente() {
        MedicalRecord outro = new MedicalRecord(patient, "Consulta de rotina",
                "Saudável", LocalDateTime.now().minusDays(10));
        outro.setId(2L);

        when(patientService.findById(1L)).thenReturn(patient);
        when(repository.findByPatientIdOrderByRecordDateDesc(1L))
                .thenReturn(List.of(record, outro));

        List<MedicalRecord> result = service.findByPatientId(1L);

        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("Deve deletar prontuário por ID")
    void deveDeletarProntuario() {
        when(repository.findById(1L)).thenReturn(Optional.of(record));
        doNothing().when(repository).delete(record);

        assertThatCode(() -> service.delete(1L)).doesNotThrowAnyException();
        verify(repository, times(1)).delete(record);
    }
}
