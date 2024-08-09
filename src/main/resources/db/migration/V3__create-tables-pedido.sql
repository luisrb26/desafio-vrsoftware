CREATE TABLE pedido
(
    id            SERIAL PRIMARY KEY,
    cliente       INT          NOT NULL,
    data_pedido   DATE         NOT NULL,
    situacao      VARCHAR(10)  NOT NULL,
    FOREIGN KEY (cliente) REFERENCES cliente (id)
);

CREATE TABLE item_pedido
(
    id             SERIAL PRIMARY KEY,
    pedido         INT            NOT NULL,
    produto        INT            NOT NULL,
    quantidade     INT            NOT NULL CHECK (quantidade > 0),
    preco_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (pedido) REFERENCES pedido (id),
    FOREIGN KEY (produto) REFERENCES produto (id),
    CONSTRAINT unique_item UNIQUE (pedido, produto)
);