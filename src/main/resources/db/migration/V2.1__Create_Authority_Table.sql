-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE authorities (
	id serial4 primary key NOT NULL,
	username varchar(45) NOT NULL,
	authority varchar(45) NOT NULL
)