public class ArraySizeException extends ArrayCalculateException {

    public static final int ARRAY_SIZE_LIMIT = 4;
    ArraySizeException (String val) {
        super(String.format("Некорректный размер массива: %s, ожидал 4x4", val));
    };

}
