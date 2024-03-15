create table if not exists product_item
(
    id   INT PRIMARY KEY,
    name varchar(8) not null,
    pid  INT        not null
);

create table if not exists business_launch
(
    id              INT auto_increment PRIMARY KEY not null,
    business_detail varchar(8)                     not null,
    target_city     varchar(32),
    target_sex      varchar(8),
    target_product  varchar(32)
);