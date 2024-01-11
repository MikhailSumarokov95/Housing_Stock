DROP TABLE IF EXISTS
users,
house
CASCADE;

CREATE TABLE IF NOT EXISTS users(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(64),
    age         INTEGER,
    password    VARCHAR(64),
    house_id    BIGSERIAL
);

CREATE TABLE IF NOT EXISTS house(
    id          BIGSERIAL PRIMARY KEY,
    address     VARCHAR(64),
    age         INTEGER,
    owner_id    BIGSERIAL
);

