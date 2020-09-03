public class Lesson1 {

    // Тесты
    public static void main (String[] args) {
        System.out.println(calc(2, 10, 25, 4) == 32.5);
        try {
            calc(2, 10, 20, 0);
        } catch (Exception e) {
            System.out.println(e.getMessage().equals("Некорректное значение входного параметра (d): 0"));
        }
        System.out.println(!(bounds(4, 5) && bounds(10, 11)) && bounds(10, 10));
        System.out.println(greeting("Вася"));
        int y = 2000;
        System.out.printf("Год %d: %S%n", y, isLeapYear(y) ? "високосный" : "не високосный");
        System.out.println(isLeapYear(2020) && !isLeapYear(2019) &&
                isLeapYear(2000) && !isLeapYear(1900));
    }

    // Написать метод вычисляющий выражение a * (b + (c / d)) и возвращающий результат с плавающей точкой,
    // где a, b, c, d – целочисленные входные параметры этого метода
    private static double calc(int a, int b, int c, int d) {
        if (d != 0)
            return (a * (b + (c / (float)d)));
        else {
            throw new IllegalArgumentException("Некорректное значение входного параметра (d): 0");
        }
    }

    // Написать метод, принимающий на вход два целых числа, и проверяющий что их сумма лежит в пределах
    // от 10 до 20(включительно), если да – вернуть true, в противном случае – false;
    private static boolean bounds(int a, int b) {
        int s = a + b;
        return s >= 10 && s <= 20;
    }

    // Написать метод, которому в качестве параметра передается строка, обозначающая имя,
    // метод должен вернуть приветственное сообщение «Привет, переданное_имя!»;
    // Вывести приветствие в консоль.
    private static String greeting(String name) {
        return String.format("Привет, %s!", name);
    }

    // Написать метод, который определяет является ли год високосным. Каждый 4-й год является високосным,
    // кроме каждого 100-го, при этом каждый 400-й – високосный.
    // Для проверки работы вывести результаты работы метода в консоль
    private static boolean isLeapYear(int y) {
        return (y % 4 == 0 && y % 100 != 0) || y % 400 == 0;
    }

}
