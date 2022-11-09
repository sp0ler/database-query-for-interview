create table customers (
   customer_id serial primary key,
   first_name varchar(30) not null,
   second_name  varchar(30) not null

);
create table items (
   item_id serial primary key,
   title varchar(60) not null,
   price numeric not null default 0

);
create table purchases (
   customer_id int references customers (customer_id) on update cascade,
   item_id int references items (item_id) on update cascade ,
   time_purchase date not null default current_date,
   constraint purchase_id primary key (customer_id, item_id)
);