import java.util.Locale;

public abstract class Vehicle implements Runnable {

    private static final int REFRESH_RATE = 3000;
    private static int counter = 0;

    private final String vehicleType;
    private final int vehicleId;
    private final double tankVolume;
    private final double fuelConsumption;
    private double fuelRest;
    private VehicleState state = VehicleState.DRIVE;

    public void refuel() {
        fuelRest = tankVolume;
        setState(VehicleState.DRIVE);
    }

    Vehicle(String vt, double fv, double fc) {
        vehicleType = vt;
        tankVolume = fv;
        fuelConsumption = fc;
        refuel();
    }

    @Override
    public void run() {
        while (true) {
            if (state == VehicleState.DRIVE) {
                if (fuelRest > 0) fuelRest = fuelRest - fuelConsumption;
                if (fuelRest <= 0) {
                    if (fuelRest < 0) fuelRest = 0;
                    setState(VehicleState.EMPTY);
                }
            }
            if (state == VehicleState.EMPTY) FuelStation.refuel(this);
            System.out.printf(Locale.US, "Vehicle: %s fuel: %2.2f %n", toString(), fuelRest);
            try {
                Thread.sleep(REFRESH_RATE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }   
    }

    @Override
    public String toString() {
        return String.format("%s %d (%s) [%s]", vehicleType, vehicleId, Thread.currentThread().getName(), state);
    }

    {
        vehicleId = counter;
        counter++;
    }

    public void setState(VehicleState state) {
        this.state = state;
    }

    public static enum VehicleState {
        DRIVE("driving"),
        EMPTY("empty tank"),
        QUEUE("awaiting refuel"),
        REFUEL("refueling");

        private final String name;

        VehicleState(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    public static class Car extends Vehicle {
        public Car() {
            super("Car", 20, 2.5);
        }
    }

    public static class Truck extends Vehicle {
        public Truck() {
            super("Truck", 60, 15);
        }
    }

    public static class Bus extends Vehicle {
        public Bus() {
            super("Bus", 40, 7.5);
        }
    }
}


