create sequence removed_book_id increment by 1;
alter sequence removed_book_id owner to tdd_it;

create table removed_book
(
    id bigint not null constraint pk_removed_book primary key,
    name varchar(255) not null,
    author varchar(255) not null,
    releaseDate DATE not null
);
