DECLARE
    contador number;
BEGIN
	select COUNT(*) into contador FROM DBA_TAB_COLS where UPPER(TABLE_NAME)=UPPER('changelog');
    if(contador=0) then
        execute immediate ' create table dbsubversion.changelog(id int not null primary key,name varchar(200) not null,registerDate timestamp default sysdate,version int not null,username varchar(100))';
    end if;

    select COUNT(*) into contador FROM DBA_TAB_COLS where UPPER(TABLE_NAME)=UPPER('changelogSpecification');
    if(contador=0) then
        execute immediate ' create table dbSubversion.changelogSpecification(id integer not null primary key,releaseName varchar(100) not null,releaseVersion varchar2(15) not null)';
    end if;
    
    select count(*) into contador from DBA_SEQUENCES where upper(SEQUENCE_NAME) like UPPER('changelogSpec_seq');
    if(contador=0) then
        execute immediate ' create sequence dbSubversion.changelogSpec_seq start with 1 increment by 1 nomaxvalue';
    end if;

    select COUNT(*) into contador FROM DBA_TAB_COLS where UPPER(TABLE_NAME)=UPPER('changelogProject');
    if(contador=0) then
        execute immediate ' create table dbSubversion.changelogProject(id integer not null primary key,projectName varchar(50))';
    end if;

    select count(*) into contador from DBA_SEQUENCES where upper(SEQUENCE_NAME) like UPPER('changelogProject_seq');
    if(contador=0) then
        execute immediate ' create sequence dbSubversion.changelogProject_seq start with 1 increment by 1 nomaxvalue';
    end if;

    select COUNT(*) into contador FROM DBA_TAB_COLS where UPPER(TABLE_NAME)=UPPER('changelogRelease');
    if(contador=0) then
        execute immediate ' create table dbSubversion.changelogRelease(id integer not null primary key,releaseName varchar(50),idProject integer not null)';
    end if;
    
    select count(*) into contador from DBA_SEQUENCES where upper(SEQUENCE_NAME) like UPPER('changelogRelease_seq');
    if(contador=0) then
        execute immediate ' create sequence dbSubversion.changelogRelease_seq start with 1 increment by 1 nomaxvalue';
    end if;

    select COUNT(*) into contador FROM DBA_TAB_COLS where UPPER(TABLE_NAME)=UPPER('changelog') and UPPER(column_name)=UPPER('idRelease');
    if(contador=0) then
        execute immediate ' alter table dbSubversion.changelog add idRelease integer';
    end if;
	
	select count(*) into contador from DBA_CONSTRAINTS where TRIM(UPPER(TABLE_NAME))=UPPER('toPromotoria') and TRIM(UPPER(CONSTRAINT_NAME))=UPPER('TOPROMOTORIA_TOAGENTE_FK');
	if(contador=0) then
		execute immediate ' alter table dbSubversion.changelog add constraint fk_changelogRelease foreign key(idRelease) references dbSubversion.changelogRelease(id)';
	end if;
END;
/
