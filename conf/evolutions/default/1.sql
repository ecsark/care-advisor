# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table mfeedback (
  feedback_id               bigint auto_increment not null,
  user_id                   bigint,
  created_time              datetime,
  content                   varchar(255),
  email                     varchar(255),
  contact                   varchar(255),
  resolved                  datetime,
  constraint pk_mfeedback primary key (feedback_id))
;

create table msession (
  session_id                bigint auto_increment not null,
  user_id                   bigint,
  created_time              datetime,
  token                     varchar(255),
  constraint pk_msession primary key (session_id))
;

create table muser (
  user_id                   bigint auto_increment not null,
  username                  varchar(255),
  password                  varchar(255),
  name                      varchar(255),
  contact                   varchar(255),
  constraint pk_muser primary key (user_id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table mfeedback;

drop table msession;

drop table muser;

SET FOREIGN_KEY_CHECKS=1;

