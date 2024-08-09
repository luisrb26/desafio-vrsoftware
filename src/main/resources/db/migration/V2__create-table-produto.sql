CREATE TABLE produto
(
    id        SERIAL PRIMARY KEY,
    codigo    VARCHAR(255)   NOT NULL,
    descricao VARCHAR(255)   NOT NULL,
    preco     DECIMAL(10, 2) NOT NULL
);