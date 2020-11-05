-- Посчитать количество сотрудников во всех отделах.

USE employees;

SELECT departments.dept_name, COUNT(*)
FROM   dept_emp, departments
WHERE  departments.dept_no = dept_emp.dept_no
GROUP BY dept_emp.dept_no;

