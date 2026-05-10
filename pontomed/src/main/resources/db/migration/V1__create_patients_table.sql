CREATE TABLE patients (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(11)  NOT NULL UNIQUE,
    birth_date DATE NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(20)
);