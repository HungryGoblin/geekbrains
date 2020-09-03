import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Field extends JPanel {

    private static final String FONT = "Courier";
    private static char[][] gameField;
    private GridLayout btnLayout;
    private JButton[] cells;
    private FrameGame frameGame;
    private int fontSize = 30;


    private static int getCellId(int x, int y) {
        return y * gameField.length + x;
    }

    public void repaintField() {
        boolean isEndGame = (GameLogic.getGameStatus() == GameLogic.STATUS_END_GAME);
        for (int y = 0; y < gameField.length; y++) {
            for (int x = 0; x < gameField.length; x++) {
                int cellId = getCellId(x, y);
                cells[cellId].setText(String.valueOf(gameField[y][x]));
                if ((gameField[y][x] != GameLogic.DOT_EMPTY) || isEndGame) cells[cellId].setEnabled(false);
            }
        }
        frameGame.updateTextStatus();
    }

    private void btnClick(JButton btn) {
        try {
            for (int y = 0; y < gameField.length; y++) {
                for (int x = 0; x < gameField.length; x++) {
                    if (cells[getCellId(x, y)] == btn) {
                        GameLogic.humanTurn(x, y);
                    }
                }
            }
        } catch (Exception e) {
            new FrameMessage(String.format("Incorrect turn: %s", e.getMessage()));
        } finally {
            repaintField();
            if (GameLogic.getGameStatus() == GameLogic.STATUS_END_GAME)
                new FrameMessage(GameLogic.getLastGameResult(), "GAME OVER");
        }

    }

    public void initField(Dimension dmn) {
        int size = GameLogic.getFieldSize();
        int buttonSize = (int) dmn.getWidth() / size;
        Dimension BUTTON_DIMENSION = new Dimension(buttonSize, buttonSize);
        if (GameLogic.getGameStatus() == GameLogic.STATUS_INIT) {
            removeAll();
            gameField = GameLogic.getField();
            btnLayout = new GridLayout(size, size);
            setLayout(btnLayout);
            cells = new JButton[GameLogic.getMaxTurns()];
            for (int i = 0; i < GameLogic.getMaxTurns(); i++) {
                cells[i] = new JButton();
                cells[i].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (GameLogic.getGameMode() == GameLogic.GAME_MODE_HUMAN || GameLogic.isNextFirstPlayer())
                            btnClick((JButton) e.getSource());
                    }
                });
                add(cells[i]);
                cells[i].setPreferredSize(BUTTON_DIMENSION);
                cells[i].setFont(new Font(FONT, Font.PLAIN, fontSize));
            }
        }
    }
    Field(FrameGame parent) {
        frameGame = parent;
    }
}
