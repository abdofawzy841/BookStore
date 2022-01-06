USE `bookstore`;
DROP procedure IF EXISTS `addNewBook`;
DELIMITER $$
USE `bookstore`$$
CREATE procedure addNewBook(ISBN INTEGER ,title VARCHAR(45) ,
							publisher_id INTEGER,publication_year year,
                            price double,category_id integer,
                            min_quantity INTEGER , quantity integer)
BEGIN
			INSERT INTO BOOK 
            VALUES( ISBN,title,publisher_id,publication_year,
					price ,category_id,min_quantity ,quantity);
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
                            min_quantity =new_quantity , 
                            cur_quantity =new_quantity
						WHERE ISBN = book_ISBN;
           return true;             
        END IF;
	return false;
END$$
DELIMITER ;



USE `bookstore`;
DROP procedure IF EXISTS `orderBooks`;
DELIMITER $$
USE `bookstore`$$
CREATE PROCEDURE orderBooks (book_ISBN INTEGER, quantity INTEGER)
BEGIN
		IF(book_ISBN in (SELECT ISBN from book))THEN
		   INSERT INTO orders (book_ISBN,copies_num) VALUES(book_ISBN, quantity);
        END IF;
END$$
DELIMITER ;



USE `bookstore`;
DROP procedure IF EXISTS `confirmOrder`;
DELIMITER $$
USE `bookstore`$$
CREATE PROCEDURE confirmOrder (order_id INTEGER)
BEGIN
		   DELETE
           FROM orders
           WHERE id = order_id;
END$$
DELIMITER ;


USE `bookstore`;
DROP procedure IF EXISTS `setManager`;
DELIMITER $$
USE `bookstore`$$
CREATE PROCEDURE setManager (manager_user_name varchar(45))
BEGIN
		IF manager_user_name IN (SELECT user_name FROM user) THEN
			update user set is_manager = true where user_name = manager_user_name;
        END IF;
END$$
DELIMITER ;


/*
* The total sales for books in the previous month
*/
USE `bookstore`;
DROP procedure IF EXISTS `totalBooksSales`;
DELIMITER $$
USE `bookstore`$$
CREATE PROCEDURE totalBooksSales()
BEGIN
			SELECT b.title, s.number_sales from book b
            inner join 
           ( 
				SELECT book_ISBN, sum(num_copies) number_sales 
                FROM sale 
                WHERE sale_time  BETWEEN now() AND DATE_FORMAT(NOW() - INTERVAL 1 MONTH, '%Y-%m-%d 00:00:00')
				GROUP BY book_ISBN
			) s  
			on s.book_ISBN = b.ISBN;
END$$
DELIMITER ;


/*
* The top 5 customers
*/

USE `bookstore`;
DROP procedure IF EXISTS `topCustomers`;
DELIMITER $$
USE `bookstore`$$
CREATE PROCEDURE topCustomers ()
BEGIN
		SELECT user_name, sum(sale_price) purchace_amount
        FROM sale
        WHERE sale_time 
						BETWEEN DATE_FORMAT(NOW() - INTERVAL 3 MONTH, '%Y-%m-01 00:00:00')
					     AND DATE_FORMAT(LAST_DAY(NOW() - INTERVAL 3 MONTH), '%Y-%m-%d 23:59:59')
		GROUP BY user_name 
        order by purchace_amount
		limit 5;
END$$
DELIMITER ;


/*
* The top 10 selling books 
*/

USE `bookstore`;
DROP procedure IF EXISTS `topSellingBooks`;
DELIMITER $$
USE `bookstore`$$
CREATE PROCEDURE topSellingBooks ()
BEGIN
           SELECT  b.title, s.number_sales from book b
           join
			(SELECT book_ISBN, sum(num_copies) number_sales 
		 	FROM sale 
			 WHERE sale_time 
					BETWEEN DATE_FORMAT(NOW() - INTERVAL 3 MONTH, '%Y-%m-01 00:00:00')
					AND DATE_FORMAT(LAST_DAY(NOW() - INTERVAL 3 MONTH), '%Y-%m-%d 23:59:59')
			GROUP BY book_ISBN 
            ORDER BY number_sales
			LIMIT 10 )s on s.book_ISBN = b.ISBN;
           
END$$
DELIMITER ;





