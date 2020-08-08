package lesson4;

import java.util.Locale;
import java.util.Random;

public class Main {

    private static final Random RANDOM = new Random();
    private static final String[] L_NAMES = new String[]{"Пьяных", "Добробаба", "Рукосуева", "Вынь-полож", "Сухозад"};
    private static final String[] F_NAMES = new String[]{"Трактор", "Дрезина", "Даздраперма", "Лентробух", "Комунара"};
    private static final String[] F2_NAMES = new String[]{"Гайкович", "Петросян", "Шмулевич", "Шаймардяковна", "Срулевич"};
    private static final int[] AGE_BOUNDS = new int[]{18, 65};
    private static final double[] SALLARY_BOUNDS = new double[]{5000, 100000};


    public static void main(String[] args) {

        // Вывести при помощи методов из пункта 3 ФИО и возраст.
        Employee employee = new Employee("Иванов Иван Иванович", 30000, 65);
        System.out.printf("Имя: %s, Зарплата: %d%n", employee.getName(), employee.getAge());

        Employee[] circusStaff = new Employee[5];
        for (int i = 0; i < circusStaff.length; i++) {
            circusStaff[i] = new Employee(
                    String.format("%s %s %s",
                            L_NAMES[RANDOM.nextInt(L_NAMES.length)],
                            F_NAMES[RANDOM.nextInt(F_NAMES.length)],
                            F2_NAMES[RANDOM.nextInt(F2_NAMES.length)]),
                    SALLARY_BOUNDS[0] + RANDOM.nextInt((int) SALLARY_BOUNDS[1] -
                            (int) SALLARY_BOUNDS[0]) - RANDOM.nextFloat(),
                    AGE_BOUNDS[0] + RANDOM.nextInt(AGE_BOUNDS[1]));
        }

        //Создать массив из 5 сотрудников. С помощью цикла вывести информацию только о сотрудниках старше 40 лет;
        for (int i = 0; i < circusStaff.length; i++) {
            if (circusStaff[i].getAge() > 40)
                System.out.printf("Сорудник: %s, возраст: %s%n", circusStaff[i].getName(), circusStaff[i].getAge());
        }

        //* Создать метод, повышающий зарплату всем сотрудникам старше 45 лет на 5000.
        Employee.middleAgeBonus(circusStaff);

        //* Подсчитать средние арифметические зарплаты и возраста
        System.out.printf("Средний возраст: %2.0f%n", Employee.getAverageAge(circusStaff));
        System.out.printf(Locale.ENGLISH, "Средний оклад: %5.2f%n", Employee.getAverageSalary(circusStaff));

        //*** Продумать конструктор таким образом, чтобы при создании каждому сотруднику присваивался личный
        //уникальный идентификационный порядковый номер
        for (int i = 0; i < circusStaff.length; i++) {
            System.out.printf(Locale.ENGLISH,"Сорудник #%d: %s, возраст: %s, оклад %5.2f%n",
                    circusStaff[i].getId(), circusStaff[i].getName(),
                    circusStaff[i].getAge(), circusStaff[i].getSalary());
        }

    }
}


