CREATE TABLE medical_records (
    id BIGSERIAL PRIMARY KEY,
    patient_id  BIGINT NOT NULL REFERENCES patients(id),
    description TEXT NOT NULL,
    diagnosis   VARCHAR(255) NOT NULL,
    record_date TIMESTAMP NOT NULL DEFAULT NOW()
);
