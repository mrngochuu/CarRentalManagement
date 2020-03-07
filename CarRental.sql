-- Database export via SQLPro (https://www.sqlprostudio.com/allapps.html)
-- Exported by ngochuu at 07-03-2020 17:40.
-- WARNING: This file may contain descructive statements such as DROPs.
-- Please ensure that you are running the script at the proper location.


-- BEGIN TABLE dbo.Cars
CREATE TABLE dbo.Cars (
	CarID int NOT NULL,
	CarName varchar(50) NOT NULL,
	Price int NOT NULL,
	Quantity int NOT NULL,
	CreatedDate datetime NOT NULL,
	RentalDate datetime NOT NULL,
	ReturnDate datetime NOT NULL,
	Status varchar(20) NOT NULL,
	ImgURL varchar(max) NOT NULL,
	CategoryID int NOT NULL
);
GO

ALTER TABLE dbo.Cars ADD CONSTRAINT PK__Cars__68A0340E6F9D82E0 PRIMARY KEY (CarID);
GO

-- Table dbo.Cars contains no data. No inserts have been genrated.
-- Inserting 0 rows into dbo.Cars


-- END TABLE dbo.Cars

-- BEGIN TABLE dbo.Categories
CREATE TABLE dbo.Categories (
	CategoryID int NOT NULL IDENTITY(1,1),
	CategoryName varchar(20) NOT NULL
);
GO

ALTER TABLE dbo.Categories ADD CONSTRAINT PK__Categori__19093A2BE9CCAC3C PRIMARY KEY (CategoryID);
GO

-- Table dbo.Categories contains no data. No inserts have been genrated.
-- Inserting 0 rows into dbo.Categories


-- END TABLE dbo.Categories

-- BEGIN TABLE dbo.OrderDetails
CREATE TABLE dbo.OrderDetails (
	Price int NOT NULL,
	Quantity int NOT NULL,
	OrderID int NOT NULL,
	CarID int NOT NULL
);
GO

-- Table dbo.OrderDetails contains no data. No inserts have been genrated.
-- Inserting 0 rows into dbo.OrderDetails


-- END TABLE dbo.OrderDetails

-- BEGIN TABLE dbo.Orders
CREATE TABLE dbo.Orders (
	OrderID int NOT NULL IDENTITY(1,1),
	ReceiverName int NOT NULL,
	ReceiverPhone int NOT NULL,
	ReceiverAddress int NOT NULL,
	PaymentDate datetime NOT NULL,
	isPayment bit NOT NULL DEFAULT ((0)),
	Email varchar(50) NOT NULL,
	CodeID int NULL
);
GO

ALTER TABLE dbo.Orders ADD CONSTRAINT PK__Orders__C3905BAF201B69AA PRIMARY KEY (OrderID);
GO

-- Table dbo.Orders contains no data. No inserts have been genrated.
-- Inserting 0 rows into dbo.Orders


-- END TABLE dbo.Orders

-- BEGIN TABLE dbo.PromotionDetails
CREATE TABLE dbo.PromotionDetails (
	ReceivedTime int NOT NULL,
	Email varchar(50) NOT NULL,
	CodeID int NOT NULL
);
GO

-- Table dbo.PromotionDetails contains no data. No inserts have been genrated.
-- Inserting 0 rows into dbo.PromotionDetails


-- END TABLE dbo.PromotionDetails

-- BEGIN TABLE dbo.Promotions
CREATE TABLE dbo.Promotions (
	CodeID int NOT NULL,
	Code varchar(max) NOT NULL,
	Percents int NOT NULL,
	Quantity int NOT NULL,
	ConditionAmount int NOT NULL,
	StartedDate datetime NOT NULL,
	EndedDate datetime NOT NULL,
	CreatedDate datetime NOT NULL,
	Status varchar(20) NOT NULL
);
GO

ALTER TABLE dbo.Promotions ADD CONSTRAINT PK__Promotio__C6DE2C3507A643CB PRIMARY KEY (CodeID);
GO

-- Table dbo.Promotions contains no data. No inserts have been genrated.
-- Inserting 0 rows into dbo.Promotions


-- END TABLE dbo.Promotions

-- BEGIN TABLE dbo.Roles
CREATE TABLE dbo.Roles (
	RoleID int NOT NULL IDENTITY(1,1),
	RoleName varchar(20) NOT NULL
);
GO

ALTER TABLE dbo.Roles ADD CONSTRAINT PK__Roles__8AFACE3AC083B6D1 PRIMARY KEY (RoleID);
GO

-- Table dbo.Roles contains no data. No inserts have been genrated.
-- Inserting 0 rows into dbo.Roles


-- END TABLE dbo.Roles

-- BEGIN TABLE dbo.Users
CREATE TABLE dbo.Users (
	Email varchar(50) NOT NULL,
	Password varchar(max) NOT NULL,
	Sex bit NOT NULL,
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

-- Table dbo.Users contains no data. No inserts have been genrated.
-- Inserting 0 rows into dbo.Users


-- END TABLE dbo.Users

IF OBJECT_ID('dbo.Cars', 'U') IS NOT NULL AND OBJECT_ID('dbo.Categories', 'U') IS NOT NULL
	ALTER TABLE dbo.Cars
	ADD CONSTRAINT FK__Cars__CategoryID__440B1D61
	FOREIGN KEY (CategoryID)
	REFERENCES dbo.Categories (CategoryID);

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
	FOREIGN KEY (CodeID)
	REFERENCES dbo.Promotions (CodeID);

IF OBJECT_ID('dbo.PromotionDetails', 'U') IS NOT NULL AND OBJECT_ID('dbo.Users', 'U') IS NOT NULL
	ALTER TABLE dbo.PromotionDetails
	ADD CONSTRAINT FK__Promotion__Email__48CFD27E
	FOREIGN KEY (Email)
	REFERENCES dbo.Users (Email);

IF OBJECT_ID('dbo.PromotionDetails', 'U') IS NOT NULL AND OBJECT_ID('dbo.Promotions', 'U') IS NOT NULL
	ALTER TABLE dbo.PromotionDetails
	ADD CONSTRAINT FK__Promotion__CodeI__49C3F6B7
	FOREIGN KEY (CodeID)
	REFERENCES dbo.Promotions (CodeID);

IF OBJECT_ID('dbo.Users', 'U') IS NOT NULL AND OBJECT_ID('dbo.Roles', 'U') IS NOT NULL
	ALTER TABLE dbo.Users
	ADD CONSTRAINT FK__Users__RoleID__3C69FB99
	FOREIGN KEY (RoleID)
	REFERENCES dbo.Roles (RoleID);

