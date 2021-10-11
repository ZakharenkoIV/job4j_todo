create table items
(
    id          serial primary key not null,
    description text,
    created     Timestamp not null,
    done        boolean DEFAULT false
);