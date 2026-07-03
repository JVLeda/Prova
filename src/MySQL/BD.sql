create database BancoDeDados;

use BancoDeDados;

create table sensores(
id int primary key auto_increment,
tipo varchar(100) not null,
localizacao varchar(100) not null
);
create table medicoes(
id int primary key auto_increment,
);

