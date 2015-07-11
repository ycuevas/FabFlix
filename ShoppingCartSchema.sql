CREATE OR REPLACE VIEW movie_set as select movies.id, stars.first_name, 
stars.last_name, movies.banner_url, movies.director, movies.title,
movies.trailer_url, movies.year
from stars_in_movies, stars, movies 
where stars_in_movies.movie_id = movies.id 
and stars.id = stars_in_movies.star_id;

CREATE TABLE shoppingcart
	(id integer NOT NULL AUTO_INCREMENT,
	 customer_id integer NOT NULL,
     movie_id integer NOT NULL,
     quantity int NOT NULL,
     primary key(id),
     foreign key(customer_id) references customers(id),
     foreign key(movie_id) references movies(id));
     
CREATE OR REPLACE VIEW movie_set as select movies.id, stars.first_name, 
	stars.last_name, movies.banner_url, movies.director, movies.title,
	movies.trailer_url, movies.year 
FROM stars_in_movies, stars, movies
WHERE stars_in_movies.movie_id = movies.id 
	AND stars.id = stars_in_movies.star_id;
