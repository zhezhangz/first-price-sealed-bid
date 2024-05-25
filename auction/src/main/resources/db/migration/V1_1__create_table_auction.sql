drop table if exists `auction`;

create table `auction`
(
    `id`        char(36)     not null,
    `product`   varchar(255) not null,
    `min_price` bigint       not null,
    `seller`    char(36)     not null,
    `status`    varchar(16)  not null,
    primary key (`id`)
);
