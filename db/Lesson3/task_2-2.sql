-- ������� ������������ �������� � ����������.

USE employees;

SELECT employees.last_name, employees.first_name, max(salaries.salary) 
FROM   employees, salaries
WHERE  employees.emp_no = salaries.emp_no AND
       salaries.from_date <= CURDATE()    AND
       salaries.to_date   > CURDATE();
