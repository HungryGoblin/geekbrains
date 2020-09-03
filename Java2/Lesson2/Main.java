import java.util.Arrays;
import java.util.Locale;

public class Main {
    public static final char CRLF = '\n';
    public static final char SEP = ' ';

    public static void main(String[] args) {
        // без ошибок
        try {
            String[][] stringArray = getStringArray("10 3 1 2\n2 3 2 2\n5 6 7 1\n300 3 1 0");
            System.out.println(Arrays.deepToString(stringArray));
            double calcVal = calculate(stringArray);
            System.out.printf(Locale.ENGLISH, "РЕЗУЛЬТАТ: %.3f%n", calcVal);
        } catch (Exception e) {
            System.out.printf("** ОШИБКА: %s (%s) %n", e.getMessage(), e.getCause());
            e.printStackTrace();
        }
        // ошибка размера
        try {
            String[][] stringArray = getStringArray("10 3 1 2 4\n2 3 2 2\n5 6 7 1\n300 3 1 0");
            System.out.println(Arrays.deepToString(stringArray));
            double calcVal = calculate(stringArray);
            System.out.printf(Locale.ENGLISH, "РЕЗУЛЬТАТ: %.3f", calcVal);
        } catch (Exception e) {
            System.out.printf("** ОШИБКА: %s (%s) %n", e.getMessage(), e.getCause());
            e.printStackTrace();
        }
        // ошибка типа
        try {
            String[][] stringArray = getStringArray("10 ошибка 1 2\n2 3 2 2\n5 6 7 1\n300 3 1 0");
            System.out.println(Arrays.deepToString(stringArray));
            double calcVal = calculate(stringArray);
            System.out.printf(Locale.ENGLISH, "РЕЗУЛЬТАТ: %.3f", calcVal);
        } catch (Exception e) {
            System.out.printf("** ОШИБКА: %s (%s) %n", e.getMessage(), e.getCause());
            e.printStackTrace();
        }
    }

    public static String[][] getStringArray(String s) throws ArrayCalculateException {
        String[][] retArr;
        int[] arrSize = new int[2];
        String[] line = s.split(String.valueOf(CRLF));
        arrSize[0] =  line.length;
        if (arrSize[0] != ArraySizeException.ARRAY_SIZE_LIMIT)
            throw new ArraySizeException(String.valueOf(String.format ("%sx%s",
                    String.valueOf(arrSize[0]), String.valueOf(arrSize[1]))));
        retArr = new String[line.length][line[0].length()];
        for (int i = 0; i < line.length; i++) {
            String[] symbol = line[i].split(String.valueOf(SEP));
            arrSize[1] = symbol.length;
            if (arrSize[1] != ArraySizeException.ARRAY_SIZE_LIMIT)
                throw new ArraySizeException(String.format ("%sx%s",
                        String.valueOf(arrSize[0]), String.valueOf(arrSize[1])));
            retArr[i] = symbol;
        }
        return retArr;
    }

    public static double calculate(String[][] srcArr) throws ArrayCalculateException {
        int summ = 0;
        String arrEl = "";
        for (int i = 0; i < srcArr.length; i++) {
            for (int j = 0; j < srcArr.length; j++) {
                try {
                    arrEl = srcArr[i][j];
                    summ += Integer.parseInt(arrEl);
                } catch (RuntimeException e) {
                    throw new ArrayTypeException(arrEl);
                }
            }
        }
        return summ / 2f;
    }
}
