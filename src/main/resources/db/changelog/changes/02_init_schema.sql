CREATE TABLE room (
                      id SERIAL PRIMARY KEY,
                      status VARCHAR NOT NULL,
                      room_number VARCHAR UNIQUE,
                      floor INTEGER,
                      hotel_id INTEGER,
                      price VARCHAR NOT NULL,
                      is_empty BOOLEAN
);
