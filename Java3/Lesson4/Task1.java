public class Task1 {

    public static void main(String[] args) {
        Thread[] threads = new Thread[LetterPrinter.letters.size()];
        for (int i = 0; i < LetterPrinter.letters.size(); i++) {
            threads[i] = new Thread(new LetterPrinter(LetterPrinter.letters.get(i)));
            threads[i].start();
        }
    }

}

