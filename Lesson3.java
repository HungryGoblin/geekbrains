import java.util.Scanner;
import java.util.Random;
/*
Полностью разобраться с кодом;
Переделать проверку победы, чтобы она не была реализована просто набором условий.
* Попробовать переписать логику проверки победы, чтобы она работала для поля 5х5 и количества фишек 4.
*** Доработать искусственный интеллект, чтобы он мог блокировать ходы игрока, и пытаться выиграть сам.
* */

public class Lesson3 {
    private static final char DOT_EMPTY = '\u2003';
    private static final int[] FIELD_SIZE_ARRAY = {3,5,9};
    private static final int[] STREAK_SIZE_ARRAY = {3,4,6};
    private static final char DOT_HUMAN = '\u263A';
    private static final char DOT_AI = '\u2620';
    private static final int DEF_SIZE = 3;
    private static final int STATUS_END_GAME = -3;
    private static final int STATUS_INIT = -2;
    private static final int STATUS_IN_PROGRESS = -1;
    private static final int STATUS_WIN_HUMAN = 0;
    private static final int STATUS_WIN_AI = 1;
    private static final int STATUS_DRAW = 2;

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static int fieldSize;
    private static int turns = 0;
    private static int maxTurns = 0;
    private static char[][] field;
    private static int winCondition = 3;
    private static boolean firstHuman = true;
    private static int gameStatus = -2;
    private static String[] gameResults = {"ПОБЕДИЛ ЧЕЛОВЕК","ПОБЕДИЛ РОБОТ","НИЧЬЯ"};


    public static void main(String[] args) {
        Game:
        while (true) {
            while (gameStatus == STATUS_INIT) {
                System.out.printf("РАЗМЕР ПОЛЯ (%s): ", arrayList(FIELD_SIZE_ARRAY));
                initGame(SCANNER.nextInt());
            }
            while (gameStatus == STATUS_IN_PROGRESS) {
                if (firstHuman) humanTurn();
                else aiTurn();
            }
            while (gameStatus == STATUS_END_GAME) {
                int playGame;
                System.out.print("ПРОДОЛЖАЕМ ИГРАТЬ? (1 - продолжить / иначе - выйти): ");
                playGame = SCANNER.nextInt();
                if (playGame == 1) gameStatus = STATUS_INIT;
                else break Game;
            }
        } // Game
        SCANNER.close();
    }

    // проверка статуса игры
    private static boolean checkEndGame (char dot) {
        boolean endGame = false;
        turns ++;
        if (checkWin(dot)) {
            gameStatus = dot == DOT_HUMAN? STATUS_WIN_HUMAN: STATUS_WIN_AI;
        }
        else if (turns == maxTurns) {
            gameStatus = STATUS_DRAW;
        }
        if (gameStatus > STATUS_IN_PROGRESS) {
            printField();
            System.out.printf("РЕЗУЛЬТАТ ИГРЫ: %s%n", gameResults[gameStatus]);
            gameStatus = STATUS_END_GAME;
            endGame = true;
        }
        else
           firstHuman = !firstHuman;
        return endGame;
    }

    // проверка на выигрыш
    private static boolean checkWin(char c) {
        return checkNorm(c, true) || checkNorm(c, false) || checkDia(c);
    }

    // проверка горизонтального и вертикального условий
    private static boolean checkNorm (char c, boolean isHor) {
        int dotStreak = 0;
        for (int i = 0; i < fieldSize; i++) {
            dotStreak = 0;
            for (int j = 0; j < fieldSize; j++) {
                if ((field[i][j] == c && isHor)||(field[j][i] == c && !isHor)) dotStreak++;
                else break;
            }
            if (dotStreak == winCondition) return true;
        }
        return false;
    }

    // проверка диагоналей
    private static boolean checkDia (char c) {
        int [] dotStreak = {0,0};
        for (int i = 0; i < fieldSize; i++) {
            if (field[i][i] == c) dotStreak[0]++;
            else dotStreak[0] = 0;
            if (field[i][fieldSize - i - 1] == c) dotStreak[1]++;
            else dotStreak[1] = 0;
            if (dotStreak[0] == winCondition || dotStreak[1] == winCondition ) return true;
        }
        return false;
    }

    // ход человека
    private static boolean humanTurn () {
        int x; int y;
        printField();
        do {
            System.out.printf("ячека по горизонтали (X):");
            x = SCANNER.nextInt() - 1;
            System.out.printf("ячека по вертикали   (Y):");
            y = SCANNER.nextInt() - 1;
        } while (!validHumanTurn(x, y));
        field[x][y] = DOT_HUMAN;
        return (checkEndGame(DOT_HUMAN));
    }

    // ход робота
    private static boolean aiTurn() {
        int x; int y;
        do {
            x = RANDOM.nextInt(fieldSize);
            y = RANDOM.nextInt(fieldSize);
        } while (!isEmptyCell(x, y,false));
        field[x][y] = DOT_AI;
        return checkEndGame(DOT_AI);
    }

    // проверяет корректность ввода координат
    private static boolean validHumanTurn (int x, int y) {
        boolean retVal = (x >= 0 && x < fieldSize) && (y >= 0 && y < fieldSize);
        if (!retVal)
            System.out.printf("** некорректные координаты %d %d! %n", x + 1, y + 1);
        else
            retVal = isEmptyCell(x, y, true);
        return retVal;
    }

    // проверяет ячейку на пустоту
    private static boolean isEmptyCell (int x, int y, boolean showMess) {
        boolean retVal = field[x][y] == DOT_EMPTY;
        if (!retVal && showMess) System.out.printf("** ячейка с координатами %d %d занята! %n", x + 1, y + 1);
        return retVal;
    }

    // инициализирует поле
    private static boolean initGame() {
        return initGame(DEF_SIZE);
    }

    private static boolean initGame(int sz) {
        if (!checkFieldSize(sz)) return false;
        turns = 0;
        fieldSize = sz;
        maxTurns = fieldSize * 4;
        winCondition = STREAK_SIZE_ARRAY[arrayIndex(sz, FIELD_SIZE_ARRAY)];
        field = new char[fieldSize][fieldSize];
        for (int x = 0; x < fieldSize; x++) {
            for (int y = 0; y < fieldSize; y++) field[x][y] = DOT_EMPTY;
        }
        System.out.printf("ПОЛЕ: %dx%d ПОБЕДА: %d в ряд%n", fieldSize, fieldSize, winCondition);
        firstHuman = RANDOM.nextInt(1) == 0;
        System.out.printf("НАЧИНАЕТ %s%n", firstHuman? "человек " + DOT_HUMAN : "робот " + DOT_AI);
        gameStatus = -1;
        return true;
    }

    // выводит поле
    private static void printField() {
        System.out.print("+");
        for (int x = 0; x < fieldSize * 2 + 1; x++)
            System.out.print((x % 2 == 0) ? String.valueOf(DOT_EMPTY) : x / 2 + 1);
        System.out.println();
        for (int y = 0; y < fieldSize; y++) {
            System.out.print(y + 1 + "|");
            for (int x = 0; x < fieldSize; x++)
                System.out.print(field[x][y] + "|");
            System.out.println();
        }
        for (int x = 0; x <= fieldSize * 2 + 1; x++)
            System.out.print("-");
        System.out.println();
    }

    // проверяет размер поля
    private static boolean checkFieldSize(int fs) {
        return arrayContains(fs, FIELD_SIZE_ARRAY);
    }

    // массив содержит элемент
    private static boolean arrayContains (int v, int[] a) {
        return (arrayIndex(v, a) >= 0);
    }

    // возвращает индекс элемента массива
    private static int arrayIndex (int v, int[] a) {
        int id = -1;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == v) {
                id = i;
                break;
            }
        }
        return id;
    }

    // список элементов массива
    private static String arrayList (int[] a) {
        String arrayLst = "";
        for (int i = 0; i < a.length; i++) {
            arrayLst = String.format("%s %d",arrayLst, a[i]);
        }
        return arrayLst.trim();
    }

}