-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE users (
	id serial4 primary key NOT NULL,
	username varchar(45) NOT NULL,
	"password" varchar(255) NOT NULL,
	avatar varchar(255) ,
	created_at timestamp NOT NULL,
	updated_at timestamp NOT NULL,
	email varchar(255) NOT NULL,
	status boolean,
	first_name varchar(255),
	last_name varchar(255),
	phone_no varchar(255),
	gender varchar(10)
)