CREATE TABLE cliente
(
    id             SERIAL PRIMARY KEY,
    codigo         VARCHAR(255)   NOT NULL,
    nome           VARCHAR(255)   NOT NULL,
    limite_compra  DECIMAL(10, 2) NOT NULL,
    dia_fechamento INT            NOT NULL CHECK (dia_fechamento >= 1 AND dia_fechamento <= 31)
);