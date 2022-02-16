create table passport (
    id              serial primary key,
    series          varchar(4) not null,
    number          varchar(6) not null,
    expiration_date timestamp  not null
);