drop database qq;

create database qq character set utf8;

use qq;

create table users (
	uuid char(36) primary key,
	username varchar(20) unique,
	password varchar(20) not null,
	gender varchar(1),
	age int(11)
);

create table friends (
	uuid char(36) primary key,
    state int(11) default 1,
	user1 char(36) not null,
	user2 char(36) not null,
	
	constraint unique(user1, user2),
	constraint foreign key(user1) references users(uuid),
	constraint foreign key(user2) references users(uuid)
);

create table saysay (
	uuid char(36) primary key,
    puuid char(36),
    userid char(36),
    content varchar(140) not null
)