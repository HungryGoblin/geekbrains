import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
	    String [] arr = {"0","1"};
	    swapArrEl(arr);
	    System.out.println(Arrays.deepToString(arr));

	    ArrayList<?> arrList = getArrayList(arr);
        System.out.println(Arrays.deepToString(arrList.toArray()));

        Apple apple = new Apple();
        Box appleBox1 = new Box();
        for (int i = 0; i < 5; i++) {
            appleBox1.put(apple);
        }
        System.out.println(appleBox1.getWeight());

        Box appleBox2 = new Box();
        for (int i = 0; i < 7; i++) {
            appleBox2.put(apple);
        }
        appleBox1.put(appleBox2);
        System.out.println(appleBox1.getWeight());

        Orange orange = new Orange();
        Box orangeBox = new Box();
        for (int i = 0; i < 5; i++) {
            orangeBox.put(orange);
        }
        System.out.println(orangeBox.getWeight());


        orangeBox.put(apple);
        orangeBox.put(appleBox1);
    }

    //1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
    public static void swapArrEl(Object[] arr) {
        Object tmpEl;
        if (arr.length < 2) throw new RuntimeException(String.format(
                "Unexpected array length: %d (expected 2 or more)", arr.length));
        tmpEl = arr[0];
        arr[0] = arr[1];
        arr[1] = tmpEl;
    }

    //2. Написать метод, который преобразует массив в ArrayList;
    public static ArrayList<?> getArrayList(Object[] arr) {
        ArrayList<Object> arrLst = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            arrLst.add(arr[i]);
        }
        return arrLst;
    }

}

