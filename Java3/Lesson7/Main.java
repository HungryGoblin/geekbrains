public class Main {

    public static void main(String[] args) {
        TestEngine test = new TestEngine();
        try {
            test.start("Mock");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
