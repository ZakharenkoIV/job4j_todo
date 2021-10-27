create table items
(
    id          serial primary key not null,
    description text unique        not null,
    created     Timestamp unique   not null,
    done        boolean DEFAULT false,
    user_id     integer            not null references users (id)
);

create table roles
(
    id   serial primary key   not null,
    name varchar(2000) unique not null
);

create table users
(
    id       serial primary key  not null,
    name     varchar(2000)       not null,
    role_id  integer             not null references roles (id),
    email    varchar(255) unique not null,
    password varchar(255)        not null
);
