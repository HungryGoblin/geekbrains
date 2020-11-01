public class ClientConnection implements  Runnable{

    private static int connCounter = 0;
    private int connId = 0;

    @Override
    public void run() {
        System.out.printf("Client connection %d established %n", connId);
        for (int i = 0; i < 10; i++) {
            System.out.printf("Client connection %d message: %d %n", connId, i);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.printf("Connection %d interrupted: %s %n", connId, e.getMessage());
            }
        }
        System.out.printf("Connection %d finished %n", connId);
    }

    ClientConnection () {
        connId = connCounter;
        connCounter++;
    }
}
