create table certificates
(
    id               bigint unsigned auto_increment
        primary key,
    name             varchar(80)                         not null,
    description      varchar(200)                        not null,
    price            decimal(10, 2)                      null,
    create_date      timestamp default CURRENT_TIMESTAMP not null,
    last_update_date timestamp default CURRENT_TIMESTAMP not null,
    duration         int                                 not null
);

create table tags
(
    id   bigint unsigned auto_increment
        primary key,
    name varchar(60) not null
);

create table certificates_tags
(
    certificate_id bigint unsigned not null,
    tag_id         bigint unsigned not null,
        foreign key (certificate_id) references certificates (id)
            on update cascade on delete cascade,
        foreign key (tag_id) references tags (id)
            on update cascade on delete cascade
);

create table users
(
    id          bigint auto_increment
        primary key,
    name        varchar(64)       not null,
    spent_money decimal default 0 not null
);

create table orders
(
    id                     bigint unsigned auto_increment
        primary key,
    order_date             timestamp default CURRENT_TIMESTAMP not null,
    orders_certificates_id bigint unsigned                     not null,
    cost                   decimal                             not null,
    orders_users_id        bigint                              not null,
        foreign key (orders_certificates_id) references certificates (id)
            on update cascade on delete cascade,
        foreign key (orders_users_id) references users (id)
            on update cascade
);

