Alter table codelab
add column creation_date timestamp;

update codelab
set creation_date = current_timestamp;