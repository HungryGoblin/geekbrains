import java.io.FileOutputStream;
import java.time.LocalDateTime;

public class Log {

    public static String logFile;

    public void append (String message) {
        try {
            FileOutputStream fileDst = new FileOutputStream(logFile, true);
            fileDst.write(String.format("%s %s%n", LocalDateTime.now().toString(), message).getBytes());
            fileDst.flush();
            fileDst.close();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Error writing log-file: %s", e.getMessage()));
        }
    }

    Log(String fileName) {
        logFile = fileName;
    }
}

