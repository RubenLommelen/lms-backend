CREATE TABLE COACH
(
    ID           integer      not null primary key,
    EMAIL        varchar(256) not null,
    DISPLAY_NAME varchar(256) not null,
    PASSWORD     varchar(256) not null
);
create sequence coach_seq start with 1 increment by 1;

INSERT INTO COACH
values (nextVal('coach_seq'), 'oscar@mail.com', 'Oscar', 'pwd');
