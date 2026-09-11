CREATE TABLE tb_user(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(255)
);

CREATE TABLE tb_book(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    author VARCHAR(255),
    category VARCHAR(255),
    number_of_pages INTEGER,
    cover VARCHAR(255)
);