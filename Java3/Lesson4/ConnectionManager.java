import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionManager {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    public static void setConnection(ClientConnection connection) {
        EXECUTOR_SERVICE.execute(connection);
    }

    public static void kill () {
        EXECUTOR_SERVICE.shutdownNow();
    }
}
