import java.util.Scanner;
import java.util.Random;
/*
Полностью разобраться с кодом;
Переделать проверку победы, чтобы она не была реализована просто набором условий.
* Попробовать переписать логику проверки победы, чтобы она работала для поля 5х5 и количества фишек 4.
*** Доработать искусственный интеллект, чтобы он мог блокировать ходы игрока, и пытаться выиграть сам.
* */

public class Lesson3 {

    // глобальные параметры
    private static final char DOT_EMPTY = '\u2003';
    private static final int[] FIELD_SIZE_ARRAY = {3, 5, 9};
    private static final int[] STREAK_SIZE_ARRAY = {3, 4, 6};
    private static final char DOT_HUMAN = '\u263A';
    private static final char DOT_AI = '\u2620';
    private static final String[] GAME_RESULT = {"ПОБЕДИЛ ЧЕЛОВЕК", "ПОБЕДИЛ РОБОТ", "НИЧЬЯ"};

    // статусы игры
    private static final int STATUS_END_GAME = -3;
    private static final int STATUS_INIT = -2;
    private static final int STATUS_IN_PROGRESS = -1;
    private static final int STATUS_WIN_HUMAN = 0;
    private static final int STATUS_WIN_AI = 1;
    private static final int STATUS_DRAW = 2;

    // переменные игры
    private static char[][] field;
    private static int fieldSize;
    private static int maxTurns;
    private static int winCondition = 3;
    private static int minWinTurns = 0;
    private static int turns = 0;
    private static int gameStatus = STATUS_INIT;
    private static boolean firstHumanInit = true;
    private static boolean firstHuman = true;
    private static int shiftField = 0;
    private static int shiftFieldNum = 0;

    // объекты
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();

    // ИГРА
    public static void main(String[] args) {
        while (gameStatus == STATUS_INIT) {
            print(String.format("РАЗМЕР ПОЛЯ (%s): ", arrayList(FIELD_SIZE_ARRAY)));
            initGame(SCANNER.nextInt());
        }
        Game:
        while (true) {
            while (gameStatus == STATUS_IN_PROGRESS) {
                if (firstHuman) humanTurn();
                else aiTurn();
            }
            while (gameStatus == STATUS_END_GAME) {
                int playGame;
                print("ПРОДОЛЖАЕМ ИГРАТЬ? (1 - продолжить / иначе - выйти): ");
                playGame = SCANNER.nextInt();
                if (playGame == 1) gameStatus = STATUS_INIT;
                else break Game;
            }
        } // Game
        SCANNER.close();
    }

    // проверка завершения игры
    private static void checkEndGame(char dot) {
        boolean endGame = false;
        if (turns >= minWinTurns) {
            if (checkWin(dot)) {
                gameStatus = dot == DOT_HUMAN ? STATUS_WIN_HUMAN : STATUS_WIN_AI;
            } else if (turns == maxTurns) {
                gameStatus = STATUS_DRAW;
            }
            if (gameStatus > STATUS_IN_PROGRESS) {
                printField();
                print(String.format("РЕЗУЛЬТАТ ИГРЫ: %s%n", GAME_RESULT[gameStatus]));
                gameStatus = STATUS_END_GAME;
                endGame = true;
            }
        }
        if (!endGame) {
            firstHuman = !firstHuman;
            turns++;
        }
    }

    // проверка на выигрыш
    private static boolean checkWin(char c) {
        boolean win = false;
        CheckWin:
        for (int shiftX = 0; shiftX < shiftField; shiftX++) {
            for (int shiftY = 0; shiftY < shiftField; shiftY++) {
                win = (checkLine(c, shiftX, shiftY) || checkDia(c, shiftX, shiftY));
                if (win) break CheckWin;
            }
        }
        return win;
    }

    // проверка горизонталей
    private static boolean checkLine(char c, int shiftX, int shiftY) {
        boolean hLine = false, vLine = false;
        for (int i = shiftX; i < fieldSize; i++) {
            for (int j = shiftY; j < fieldSize; j++) {
                hLine ^= (field[i][i] == c);
                vLine ^= (field[j][i] == c);
            }
        }
        return (hLine || vLine);
    }

    // проверка диагоналей
    private static boolean checkDia(char c, int shiftX, int shiftY) {
        boolean rDia = false, lDia = false;
        for (int i = shiftX; i < winCondition; i++) {
            rDia ^= (field[i][i + shiftY] == c);
            lDia ^= (field[fieldSize - i - 1][i + shiftY] == c);
        }
        return (rDia || lDia) ;
    }

    // проверяет корректность ввода координат
    private static boolean validHumanTurn(int x, int y) {
        boolean retVal = (x >= 0 && x < fieldSize) && (y >= 0 && y < fieldSize);
        if (!retVal)
            print(String.format("** некорректные координаты %d %d! %n", x + 1, y + 1));
        else
            retVal = isEmptyCell(x, y);
        return retVal;
    }

    // проверяет ячейку на пустоту
    private static boolean isEmptyCell(int x, int y) {
        boolean retVal = (field[x][y] == DOT_EMPTY);
        if (!retVal && firstHuman) print(String.format("** ячейка с координатами %d %d занята! %n", x + 1, y + 1));
        return retVal;
    }

    // выводит поле
    private static void printField() {
        print("+");
        for (int x = 0; x < fieldSize * 2 + 1; x++)
            print((x % 2 == 0) ? String.valueOf(DOT_EMPTY) : String.valueOf(x / 2 + 1));
        print("\n");
        for (int y = 0; y < fieldSize; y++) {
            print(y + 1 + "|");
            for (int x = 0; x < fieldSize; x++)
                print(field[x][y] + "|");
            print("\n");
        }
        for (int x = 0; x <= fieldSize * 2 + 1; x++)
            print("-");
        print("\n");
    }

    // проверяет размер поля
    private static boolean checkFieldSize(int fs) {
        return arrayContains(fs, FIELD_SIZE_ARRAY);
    }

    // массив содержит элемент
    private static boolean arrayContains(int v, int[] a) {
        return (arrayIndex(v, a) >= 0);
    }

    // возвращает индекс элемента массива
    private static int arrayIndex(int v, int[] a) {
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
    private static String arrayList(int[] a) {
        String arrayLst = "";
        for (int j : a) arrayLst = String.format("%s %d", arrayLst, j);
        return arrayLst.trim();
    }

    // вывод
    private static void print(String s) {
        System.out.print(s);
    }

    // инициализирует игру
    private static void initGame(int sz) {
        if (!checkFieldSize(sz)) return;
        fieldSize = sz;
        maxTurns = fieldSize * 4;
        winCondition = STREAK_SIZE_ARRAY[arrayIndex(sz, FIELD_SIZE_ARRAY)];
        shiftField = fieldSize - winCondition;
        shiftFieldNum = (int)Math.pow(shiftField + 1, 2);
        minWinTurns = winCondition * 2 - 1;
        firstHumanInit = RANDOM.nextBoolean();
        field = new char[fieldSize][fieldSize];
        for (int x = 0; x < fieldSize; x++) {
            for (int y = 0; y < fieldSize; y++) field[x][y] = DOT_EMPTY;
        }
        firstHuman = firstHumanInit;
        turns = 0;
        gameStatus = STATUS_IN_PROGRESS;
        print(String.format("ПОЛЕ: %dx%d ПОБЕДА: %d в ряд%n", fieldSize, fieldSize, winCondition));
        print(String.format("НАЧИНАЕТ %s%n", firstHuman ? "человек " + DOT_HUMAN : "робот " + DOT_AI));
    }

    // ход человека
    private static void humanTurn() {
        int x; int y;
        printField();
        do {
            print("ячека по горизонтали (X):");
            x = SCANNER.nextInt() - 1;
            print("ячека по вертикали   (Y):");
            y = SCANNER.nextInt() - 1;
        } while (!validHumanTurn(x, y));
        field[x][y] = DOT_HUMAN;
        checkEndGame(DOT_HUMAN);
    }

    // ход робота
    private static void aiTurn() {
        int x; int y;
        do {
            x = RANDOM.nextInt(fieldSize);
            y = RANDOM.nextInt(fieldSize);
        } while (!isEmptyCell(x, y));
        field[x][y] = DOT_AI;
        checkEndGame(DOT_AI);
    }
}
