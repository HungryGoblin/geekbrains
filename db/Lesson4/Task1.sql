-- 1. Создать VIEW на основе запросов, которые вы сделали в ДЗ к уроку 3.

CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `employees`.`max_salary` AS
    SELECT 
        `employees`.`employees`.`last_name` AS `last_name`,
        `employees`.`employees`.`first_name` AS `first_name`,
        MAX(`employees`.`salaries`.`salary`) AS `max(salaries.salary)`
    FROM
        (`employees`.`employees`
        JOIN `employees`.`salaries`)
    WHERE
        ((`employees`.`employees`.`emp_no` = `employees`.`salaries`.`emp_no`)
            AND (`employees`.`salaries`.`from_date` <= CURDATE())
            AND (`employees`.`salaries`.`to_date` > CURDATE()))
