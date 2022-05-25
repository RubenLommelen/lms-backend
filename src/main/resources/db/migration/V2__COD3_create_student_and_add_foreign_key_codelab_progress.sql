CREATE TABLE STUDENT (
    ID integer not null primary key,
    EMAIL varchar(256) not null,
    DISPLAY_NAME varchar(256) not null,
    PASSWORD varchar(256) not null
);
create sequence student_seq start with 1 increment by 1;

ALTER TABLE codelab_progress ADD COLUMN FK_STUDENT INTEGER;
ALTER TABLE codelab_progress ADD CONSTRAINT FK_STUDENT_TO_CODELAB_PROGRESS
    FOREIGN KEY (FK_STUDENT) REFERENCES student (ID);