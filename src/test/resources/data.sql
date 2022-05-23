INSERT INTO STUDENT
values (nextVal('student_seq'), 'rinaldo@spaghetto.be', 'Rinaldo', 'Spaghetto');

INSERT INTO codelab
values (nextVal('codelab_seq'), 'Rinaldo');

INSERT INTO codelab_progress
values (nextVal('codelab_progress_seq'), currval('codelab_seq'), 'DONE', currval('student_seq'));

INSERT INTO codelab
values (nextVal('codelab_seq'), 'Ronaldo');

INSERT INTO codelab_progress
values (nextVal('codelab_progress_seq'), currval('codelab_seq'), 'BUSY', currval('student_seq'));

INSERT INTO STUDENT
values (nextVal('student_seq'), 'student@mail.com', 'student', 'pwd');