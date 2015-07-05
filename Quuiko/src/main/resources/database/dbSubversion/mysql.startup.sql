create table if not exists dbSubversion.changelog(id int not null auto_increment primary key,name varchar(200) not null,registerDate timestamp default now(),version bigint not null)Engine=InnoDB;

create table if not exists dbSubversion.changelogSpecification(id integer not null primary key,releaseName varchar(100) not null,releaseVersion varchar(15) not null)Engine=InnoDB;

create table if not exists dbSubversion.changelogProject(id integer not null auto_increment primary key,projectName varchar(50))Engine=InnoDB;

create table if not exists dbSubversion.changelogRelease(id integer not null auto_increment primary key,releaseName varchar(50),idProject integer not null)Engine=InnoDB;

alter table dbSubversion.changelog add column userName varchar(100) default 'dbSubversionAdmin';

alter table dbSubversion.changelog add idRelease integer;

alter table dbSubversion.changelog add constraint fk_changelogRelease foreign key(idRelease) references dbSubversion.changelogRelease(id);


