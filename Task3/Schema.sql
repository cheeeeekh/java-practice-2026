create table Product (
	id serial unique not null,
	name char(20) not null,
	cost integer check(cost > -1) not null
);

insert into Product (name, cost) values 
	('Ноутбук', 105000),
	('Мышь', 1200),
	('Клавиатура', 3500),
	('Монитор', 25000);

select * from Product;