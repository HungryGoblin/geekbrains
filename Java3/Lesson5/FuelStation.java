import com.sun.istack.internal.NotNull;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FuelStation {

    private static final int CAPACITY = 3;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(CAPACITY);
    private static final ConcurrentLinkedQueue<Vehicle> refuelQueue = new ConcurrentLinkedQueue<>();


    public static void refuel(@NotNull Vehicle vehicle) {
       if (refuelQueue.add(vehicle)) {
           vehicle.setState(Vehicle.VehicleState.QUEUE);
           System.out.printf("Fuel station: %s added to queue %n", vehicle.toString());
       }
    }

    public static void Stop () {
        executorService.shutdownNow();
    }

    static {
        for (int i = 0; i < CAPACITY; i++) {
            executorService.execute(new RefuelSlot(refuelQueue));
        }
    }
}
