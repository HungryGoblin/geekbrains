package Animal;

public class Main {

    public static void main(String[] args) {
        Cat cat = new Cat();
        System.out.println(cat.run(300));
        System.out.println(cat.run(100));
        System.out.println(cat.jump(100));
        System.out.println(cat.swim(5));
        System.out.println("----------------------------------------------------------------------------------------");
        Dog dog = new Dog();
        System.out.println(dog.run(1000));
        System.out.println(dog.run(100));
        System.out.println(dog.jump(1));
        System.out.println(dog.swim(5));
        System.out.println("----------------------------------------------------------------------------------------");
        Dog seekDog = new Dog(100, 0.1F, 2);
        System.out.println(seekDog.run(1000));
        System.out.println(seekDog.run(100));
        System.out.println(seekDog.jump(1));
        System.out.println(seekDog.swim(5));
        System.out.println("----------------------------------------------------------------------------------------");
        seekDog.setLimits(1000, 800, 50);
        System.out.println(seekDog.run(1000));
        System.out.println(seekDog.run(100));
        System.out.println(seekDog.jump(1));
        System.out.println(seekDog.swim(500));
    }
}
