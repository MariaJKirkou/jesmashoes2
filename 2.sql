insert into OrderStatus (month) values
('Active'), ('Paid');

insert into Shoe (colour, size, price, brand) values
('Black', 38, 399, 'Ecco'),
('Black', 42, 799, 'Birkenstock'),
('Brown', 44, 699, 'Timberland'),
('White', 37, 899, 'Adidas'),
('Red', 38, 1299, 'Adidas'),
('Blue', 39, 299, 'Havaianas'),
('Green', 41, 1299, 'Columbia'),
('White', 36, 799, 'Vans');

insert into Category (name) values
('Sandals'), ('Boots'), ('Sneakers'), ('Running'), ('Flip-flops'), ('Mens'), ('Womens'), ('Childrens'), ('Sports'), ('Outlet');

insert into ShoeCategory (shoeId, categoryId) values
(1, 1), (1, 7), (1, 10),
(2, 2), (2, 6), (3, 2),
(3, 6),
(4, 3), (4, 4), (4, 7), (4, 9),
(5, 3), (5, 4), (5, 8), (5, 9),
(6, 5), (6, 6),
(7, 2), (7, 6),
(8, 3), (8, 6), (8, 7), (8, 8);

INSERT INTO Customer (firstName, lastName, street, zip, username, password)
VALUES 
('Michael', 'Scott', '1725 Slough Avenue', 'Scranton', 'michael1', 'default123'),
('Marge', 'Simpson', '742 Evergreen Terrace', 'Springfield', 'marge1', 'default123'),
('Lisa', 'Simpson', '742 Evergreen Terrace', 'Springfield', 'lisa1', 'default123'),
('Barney', 'Stinson', '150 West 81st Street', 'New York', 'barney1', 'default123'),
('Kevin', 'McCallister', '671 Lincoln Avenue', 'Illinois', 'kevin1', 'default123');

insert into Inventory (shoeId, stock) values
(1, 50),
(2, 12),
(3, 43),
(4, 23),
(5, 65),
(6, 76),
(7, 0),
(8, 23);


UPDATE Inventory 
SET stock = 0 
WHERE shoeId = (SELECT id FROM Shoe WHERE brand = 'Vans' AND colour = 'White' AND size = 36);
