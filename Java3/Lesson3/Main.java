import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 200; i++) {
            MessageLogger.writeMessage("Sender; Receiver; Text_" + i);
        }

        ArrayList<String> messList = MessageLogger.loadHistory();
        for (int i = 0; i < messList.size(); i++) {
            System.out.println(messList.get(i));
        }
    }
}
