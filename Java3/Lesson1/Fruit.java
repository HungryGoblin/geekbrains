import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

//3. Большая задача:
//a. Есть классы Fruit -> Apple, Orange;(больше фруктов не надо)
//b. Класс Box в который можно складывать фрукты, коробки условно сортируются по типу фрукта, поэтому в одну коробку
// нельзя сложить и яблоки, и апельсины;
//c. Для хранения фруктов внутри коробки можете использовать ArrayList;
//d. Сделать метод getWeight() который высчитывает вес коробки, зная количество фруктов и вес одного
// фрукта(вес яблока - 1.0f, апельсина - 1.5f, не важно в каких это единицах);
//e. Внутри класса коробка сделать метод compare, который позволяет сравнить текущую коробку с той,
// которую подадут в compare в качестве параметра, true - если их веса равны, false в противном случае(коробки
// с яблоками мы можем сравнивать с коробками с апельсинами);
//f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку
// (помним про сортировку фруктов, нельзя яблоки высыпать в коробку с апельсинами), соответственно в текущей
// коробке фруктов не остается, а в другую перекидываются объекты, которые были в этой коробке;
//g. Не забываем про метод добавления фрукта в коробку.

abstract class Fruit {

    public static float getWeight() {
        return weight;
    }

    protected static float weight;
    private String name;

    Fruit(String name) {
        this.name = name;
    }
}

class Apple extends Fruit {
    Apple() {
        super("Apple");
        weight = 1.0f;
    }
}

class Orange extends Fruit {
    Orange() {
        super("Orange");
        weight = 1.5f;
    }
}

class Box<T extends Fruit> implements Comparable<Box> {

    private ArrayList<T> fruits = new ArrayList<>();

    public float getWeight() {
        if (fruits.size() > 0)
            return fruits.size() * fruits.get(0).getWeight();
        else
            return 0f;
    }

    @Override
    public int compareTo(Box box) {
        if (this.getWeight() > box.getWeight()) return -1;
        else if (this.getWeight() < box.getWeight());
        return 0;
    }

    public int count() {
        return fruits.size();
    }

    public void put(T fruit) {
        fruits.add(fruit);
    }

    public void put(Box box) {
        if (this.compareTo(box) == 0) {
            this.fruits.addAll(box.getAll());
        }
    }

    public T get(int num) {
        return fruits.get(num);
    }

    public ArrayList<T> getAll() {
        return fruits;
    }

}
