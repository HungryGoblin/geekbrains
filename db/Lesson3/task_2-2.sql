-- Выбрать максимальную зарплату у сотрудника.

USE employees;

SELECT employees.last_name, employees.first_name, max(salaries.salary) 
FROM   employees, salaries
WHERE  employees.emp_no = salaries.emp_no;
