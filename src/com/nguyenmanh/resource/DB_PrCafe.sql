/******  
 * @purpose: Create Database PrCafe
 * @author: NguyenManh
 * @date: 2018/03/18
******/
CREATE DATABASE PrCafe
USE
PrCafe
GO

/****** Create Table table_info ******/
CREATE TABLE table_info(
	table_id INT IDENTITY (1,1) NOT NULL,
	table_name NVARCHAR(50) NOT NULL,
	table_status BIT NOT NULL,
)
ALTER TABLE table_info ADD CONSTRAINT PK_Table PRIMARY KEY (table_id)

INSERT INTO table_info VALUES ('Bàn 01',0)
INSERT INTO table_info VALUES ('Bàn 02',0)
INSERT INTO table_info VALUES ('Bàn 03',0)
INSERT INTO table_info VALUES ('Bàn 04',0)
INSERT INTO table_info VALUES ('Bàn 05',0)
INSERT INTO table_info VALUES ('Bàn 06',0)
INSERT INTO table_info VALUES ('Bàn 07',0)
INSERT INTO table_info VALUES ('Bàn 08',0)
INSERT INTO table_info VALUES ('Bàn 09',0)
INSERT INTO table_info VALUES ('Bàn 10',0)
INSERT INTO table_info VALUES ('Bàn 11',0)
INSERT INTO table_info VALUES ('Bàn 12',0)

SELECT * FROM table_info

/****** Create Table product_info ******/
CREATE TABLE product_info(
	product_id INT NOT NULL,
	product_name NVARCHAR(50) NOT NULL,
	product_cost FLOAT NOT NULL,
	product_price FLOAT NOT NULL,
	product_image NVARCHAR(50) NOT NULL,
	product_unit NVARCHAR(50) NOT NULL,
	product_quantity_sell INT,
)
ALTER TABLE product_info ADD CONSTRAINT PK_Product PRIMARY KEY(product_id)

INSERT INTO product_info VALUES (111,'Cafe Arabica', 20000, 45000, 'coffee.png', N'Cốc',5)
INSERT INTO product_info VALUES (112,'Cafe Culi', 10000, 25000, 'cup1.png', N'Cốc',5)
INSERT INTO product_info VALUES (113,'Cafe Cherry', 20000, 30000, 'cup2.png', N'Cốc',6)
INSERT INTO product_info VALUES (114,'Cafe Moka', 10000, 25000, 'cup3.png', N'Cốc',10)
INSERT INTO product_info VALUES (115,'Cafe Robusta', 25000, 50000, 'cup4.png', N'Cốc',20)
INSERT INTO product_info VALUES (116,'Cafe Espresso', 15000, 40000, 'cup5.png', N'Cốc',12)
INSERT INTO product_info VALUES (117,'Cafe Macchiato', 20000, 35000, 'cup6.png', N'Cốc',30)
INSERT INTO product_info VALUES (118,'Cafe Mocha', 10000, 30000, 'cup7.png', N'Cốc',9)
INSERT INTO product_info VALUES (119,'Cafe Latte', 20000, 30000, 'cup8.png', N'Cốc',50)
INSERT INTO product_info VALUES (120,'Cafe Americano', 40000, 60000, 'cup9.png', N'Cốc',30)

SELECT * FROM product_info

/****** Create Table employee_info ******/
CREATE TABLE employee_info(
	employee_name VARCHAR(50) NOT NULL,
	employee_password VARCHAR(50) NOT NULL,
)
ALTER TABLE employee_info ADD CONSTRAINT PK_Employee PRIMARY KEY (employee_name)

INSERT INTO employee_info VALUES ('Manhnv', '123456')
SELECT * FROM employee_info

/****** Create Table invoice_info  ******/
CREATE TABLE invoice_info(
	invoice_id INT IDENTITY(1,1) NOT NULL,
	employee_name varchar(50) NULL,
	table_id INT NOT NULL,
	invoice_date DATE NOT NULL,
	invoice_pay int,
)
ALTER TABLE invoice_info ADD CONSTRAINT PK_Invoice PRIMARY KEY (invoice_id)
SET ANSI_PADDING OFF

/****** Create Table cart_info  ******/
CREATE TABLE cart_info(
	cart_id INT IDENTITY(1,1) NOT NULL,
	product_id INT NOT NULL,
	invoice_id INT NOT NULL,
	cart_quantity INT NOT NULL,
)
ALTER TABLE cart_info ADD CONSTRAINT PK_Cart PRIMARY KEY (cart_id)

/****** ADD CONSTRAINT FOREIGN KEY FK_Cart_Invoice ******/
ALTER TABLE cart_info WITH CHECK ADD CONSTRAINT FK_Cart_Invoice FOREIGN KEY(invoice_id)
REFERENCES invoice_info (invoice_id)
ALTER TABLE cart_info CHECK CONSTRAINT FK_Cart_Invoice

/****** ADD CONSTRAINT FOREIGN KEY FK_Cart_Product ******/
ALTER TABLE cart_info WITH CHECK ADD CONSTRAINT FK_Cart_Product FOREIGN KEY(product_id)
REFERENCES product_info (product_id)
ALTER TABLE cart_info CHECK CONSTRAINT FK_Cart_Product

/****** ADD CONSTRAINT FOREIGN KEY FK_Cart_Table ******/
ALTER TABLE invoice_info WITH CHECK ADD CONSTRAINT FK_Invoice_Table FOREIGN KEY(table_id)
REFERENCES table_info (table_id)
ALTER TABLE invoice_info CHECK CONSTRAINT FK_Invoice_Table

/****** ADD CONSTRAINT FOREIGN KEY FK_Invoice_Employee ******/
ALTER TABLE invoice_info WITH CHECK ADD CONSTRAINT FK_Invoice_Employee FOREIGN KEY(employee_name)
REFERENCES employee_info (employee_name)
ALTER TABLE invoice_info CHECK CONSTRAINT FK_Invoice_Employee