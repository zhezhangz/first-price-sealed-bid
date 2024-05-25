drop table if exists `bid`;

create table `bid`
(
    `id`         char(36)    not null,
    `auction_id` varchar(36) not null,
    `price`      bigint      not null,
    `buyer`      char(36)    not null,
    `bid_at`     varchar(64) not null,
    primary key (`id`),
    foreign key (`auction_id`) references `auction` (`id`)
);
