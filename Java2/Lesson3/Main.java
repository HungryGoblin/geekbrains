import java.util.*;

public class Main {

    private static final String[] LIB = {"Пердимонокль", "Пипка", "Мимозыря", "Хухря", "Ендовочник", "Козлодер",
            "Курвиметр", "Пендельтюр", "Вувузела", "Шуфлядка", "Пипидастр", "Целовальник", "Многочлен", "Козулька"};
    private static final int ARR_LEN = 20;
    private static final Random RANDOM = new Random();

    private static final Comparator<String> ASCENDING_STRING = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return String.CASE_INSENSITIVE_ORDER.compare(o1, o2);
        }
    };

    public static void main(String[] args) {
        try {
            ArrayList<String> wordList = getWordSet(LIB, ARR_LEN);
            System.out.println(wordList.toString());
            for (int i = 0; i < LIB.length; i++) {
                System.out.printf("%s: %d%n", LIB[i], numEntries(wordList, LIB[i]));
            }

            PhoneBook phoneBook = new PhoneBook();
            for (int i = 0; i < LIB.length; i++) {
                for (int j = 0; j < RANDOM.nextInt(LIB.length); j++) {
                    phoneBook.add(LIB[i], String.format("+7(919)222-11-%02d", j + j * 10));
                }
            }
            for (int i = 0; i < LIB.length; i++) {
                System.out.printf("name: %s, number(s): %s%n", LIB[i], phoneBook.get(LIB[i]));
            }
            phoneBook.add("Ошибкович", "123");
        } catch (Exception e) {
            System.out.println("** ERROR:");
            e.printStackTrace();
        }
    }

    private static ArrayList<String> getWordSet(String[] lib, int len) {
        ArrayList<String> destSet = new ArrayList<String>();
        for (int i = 0; i < len; i++) {
            destSet.add(lib[RANDOM.nextInt(lib.length)]);
        }
        destSet.sort(ASCENDING_STRING);
        return destSet;
    }

    private static int numEntries(ArrayList<String> source, String target) {
        return source.lastIndexOf(target) - source.indexOf(target) + 1;
    }
}

class PhoneBook {

    private static final String PHONE_TEMPLATE = "^[+][0-9].[0-9,\\-,(,)]{7,20}";
    private HashMap<String, HashSet> phoneBook = new HashMap<>();

    // исходим из того, что одинаковые номера могут быть у различных людей, например, рабочие номера телефонов
    public void add(String name, String number) {
        HashSet<String> numberSet;
        if (name == null || name == "")
            throw new RuntimeException("The name should be not empty");
        if (!number.matches(PHONE_TEMPLATE))
            throw new RuntimeException(String.format(
                    "Error updating phonebook (person \"%s\"): The phone number \"%s\" has wrong format",name, number));
        numberSet = phoneBook.get(name);
        if (numberSet == null) numberSet = new HashSet<>();
        numberSet.add(number);
        phoneBook.put(name, numberSet);
    }

    public HashSet<String> get(String name) {
        return phoneBook.get(name);
    }
};