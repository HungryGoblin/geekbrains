import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Collections;

class LetterPrinter implements Runnable, AutoCloseable {

    private char letter;
    private static char currentLetter;
    public static ArrayList<Character> letters = new ArrayList<>();
    private static final DataOutputStream OUTPUT = new DataOutputStream(System.out);

    private void setNextLetter() {
        int nextId = letters.indexOf(letter) + 1;
        currentLetter = nextId >= letters.size() ? letters.get(0) : letters.get(nextId);
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                synchronized (OUTPUT) {
                    while (currentLetter != letter)
                        OUTPUT.wait();
                    OUTPUT.write((byte) letter);
                    setNextLetter();
                    OUTPUT.notifyAll();
                }
            }
            OUTPUT.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        Collections.addAll(letters, 'A', 'B', 'C');
    }

    LetterPrinter(char letter) {
        this.letter = letter;
        if (letter == letters.get(0)) currentLetter = letter;
    }

    @Override
    public void close() throws Exception {
        OUTPUT.write('\n');
        OUTPUT.flush();
        OUTPUT.close();
    }
}
