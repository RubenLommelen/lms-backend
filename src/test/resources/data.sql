INSERT INTO STUDENT
values (nextVal('student_seq'), 'rinaldo@spaghetto.be', 'Rinaldo', 'Spaghetto');

INSERT INTO codelab
values (nextVal('codelab_seq'), 'Rinaldo' , current_date);

INSERT INTO student_codelab_progress
values (nextVal('codelab_progress_seq'), currval('codelab_seq'), 'DONE', currval('student_seq'), '');

INSERT INTO codelab
values (nextVal('codelab_seq'), 'Ronaldo', current_date);

INSERT INTO student_codelab_progress
values (nextVal('codelab_progress_seq'), currval('codelab_seq'), 'BUSY', currval('student_seq'), '');

INSERT INTO codelab
values (99999998, 'Cristiano', current_date - 2);

INSERT INTO codelab
values (99999999, 'Benzema', current_timestamp + 1 );


