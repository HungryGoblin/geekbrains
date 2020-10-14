import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class Lesson6Test {
    @Test
    public void splitArrayTest() {
        Assertions.assertArrayEquals(new int[] {5}, Lesson6.splitArray(new int[]{1,2,3,4,5}));
        Assertions.assertArrayEquals(new int[]{3,2,4}, Lesson6.splitArray(new int[]{4,3,2,4}));
        Assertions.assertArrayEquals(null, Lesson6.splitArray(new int[]{1,2,3,4}));
        Assertions.assertThrows(RuntimeException.class, () -> Lesson6.splitArray(new int[]{1,2,3}));
    }

    @Test
    public void arrayContainsNumbersTest() {
        Assertions.assertFalse(Lesson6.arrayContainsNumbers(new int[]{0, 2, 3}));
        Assertions.assertFalse(Lesson6.arrayContainsNumbers(new int[]{1, 2, 3}));
        Assertions.assertFalse(Lesson6.arrayContainsNumbers(new int[]{4}));
        Assertions.assertTrue(Lesson6.arrayContainsNumbers(new int[]{1,3,2,4}));
        Assertions.assertTrue(Lesson6.arrayContainsNumbers(new int[]{0,2,1,4,5}));
    }

    @Test
    public void arrayContainsNumbersBSTest() {
        Assertions.assertFalse(Lesson6.arrayContainsNumbersBS(new int[]{0, 2, 3}));
        Assertions.assertFalse(Lesson6.arrayContainsNumbersBS(new int[]{1, 2, 3}));
        Assertions.assertFalse(Lesson6.arrayContainsNumbersBS(new int[]{4}));
        Assertions.assertTrue(Lesson6.arrayContainsNumbersBS(new int[]{1,3,2,4}));
        Assertions.assertTrue(Lesson6.arrayContainsNumbersBS(new int[]{0,2,1,4,5}));
    }
}
