# trabalha-infra-furb
Trabalho desenvolvido para disciplina de Infraestrutura de Tecnologia da Informação e Comunicação
# PontoMed - Sistema de Prontuário Médico

Trabalho desenvolvido para a disciplina de **Infraestrutura de Tecnologia da Informação e Comunicação** – FURB.

## Resumo do projeto

O PontoMed é um sistema web de prontuário médico desenvolvido em Java com Spring Boot, com integração ao PostgreSQL e execução automatizada via GitHub Actions. O projeto expõe endpoints para cadastro e consulta de pacientes e prontuários, além de pipeline de CI/CD para build, testes e publicação de imagem Docker.

## Integrantes

-Nickolas Guenther
-Pablo Gabriel Sautner
-Lucas Fritzke
-Rafael Julio Klug
-Ricardo Nilson Klug

## Imagem Docker

<!-- Adicionar link após primeiro push na main -->
`docker pull <DOCKERHUB_USERNAME>/pontomed:latest`


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

| Método | Rota                    | Descrição                    |
|--------|-------------------------|------------------------------|
| POST   | /api/patients           | Cria novo paciente           |
| GET    | /api/patients          | Lista todos os pacientes     |
| GET    | /api/patients/{id}     | Busca paciente por ID        |
| GET    | /api/patients/cpf/{cpf}| Busca paciente por CPF       |
| PUT    | /api/patients/{id}     | Atualiza paciente            |
| DELETE | /api/patients/{id}     | Remove paciente              |

### Prontuários (Medical Records)

Todos os endpoints de prontuário usam o prefixo `/api`.

| Método | Rota                                    | Descrição                        |
|--------|-----------------------------------------|----------------------------------|
| POST   | /api/patients/{patientId}/records       | Cria prontuário para paciente    |
| GET    | /api/records                            | Lista todos os prontuários       |
| GET    | /api/records/{id}                       | Busca prontuário por ID          |
| GET    | /api/patients/{patientId}/records       | Lista prontuários de um paciente |
| PUT    | /api/records/{id}                       | Atualiza prontuário              |
| DELETE | /api/records/{id}                       | Remove prontuário                |

## Payloads para teste

Use estes exemplos no Postman, Insomnia ou `curl` para testar a API.

### Criar paciente

```json
{
    "name": "Maria Silva",
    "cpf": "12345678901",
    "birthDate": "1995-08-20",
    "email": "maria.silva@email.com",
    "phone": "47999998888"
}
```

### Atualizar paciente

```json
{
    "name": "Maria Silva Souza",
    "cpf": "12345678901",
    "birthDate": "1995-08-20",
    "email": "maria.silva@email.com",
    "phone": "47999998888"
}
```

### Criar prontuário

Envie este payload em `POST /api/patients/{patientId}/records`.

```json
{
    "description": "Paciente relatou dor de cabeça e febre há 2 dias.",
    "diagnosis": "Suspeita de infecção viral"
}
```

### Atualizar prontuário

```json
{
    "description": "Paciente relatou melhora dos sintomas após medicação.",
    "diagnosis": "Quadro em regressão",
    "recordDate": "2026-05-13T14:30:00"
}
```

Observação: no `PUT /api/records/{id}`, o campo `recordDate` deve ser enviado porque o serviço atualiza esse valor junto com `description` e `diagnosis`.

### Exemplo com `curl`

```bash
curl -X POST http://localhost:8080/api/patients \
    -H "Content-Type: application/json" \
    -d '{
        "name": "Maria Silva",
        "cpf": "12345678901",
        "birthDate": "1995-08-20",
        "email": "maria.silva@email.com",
        "phone": "47999998888"
    }'
```

```bash
curl -X POST http://localhost:8080/api/patients/1/records \
    -H "Content-Type: application/json" \
    -d '{
        "description": "Paciente relatou dor de cabeça e febre há 2 dias.",
        "diagnosis": "Suspeita de infecção viral"
    }'
```

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

## Como rodar testes

O projeto possui 5 testes unitários atualmente, todos focados no serviço de prontuários.

```bash
./mvnw test
```

No Windows, você também pode usar:

```bat
mvnw.cmd test
```

Se quiser executar apenas a suíte de testes do Maven sem subir a aplicação, esse é o comando recomendado para validar a base antes de abrir PR.

## Variáveis de ambiente

| Variável      | Padrão                              |
|---------------|-------------------------------------|
| `DB_URL`      | `jdbc:postgresql://db:5432/pontomed` (Docker) / `jdbc:postgresql://localhost:5432/pontomed` (local)
| `DB_USERNAME` | `postgres`                          |
| `DB_PASSWORD` | `postgres`                          |
| `DB_NAME`     | `pontomed`                          |

## Arquivo .env

O projeto usa um arquivo `.env` na raiz para fornecer as credenciais ao Docker Compose e à aplicação. Siga estes passos antes de executar o projeto com Docker Compose:

- Copie o arquivo de exemplo para criar o `.env` localmente:

```bash
cp .env.example .env        # Unix / macOS
Copy-Item .env.example .env # PowerShell
copy .env.example .env      # CMD
```

- Abra o arquivo `.env` e preencha as variáveis com as credenciais do banco. Para o ambiente Docker usado pelo professor, insira exatamente:

```
DB_URL=jdbc:postgresql://db:5432/pontomed
DB_USERNAME=postgres
DB_PASSWORD=postgres
DB_NAME=pontomed
```

- Certifique-se de que o arquivo `.env` esteja na raiz do repositório (mesma pasta de `docker-compose.yml`).

- O repositório contém um arquivo `.env.example` com as chaves vazias para referência:

```
DB_URL=
DB_USERNAME=
DB_PASSWORD=
DB_NAME=
```

## Imagem Docker

<!-- Adicionar link após primeiro push na main -->
`docker pull <DOCKERHUB_USERNAME>/pontomed:latest`

## Guia de contribuição e estilo

- Crie uma branch a partir de `develop` para novas alterações.
- Abra Pull Request para `main` somente quando `mvn test` estiver passando.
- Mantenha os nomes de classes, métodos e endpoints consistentes com o padrão já usado no projeto.
- Prefira mudanças pequenas e objetivas, especialmente em handlers, services e repositories.
- Antes de enviar, valide localmente com `./mvnw test` ou `mvnw.cmd test` no Windows.

## Licença e contato

Este repositório não possui licença explícita cadastrada no momento. Caso o grupo adote uma licença depois, esta seção deve ser atualizada.

Contato do projeto: integrantes listados no início deste README.

## Respostas para o relatório CI/CD

1. O que acontece se um teste falhar propositalmente?

    Se um teste falhar, o comando `mvn test` retorna erro e o job de testes falha. No workflow atual, isso interrompe a validação do pipeline e impede que o PR siga para merge ou que o job de CD seja executado.

2. Em que cenário real a publicação de artefatos seria útil?

    A publicação de artefatos é útil para disponibilizar o `.jar` gerado pelo build para download, auditoria, validação manual, distribuição para homologação ou reutilização em ambientes diferentes sem recompilar.

3. Por que nunca devemos commitar credenciais no código?

    Porque credenciais expostas podem dar acesso indevido ao banco, serviços externos e recursos pagos. O correto é usar secrets do GitHub Actions e variáveis de ambiente, como o `.env` do projeto.

Teste falhando propositalmente:
<img width="2524" height="1166" alt="Captura de tela 2026-05-13 152341" src="https://github.com/user-attachments/assets/1e8df4f9-e49f-46ca-aadc-8c22e98bf22e" />

Reversão teste funcionando novamente:
<img width="2524" height="1160" alt="Captura de tela 2026-05-13 152634" src="https://github.com/user-attachments/assets/36b38cb7-17f8-4d8f-beb6-aa3b74149b59" />

4. Qual versão apresentou alguma diferença de comportamento, se houver?

    O workflow testa Java 17 e Java 21. No estado atual do projeto, não há diferença funcional registrada entre as duas versões para os testes existentes, mas a matriz permite detectar incompatibilidades caso elas apareçam no futuro.

5. Por que paralelismo importa em pipelines de CI?

    Porque reduz o tempo total de feedback. No projeto, build e testes rodam em jobs separados e podem ser executados em paralelo, acelerando a validação do código.

6. Qual a diferença entre uma tag latest e uma tag por SHA?

    A tag `latest` aponta para a versão mais recente publicada, enquanto a tag por SHA identifica exatamente um commit específico. A tag por SHA é mais rastreável e reproduzível.

7. Quando usar uma tag latest e quando usar uma tag por SHA?

    Use `latest` para facilitar testes rápidos e consumo da versão mais atual. Use a tag por SHA quando precisar de rastreabilidade, rollback, auditoria ou garantia de que a imagem é exatamente aquela gerada por um commit.

8. O que foi desenvolvido no projeto e qual stack foi escolhida?

    Foi desenvolvido o PontoMed, um sistema de prontuário médico com endpoints para pacientes e prontuários. A stack escolhida foi Java 17, Spring Boot 3.2, PostgreSQL 16, Maven, Docker e GitHub Actions.

9. Quais são os principais arquivos e a estrutura do repositório?

    Os principais arquivos são `Dockerfile`, `docker-compose.yml`, `pom.xml`, `README.md`, `src/main/java/.../patient`, `src/main/java/.../medicalrecord` e os workflows em `.github/workflows/ci.yml` e `.github/workflows/pr.yml`.

10. Como funciona o workflow configurado no GitHub Actions?

    O workflow `ci.yml` é disparado em push em qualquer branch, executa build e testes em matriz de Java 17/21, publica os artefatos do build e, na branch `main`, publica a imagem Docker quando build e testes passam. O workflow `pr.yml` roda em pull requests para `main` e serve como verificação obrigatória antes do merge.

11. O que cada job e step do arquivo YAML faz?

    No `ci.yml`, o job `build` baixa o repositório, configura Java na matriz 17/21, executa `mvn clean package -DskipTests` e publica o `.jar`. O job `test` faz checkout, configura Java 17/21, executa `mvn test` e salva os relatórios do Surefire. O job `docker` depende de `build` e `test`, roda apenas na `main`, faz login no Docker Hub e publica a imagem com as tags `latest` e `${{ github.sha }}`. No `pr.yml`, os jobs `build` e `test` rodam em PRs para `main` e o job `quality-gate` sinaliza que o PR pode ser aprovado quando tudo passar.

12. Quais foram os aprendizados e dificuldades encontradas no projeto?

    Os principais aprendizados foram estruturar uma aplicação Java com banco de dados, organizar endpoints e alinhar CI/CD com o código. As principais dificuldades costumam estar em configurar o workflow, secrets, banco local, matriz de versões e manter a documentação sincronizada com a implementação.
