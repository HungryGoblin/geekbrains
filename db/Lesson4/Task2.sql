-- 2. Создать функцию, которая найдет менеджера по имени и фамилии.

CREATE DEFINER=`root`@`localhost` FUNCTION `Get_Manager`(firstName VARCHAR(32) CHARACTER SET utf8, lastName VARCHAR(32) CHARACTER SET utf8) RETURNS int
    DETERMINISTIC
BEGIN
DECLARE emp_no INT;
SELECT employees.emp_no INTO emp_no
    FROM employees
    WHERE employees.first_name = firstName  AND employees.last_name = lastName;
RETURN emp_no;
END
