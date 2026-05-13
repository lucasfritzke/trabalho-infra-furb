# trabalha-infra-furb
Trabalho desenvolvido para disciplina de Infraestrutura de Tecnologia da Informação e Comunicação
# PontoMed - Sistema de Prontuário Médico

Trabalho desenvolvido para a disciplina de **Infraestrutura de Tecnologia da Informação e Comunicação** – FURB.

## Integrantes

- Lucas Fritzke
- Rafael Julio Klug
- Ricardo Klug
-


## Stack

- **Linguagem:** Java 17
- **Framework:** Spring Boot 3.2
- **Banco de dados:** PostgreSQL 16
- **Testes:** JUnit 5 + Mockito
- **Containerização:** Docker + Docker Compose
- **CI/CD:** GitHub Actions

## Estrutura do projeto (Bounded Context)

```
src/main/java/br/furb/pontomed/
├── PontoMedApplication.java
├── patient/
│   ├── Patient.java
│   ├── handler/PatientHandler.java
│   ├── repository/PatientRepository.java
│   └── service/PatientService.java
└── medicalrecord/
    ├── MedicalRecord.java
    ├── handler/MedicalRecordHandler.java
    ├── repository/MedicalRecordRepository.java
    └── service/MedicalRecordService.java
```

## Endpoints

### Pacientes (`/api/patients`)

| Método | Rota                | Descrição               |
|--------|---------------------|-------------------------|
| GET    | /api/patients       | Lista todos os pacientes |
| GET    | /api/patients/{id}  | Busca paciente por ID    |
| POST   | /api/patients       | Cria novo paciente       |
| PUT    | /api/patients/{id}  | Atualiza paciente        |
| DELETE | /api/patients/{id}  | Remove paciente          |

### Prontuários (`/api/medical-records`)

| Método | Rota                                    | Descrição                        |
|--------|-----------------------------------------|----------------------------------|
| GET    | /api/medical-records                    | Lista todos os prontuários       |
| GET    | /api/medical-records/{id}               | Busca prontuário por ID          |
| GET    | /api/medical-records/patient/{patientId}| Lista prontuários de um paciente |
| POST   | /api/medical-records/patient/{patientId}| Cria prontuário para paciente    |
| PUT    | /api/medical-records/{id}               | Atualiza prontuário              |
| DELETE | /api/medical-records/{id}               | Remove prontuário                |

## Como executar

### Com Docker Compose

```bash
docker compose up --build
```

A aplicação ficará disponível em `http://localhost:8080`.

### Localmente

Requisitos: Java 17+, Maven, PostgreSQL rodando na porta 5432.

```bash
mvn spring-boot:run
```

## Variáveis de ambiente

| Variável      | Padrão                              |
|---------------|-------------------------------------|
| `DB_URL`      | `jdbc:postgresql://localhost:5432/pontomed` |
| `DB_USERNAME` | `postgres`                          |
| `DB_PASSWORD` | `postgres`                          |

## Imagem Docker

<!-- Adicionar link após primeiro push na main -->
`docker pull <DOCKERHUB_USERNAME>/pontomed:latest`

## Tarefa 03: Execução Automática de Testes

Teste falhando proposiltamente:
<img width="2524" height="1166" alt="Captura de tela 2026-05-13 152341" src="https://github.com/user-attachments/assets/1e8df4f9-e49f-46ca-aadc-8c22e98bf22e" />

Reversão teste funcionando novamente:
<img width="2524" height="1160" alt="Captura de tela 2026-05-13 152634" src="https://github.com/user-attachments/assets/36b38cb7-17f8-4d8f-beb6-aa3b74149b59" />

