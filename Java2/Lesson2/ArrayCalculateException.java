import java.io.Serializable;

public class ArrayCalculateException extends RuntimeException {
    public static String TITLE = "Ошибка пересчета массива";

    public ArrayCalculateException(String s) {
        super(String.format("%s: %s", TITLE, s));
    }

}
