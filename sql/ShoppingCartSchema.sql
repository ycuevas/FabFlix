CREATE TABLE shoppingcart
	(id integer NOT NULL AUTO_INCREMENT,
	 customer_id integer NOT NULL,
     movie_id integer NOT NULL,
     quantity int NOT NULL,
     primary key(id),
     foreign key(customer_id) references customers(id),
     foreign key(movie_id) references movies(id));