drop database if exists api_empresa;
create database api_empresa;
use api_empresa;

create table empresa(
	id_empresa int not null auto_increment,
	razao_social varchar(25),
	cnpj varchar(25),
	primary key(id_empresa)
);

create table funcionario(
	id_funcionario int not null auto_increment,
	nome varchar(40),
	email varchar(40),
	senha varchar(100),
	cpf varchar(15),
	primary key(id_funcionario)
);

create table lancamento(
	id_lancamento int not null auto_increment,
	dia Date,
	descricao varchar(50),
	localizacao varchar(50),
	primary key(id_lancamento)
);


SELECT * FROM  empresa;