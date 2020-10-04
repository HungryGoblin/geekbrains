import java.util.ArrayList;

public class MessageLogger {

    private static final int HISTORY_LENGTH = 100;
    private static final String HISTORY_FILE = "message.log";

    private static FileEx file = new FileEx(HISTORY_FILE);

    public static ArrayList<String> loadHistory() {
        try {
            return file.readLinesReverse(HISTORY_LENGTH);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Error loading message history: %s", e.getMessage()));
        }
    }

    public static void writeMessage (String text) {
        try {
            file.writeLine(text);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Error updating message history: %s", e.getMessage()));
        }
    }

}
