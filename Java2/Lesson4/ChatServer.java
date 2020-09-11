import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class ChatServer implements Thread.UncaughtExceptionHandler {

    public static Log log;

    public static final String LOG_FILE = "chatserver.log";

    private boolean serverStarted = false;

    private ServerSocket serverSocket;

    private BufferedReader serverSocketIn;

    public void start(int port) {
        if (serverStarted) return;
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getLocalHost();
            System.out.println(inetAddress.getHostAddress());
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            appendLog(String.format("Can't start server: %s", e.getMessage()));
        }
        serverStarted = true;
        appendLog(String.format("Server started: %s (%s)",
                serverSocket.getInetAddress(), serverSocket.getLocalSocketAddress()));
    }

    public void stop() {
        if (!serverStarted) return;
        serverStarted = false;
        appendLog("Server stopped");
    }


    private static boolean userExists(String user) {
        // код проверки
        return true;
    }

    private static boolean checkUser(String user) {
        if (!userExists(user))
            throw new RuntimeException(String.format("UserId: %s does not exist", user));
        return true;
    }

    private void appendLog(String message) {
        try {
            FileOutputStream fileDst = new FileOutputStream(LOG_FILE, true);
            fileDst.write(String.format("%s %s%n", LocalDateTime.now().toString(), message).getBytes());
            fileDst.flush();
            fileDst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String user, String userDest, String message) {
        if (serverStarted) return;
        try {
            if (!checkUser(user) || !checkUser(userDest))
                throw new RuntimeException(String.format("Incorrect UserId: %s", user));
            log.append(String.format("%s>%s: %s", user, userDest, message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        serverStarted = false;
        e.printStackTrace();
    }

    ChatServer() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        log = new Log("chatserver.log");
    }
}


