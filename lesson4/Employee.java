package lesson4;
public class Employee {

    private static final double MIDDLE_AGE_BONUS = 5000;
    private static final int MIDDLE_AGE = 45;
    private static int staffCounter = 0;

    private String name;
    private double salary;
    private int age;
    private final int id;

    public final String getName () {return name;}
    public final double getSalary () {return salary;}
    public final int getAge () {return age;}
    public int getId() {return id;}

    public static void addSalaryByAge(Employee employee, int age, double rate) {
        if (employee.age > age) employee.addSalary(rate);
    }

    public static void middleAgeBonus (Employee[] staff) {
        for (int i = 1; i < staff.length; i++) {
            if (staff[i].age > MIDDLE_AGE) staff[i].addSalary(MIDDLE_AGE_BONUS);
        }
    }

    public void addSalary(double rate) {
        this.salary += rate;
    }

    public static double getAverageAge (Employee[] staff) {
        int totalAge = 0;
        for (int i = 1; i < staff.length; i++) {
            totalAge += staff[i].age;
        }
        return (staff.length > 0)? totalAge / (double)staff.length: 0;
    }

    public static double getAverageSalary (Employee[] staff) {
        double totalSalary = 0;
        for (int i = 1; i < staff.length; i++) {
            totalSalary += staff[i].salary;
        }
        return (staff.length > 0)? totalSalary / (double)staff.length: 0;
    }

    {
        staffCounter++;
    }

    Employee (String name, double salary, int age) {
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.id = staffCounter;
    }

}
