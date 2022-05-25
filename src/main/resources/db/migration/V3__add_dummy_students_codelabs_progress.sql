insert into student
values (nextVal('student_seq'), 'kamiel@mail.com', 'Kamiel', 'pwd');

INSERT INTO codelab
values (nextVal('codelab_seq'), 'Variables');

INSERT INTO codelab_progress
values (nextVal('codelab_progress_seq'), currval('codelab_seq'), 'DONE', currval('student_seq'));

INSERT INTO codelab
values (nextVal('codelab_seq'), 'Streams');

INSERT INTO codelab_progress
values (nextVal('codelab_progress_seq'), currval('codelab_seq'), 'BUSY', currval('student_seq'));

INSERT INTO codelab
values (nextVal('codelab_seq'), 'Generics');

INSERT INTO STUDENT
values (nextVal('student_seq'), 'student@mail.com', 'student', 'pwd');

INSERT INTO codelab_progress
values (nextVal('codelab_progress_seq'), currval('codelab_seq'), 'BUSY', currval('student_seq'));
