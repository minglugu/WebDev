-- 编写建库和建表的 sql

create database if not exists java102_blog;

use java102_blog;

-- 创建一个播客表, mediumtext for longer text
drop table if exists blog;
create table blog (
    blogId int primary key auto_increment,
    title varchar(1024),
    content mediumtext,
    userId int, -- 文章作者id
    postTime datetime    -- 发布时间
);

-- 用户表
drop table if exists user;
create table user (
    userId int primary key auto_increment,
    username varchar(128) unique, -- 后续会使用用户名进行登录，一般用于登录的用户名是不能重复的
    password varchar(128)
);

insert into user values(null, 'zhangsan', '123');
insert into user values(null, 'lisi', '456');





