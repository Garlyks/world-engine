CREATE TABLE world
(
    id     VARCHAR NOT NULL CONSTRAINT world_pkey PRIMARY KEY,
    name   VARCHAR(255),
    width  BIGINT,
    height BIGINT,
    status varchar
);

CREATE TABLE item
(
    id       VARCHAR NOT NULL CONSTRAINT item_pkey PRIMARY KEY,
    world_id varchar NOT NULL,
    name     VARCHAR(255),
    x        BIGINT, --TODO NEED to be indexed
    y        BIGINT, --TODO NEED to be indexed
    status   varchar,
    FOREIGN KEY (world_id) REFERENCES world (id)
);
