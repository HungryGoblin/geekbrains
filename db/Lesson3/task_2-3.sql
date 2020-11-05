-- Удалить одного сотрудника, у которого максимальная зарплата.

DELETE employees
FROM   employees, salaries 
WHERE  employees.emp_no = salaries.emp_no AND salaries.salary IN (
       SELECT MAX(salaries.salary)
       FROM salaries
       WHERE salaries.from_date <= CURDATE() AND
             salaries.to_date > CURDATE());
