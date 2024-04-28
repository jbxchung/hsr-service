DROP DATABASE IF EXISTS hsr;
CREATE DATABASE hsr;
USE hsr;

create table characters (
    id varchar(255) not null,
    description varchar(1023),
    name varchar(255),
    path enum ('ABUNDANCE','DESTRUCTION','ERUDITION','HARMONY','NIHILITY','PRESERVATION','THE_HUNT'),
    rarity integer,
    thumbnail_file_path varchar(255),
    element enum ('FIRE','ICE','IMAGINARY','LIGHTNING','PHYSICAL','QUANTUM','WIND'),
    primary key (id)
);

create table light_cones (
    id varchar(255) not null,
    description varchar(1023),
    name varchar(255),
    path enum ('ABUNDANCE','DESTRUCTION','ERUDITION','HARMONY','NIHILITY','PRESERVATION','THE_HUNT'),
    rarity integer,
    thumbnail_file_path varchar(255),
    primary key (id)
);

create table users (
    user_id bigint not null auto_increment,
    account_name varchar(255) unique,
    created date,
    email_address varchar(255) unique,
    password_enc varchar(255),
    user_role enum ('ADMIN','USER'),
    primary key (user_id)
);

create table friendships (
    receiver bigint not null,
    sender bigint not null,
    status tinyint,
    primary key (receiver, sender)
);

create table gacha_pulls (
    id bigint not null auto_increment,
    gacha_entity_type varchar(255),
    timestamp datetime(6),
    gacha_entity_id varchar(255) not null,
    user_account_name varchar(255),
    foreign key (user_account_name) references users(account_name),
    primary key (id)
);

create table items (
    id varchar(255) not null,
    name varchar(255),
    primary key (id)
);

create table user_items (
    quantity bigint,
    user_account_name varchar(255),
    item_id varchar(255) not null,
    foreign key (user_account_name) references users(account_name),
    foreign key (item_id) references items(id),
    primary key (item_id, user_account_name)
);
