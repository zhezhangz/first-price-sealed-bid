drop table if exists `users`;

create table `users`
(
    `id`       int          not null auto_increment,
    `uuid`     char(36)     not null,
    `username` varchar(255) not null unique,
    primary key (`id`)
);

create unique index `user_username_uindex` on `users` (`username`);
