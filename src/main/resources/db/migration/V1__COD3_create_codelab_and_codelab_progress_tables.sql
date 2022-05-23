CREATE TABLE CODELAB (
    id integer not null primary key,
    name varchar(256) not null
);
create sequence codelab_seq start with 1 increment by 1;

CREATE TABLE CODELAB_PROGRESS (
    id integer not null primary key,
    fk_codelab integer not null,
    progress varchar(256) not null,
    constraint FK_CODELAB_PROGRESS_TO_CODELAB FOREIGN KEY(fk_codelab) REFERENCES CODELAB (ID)
);
create sequence codelab_progress_seq start with 1 increment by 1;