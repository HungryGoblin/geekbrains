-- ������� ������� �������� �� �������.

USE employees;

SELECT departments.dept_name, AVG(salaries.salary)
FROM   dept_emp, departments, employees, salaries
WHERE  salaries.emp_no = employees.emp_no AND 
       salaries.from_date <= CURDATE()    AND
       salaries.to_date > CURDATE()       AND
       dept_emp.emp_no = employees.emp_no AND
       dept_emp.from_date <= CURDATE()    AND
       dept_emp.to_date > CURDATE()       AND
       departments.dept_no = dept_emp.dept_no
GROUP BY dept_emp.dept_no;
