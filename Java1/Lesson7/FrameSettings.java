import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameSettings extends Frame {

    private static final int WIDTH = 350;
    private static final int HEIGHT = 250;
    private static final String TITLE = "Game settings";
    private static final String PREFIX_FIELD_SIZE = "Field size";
    private static final String PREFIX_WIN_STREAK = "Win streak length";

    private FrameGame frameGame;
    private JRadioButton[] gameModesRadioButtons;
    private JSlider slideFieldSize;
    private JSlider slideWinStreak;

    private void addGameFieldControls() {
        JLabel lbFieldSize = new JLabel(String.format("%s: %d", PREFIX_FIELD_SIZE, GameLogic.MIN_FIELD_SIZE));
        JLabel lbWinStreak = new JLabel(String.format("%s: %d", PREFIX_WIN_STREAK, GameLogic.MIN_FIELD_SIZE));
        slideFieldSize = new JSlider(GameLogic.MIN_FIELD_SIZE, GameLogic.MAX_FIELD_SIZE, GameLogic.MIN_FIELD_SIZE);
        slideWinStreak = new JSlider(GameLogic.MIN_FIELD_SIZE, GameLogic.MIN_FIELD_SIZE, GameLogic.MIN_FIELD_SIZE);

        slideFieldSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int currentValue = slideFieldSize.getValue();
                lbFieldSize.setText(String.format("%s: %d", PREFIX_FIELD_SIZE, currentValue));
                slideWinStreak.setMaximum(currentValue);
            }
        });
        slideWinStreak.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                lbWinStreak.setText(String.format("  %s: %d ", PREFIX_WIN_STREAK, slideWinStreak.getValue()));
            }
        });
        add(lbFieldSize);
        add(slideFieldSize);
        add(lbWinStreak);
        add(slideWinStreak);
        JButton btnStart = new JButton("Start game");
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnStartClick();
            }
        });
        add(btnStart, BorderLayout.SOUTH);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    private void addGameModeControls() {
        gameModesRadioButtons = new JRadioButton[GameLogic.GAME_MODES.length];
        ButtonGroup gameModesGroup = new ButtonGroup();
        add(new JLabel("GAME MODE:"), BorderLayout.NORTH);
        for (int i = 0; i < GameLogic.GAME_MODES.length; i++) {
            gameModesRadioButtons[i] = new JRadioButton(GameLogic.GAME_MODES[i], (i == 0));
            gameModesGroup.add(gameModesRadioButtons[i]);
            add(gameModesRadioButtons[i]);
        }
        addGameFieldControls();
    }

    private int getGameMode() {
        int gameMode = -1;
        for (int i = 0; i < gameModesRadioButtons.length; i++) {
            if (gameModesRadioButtons[i].isSelected()) {
                gameMode = i;
                break;
            }
        }
        return gameMode;
    }

    private void btnStartClick() {
        try {
            frameGame.startNewGame(getGameMode(), slideFieldSize.getValue(), slideWinStreak.getValue());
        } catch (Exception e) {
            new FrameMessage(e.getMessage());
            return;
        }
        setVisible(false);
        frameGame.btnStartGame.setText("Restart game");
    }

    FrameSettings(Frame parent) {
        super(parent, WIDTH, HEIGHT, TITLE);
        frameGame = (FrameGame) parent;
        setResizable(false);
        layout.setLayout(new GridLayout(9, 1));
        addGameModeControls();
        pack();
    }

}
