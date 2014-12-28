# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table mfeedback (
  feedback_id               bigint not null,
  user_id                   bigint,
  created_time              timestamp,
  content                   varchar(255),
  email                     varchar(255),
  contact                   varchar(255),
  resolved                  timestamp,
  constraint pk_mfeedback primary key (feedback_id))
;

create table msession (
  session_id                bigint not null,
  user_id                   bigint,
  created_time              timestamp,
  token                     varchar(255),
  constraint pk_msession primary key (session_id))
;

create table muser (
  user_id                   bigint not null,
  username                  varchar(255),
  password                  varchar(255),
  name                      varchar(255),
  contact                   varchar(255),
  constraint pk_muser primary key (user_id))
;

create sequence mfeedback_seq;

create sequence msession_seq;

create sequence muser_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists mfeedback;

drop table if exists msession;

drop table if exists muser;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists mfeedback_seq;

drop sequence if exists msession_seq;

drop sequence if exists muser_seq;

