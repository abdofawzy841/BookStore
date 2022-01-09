USE `bookstore`;
SET GLOBAL log_bin_trust_function_creators = 1;
DROP function IF EXISTS `addNewBook`;
DELIMITER $$
USE `bookstore`$$
CREATE function addNewBook(ISBN INTEGER ,title VARCHAR(45) ,
							publisher_id INTEGER,publication_year year,
                            price double,category_id integer,
                            min_quantity INTEGER , quantity integer, author_id int)
                            returns boolean
BEGIN
			INSERT INTO BOOK 
            VALUES( ISBN,title,publisher_id,publication_year,
					price ,category_id,min_quantity ,quantity);
			Insert into book_author values (ISBN,author_id);
			return true;
END$$
DELIMITER ;

USE `bookstore`;
DROP function IF EXISTS `modifyBook`;
DELIMITER $$
USE `bookstore`$$
CREATE FUNCTION modifyBook (book_ISBN INTEGER ,new_title VARCHAR(45) ,
							new_publisher_id INTEGER,new_publication_year year,
                            new_price double,new_category_id integer,
                            new_min_quantity INTEGER , new_quantity integer)
RETURNS boolean
BEGIN
		IF(book_ISBN in (SELECT ISBN from book))THEN
		   UPDATE book SET title =  new_title,
							publisher_id = new_publisher_id,
                            publication_year = new_publication_year,
                            price = new_price,
                            category_id =new_category_id,
                            min_quantity =new_min_quantity , 
                            cur_quantity =new_quantity
						WHERE ISBN = book_ISBN;
           return true;   
           
        END IF;
	return false;
END$$
DELIMITER ;



USE `bookstore`;
DROP function IF EXISTS `orderBooks`;
DELIMITER $$
USE `bookstore`$$
CREATE function orderBooks (book_ISBN INTEGER, quantity INTEGER) returns boolean
BEGIN
		IF(book_ISBN in (SELECT ISBN from book))THEN
		   INSERT INTO orders (book_ISBN,copies_num) VALUES(book_ISBN, quantity);
           return true;
        END IF;
        return false;
END$$
DELIMITER ;



USE `bookstore`;
DROP function IF EXISTS `confirmOrder`;
DELIMITER $$
USE `bookstore`$$
CREATE function confirmOrder (order_id INTEGER) returns boolean
BEGIN
			DELETE
			FROM orders
			WHERE id = order_id;
			return true;
END$$
DELIMITER ;

USE `bookstore`;
DROP procedure IF EXISTS `getOrders`;
DELIMITER $$
USE `bookstore`$$
CREATE PROCEDURE getOrders ()
BEGIN
	select *
    from orders;
END$$
DELIMITER ;


USE `bookstore`;
DROP function IF EXISTS `promote`;
DELIMITER $$
USE `bookstore`$$
CREATE function promote (manager_user_name varchar(45)) returns boolean
BEGIN
		IF manager_user_name IN (SELECT user_name FROM user) THEN
			update user set is_manager = true where user_name = manager_user_name;
            return true;
        END IF;
        return false;
END$$
DELIMITER ;

USE `bookstore`;
DROP function IF EXISTS `demote`;
DELIMITER $$
USE `bookstore`$$
CREATE function demote (customer_user_name varchar(45)) returns boolean
BEGIN
		IF customer_user_name IN (SELECT user_name FROM user) THEN
			update user set is_manager = false where user_name = customer_user_name;
            return true;
        END IF;
        return false;
END$$
DELIMITER ;


/*
* The total sales for books in the previous month
*/
USE bookstore;
DROP procedure IF EXISTS totalBooksSales;
DELIMITER $$
USE bookstore$$
CREATE PROCEDURE totalBooksSales()
BEGIN
declare number_sales int;
            SELECT s.number_sales,b.title from book b
            inner join 
           ( 
                SELECT book_ISBN, sum(num_copies) number_sales 
                FROM sale 
                WHERE sale_time  >= DATE_ADD(NOW(),INTERVAL -30 DAY)
                GROUP BY book_ISBN
                			Order BY number_sales DESC
            ) s
            on s.book_ISBN = b.ISBN;
            
END$$
DELIMITER ;

/*
* The top 5 customers
*/

USE bookstore;
DROP procedure IF EXISTS `topCustomers`;
DELIMITER $$
USE bookstore$$
CREATE PROCEDURE topCustomers ()
BEGIN
        SELECT sum(sale_price), user_name
        FROM sale
        WHERE sale_time >= DATE_ADD(NOW(),INTERVAL -90 DAY)
        GROUP BY user_name 
        order by sum(sale_price) DESC
        limit 5;
END$$
DELIMITER ;


/*
* The top 10 selling books 
*/

USE bookstore;
DROP procedure IF EXISTS topSellingBooks;
DELIMITER $$
USE bookstore$$
CREATE PROCEDURE topSellingBooks ()
BEGIN
           SELECT   s.number_sales, b.title from book b
           join
            (SELECT book_ISBN, sum(num_copies) number_sales 
             FROM sale 
             WHERE sale_time >= DATE_ADD(NOW(),INTERVAL -30 DAY)
            GROUP BY book_ISBN 
            ORDER BY number_sales DESC
            LIMIT 10 )s on s.book_ISBN = b.ISBN;

END$$
DELIMITER ;

USE bookstore;
DROP procedure IF EXISTS getCategories;
DELIMITER $$
USE bookstore$$
CREATE PROCEDURE getCategories ()
BEGIN
        SELECT *
        FROM category; 
END$$
DELIMITER ;

USE bookstore;
DROP procedure IF EXISTS getPublishers;
DELIMITER $$
USE bookstore$$
CREATE PROCEDURE getPublishers ()
BEGIN
        SELECT *
        FROM publisher; 
END$$
DELIMITER ;

USE bookstore;
DROP procedure IF EXISTS getAuthors;
DELIMITER $$
USE bookstore$$
CREATE PROCEDURE getAuthors ()
BEGIN
        SELECT *
        FROM author; 
END$$
DELIMITER ;

USE bookstore;
DROP procedure IF EXISTS getBook;
DELIMITER $$
USE bookstore$$
CREATE PROCEDURE getBook (book_isbn int)
BEGIN
        SELECT *
        FROM book
        where ISBN = book_isbn;
END$$
DELIMITER ;





