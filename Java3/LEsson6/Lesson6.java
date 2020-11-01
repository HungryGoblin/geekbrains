import com.sun.istack.internal.NotNull;
import java.util.Arrays;

public class Lesson6 {

    private static final int DEFAULT_SPLIT_NUM = 4;

    public static int[] splitArray(@NotNull int[] srcArr) {
        int[] dstArr = null;
        boolean containsNum = false;
        for (int i = 0; i < srcArr.length; i++) {
            if (srcArr[i] == DEFAULT_SPLIT_NUM) {
                containsNum = true;
                if (i < srcArr.length - 1) dstArr = Arrays.copyOfRange(srcArr, i + 1, srcArr.length);
                break;
            }
        }
        if (!containsNum) throw new RuntimeException(String.format("Array should contain %d", DEFAULT_SPLIT_NUM));
        return dstArr;
    }

    public static boolean arrayContainsNumbers (@NotNull int[] srcArr) {
        boolean f1 = false;
        boolean f4 = false;
        for (int i = 0; i < srcArr.length; i++) {
            if (srcArr[i] == 1) f1 = true;
            else if (srcArr[i] == 4) f4 = true;
        }
        return f1 && f4;
    }

    public static boolean arrayContainsNumbersBS(@NotNull int[] srcArr) {
        return Arrays.binarySearch(srcArr, 1) >= 0 && Arrays.binarySearch(srcArr, 4) >= 0;
    }

}
