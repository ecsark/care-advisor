# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table login (
  login_id                  varchar(255) not null,
  user_id                   varchar(255),
  login_date                date,
  token                     varchar(255),
  constraint pk_login primary key (login_id))
;

create table user (
  id                        varchar(255) not null,
  password                  varchar(255),
  name                      varchar(255),
  role                      varchar(255),
  constraint pk_user primary key (id))
;

create sequence login_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists login;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists login_seq;

drop sequence if exists user_seq;

