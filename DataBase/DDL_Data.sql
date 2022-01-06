CREATE SCHEMA IF NOT EXISTS BookStore;
USE BookStore ;
-- -----------------------------------------------------
-- Table `BookStore`.`publisher`
-- -----------------------------------------------------

  CREATE TABLE  IF NOT EXISTS  publisher(
	publisher_id INTEGER PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    address VARCHAR(45),
    phone VARCHAR(12)
);


-- -----------------------------------------------------
-- Table `BookStore`.`category`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS category(
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(45) UNIQUE NOT NULL
);


-- -----------------------------------------------------
-- Table `BookStore`.`book`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS book(
	ISBN INTEGER PRIMARY KEY,
    title VARCHAR(45) UNIQUE NOT NULL,
    publisher_id INTEGER,
    publication_year year,
    price double,
    category_id integer,
    min_quantity INTEGER,
    cur_quantity INTEGER,
    FOREIGN KEY(category_id) REFERENCES category(id) 
				ON UPDATE CASCADE ON DELETE SET NULL,
    FOREIGN KEY(publisher_id) REFERENCES publisher(publisher_id) 
				ON UPDATE CASCADE ON DELETE SET NULL
);

-- -----------------------------------------------------
-- Table `BookStore`.`author`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS author(
	id INTEGER PRIMARY KEY,
    name VARCHAR(45) NOT NULL
);

-- -----------------------------------------------------
-- Table `BookStore`.`book_author`
-- -----------------------------------------------------

CREATE TABLE  IF NOT EXISTS book_author(
	book_ISBN INTEGER,
    author_id INTEGER,
    PRIMARY KEY(book_ISBN,author_id),
    FOREIGN KEY(book_ISBN) REFERENCES book(ISBN) 
				ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(author_id) REFERENCES author(author_id) 
				ON UPDATE CASCADE ON DELETE CASCADE
);


-- -----------------------------------------------------
-- Table `BookStore`.`user`
-- -----------------------------------------------------

CREATE TABLE  IF NOT EXISTS user(
	user_name VARCHAR(45) PRIMARY KEY,
    password VARCHAR(15) NOT NULL,
    last_name VARCHAR(45) NOT NULL,
    first_name VARCHAR(45) NOT NULL,
    email_address VARCHAR(45) UNIQUE NOT NULL,
    phone VARCHAR(12),
    shipping_address VARCHAR(45),
    is_manager BOOLEAN NOT NULL default false
);

-- -----------------------------------------------------
-- Table `BookStore`.`sale`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS sale(
    book_ISBN INTEGER NOT NULL,
    card_number VARCHAR(16) NOT NULL,
    user_name VARCHAR(45) NOT NULL,
    sale_time DATETIME NOT NULL,
    num_copies INTEGER default 1 NOT NULL,
    sale_price double NOT NULL,
	expire_date DATE NOT NULL,
	PRIMARY KEY(book_isbn,user_name,sale_time),
	FOREIGN KEY(book_ISBN) REFERENCES book(ISBN) 
				ON UPDATE CASCADE,
	FOREIGN KEY(user_name) REFERENCES user(user_name) 
				ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table `BookStore`.`cart`
-- -----------------------------------------------------

CREATE TABLE  IF NOT EXISTS cart(
	book_ISBN INTEGER NOT NULL,
    user_name VARCHAR(45) NOT NULL,
    num_copies INTEGER default 1 NOT NULL,
    PRIMARY KEY(book_ISBN, user_name),
    FOREIGN KEY(book_ISBN) REFERENCES book(ISBN) 
				ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(user_name) REFERENCES user(user_name) 
				ON UPDATE CASCADE ON DELETE CASCADE
 );
 
 -- -----------------------------------------------------
-- Table `BookStore`.`orders`
-- -----------------------------------------------------

CREATE TABLE  IF NOT EXISTS orders(
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
    book_ISBN INTEGER,
    copies_num INTEGER,
    FOREIGN KEY(book_ISBN) REFERENCES book(ISBN) 
				ON UPDATE CASCADE ON DELETE CASCADE
);


-- -----------------------------------------------------
-- triggers
-- -----------------------------------------------------
USE `bookstore`;
DROP TRIGGER IF EXISTS `book_BEFORE_UPDATE`;
DELIMITER $$
USE `bookstore`$$

CREATE DEFINER = CURRENT_USER TRIGGER `BookStore`.`book_BEFORE_UPDATE` BEFORE UPDATE ON `book` FOR EACH ROW
BEGIN
 IF NEW.min_quantity<0 then SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "COPIES CAN NOT BE LESS THAN 0" ; 
 END if;
END$$
DELIMITER ;

USE `bookstore`;
DROP TRIGGER IF EXISTS `book_AFTER_UPDATE`;
DELIMITER $$
USE `bookstore`$$
CREATE DEFINER = CURRENT_USER TRIGGER `BookStore`.`book_AFTER_UPDATE` AFTER UPDATE ON `book` FOR EACH ROW
BEGIN
IF NEW.cur_quantity<=OLD.min_quantity then 
   insert into orders(book_ISBN,copies_num) values(NEW.ISBN,( Select min_quantity from Book where Book.ISBN = NEW.ISBN));
    END if;
END$$
DELIMITER ;

USE `bookstore`;
DROP TRIGGER IF EXISTS `orders_BEFORE_DELETE`;
DELIMITER $$
USE `bookstore`$$
CREATE DEFINER = CURRENT_USER TRIGGER `BookStore`.`orders_BEFORE_DELETE` BEFORE DELETE ON `orders` FOR EACH ROW
BEGIN
update book set cur_quantity = cur_quantity + old.copies_num where ISBN = old.book.ISBN;
END$$
DELIMITER ;





