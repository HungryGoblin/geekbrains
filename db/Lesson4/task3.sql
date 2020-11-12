CREATE DEFINER=`root`@`localhost` TRIGGER `employees_AFTER_INSERT` AFTER INSERT ON `employees` FOR EACH ROW 
BEGIN
    INSERT INTO emplyees.salaries (emp_no, salary, from_date) 
    VALUES (NEW.emp_no, 100, CURDATE());
END