ALTER TABLE `employees`.`salaries` 
ADD INDEX `idx_to_date` (`to_date` ASC),
ADD INDEX `idx_from_date` (`from_date` ASC);

ALTER TABLE `employees`.`employees` 
ADD INDEX `idx_hire_darte` (`hire_date` ASC);
