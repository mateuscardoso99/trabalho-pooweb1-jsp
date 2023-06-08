create table usuario(
	id serial primary key not null,
	nome varchar(255) not null,
	email varchar(255) unique not null,
	senha varchar(255) not null
);

create table contato(
	id serial primary key not null,
	nome varchar(255) not null,
	telefone varchar(20) not null,
	foto varchar(255),
	id_usuario int not null,
	foreign key(id_usuario) references usuario(id)
);

create table link(
	id serial primary key not null,
	url varchar(255) not null,
	descricao text,
	id_usuario int not null,
	foreign key(id_usuario) references usuario(id)
);

create table documento(
	id serial primary key not null,
	arquivo varchar(255) not null,
	id_usuario int not null,
	tipo_arquivo varchar(50) not null,
	foreign key(id_usuario) references usuario(id)
);