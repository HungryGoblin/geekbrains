-- Выбрать среднюю зарплату по отделам.

USE employees;

SELECT departments.dept_name, AVG(salaries.salary)
FROM   dept_emp, departments, employees, salaries
WHERE  salaries.emp_no = employees.emp_no AND 
       dept_emp.emp_no = employees.emp_no AND
       departments.dept_no = dept_emp.dept_no
GROUP BY dept_emp.dept_no;
