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
        String[] line = s.split(String.valueOf(CRLF));
        if (line.length > ArraySizeException.ARRAY_SIZE_LIMIT)
            throw new ArraySizeException();
        retArr = new String[line.length][line[0].length()];
        for (int i = 0; i < line.length; i++) {
            String[] symbol = line[i].split(String.valueOf(SEP));
            if (symbol.length > ArraySizeException.ARRAY_SIZE_LIMIT)
                throw new ArraySizeException();
            retArr[i] = symbol;
        }
        return retArr;
    }

    public static double calculate(String[][] srcArr) throws ArrayCalculateException {
        int summ = 0;
        for (int i = 0; i < srcArr.length; i++) {
            for (int j = 0; j < srcArr.length; j++) {
                try {
                    summ += Integer.parseInt(srcArr[i][j]);
                } catch (RuntimeException e) {
                    throw new ArrayTypeException();
                }
            }
        }
        return summ / 2f;
    }
}
