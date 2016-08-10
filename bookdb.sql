create database bookdb;
use bookdb;
CREATE TABLE book (
id CHAR(30) NOT NULL,
bookname VARCHAR(200),
booktype CHAR(30) NOT NULL,
author CHAR(40)NOT NULL,
translator CHAR(40),
publisher VARCHAR(200),
publish_time DATE,
price FLOAT NOT NULL,
stock int not null,
page int not null,
primary key(id)
);

CREATE TABLE borrow(
id CHAR(30) NOT NULL,
book_id CHAR(40) NOT NULL,
reader_id CHAR(40) NOT NULL,
borrow_date DATE,
back_date DATE,
if_back CHAR(20) NOT NULL,
primary key(id)
);
DROP database reader
CREATE TABLE reader(
id CHAR(40) NOT NULL,
readername CHAR(80) NOT NULL,
readertype CHAR(40) NOT NULL,
sex CHAR(20) NOT NULL,
max_num int NOT NULL,
days_num int NOT NULL,
PRIMARY KEY(ID)
);

CREATE TABLE user(
id INT NOT NULL AUTO_INCREMENT,
username VARCHAR(80) NOT NULL,
password VARCHAR(80) NOT NULL,
is_admin CHAR(20) NOT NULL,
PRIMARY KEY(id)
);











