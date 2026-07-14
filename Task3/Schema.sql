create table Product (
	id serial unique not null,
	name char(20) not null,
	email char(20) not null,
	password char(20) not null,
	profile_description char(20) not null,
);

insert into Product (name, email, password, profile_description) values
	('Marsel', 'sidikov@gmail.com', 'qwerty007', 'Programmer'),
	('Roman', 'roman@gmail.com', 'qwerty008', 'Student'),
	('Alexey', 'alexey@gmail.com', 'qwerty009', 'Student');

select * from Product;