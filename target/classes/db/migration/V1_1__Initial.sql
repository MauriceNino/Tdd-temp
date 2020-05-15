create sequence book_id increment by 1;
alter sequence book_id owner to tdd_it;

create table book
(
    id bigint not null constraint pk_book primary key,
    name varchar(255) not null,
    author varchar(255) not null,
    releaseDate DATE not null
);
