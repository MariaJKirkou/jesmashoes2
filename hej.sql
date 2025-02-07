drop database if exists jesmaShoes2;
create database jesmaShoes2;
use jesmaShoes2;

create table OrderStatus(
id int not null auto_increment PRIMARY KEY,
month varchar(30) not null
);

create table Customer(
id int not null auto_increment primary key,
firstName varchar(30),
lastName varchar(30),
street varchar(30),
zip varchar(30),
username varchar(30),
password varchar(30)
);

create table Shoe(
id int not null auto_increment primary key,
colour varchar(30),
size int,
price int, 
brand varchar (30)
);

create table Category(
id int not null auto_increment primary key,
name varchar(30) not null
);

create table ShoeCategory(
shoeId int,
categoryId int,
foreign key (shoeId) references shoe(id) on delete cascade,
foreign key (categoryId) references category(id) on delete cascade
);

create table Inventory(
id int not null auto_increment primary key,
shoeId int,
stock int,
foreign key (shoeId) references shoe(id)
);

create table CustomerOrder(
id int auto_increment PRIMARY KEY,
customerId int,
OrderStatus int,
foreign key (customerId) references customer(id),
foreign key (OrderStatus) references OrderStatus(id) on delete set null
);

create table Cart(
shoeId int,
quantity int,
customerorderid int,
foreign key (shoeId) references shoe(id),
foreign key (customerorderid) references customerorder(id)
);

create index idx_shoe_brand on Shoe(brand);
create index idx_shoe_category on category(name);

create or replace view ViewCustomerOrders as select co.customerId, c.customerOrderId, c.shoeId, c.quantity, co.OrderStatus from customerOrder co
inner join cart c on co.id = c.customerorderid
inner join shoe s on c.shoeId = s.id
inner join customer on co.customerid = customer.id
;
