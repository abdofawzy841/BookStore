USE `bookstore`;
DROP function IF EXISTS `isManager`;
DELIMITER $$
USE `bookstore`$$
CREATE FUNCTION  isManager (userName varchar(45))
RETURNS boolean
BEGIN
		IF((SELECT is_manager from user where user_name = userName) = true) THEN
			return true;
		END IF;
	RETURN FALSE;
END$$
DELIMITER ;


USE `bookstore`;
DROP function IF EXISTS `login`;
DELIMITER $$
USE `bookstore`$$
CREATE FUNCTION login (userName varchar(45), user_password varchar(15))
RETURNS boolean
BEGIN
	IF userName in (select user_name from user) THEN
		IF user_password in (select password from user where user_name = userName) THEN
			RETURN TRUE;
		ELSE 
			RETURN FALSE;
        END IF;
	ELSE 
		RETURN FALSE;
	END IF;
END$$

DELIMITER ;


USE bookstore;
DROP function IF EXISTS signUp;
DELIMITER $$
USE bookstore$$
CREATE FUNCTION signUp (userName varchar(45), password varchar(15),
                     last_name varchar(45), first_name varchar(45), email varchar(45), 
                     phone varchar(12),shipping_address varchar(45))
 RETURNS boolean
BEGIN
    IF userName in (select user_name from user) THEN
		return false;
	ELSEIF email in (select email_address from user) THEN
		return false;
    ELSE 
    INSERT INTO user values(userName, password, last_name, first_name, email, phone,shipping_address,false);
            RETURN true;
    END IF;

END$$
DELIMITER ;

USE `bookstore`;
DROP procedure IF EXISTS `logout`;
DELIMITER $$
USE `bookstore`$$
CREATE PROCEDURE logout (userName varchar(45))
BEGIN
		DELETE FROM cart 
		WHERE user_name = userName;
END$$
DELIMITER ;

USE bookstore;
DROP procedure IF EXISTS getUser;
DELIMITER $$

USE bookstore$$
CREATE PROCEDURE getUser (userName varchar(45))
BEGIN
        SELECT*
        FROM user 
        WHERE user_name = userName;
END$$
DELIMITER ;