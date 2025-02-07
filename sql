B1. Tạo tài khoản
create user 'flashcard'@'localhost' identified by 'flashcard';
grant all privileges on * . * to 'flashcard'@'localhost'

B2. tạo db và bảng
create database if not exists `flash_card` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
use flash_card;
drop table if exists user;
create table user(
	id int primary key not null auto_increment,
	username varchar(50) default null,
    pass varchar(50) default null,
    first_name varchar(50) default null,
	last_name varchar(50) default null,
    address varchar(50) default null,
    email varchar(50) default null,
    date_create varchar(50) default null,
    verified int default null
)
