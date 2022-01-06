
USE `bookstore`;
DROP procedure IF EXISTS `searchForBookByISBN`;
DELIMITER $$
USE `bookstore`$$
create procedure searchForBookByISBN (search_ISBN integer)
BEGIN
  select * 
  from book 
  where search_ISBN =ISBN;
END$$
DELIMITER ;



USE `bookstore`;
DROP procedure IF EXISTS `searchForBookByTitle`;
DELIMITER $$
USE `bookstore`$$
create procedure searchForBookByTitle (search_title VARCHAR(45))
BEGIN
  select * 
  from book 
  where search_title =title;
END$$
DELIMITER ;


USE `bookstore`;
DROP procedure IF EXISTS `searchForBookByCategory`;
DELIMITER $$
USE `bookstore`$$
create procedure searchForBookByCategory (categoryName VARCHAR(45))
BEGIN
  select * 
  from book 
  where category_id = (select id 
					  from category
                      where name = categoryName);
END$$
DELIMITER ;


USE `bookstore`;
DROP procedure IF EXISTS `searchForBookByAuthor`;
DELIMITER $$
USE `bookstore`$$
create procedure searchForBookByAuthor (authorName VARCHAR(45))
BEGIN
select*
FROM book
Where ISBN in(select book_ISBN
               from book_author
               where author_id in (select id
                                    from author
                                    where name=authorName));

END$$
DELIMITER ;



USE `bookstore`;
DROP procedure IF EXISTS `searchForBookByPublisher`;
DELIMITER $$
USE `bookstore`$$
create procedure searchForBookByPublisher (publisherName VARCHAR(45))
BEGIN
  select * 
  from book 
  where publisher_id = (select publisher_id 
					  from publisher
                      where name = publisherName);
END$$
DELIMITER ;



/*
	Edit user information
*/

USE `bookstore`;
DROP procedure IF EXISTS `editUserInfo`;
DELIMITER $$
USE `bookstore`$$
CREATE PROCEDURE editUserInfo(userName varchar(45),
					 new_user_name varchar(45), new_password varchar(15),
					 new_last_name varchar(45), new_first_name varchar(45), new_email varchar(45), 
					 new_phone varchar(12),new_shipping_address varchar(45))
BEGIN
			UPDATE user
			SET user_name = new_user_name,
				password = new_password, 
				last_name = new_last_name,
				first_name = new_first_name,
				email_address = new_email,
                phone = new_phone,
				shipping_address = new_shipping_address
			WHERE user_name = userName;
END$$
DELIMITER ;




/*
	add books to shopping cart
*/
USE `bookstore`;
DROP function IF EXISTS `addBookToShoppingCart`;
DELIMITER $$
USE `bookstore`$$
CREATE FUNCTION addBookToShoppingCart (userName varchar(45),bookISBN INTEGER,numofbooks INTEGER)
RETURNS BOOLEAN
BEGIN
		IF bookNumber in (select ISBN from book) THEN
			INSERT INTO cart (book_ISBN ,user_name,num_copies) VALUES (bookISBN, userName,numofbooks);
            RETURN TRUE;
		END IF;
RETURN FALSE;
END$$
DELIMITER ;



/*
	view items carts
*/
USE `bookstore`;
DROP procedure IF EXISTS `viewCartItems`;
DELIMITER $$
USE `bookstore`$$
CREATE PROCEDURE viewCartItems(userName varchar(45))
BEGIN
		SELECT b.* FROM book b 
        JOIN ( 
			SELECT book_ISBN 
            FROM cart
            WHERE user_name = userName
            ) s
		ON B.ISBN = s.book_ISBN;
END$$
DELIMITER ;



/*
* view Book Cart Price
*/
USE `bookstore`;
DROP procedure IF EXISTS `viewBookCartPrice`;
DELIMITER $$
USE `bookstore`$$
CREATE PROCEDURE viewBookCartPrice (userName varchar(45), bookISBN INTEGER)
BEGIN
		IF( bookISBN in (
				SELECT book_ISBN FROM carT 
                WHERE user_name = userName)
			) THEN
				SELECT price 
                FROM book 
                WHERE ISBN = bookISBN;
			END IF;
END$$
DELIMITER ;



/*
* view total Cart Price
*/
USE `bookstore`;
DROP procedure IF EXISTS `viewCartPrice`;
DELIMITER $$
USE `bookstore`$$
CREATE PROCEDURE viewCartPrice (userName varchar(45))
BEGIN
			SELECT SUM(b.price) total_price 
            FROM book b 
            JOIN (
				SELECT book_ISBN 
                FROM cart
                WHERE user_name = userName
            ) s 
            ON s.book_ISBN = b.ISBN ;
END$$
DELIMITER ;

/*
* Remove items from the cart
*/
USE `bookstore`;
DROP function IF EXISTS `removeItemFromCart`;
DELIMITER $$
USE `bookstore`$$
CREATE FUNCTION removeItemFromCart (userName varchar(45), bookISBN INTEGER)
RETURNS BOOLEAN
BEGIN
		IF( bookISBN in (
				SELECT book_ISBN FROM cart
                WHERE user_name = userName)
			) THEN
				DELETE
                FROM cart
                WHERE user_name = userName AND book_ISBN = bookISBN;
			return true;
			END IF;
return false;
END$$
DELIMITER ;

/*
* buy book
*/
USE `bookstore`;
DROP procedure IF EXISTS `buyBook`;
DELIMITER $$
USE `bookstore`$$
CREATE PROCEDURE `buyBook`(userName varchar(45),
							bookISBN INTEGER, book_price double,CardNumber varchar(16),expireDate DATE)
BEGIN
	DECLARE copies INTEGER;
		IF(bookISBN in (SELECT ISBN from book)) THEN
			SET copies = (
            select num_copies 
            from cart 
            where user_name = userName and book_ISBN = bookISBN limit 1
            );
			insert into sale values (bookISBN,CardNumber,userName,now(),
									  copies,expireDate);
            update book set cur_quantity = cur_quantity - copies where ISBN = bookISBN;
            DELETE FROM cart where user_name = userName AND book_ISBN=bookISBN ;
        END IF;
END$$
DELIMITER ;





