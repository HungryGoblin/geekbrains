public class Mock {

    @BeforeSuite
    public static void testBeforeSuite () {
        System.out.println("testBeforeSuite");
    }

    @AfterSuite
    public static void testAfterSuite () {
        System.out.println("testAfterSuite");
    }

    @Test(priority = 10)
    public static void testPriority10 () {
        System.out.println("testPriority10");
    }

    @Test(priority = 3)
    public static void testPriority3 () {
        System.out.println("testPriority3");
    }

    @Test(priority = 5)
    public static void testPriority5 () {
        System.out.println("testPriority5");
    }

}
