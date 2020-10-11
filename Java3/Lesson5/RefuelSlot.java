import com.sun.istack.internal.NotNull;

import java.util.concurrent.ConcurrentLinkedQueue;

class RefuelSlot implements Runnable {

    private static final long REFUEL_TIME = 5000;
    private static final long REFRESH_RATE = 500;
    private static int counter = 0;

    private final int slotId;
    private final ConcurrentLinkedQueue<Vehicle> refuelQueue;

    private void refuel(@NotNull Vehicle vehicle) throws InterruptedException {
        System.out.printf("%s: %s got out from queue %n", toString(), vehicle.toString());
        vehicle.setState(Vehicle.VehicleState.REFUEL);
        Thread.sleep(REFUEL_TIME);
        vehicle.refuel();
        System.out.printf("%s: vehicle %s refueled %n", toString(), vehicle.toString());
    }

    @Override
    public void run() {
        while (true) {
            try {
                Vehicle vehicle = refuelQueue.poll();
                if (vehicle != null)
                    refuel(vehicle);
                else
                    Thread.sleep(REFRESH_RATE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Fuel station slot %d (%s)", slotId, Thread.currentThread().getName());
    }

    RefuelSlot(ConcurrentLinkedQueue<Vehicle> refuelQueue) {
        {
            this.refuelQueue = refuelQueue;
            slotId = counter;
            counter++;
        }
    }
}

