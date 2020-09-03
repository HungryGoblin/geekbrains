import java.util.Arrays;

public class Lesson2 {

    public static String assrt (String ex, boolean rslt) {
        return String.format("Задание %s: %s%n", ex, rslt? "+": "-");
    }

    // Тесты
    public static void main (String[] args) {

        int aSrc1[] = {1, 1, 0, 0, 1, 0, 1, 1, 0, 0};
        int aExp1[] = {0, 0, 1, 1, 0, 1, 0, 0, 1, 1};
        swa(aSrc1);
        System.out.printf(assrt("1", Arrays.equals(aSrc1, aExp1)));

        int aSrc2[] = new int[8];
        int aExp2 [] = {1, 4, 7, 10, 13, 16, 19, 22};
        inita(aSrc2, aExp2);
        System.out.printf(assrt("2", Arrays.equals(aSrc2, aExp2)));

        int aSrc3[] = {1, 5,  3, 2, 11, 4, 5,  2, 4, 8, 9, 1};
        int aExp3[] = {2, 10, 6, 4, 11, 8, 10, 4, 8, 8, 9, 2};
        multi2(aSrc3);
        System.out.printf(assrt("3", Arrays.equals(aSrc3, aExp3)));

        int aExp41[] = {1, 2, 3, 2,  0};
        int aExp42[] = {3, 2, 1, 0, -1};
        System.out.printf(assrt("4.1", maxVal(aExp41) == 3));
        System.out.printf(assrt("4.2", minVal(aExp41) == 0));
        System.out.printf(assrt("4.3", maxVal(aExp42) == 3));
        System.out.printf(assrt("4.4", minVal(aExp42) == -1));

        int [][] aSrc5 = new int [4][4];
        int aExp5[][] = {{1, 0, 0, 1},
                           {0, 1, 1, 0},
                           {0, 1, 1, 0},
                           {1, 0, 0, 1}};
        diag(aSrc5);
        System.out.printf(assrt("5", Arrays.deepEquals(aSrc5, aExp5)));

        int aExp61[] = {1, 1, 1, 2, 1};
        int aExp62[] = {2, 1, 1, 2, 1};
        int aExp63[] = {10, 1, 2, 3, 4};
        System.out.printf(assrt("6.1", checkBalance(aExp61) == true));
        System.out.printf(assrt("6.2", checkBalance(aExp62) == false));
        System.out.printf(assrt("6.3", checkBalance(aExp63) == true));

        int[] aSrcTmp;
        int[] aSrc71 = {1, 2, 3, 4, 5};
        int[] aExp71 = {3, 4, 5, 1, 2}; // -2
        aSrcTmp = aSrc71;
        dspl(aSrcTmp, -2);
        System.out.printf(assrt("7.2", Arrays.equals(aSrcTmp, aExp71)));
    }

    // 1 Задать целочисленный массив, состоящий из элементов 0 и 1. Например: [ 1, 1, 0, 0, 1, 0, 1, 1, 0, 0 ].
    // Написать метод, заменяющий в принятом массиве 0 на 1, 1 на 0;
    public static void swa (int a[]) {
        for (int i = 0; i < a.length; i++) a[i] = a[i] == 0? 1: 0;
    }

    // 2 Задать пустой целочисленный массив размером 8. Написать метод,
    // который помощью цикла заполнит его значениями 1 4 7 10 13 16 19 22;
    public static void inita (int a[], int b[]) {
        for (int i = 0; i < a.length; i++) a[i] = b[i];
    }

    // 3 Задать массив [ 1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1 ], написать метод,
    // принимающий на вход массив и умножающий числа меньше 6 на 2;
    public static void multi2(int a []) {
        for (int i = 0; i < a.length; i++)a[i] = a[i] < 6? a[i] * 2: a[i];
    }

    // 4 Задать одномерный массив. Написать методы поиска в нём минимального и максимального элемента;
    public static int maxVal(int a[]) {
        return minMax(a, true);
    }
    public static int minVal(int a[]) {
        return minMax(a, false);
    }
    public static int minMax(int a[], boolean tf) {
        int mm = a[0];
        for (int i = 1; i < a.length; i++) mm = tf?(mm < a[i]? a[i]: mm):(mm > a[i]? a[i]: mm);
        return mm;
    }

    // 5 * Создать квадратный целочисленный массив (количество строк и столбцов одинаковое), заполнить его диагональные
    // элементы единицами, используя цикл(ы);
    public static void diag(int a[][]) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) a[i][j] = ((i == j) || (j == a[i].length - i - 1)? 1 : 0);
        }
    }

    // 6 ** Написать метод, в который передается не пустой одномерный целочисленный массив, метод должен вернуть
    // true если в массиве есть место, в котором сумма левой и правой части массива равны. Примеры:
    // checkBalance([1, 1, 1, 2, 1]) → true, checkBalance ([2, 1, 1, 2, 1]) → false, checkBalance ([10, 1, 2, 3, 4]) → true.
    public static boolean checkBalance(int a[]) {
        int summ[] = new int[2];
        boolean bal = false;
        for (int i = 0; i < a.length; i++) summ[1] += a[i];
        for (int i = 0; i < a.length; i++) {
            bal = (summ[0] += a[i]) == (summ[1] -= a[i]);
            if (bal) break;
        }
        return bal;
    }

    // 7. **** Написать метод, которому на вход подается одномерный массив и число n (может быть положительным,
    // или отрицательным), при этом метод должен сместить все элементымассива на n позиций. Для усложнения задачи
    // нельзя пользоваться вспомогательными массивами.

    public static void dspl (int arr[], int n) {
            int len = arr.length;
            n = n % len;
            if (n < 0) n += len;
            for (int j = 0; j < n; j++) {
                int tmp = arr[len - 1];
                for (int i = 0; i < len; i++) {
                    int cur = arr[i];
                    arr[i] = tmp;
                    tmp = cur;
                }
            }
        }
    }
