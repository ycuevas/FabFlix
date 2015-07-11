DELIMITER //
 
DROP PROCEDURE IF EXISTS `add_movie`//

CREATE PROCEDURE `add_movie` (IN movieTitle VARCHAR(100), IN movieYear INT(11), IN movieDirector VARCHAR(100),
							  IN starFirstName VARCHAR(50), IN starLastName VARCHAR(50), IN genreName VARCHAR(32), OUT sprocResult VARCHAR(100))
LANGUAGE SQL
DETERMINISTIC
SQL SECURITY DEFINER
COMMENT 'Add movie procedure for group 21 by George Mokbel, Yari Cuevas and Steven Chan.'
BEGIN
    DECLARE starAlreadyExists, genreAlreadyExists, movieExists BOOLEAN DEFAULT FALSE;
    DECLARE starId, movieId, genreId INT DEFAULT 0;
    IF (movieTitle IS NULL OR movieTitle = '') THEN
		SET sprocResult = 'Movie failed to create, title cannot be empty.';
    ELSEIF ( movieYear IS NULL OR movieYear = 0) THEN
		SEt sprocResult = 'Movie failed to create, year cannot be empty.';
	ELSEIF ( movieDirector IS NULL OR movieDirector = '') THEN
		SET sprocResult = 'Movie failed to create, director cannot be empty.';
    ELSEIF ( starFirstName IS NULL AND starLastName is NULL OR starFirstName = '' AND starLastName = '' )THEN
		SET sprocResult = 'Movie failed to create, a star must at least have a first name or last name.';
	ELSEIF ( genreName IS NULL OR genreName = '') THEN
		SET sprocResult = 'Movie failed to create, a genre name must be provided.';
    ELSEIF (SELECT count(*) FROM movies where title = movieTitle and movies.year = movieYear and director = movieDirector) = 0 THEN
		INSERT INTO movies(id, title, year, director, banner_url, trailer_url) VALUES (Default, movieTitle, movieYear, movieDirector, DEFAULT, DEFAULT);
-- 		# IF doesnt exist, create him.
		IF (SELECT count(*) FROM stars where first_name = starFirstName AND last_name = starLastName) = 0 THEN
			INSERT INTO stars(id, first_name, last_name, dob, photo_url) VALUES (Default, starFirstName, starLastName, NULL, NULL);
		ELSE
			SET starAlreadyExists = true;
 		END IF;
        Select id into starId from stars where first_name = starFirstName AND last_name = starLastName;
         
--          # If genre doesn't exist, create it
 		IF (SELECT count(*) FROM genres where name = genreName ) = 0 then
 			INSERT INTO genres(id, name) VALUES (Default, genreName);
		ELSE
			SET genreAlreadyExists = true;
 		END IF;
 		Select id into genreId from genres where genres.name = genreName;
         # Get the newly created movie id.
         SELECT id into movieId from movies where title = movieTitle and director = movieDirector and movies.year = movieYear;   
         #Insert the genres in movies record.
         INSERT INTO genres_in_movies(genre_id, movie_id) values (genreId, movieId);
         #Insert the star in movies record
         INSErT INTO stars_in_movies(star_id, movie_id) values(starId, movieId);
         SET sprocResult = 'Movie successfully created.';
	ELSE
		SET sprocResult = 'Movie already exists in the database.';
    END IF;
END//

DELIMITER ;