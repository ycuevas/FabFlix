CREATE TABLE movies
	(id integer NOT NULL AUTO_INCREMENT,
	 title varchar(100) NOT NULL,
     year integer NOT NULL,
     director varchar(100) NOT NULL,
     banner_url varchar(200),
     trailer_url varchar(200),
     primary key(id));

CREATE TABLE stars
	(id integer NOT NULL AUTO_INCREMENT,
	 first_name varchar(50) NOT NULL,
     last_name varchar(50) NOT NULL,
     dob date,
     photo_url varchar(200),
     primary key(id));
     
CREATE TABLE stars_in_movies
	(star_id integer NOT NULL,
	 movie_id integer NOT NULL,
     primary key(star_id, movie_id),
     foreign key(star_id) references stars(id),
     foreign key(movie_id) references movies(id));

CREATE TABLE genres
	(id integer NOT NULL AUTO_INCREMENT,
     name varchar(32) NOT NULL,
     primary key(id));

CREATE TABLE genres_in_movies
	(genre_id integer NOT NULL,
     movie_id integer NOT NULL,
     primary key (genre_id, movie_id),
     foreign key(genre_id) references genres(id),
     foreign key(movie_id) references movies(id));

CREATE TABLE creditcards
	(id varchar(20) NOT NULL,
     first_name varchar(50) NOT NULL,
     last_name varchar(50) NOT NULL,
     expiration date NOT NULL,
     primary key(id));
     
CREATE TABLE customers
	(id integer NOT NULL AUTO_INCREMENT,
	 first_name varchar(50) NOT NULL,
     last_name varchar(50) NOT NULL,
     cc_id varchar(20) NOT NULL,
     address varchar(200) NOT NULL,
     email varchar(50) NOT NULL,
     password varchar(20) NOT NULL,
     primary key(id),
     foreign key(cc_id) references creditcards(id));
     
CREATE TABLE sales
	(id integer NOT NULL AUTO_INCREMENT,
	 customer_id integer NOT NULL,
     movie_id integer NOT NULL,
     sale_date date NOT NULL,
     primary key(id),
     foreign key(customer_id) references customers(id),
     foreign key(movie_id) references movies(id));
