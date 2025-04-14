create table medicos (
  id bigint not null auto_increment, 
  crm varchar(255), 
  email varchar(255), 
  logradouro varchar(255), 
  bairro varchar(255), 
  cep varchar(255), 
  cidade varchar(255), 
  uf varchar(255), 
  numero varchar(255), 
  complemento varchar(255), 
  especialidade varchar(255), 
  nome varchar(255), 
  primary key (id)
)
