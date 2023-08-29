Create database library_app;

use library_app;

create table book (
	isbn VARCHAR(17) PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
	year INT NOT NULL,
	publisher VARCHAR(100) NOT NULL,
	date_added TIMESTAMP NOT NULL DEFAULT NOW()
);

create table author (
	id INT PRIMARY KEY AUTO_INCREMENT,
	firstName VARCHAR(50) NOT NULL,
	lastName VARCHAR(50) NOT NULL
);
create table genre (
	id INT PRIMARY KEY AUTO_INCREMENT,
	`name` VARCHAR(50) NOT NULL
);
create table user(
	id INT PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(50) NOT NULL UNIQUE,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
    display_name VARCHAR(50) NOT NULL,
	`password` VARCHAR(255) NOT NULL,
	date_of_birth DATE NOT NULL,
	`role` INT NOT NULL
    );
create table book_author(
	isbn VARCHAR(17) NOT NULL,
    author_id INT NOT NULL,
    CONSTRAINT pk_book_author
    primary key(isbn,author_id),
    constraint fk_book_author_book
    foreign key (isbn)
    references book(isbn),
    constraint fk_book_author_author
    foreign key (author_id)
    references author(id)
    );
create table book_genre(
	isbn VARCHAR(17) NOT NULL,
    genre_id INT NOT NULL,
    CONSTRAINT pk_book_genre
    primary key(isbn,genre_id),
    constraint fk_book_genre_book
    foreign key (isbn)
    references book(isbn),
    constraint fk_book_genre_genre
    foreign key (genre_id)
    references genre(id)
    );
    