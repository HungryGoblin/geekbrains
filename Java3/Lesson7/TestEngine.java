import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * 1. Создать класс, который может выполнять «тесты», в качестве тестов выступают классы с наборами методов с
 * аннотациями @Test. Для этого у него должен быть статический метод start(), которому в качестве параметра
 * передается или объект типа Class, или имя класса.
 * Из «класса-теста» вначале должен быть запущен метод с
 * аннотацией @BeforeSuite, если такой имеется, далее запущены методы с аннотациями @Test, а по завершению
 * всех тестов – метод с аннотацией @AfterSuite. К каждому тесту необходимо также добавить приоритеты
 * (int числа от 1 до 10), в соответствии с которыми будет выбираться порядок их выполнения, если приоритет
 * одинаковый, то порядок не имеет значения. Методы с аннотациями @BeforeSuite и @AfterSuite должны присутствовать
 * в единственном экземпляре, иначе необходимо бросить RuntimeException при запуске «тестирования».
 */

public class TestEngine {

    private Method beforeMethod = null;
    private Method afterMethod = null;
    private final ArrayList<Method> testMethods = new ArrayList<>();

    private void initialize (Class cls) {
        Method[] methods = cls.getDeclaredMethods();

        for (int i = 0; i < methods.length; i++) {
            if (methods[i].isAnnotationPresent(Test.class)) {
                testMethods.add(methods[i]);
                continue;
            }
            if (methods[i].isAnnotationPresent(BeforeSuite.class)) {
                if (beforeMethod != null) throw new RuntimeException("should be only one BeforeSuit method");
                beforeMethod = methods[i];
                continue;
            }
            if (methods[i].isAnnotationPresent(AfterSuite.class)) {
                if (afterMethod != null) throw new RuntimeException("should be only one AfterSuit method");
                afterMethod = methods[i];
            }
        }
        testMethods.sort(Comparator.comparingInt(o -> o.getAnnotation(Test.class).priority()));
    }

    public void start (String clsName) throws ClassNotFoundException {
        start(Class.forName(clsName));
    }

    public void start (Class cls) {
        try {
            initialize(cls);
            if (beforeMethod != null) beforeMethod.invoke(null);
            for (int i = 0; i < testMethods.size(); i++) {
                testMethods.get(i).invoke(null);
                System.out.println(testMethods.get(i));
            }
            if (afterMethod != null) afterMethod.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();}
    }
}
