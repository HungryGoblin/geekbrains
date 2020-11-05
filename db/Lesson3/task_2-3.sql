-- Удалить одного сотрудника, у которого максимальная зарплата.

DELETE employees
FROM   employees, salaries 
WHERE  employees.emp_no = salaries.emp_no AND salaries.salary IN (
       SELECT MAX(salaries.salary)
       FROM salaries);
