package br.com.furb.pontomed.patient.service;

import br.com.furb.pontomed.patient.model.Patient;
import br.com.furb.pontomed.patient.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository repository;

    @InjectMocks
    private PatientService service;

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient("Rafael Klug", "12345678900",
                LocalDate.of(1995, 3, 10), "rafaeljulioklug@gmail.com", "47999999999");
        patient.setId(1L);
    }

    @Test
    @DisplayName("Deve criar paciente com sucesso quando CPF não existe")
    void deveCriarPaciente() {
        when(repository.existsByCpf("12345678900")).thenReturn(false);
        when(repository.save(patient)).thenReturn(patient);

        Patient result = service.create(patient);

        assertThat(result).isNull();
        assertThat(result.getName()).isEqualTo("Rafael Klug");
        verify(repository, times(1)).save(patient);
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar paciente com CPF duplicado")
    void deveLancarExcecaoParaCpfDuplicado() {
        when(repository.existsByCpf("12345678900")).thenReturn(true);

        assertThatThrownBy(() -> service.create(patient))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("12345678900");

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve retornar lista de todos os pacientes")
    void deveRetornarTodosOsPacientes() {
        Patient outro = new Patient("Ricardo", "99988877766",
                LocalDate.of(1988, 6, 20), "rick@gmail.com", "47988880000");
        outro.setId(2L);

        when(repository.findAll()).thenReturn(List.of(patient, outro));

        List<Patient> result = service.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Rafael Klug");
    }

    @Test
    @DisplayName("Deve retornar paciente por ID com sucesso")
    void deveRetornarPacientePorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(patient));

        Patient result = service.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getCpf()).isEqualTo("12345678900");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar paciente por ID inexistente")
    void deveLancarExcecaoParaIdInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("Deve retornar paciente por CPF com sucesso")
    void deveRetornarPacientePorCpf() {
        when(repository.findByCpf("12345678900")).thenReturn(Optional.of(patient));

        Patient result = service.findByCpf("12345678900");

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Rafael Klug");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar paciente por CPF inexistente")
    void deveLancarExcecaoParaCpfInexistente() {
        when(repository.findByCpf("00000000000")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findByCpf("00000000000"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("00000000000");
    }

    @Test
    @DisplayName("Deve atualizar dados do paciente com sucesso")
    void deveAtualizarPaciente() {
        Patient dadosAtualizados = new Patient("Rafael Julio Klug", "12345678900",
                LocalDate.of(1995, 3, 10), "rafa.klug@gmail.com", "47911110000");

        when(repository.findById(1L)).thenReturn(Optional.of(patient));
        when(repository.save(patient)).thenReturn(patient);

        Patient result = service.update(1L, dadosAtualizados);

        assertThat(result.getName()).isEqualTo("Rafael Julio Klug");
        assertThat(result.getEmail()).isEqualTo("rafa.klug@gmail.com");
        verify(repository, times(1)).save(patient);
    }

    @Test
    @DisplayName("Deve deletar paciente por ID com sucesso")
    void deveDeletarPaciente() {
        when(repository.findById(1L)).thenReturn(Optional.of(patient));
        doNothing().when(repository).delete(patient);

        assertThatCode(() -> service.delete(1L)).doesNotThrowAnyException();
        verify(repository, times(1)).delete(patient);
    }
}