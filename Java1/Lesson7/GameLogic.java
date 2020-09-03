import java.util.Random;

public class GameLogic {
    public static final int MIN_FIELD_SIZE = 3;
    public static final int MAX_FIELD_SIZE = 9;
    private static final int MAX_DEPTH = 3;
    private static final int WT_WIN = 777;

    public static final char DOT_EMPTY = ' ';
    public static final char DOT_PLAYER1 = 'X';
    public static final char DOT_PLAYER2 = '0';
    private static final char DOT_UNAVAIL = '-';

    // game status list
    public static final int STATUS_END_GAME = -3;
    public static final int STATUS_INIT = -2;
    public static final int STATUS_IN_PROGRESS = -1;
    public static final int STATUS_WIN_HUMAN = 0;
    public static final int STATUS_WIN_AI = 1;
    public static final int STATUS_DRAW = 2;
    public static final int STATUS_WIN_PLAYER1 = 3;
    public static final int STATUS_WIN_PLAYER2 = 4;
    public static final int GAME_MODE_AI = 0;
    public static final int GAME_MODE_HUMAN = 1;
    public static final String[] GAME_MODES = {"Human vs AI", "Human vs Human"};
    private static final String[] GAME_RESULT = {"HUMAN WIN", "AI WIN", "DRAW", "PLAYER 1 WIN", "PLAYER 2 WIN"};
    // game variables
    private static int gameMode = 0;
    private static char[][] field;
    private static int fieldSize;

    private static int maxTurns;
    private static int winCondition = MIN_FIELD_SIZE;
    private static int gameStatus = STATUS_INIT;

    private static int minWinTurns = 0;
    private static int turns = 1;
    private static boolean nextFirstPlayer = true;
    private static String textStatus;

    private static String lastGameResult;

    // objects
    private static final Random RANDOM = new Random();
    protected static FrameGame frameGame;

    // AI logic
    private static final boolean AI_LOGIC_ENABLED = true;
    private static int turnAiX;
    private static int turnAiY;
    private static int turnHumanX;
    private static int turnHumanY;

    public static void main(String[] args) {
        frameGame = new FrameGame();
    }

    public static String getTextStatus() {
        return (textStatus != null) ? textStatus : "";
    }

    public static int getGameMode() {
        return gameMode;
    }

    public static int getMaxTurns() {
        return maxTurns;
    }

    public static char[][] getField() {
        return field;
    }

    public static String getLastGameResult() {
        return lastGameResult;
    }

    public static int getFieldSize() {
        return fieldSize;
    }

    public static int getGameStatus() {
        return gameStatus;
    }

    public static void setTextStatus(String message) {
        if (textStatus != null && !textStatus.equals(""))
            textStatus = String.format("%s%n%s", message, textStatus);
        else
            textStatus = message;
    }

    public static void resetTextStatus() {
        textStatus = "";
    }

    public static boolean isNextFirstPlayer() {
        return nextFirstPlayer;
    }

    public static void setFieldSize(int size) throws Exception {
        if (size >= MIN_FIELD_SIZE && size <= MAX_FIELD_SIZE)
            GameLogic.fieldSize = size;
        else throw new Exception(String.format("Incompatible field size: %d expected: %d - %d",
                size, MIN_FIELD_SIZE, MAX_FIELD_SIZE));
        if (winCondition > fieldSize) setWinCondition(fieldSize);
    }

    public static void setWinCondition(int cond) throws Exception {
        if (cond <= fieldSize && cond >= MIN_FIELD_SIZE)
            GameLogic.winCondition = cond;
        else throw new Exception(String.format("Unsupported field size: %d expected: %d - %d",
                cond, MIN_FIELD_SIZE, fieldSize));
    }

    public static void setGameMode(int mode) throws Exception {
        if (mode < 0 || mode >= GAME_MODES.length) throw new Exception(String.format(
                "Game mode %d does not support, expected ", mode));
        gameMode = mode;
    }

    public static boolean isValidTurn(int x, int y) {
        return (x >= 0 && x < fieldSize) && (y >= 0 && y < fieldSize && isEmptyCell(x, y));
    }

    public static boolean isEmptyCell(int x, int y) {
        return (field[y][x] == DOT_EMPTY);
    }

    public static void humanTurn(int x, int y) throws Exception {
        if (gameStatus == STATUS_IN_PROGRESS) {
            if ((gameMode == GAME_MODE_AI && nextFirstPlayer) || gameMode == GAME_MODE_HUMAN) {
                if (!isValidTurn(x, y)) throw
                        new Exception("Incorrect turn");
                field[y][x] = (nextFirstPlayer) ? DOT_PLAYER1 : DOT_PLAYER2;
                turnHumanX = x;
                turnHumanY = y;
                setTextStatus(String.format("Human turn X:%d Y:%d", turnHumanX + 1, turnHumanY + 1));
                if (!checkEndGame(DOT_PLAYER1)) {
                    nextFirstPlayer = !nextFirstPlayer;
                    if (gameMode == GAME_MODE_AI)
                        aiTurn();
                }
            }
        }
    }

    public static void aiTurn() {
        if (gameMode == GAME_MODE_AI && gameStatus == STATUS_IN_PROGRESS &&
                AI_LOGIC_ENABLED && turns > 1 && !nextFirstPlayer) {
            char[][] pField = new char[fieldSize][fieldSize];
            turnAiX = -1;
            turnAiY = -1;
            if (!(oneTurnEnd(DOT_PLAYER2) || oneTurnEnd(DOT_PLAYER1))) {
                for (int y = 0; y < fieldSize; y++) System.arraycopy(field[y], 0, pField[y], 0, fieldSize);
                weightPossibleTurns(DOT_PLAYER2, pField, 0, 0, (turns <= MAX_DEPTH) ? MAX_DEPTH - turns : MAX_DEPTH);
            }
            if (turnAiX != -1 && turnAiY != -1) field[turnAiY][turnAiX] = DOT_PLAYER2;
            else randomTurn();
        } else randomTurn();
        setTextStatus(String.format("Ai turn X:%d Y:%d", turnAiX + 1, turnAiY + 1));
        if (!checkEndGame(DOT_PLAYER2))
            nextFirstPlayer = !nextFirstPlayer;
    }

    private static boolean checkEndGame(char dot) {
        boolean endGame = false;
        if (turns >= minWinTurns) {
            if (checkWin(dot,
                    (dot == DOT_PLAYER2) ? turnAiX : turnHumanX,
                    (dot == DOT_PLAYER2) ? turnAiY : turnHumanY)) {
                if (gameMode == GAME_MODE_AI)
                    gameStatus = dot == DOT_PLAYER1 ? STATUS_WIN_HUMAN : STATUS_WIN_AI;
                else if (gameMode == GAME_MODE_HUMAN) {
                    gameStatus = (dot == DOT_PLAYER1) ? STATUS_WIN_PLAYER1 : STATUS_WIN_PLAYER2;
                }
            } else if (turns == maxTurns) {
                gameStatus = STATUS_DRAW;
            }
            if (gameStatus > STATUS_IN_PROGRESS) {
                lastGameResult = String.format("GAME OVER: %s", GAME_RESULT[gameStatus]);
                setTextStatus(lastGameResult);
                gameStatus = STATUS_END_GAME;
                endGame = true;
            }
        }
        if (!endGame) turns++;
        return endGame;
    }

    private static void randomTurn() {
        do {
            turnAiX = RANDOM.nextInt(fieldSize);
            turnAiY = RANDOM.nextInt(fieldSize);
        } while (!isEmptyCell(turnAiX, turnAiY));
        field[turnAiY][turnAiX] = DOT_PLAYER2;
    }

    private static boolean oneTurnEnd(char dot) {
        char[][] pField = new char[fieldSize][fieldSize];
        for (int y = 0; y < fieldSize; y++) System.arraycopy(field[y], 0, pField[y], 0, fieldSize);
        for (int y = 0; y < fieldSize; y++) {
            for (int x = 0; x < fieldSize; x++) {
                if (pField[y][x] == DOT_EMPTY) {
                    pField[y][x] = dot;
                    if (weightTurn(dot, pField, x, y) >= winCondition - 1) {
                        turnAiX = x;
                        turnAiY = y;
                        return true;
                    }
                    pField[y][x] = DOT_EMPTY;
                }
            }
        }
        return false;
    }

    private static boolean checkWin(char dot, int dotX, int dotY) {
        return weightTurn(dot, field, dotX, dotY) >= WT_WIN;
    }

    private static void weightPossibleTurns(char dot, char[][] pField, int maxAi, int minAi, int depth) {
        depth--;
        if (depth < 0) return;
        for (int y = 0; y < fieldSize; y++) {
            for (int x = 0; x < fieldSize; x++) {
                int wt;
                if (pField[y][x] == DOT_EMPTY) {
                    pField[y][x] = dot;
                    wt = weightTurn(dot, pField, x, y);
                    if (dot == DOT_PLAYER1) {
                        if (wt < minAi) {
                            minAi = wt;
                            turnAiX = x;
                            turnAiY = y;
                        }
                        dot = DOT_PLAYER2;
                    } else if (dot == DOT_PLAYER2) {
                        if (wt > maxAi) {
                            maxAi = wt;
                            turnAiX = x;
                            turnAiY = y;
                        }
                        dot = DOT_PLAYER1;
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

    private static int weightTurn(char dot, char[][] fld, int dotX, int dotY) {
        char[] dotVal = new char[]{DOT_UNAVAIL, DOT_UNAVAIL, DOT_UNAVAIL, DOT_UNAVAIL};
        char[][] streakLine = new char[4][fieldSize];
        int maxWt = 0;
        int[] y = new int[]{0, 1};
        for (int i = 0; i < fieldSize; i++) {
            dotVal[0] = fld[i][dotX];
            dotVal[1] = fld[dotY][i];
            y[0] = dotY - dotX + i;
            y[1] = dotY + dotX - i;
            dotVal[2] = (y[0] >= 0 && y[0] < fieldSize) ? fld[y[0]][i] : DOT_UNAVAIL;
            dotVal[3] = (y[1] >= 0 && y[1] < fieldSize) ? fld[y[1]][i] : DOT_UNAVAIL;
            for (int j = 0; j < dotVal.length; j++)
                streakLine[j][i] = dotVal[j];
        }
        for (char[] chars : streakLine) {
            int wt;
            int streak = 0;
            int dotNum = 0;
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < fieldSize; j++) {
                if (chars[j] == dot || chars[j] == DOT_EMPTY) {
                    line.append(chars[j]);
                    if (chars[j] == dot) {
                        streak++;
                        dotNum++;
                    } else
                        streak = 0;
                } else {
                    streak = 0;
                    dotNum = 0;
                    line.setLength(0);
                }
                if (streak == winCondition)
                    return WT_WIN;
                if (line.length() >= winCondition) {
                    if (streak == winCondition - 1) wt = 100;
                    else if (streak == winCondition - 2) wt = 50;
                    else wt = dotNum;
                }
                else
                    wt = -1;
                if (maxWt < wt) maxWt = wt;
            }
        }
        return maxWt;
    }

    public static void initGame(int mode, int size, int cond) throws Exception {
        try {
            setFieldSize(size);
            setWinCondition(cond);
            setGameMode(mode);
            maxTurns = (int) Math.pow(fieldSize, 2);
            minWinTurns = winCondition * 2 - 1;
            nextFirstPlayer = gameMode != GAME_MODE_AI || RANDOM.nextBoolean();

            field = new char[fieldSize][fieldSize];
            for (int i = 0; i < fieldSize; i++) {
                for (int j = 0; j < fieldSize; j++) field[j][i] = DOT_EMPTY;
            }
            turns = 1;

            setTextStatus(String.format("GAME MODE: %s FIELD: %dx%d%nWIN CONDITION: %d IN THE ROW%n",
                    GAME_MODES[gameMode], fieldSize, fieldSize, winCondition));
            if (gameMode == GAME_MODE_AI)
                setTextStatus(String.format("FIRST TURN: %s", nextFirstPlayer ?
                        "human (" + DOT_PLAYER1 + ")" : "robot (" + DOT_PLAYER2 + ")"));
            gameStatus = STATUS_INIT;
        } catch (Exception e) {
            throw new Exception(String.format("Can't start game: %s", e.getMessage()));
        }
    }

    public static void startGame() {
        if (gameStatus == STATUS_INIT) {
            gameStatus = STATUS_IN_PROGRESS;
            if (!nextFirstPlayer) aiTurn();
        }
    }
}
