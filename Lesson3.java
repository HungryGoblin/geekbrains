import java.util.Scanner;
import java.util.Random;


public class Lesson3 {

    private static final char DOT_EMPTY = ' ';
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = '0';
    private static final int[] FIELD_SIZE_ARRAY = {3, 5, 9};
    private static final int[] STREAK_SIZE_ARRAY = {3, 4, 6};
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
    private static int turns = 1;
    private static int gameStatus = STATUS_INIT;
    private static boolean firstHuman = true;
    private static int shiftField = 1;

    // интеллект
    private static final boolean AI_LOGIC_ENABLED = true;
    private static final int WEIGHT_GAP0 = 77777777;
    private static final int WEIGHT_GAP1 = 350;
    private static final int WEIGHT_GAP2 = 75;
    private static final int WEIGHT_GAP3 = 25;
    private static final int WEIGHT_GAP4 = 5;
    private static final int WEIGHT_GAP5 = 3;
    private static final int WEIGHT_GAP6 = 1;
    private static final int[] DEPTH_ARRAY = {5,4,3};
    private static final int[] WEIGHT_GAP =
            {WEIGHT_GAP0, WEIGHT_GAP1, WEIGHT_GAP2, WEIGHT_GAP3, WEIGHT_GAP4, WEIGHT_GAP5, WEIGHT_GAP6};
    private static int turnAiX;
    private static int turnAiY;
    private static int maxDepth;

    // объекты
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();


    // ИГРА
    public static void main(String[] args) {
        Game:
        while (true) {
            while (gameStatus == STATUS_INIT) {
                System.out.print(String.format("РАЗМЕР ПОЛЯ (%s): ", arrayList(FIELD_SIZE_ARRAY)));
                initGame(SCANNER.nextInt());
            }
            while (gameStatus == STATUS_IN_PROGRESS) {
                if (firstHuman) humanTurn();
                else aiTurn();
            }
            while (gameStatus == STATUS_END_GAME) {
                int playGame;
                System.out.print("СЫГРАЕМ ЕЩЕ? (1 - еще разик / иначе - наигрался): ");
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
                System.out.print(String.format("РЕЗУЛЬТАТ ИГРЫ: %s%n", GAME_RESULT[gameStatus]));
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
    private static boolean checkWin(char dot) {
        boolean win = false;
        CheckWin:
        for (int shiftX = 0; shiftX < shiftField; shiftX++) {
            for (int shiftY = 0; shiftY < shiftField; shiftY++) {
                win = (checkLine(dot, shiftX, shiftY) || checkDia(dot, shiftX, shiftY));
                if (win) break CheckWin;
            }
        }
        return win;
    }

    // проверка горизонталей и вертикалей
    private static boolean checkLine(char dot, int shiftX, int shiftY) {
        for (int i = shiftX; i < fieldSize; i++) {
            int gapV = winCondition, gapH = winCondition;
            for (int j = shiftY; j < fieldSize; j++) {
                if (field[i][j] == dot) gapV--;
                if (field[j][i] == dot) gapH--;
            }
            if (gapV == 0 || gapH == 0) return true;
        }
        return false;
    }

    // проверка диагоналей
    private static boolean checkDia(char dot, int shiftX, int shiftY) {
        int gapL = winCondition, gapR = winCondition;
        for (int i = shiftX; i < winCondition; i++) {
            if (field[i + shiftX][i + shiftY] == dot) gapL--;
            if (field[fieldSize - 1 - i + shiftX][i + shiftY] == dot) gapR--;
        }
        return (gapL == 0 || gapR == 0);
    }

    // проверяет корректность ввода координат
    private static boolean validHumanTurn(int x, int y) {
        boolean retVal = (x >= 0 && x < fieldSize) && (y >= 0 && y < fieldSize);
        if (!retVal) System.out.print(String.format(
                "** некорректные координаты %d %d! %n", x + 1, y + 1));
        else retVal = isEmptyCell(x, y);
        return retVal;
    }

    // проверяет ячейку на пустоту
    private static boolean isEmptyCell(int x, int y) {
        boolean retVal = (field[y][x] == DOT_EMPTY);
        if (!retVal && firstHuman) System.out.print(String.format(
                "** ячейка с координатами %d %d занята! %n", x + 1, y + 1));
        return retVal;
    }


    // выводит поле (для вывода отладки)
    private static void printField() {
        System.out.print("+");
        for (int x = 0; x < fieldSize * 2 + 1; x++)
            System.out.print((x % 2 == 0) ? String.valueOf(DOT_EMPTY) : String.valueOf(x / 2 + 1));
        System.out.print("\n");
        for (int y = 0; y < fieldSize; y++) {
            System.out.print(y + 1 + "|");
            for (int x = 0; x < fieldSize; x++)
                System.out.print(field[y][x] + "|");
            System.out.print("\n");
        }
        for (int x = 0; x <= fieldSize * 2 + 1; x++)
            System.out.print("-");
        System.out.print("\n");
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

    // инициализирует игру
    private static void initGame(int sz) {
        int sizeId = arrayIndex(sz, FIELD_SIZE_ARRAY);
        if (!checkFieldSize(sz)) return;
        fieldSize = sz;
        maxTurns = (int) Math.pow(fieldSize, 2);
        maxDepth = DEPTH_ARRAY[sizeId];
        winCondition = STREAK_SIZE_ARRAY[sizeId];
        shiftField = fieldSize - winCondition + 1;
        minWinTurns = winCondition * 2 - 1;
        firstHuman = RANDOM.nextBoolean();
        field = new char[fieldSize][fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) field[j][i] = DOT_EMPTY;
        }
        turns = 1;
        gameStatus = STATUS_IN_PROGRESS;
        System.out.print(String.format("ПОЛЕ: %dx%d ПОБЕДА: %d в ряд%n", fieldSize, fieldSize, winCondition));
        System.out.print(String.format("НАЧИНАЕТ %s%n", firstHuman ? "человек > " + DOT_HUMAN : "робот > " + DOT_AI));
    }

    // ход человека
    private static void humanTurn() {
        int x, y;
        printField();
        do {
            System.out.print("введите координаты (через пробел):");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        } while (!validHumanTurn(x, y));
        field[y][x] = DOT_HUMAN;
        checkEndGame(DOT_HUMAN);
    }

    // ход робота
    private static void aiTurn() {
        if (AI_LOGIC_ENABLED) {
            char[][] pField = new char[fieldSize][fieldSize];
            for (int y = 0; y < fieldSize; y++) System.arraycopy(field[y], 0, pField[y], 0, fieldSize);
            if (turns > 1) {
                turnAiX = -1;
                turnAiY = -1;
                weightPossibleTurns(DOT_AI, pField, 0, 0, (turns < maxDepth)?maxDepth - turns: maxDepth);
                if (turnAiX != -1 && turnAiY != -1) field[turnAiY][turnAiX] = DOT_AI;
                else randomTurn();
            } else randomTurn();
        } else randomTurn();
        checkEndGame(DOT_AI);
    }

    // случайный ход
    private static void randomTurn() {
        int x, y;
        do {
            x = RANDOM.nextInt(fieldSize);
            y = RANDOM.nextInt(fieldSize);
        } while (!isEmptyCell(x, y));
        field[y][x] = DOT_AI;
    }

    // взвешивание всех возможных ходов
    private static void weightPossibleTurns(char dot, char[][] pField, int maxAi, int minAi, int depth) {
        depth--;
        if (depth < 0) return;
        for (int y = 0; y < fieldSize; y++) {
            for (int x = 0; x < fieldSize; x++) {
                int wt;
                if (pField[y][x] == DOT_EMPTY) {
                    pField[y][x] = dot;
                    wt = weightLines(dot, pField);
                    if (dot == DOT_AI) {
                        if (wt > maxAi) {
                            maxAi = wt;
                            turnAiX = x;
                            turnAiY = y;
                        }
                        dot = DOT_HUMAN;
                    } else {
                        if (wt < minAi) {
                            minAi = wt;
                            turnAiX = x;
                            turnAiY = y;
                        }
                        dot = DOT_AI;
                    }
                    if (minAi > maxAi) {
                        pField[y][x] = DOT_EMPTY;
                        return;
                    }
                    weightPossibleTurns(dot, pField, maxAi, minAi, depth);
                    pField[y][x] = DOT_EMPTY;
                }
            }
        }
    }

    // взвешивает все линии
    private static int weightLines(char dot, char[][] pField) {
        int weight = 0;
        for (int shiftY = 0; shiftY < shiftField; shiftY++) {
            for (int shiftX = 0; shiftX < shiftField; shiftX++) {
                weight = weightLine(dot, pField, shiftX, shiftY) + weightDia(dot, pField, shiftX, shiftY);
            }
        }
        return weight;
    }

    // взвешивает диагонали
    private static int weightDia(char dot, char[][] pField, int shiftX, int shiftY) {
        int weight;
        int gapL = winCondition, gapR = winCondition;
        for (int x = shiftX; x < winCondition; x++) {
            if ((byte) pField[x + shiftX][x + shiftY] == dot) gapL--;
            if ((byte) pField[fieldSize - 1 - x + shiftX][x + shiftY] == dot) gapR--;
        }
        if (gapL < 0) gapL = 0;
        if (gapR < 0) gapR = 0;
        weight = (gapL < winCondition ? WEIGHT_GAP[gapL] : 0) +
                (gapR < winCondition ? WEIGHT_GAP[gapR] : 0);
        return weight;
    }

    // взвешивание горизонталей и вертикалей
    private static int weightLine(char dot, char[][] pField, int shiftX, int shiftY) {
        int weight = 0;
        for (int y = shiftY; y < fieldSize; y++) {
            int gapV = winCondition, gapH = winCondition;
            for (int x = shiftX; x < fieldSize; x++) {
                if ((byte) pField[y][x] == dot) gapV--;
                if ((byte) pField[x][y] == dot) gapH--;
            }
            if (gapV < 0) gapV = 0;
            if (gapH < 0) gapH = 0;
            weight += (gapV < winCondition ? WEIGHT_GAP[gapV] : 0) +
                    (gapH < winCondition ? WEIGHT_GAP[gapH] : 0);
        }
        return weight;
    }
}
