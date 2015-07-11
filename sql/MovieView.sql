CREATE OR REPLACE VIEW movie_set as select movies.id, stars.first_name, 
	stars.last_name, movies.banner_url, movies.director, movies.title,
	movies.trailer_url, movies.year 
FROM stars_in_movies, stars, movies
WHERE stars_in_movies.movie_id = movies.id 
	AND stars.id = stars_in_movies.star_id;