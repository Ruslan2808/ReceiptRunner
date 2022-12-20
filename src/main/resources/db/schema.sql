DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS discount_card;

DROP SEQUENCE IF EXISTS product_id_seq;
DROP SEQUENCE IF EXISTS discount_card_id_seq;

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    price DOUBLE PRECISION NOT NULL,
    is_promotional BOOLEAN NOT NULL
);

CREATE TABLE discount_card (
    id SERIAL PRIMARY KEY,
    number INTEGER NOT NULL UNIQUE,
    discount DOUBLE PRECISION NOT NULL
);