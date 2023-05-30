CREATE TABLE Hotel (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL UNIQUE,
                       address VARCHAR(255) NOT NULL,
                       phone_number VARCHAR(255) UNIQUE,
                       floor INTEGER NOT NULL
);
