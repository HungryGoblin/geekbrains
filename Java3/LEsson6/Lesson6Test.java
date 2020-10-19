import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;


public class Lesson6Test {

    @ParameterizedTest
    @MethodSource("setTestData_splitArray")
    public void expectedPassed_splitArray(int[] expArray, int[] numArray) {
        Assertions.assertArrayEquals(expArray, Lesson6.splitArray(numArray));
    }

    public static Stream<Arguments> setTestData_splitArray() {
        return Stream.of(
                Arguments.arguments(new int[]{5}, new int[]{1, 2, 3, 4, 5}),
                Arguments.arguments(new int[]{3,2,4}, new int[]{4,3,2,4}),
                Arguments.arguments(null, new int[]{1,2,3,4}));
    }
    @Test
    public void expectedExceptionSplitArray() {
        Assertions.assertThrows(RuntimeException.class, () -> Lesson6.splitArray(new int[]{1,2,3}));
    }

    @ParameterizedTest
    @MethodSource("setPassedTestData_arrayContainsNumber")
    public void expectedPassed_arrayContainsNumber(int[] numArray) {
        Assertions.assertTrue(Lesson6.arrayContainsNumbers(numArray));
    }

    public static Stream<Arguments> setPassedTestData_arrayContainsNumber() {
        return Stream.of(
                Arguments.arguments(new int[]{1,3,2,4}),
                Arguments.arguments(new int[]{0,2,1,4,5}));
    }

    @ParameterizedTest
    @MethodSource("setFailedTestData_arrayContainsNumber")
    public void expectedFailed_arrayContainsNumber(int[] numArray) {
        Assertions.assertFalse(Lesson6.arrayContainsNumbers(numArray));
    }

    public static Stream<Arguments> setFailedTestData_arrayContainsNumber() {
        return Stream.of(
                Arguments.arguments(new int[]{0, 2, 3}),
                Arguments.arguments(new int[]{1, 2, 3}),
                Arguments.arguments(new int[]{4}));
    }
}
