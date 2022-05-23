INSERT INTO codelab
values (nextVal('codelab_seq'), 'Rinaldo');

INSERT INTO codelab_progress
values (nextVal('codelab_progress_seq'), currval('codelab_seq'), 'DONE');

INSERT INTO codelab
values (nextVal('codelab_seq'), 'Ronaldo');

INSERT INTO codelab_progress
values (nextVal('codelab_progress_seq'), currval('codelab_seq'), 'BUSY');