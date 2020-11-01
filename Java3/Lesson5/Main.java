import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Создать классы Car, Truck, Bus. Каждый обладает объемом топлива и расходом
 * (Car - 20\2.5, Truck - 60\15, Bus - 40\7.5) и уникальных значением для определения среди разных транспортных средств.
 * Для каждого транспортного средства оставшиеся в баке количество топлива вычисляется раз в 3 сек.
 * Создать до 10 экземпляров разных транспортных средств и запустить их в работу
 * Создать класс FuelStaion. Одновременно может заправлять 3 автомобиля, остальные должны пока не освободится место.
 * Заправка занимает 5 сек, зачем транспортное средство освобождает место
 * Транспортные средства должны выстраиваться в очередь, если нет свобожных мест для заправки и начинать заправку в
 * строгом порядке своей очередь 6 .
 * Транспортные средства после заправки возвращаются на дорогу и продолжают
 * свое движение
 */

public class Main {

    static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        try {
            for (int i = 0; i < 10; i++) {
                executorService.execute(new Vehicle.Car());
                executorService.execute(new Vehicle.Bus());
                executorService.execute(new Vehicle.Truck());
            }
        } finally {
            executorService.shutdownNow();
        }
    }
}
