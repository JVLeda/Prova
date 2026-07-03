create database BancoDeDados;

use BancoDeDados;

create table sensores(
                         id int primary key auto_increment,
                         codigo varchar(100) not null unique,
                         tipo varchar(100) not null,
                         localizacao varchar(100) not null
);

create table medicoes(
                         id int primary key auto_increment,
                         sensor_id int not null,
                         valor double not null,
                         unidade varchar(100) not null,
                         dataHora varchar(100) not null,
                         foreign key(sensor_id) references sensores(id)
);

select * from sensores;
select * from medicoes;


