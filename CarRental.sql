-- Database export via SQLPro (https://www.sqlprostudio.com/allapps.html)
-- Exported by ngochuu at 17-03-2020 05:37.
-- WARNING: This file may contain descructive statements such as DROPs.
-- Please ensure that you are running the script at the proper location.
CREATE DATABASE CarRental;
USE CarRental;

-- BEGIN TABLE dbo.Cars
IF OBJECT_ID('dbo.Cars', 'U') IS NOT NULL
DROP TABLE dbo.Cars;
GO

CREATE TABLE dbo.Cars (
	CarID int NOT NULL IDENTITY(1,1),
	CarName varchar(50) NOT NULL,
	Price int NOT NULL,
	Quantity int NOT NULL,
	CreatedDate datetime NOT NULL,
	Status varchar(20) NOT NULL,
	ImgURL varchar(max) NOT NULL,
	CategoryID int NOT NULL,
	Color varchar(20) NOT NULL,
	Model varchar(30) NOT NULL
);
GO

ALTER TABLE dbo.Cars ADD CONSTRAINT PK__Cars__68A0340E6F9D82E0 PRIMARY KEY (CarID);
GO

-- Inserting 9 rows into dbo.Cars
-- Insert batch #1
INSERT INTO dbo.Cars (CarID, CarName, Price, Quantity, CreatedDate, Status, ImgURL, CategoryID, Color, Model) VALUES
(3, 'Xe hoi', 5, 10, '2020-03-03 00:00:00', 'inactive', 'img', 1, 'yellow', '2000'),
(4, 'Xe hoi', 5, 8, '2020-03-03 00:00:00', 'inactive', 'img', 1, 'yellow', '2000'),
(5, 'Xe do', 5, 10, '2020-03-02 00:00:00', 'inactive', 'img', 1, 'yellow', '2000'),
(6, 'Honda City', 100, 20, '2020-03-17 01:04:49', 'active', 'Honda-City.jpg', 2, 'Yellow dragon', '2019'),
(9, 'Honda City', 110, 30, '2020-03-17 04:54:55', 'active', 'Honda-City-2.png', 2, 'White', '2018'),
(10, 'Lexus rx350', 400, 10, '2020-03-17 05:02:49', 'active', 'lexus.jpg', 6, 'Black', '2019'),
(11, 'BMW 320i', 400, 13, '2020-03-17 05:05:26', 'active', 'bmw.jpg', 2, 'Red', '2017'),
(12, 'BMW X7', 500, 20, '2020-03-17 05:07:10', 'active', 'bmw-x7.jpg', 6, 'Black', '2019'),
(13, 'Ferrari 812 GTX', 1900, 3, '2020-03-17 05:09:39', 'active', 'ferrari.jpg', 10, 'Back', '2019');

-- END TABLE dbo.Cars

-- BEGIN TABLE dbo.Categories
IF OBJECT_ID('dbo.Categories', 'U') IS NOT NULL
DROP TABLE dbo.Categories;
GO

CREATE TABLE dbo.Categories (
	CategoryID int NOT NULL IDENTITY(1,1),
	CategoryName varchar(50) NOT NULL
);
GO

ALTER TABLE dbo.Categories ADD CONSTRAINT PK__Categori__19093A2BE9CCAC3C PRIMARY KEY (CategoryID);
GO

-- Inserting 8 rows into dbo.Categories
-- Insert batch #1
INSERT INTO dbo.Categories (CategoryID, CategoryName) VALUES
(1, 'Hatchbacks'),
(2, 'Sedan'),
(6, 'SUV'),
(7, 'MPV/ MUV'),
(8, 'Convertibles'),
(9, 'Estate car/ station wagon'),
(10, 'Coupe'),
(11, 'Pick-up');

-- END TABLE dbo.Categories

-- BEGIN TABLE dbo.Feedbacks
IF OBJECT_ID('dbo.Feedbacks', 'U') IS NOT NULL
DROP TABLE dbo.Feedbacks;
GO

CREATE TABLE dbo.Feedbacks (
	Rating int NOT NULL,
	Feedback varchar(max) NOT NULL,
	FeedbackDate datetime NOT NULL,
	Email varchar(50) NULL,
	CarID int NULL
);
GO

-- Inserting 2 rows into dbo.Feedbacks
-- Insert batch #1
INSERT INTO dbo.Feedbacks (Rating, Feedback, FeedbackDate, Email, CarID) VALUES
(9, 'Chip car', '2020-03-17 05:36:12', 'ngochuu.bts@gmail.com', 6),
(7, 'Beautiful car', '2020-03-17 05:36:26', 'ngochuu.bts@gmail.com', 6);

-- END TABLE dbo.Feedbacks

-- BEGIN TABLE dbo.OrderDetails
IF OBJECT_ID('dbo.OrderDetails', 'U') IS NOT NULL
DROP TABLE dbo.OrderDetails;
GO

CREATE TABLE dbo.OrderDetails (
	Price int NOT NULL,
	Quantity int NOT NULL,
	OrderID int NOT NULL,
	CarID int NOT NULL,
	RentalDate datetime NULL DEFAULT (NULL),
	ReturnDate datetime NULL DEFAULT (NULL),
	Status varchar(20) NULL DEFAULT (NULL)
);
GO

-- Inserting 2 rows into dbo.OrderDetails
-- Insert batch #1
INSERT INTO dbo.OrderDetails (Price, Quantity, OrderID, CarID, RentalDate, ReturnDate, Status) VALUES
(100, 10, 38, 6, '2020-03-17 00:00:00', '2020-03-18 23:59:59', 'returned'),
(1900, 1, 39, 13, '2020-03-17 00:00:00', '2020-03-18 23:59:59', 'waiting');

-- END TABLE dbo.OrderDetails

-- BEGIN TABLE dbo.Orders
IF OBJECT_ID('dbo.Orders', 'U') IS NOT NULL
DROP TABLE dbo.Orders;
GO

CREATE TABLE dbo.Orders (
	OrderID int NOT NULL IDENTITY(1,1),
	ReceiverName varchar(50) NULL DEFAULT ('NULL'),
	ReceiverPhone varchar(20) NULL DEFAULT ('NULL'),
	Address varchar(max) NULL DEFAULT ('NULL'),
	PaymentDate datetime NULL DEFAULT (NULL),
	isPayment bit NOT NULL DEFAULT ((0)),
	Email varchar(50) NULL DEFAULT (NULL),
	PromotionID int NULL DEFAULT ('NULL'),
	Status varchar(20) NOT NULL DEFAULT ('active')
);
GO

ALTER TABLE dbo.Orders ADD CONSTRAINT PK__Orders__C3905BAF201B69AA PRIMARY KEY (OrderID);
GO

-- Inserting 3 rows into dbo.Orders
-- Insert batch #1
INSERT INTO dbo.Orders (OrderID, ReceiverName, ReceiverPhone, Address, PaymentDate, isPayment, Email, PromotionID, Status) VALUES
(38, 'Do Ngoc Huu', '039-7189696', '381 Phan Van Tri', '2020-03-17 04:57:34', 1, 'ngochuu.bts@gmail.com', NULL, 'active'),
(39, 'Nguyen Van Teo', '039-7189696', '381 Phan Van Tri', '2020-03-17 05:12:27', 1, 'ngochuu.bts@gmail.com', 1, 'active'),
(40, NULL, NULL, NULL, NULL, 0, 'ngochuu.bts@gmail.com', NULL, 'active');

-- END TABLE dbo.Orders

-- BEGIN TABLE dbo.PromotionDetails
IF OBJECT_ID('dbo.PromotionDetails', 'U') IS NOT NULL
DROP TABLE dbo.PromotionDetails;
GO

CREATE TABLE dbo.PromotionDetails (
	ReceivedDate datetime NOT NULL,
	Email varchar(50) NOT NULL,
	PromotionID int NOT NULL,
	ExpiriedDate datetime NOT NULL,
	Code varchar(max) NOT NULL,
	Status varchar(20) NOT NULL DEFAULT ('available')
);
GO

-- Inserting 1 row into dbo.PromotionDetails
-- Insert batch #1
INSERT INTO dbo.PromotionDetails (ReceivedDate, Email, PromotionID, ExpiriedDate, Code, Status) VALUES
('2020-03-17 04:57:35', 'ngochuu.bts@gmail.com', 1, '2020-04-16 04:57:35', '63cc9ade-e462-426b-8c0a-3e4569323de9', 'unavailable');

-- END TABLE dbo.PromotionDetails

-- BEGIN TABLE dbo.Promotions
IF OBJECT_ID('dbo.Promotions', 'U') IS NOT NULL
DROP TABLE dbo.Promotions;
GO

CREATE TABLE dbo.Promotions (
	PromotionID int NOT NULL IDENTITY(1,1),
	PromotionName varchar(max) NOT NULL,
	Percents int NOT NULL,
	ConditionAmount int NOT NULL,
	StartedDate datetime NOT NULL,
	EndedDate datetime NOT NULL,
	CreatedDate datetime NOT NULL,
	Status varchar(20) NOT NULL DEFAULT ('active'),
	ExpiryDate int NOT NULL
);
GO

ALTER TABLE dbo.Promotions ADD CONSTRAINT PK__Promotio__C6DE2C3507A643CB PRIMARY KEY (PromotionID);
GO

-- Inserting 3 rows into dbo.Promotions
-- Insert batch #1
INSERT INTO dbo.Promotions (PromotionID, PromotionName, Percents, ConditionAmount, StartedDate, EndedDate, CreatedDate, Status, ExpiryDate) VALUES
(1, 'Summer', 30, 500, '2020-03-14 00:00:00', '2020-03-20 00:00:00', '2020-03-15 00:00:00', 'active', 30),
(2, 'Spring', 50, 500, '2020-03-17 00:00:00', '2020-03-31 00:00:00', '2020-03-17 04:37:50', 'active', 30),
(4, 'covid-19', 80, 500, '2020-03-19 00:00:00', '2020-04-22 00:00:00', '2020-03-17 04:59:46', 'active', 30);

-- END TABLE dbo.Promotions

-- BEGIN TABLE dbo.Roles
IF OBJECT_ID('dbo.Roles', 'U') IS NOT NULL
DROP TABLE dbo.Roles;
GO

CREATE TABLE dbo.Roles (
	RoleID int NOT NULL IDENTITY(1,1),
	RoleName varchar(30) NOT NULL
);
GO

ALTER TABLE dbo.Roles ADD CONSTRAINT PK__Roles__8AFACE3AC083B6D1 PRIMARY KEY (RoleID);
GO

-- Inserting 2 rows into dbo.Roles
-- Insert batch #1
INSERT INTO dbo.Roles (RoleID, RoleName) VALUES
(1, 'admin'),
(2, 'customer');

-- END TABLE dbo.Roles

-- BEGIN TABLE dbo.Users
IF OBJECT_ID('dbo.Users', 'U') IS NOT NULL
DROP TABLE dbo.Users;
GO

CREATE TABLE dbo.Users (
	Email varchar(50) NOT NULL,
	Password varchar(max) NOT NULL,
	Sex varchar(20) NOT NULL,
	Fullname varchar(50) NOT NULL,
	Phone varchar(20) NOT NULL,
	Address varchar(max) NOT NULL,
	CreatedDate datetime NOT NULL,
	Status varchar(20) NOT NULL,
	RoleID int NOT NULL
);
GO

ALTER TABLE dbo.Users ADD CONSTRAINT PK__Users__A9D105350BCDA54D PRIMARY KEY (Email);
GO

-- Inserting 2 rows into dbo.Users
-- Insert batch #1
INSERT INTO dbo.Users (Email, Password, Sex, Fullname, Phone, Address, CreatedDate, Status, RoleID) VALUES
('dongochuu95@gmail.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'Male', 'Do Ngoc Huu', '039-7189696', '123', '2020-03-10 00:45:42', 'active', 1),
('ngochuu.bts@gmail.com', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 'Male', 'Do Ngoc Huu', '039-7189696', '381', '2020-03-08 21:37:15', 'active', 2);

-- END TABLE dbo.Users

IF OBJECT_ID('dbo.Cars', 'U') IS NOT NULL AND OBJECT_ID('dbo.Categories', 'U') IS NOT NULL
	ALTER TABLE dbo.Cars
	ADD CONSTRAINT FK__Cars__CategoryID__440B1D61
	FOREIGN KEY (CategoryID)
	REFERENCES dbo.Categories (CategoryID);

IF OBJECT_ID('dbo.Feedbacks', 'U') IS NOT NULL AND OBJECT_ID('dbo.Cars', 'U') IS NOT NULL
	ALTER TABLE dbo.Feedbacks
	ADD CONSTRAINT FK__feedbacks__CarID__1BC821DD
	FOREIGN KEY (CarID)
	REFERENCES dbo.Cars (CarID);

IF OBJECT_ID('dbo.Feedbacks', 'U') IS NOT NULL AND OBJECT_ID('dbo.Users', 'U') IS NOT NULL
	ALTER TABLE dbo.Feedbacks
	ADD CONSTRAINT FK__feedbacks__Email__1AD3FDA4
	FOREIGN KEY (Email)
	REFERENCES dbo.Users (Email);

IF OBJECT_ID('dbo.OrderDetails', 'U') IS NOT NULL AND OBJECT_ID('dbo.Cars', 'U') IS NOT NULL
	ALTER TABLE dbo.OrderDetails
	ADD CONSTRAINT FK__OrderDeta__CarID__4CA06362
	FOREIGN KEY (CarID)
	REFERENCES dbo.Cars (CarID);

IF OBJECT_ID('dbo.OrderDetails', 'U') IS NOT NULL AND OBJECT_ID('dbo.Orders', 'U') IS NOT NULL
	ALTER TABLE dbo.OrderDetails
	ADD CONSTRAINT FK__OrderDeta__Order__4BAC3F29
	FOREIGN KEY (OrderID)
	REFERENCES dbo.Orders (OrderID);

IF OBJECT_ID('dbo.Orders', 'U') IS NOT NULL AND OBJECT_ID('dbo.Users', 'U') IS NOT NULL
	ALTER TABLE dbo.Orders
	ADD CONSTRAINT FK__Orders__Email__403A8C7D
	FOREIGN KEY (Email)
	REFERENCES dbo.Users (Email);

IF OBJECT_ID('dbo.Orders', 'U') IS NOT NULL AND OBJECT_ID('dbo.Promotions', 'U') IS NOT NULL
	ALTER TABLE dbo.Orders
	ADD CONSTRAINT FK__Orders__CodeID__412EB0B6
	FOREIGN KEY (PromotionID)
	REFERENCES dbo.Promotions (PromotionID);

IF OBJECT_ID('dbo.PromotionDetails', 'U') IS NOT NULL AND OBJECT_ID('dbo.Users', 'U') IS NOT NULL
	ALTER TABLE dbo.PromotionDetails
	ADD CONSTRAINT FK__Promotion__Email__48CFD27E
	FOREIGN KEY (Email)
	REFERENCES dbo.Users (Email);

IF OBJECT_ID('dbo.PromotionDetails', 'U') IS NOT NULL AND OBJECT_ID('dbo.Promotions', 'U') IS NOT NULL
	ALTER TABLE dbo.PromotionDetails
	ADD CONSTRAINT FK__Promotion__CodeI__49C3F6B7
	FOREIGN KEY (PromotionID)
	REFERENCES dbo.Promotions (PromotionID);

IF OBJECT_ID('dbo.Users', 'U') IS NOT NULL AND OBJECT_ID('dbo.Roles', 'U') IS NOT NULL
	ALTER TABLE dbo.Users
	ADD CONSTRAINT FK__Users__RoleID__3C69FB99
	FOREIGN KEY (RoleID)
	REFERENCES dbo.Roles (RoleID);

