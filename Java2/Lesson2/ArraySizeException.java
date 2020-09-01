public class ArraySizeException extends ArrayCalculateException {

    public static final int ARRAY_SIZE_LIMIT = 4;
    ArraySizeException () {
        super("Некорректный размер массива, ожидал 4x4");
    };

}
