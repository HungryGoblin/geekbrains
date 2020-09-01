public class ArrayTypeException extends ArrayCalculateException {

    ArrayTypeException (String val) {
        super(String.format("Некорректный тип элемента массива: %s, ожидал int", val));
    };

}
