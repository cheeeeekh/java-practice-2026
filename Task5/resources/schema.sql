create table Product (
	id serial unique not null,
	name char(20) not null,
	email char(20) not null,
	password char(20) not null,
	profile_description char(20) not null,
);