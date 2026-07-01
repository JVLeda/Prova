create database BancoDeDados;

use BancoDeDados;

create table sensores(
id int primary key auto_increment,
codigo varchar(100) not null,
tipo varchar(100) not null,
localizacao varchar(100) not null
);
create table medicoes(
id int primary key auto_increment,
sensor varchar(100) not null,
valor varchar(100) not null,
localizacao varchar(100) not null,
dataHora varchar(100) not null
);


