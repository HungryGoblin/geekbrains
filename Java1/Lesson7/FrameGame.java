import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameGame extends Frame {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 700;
    private static final Dimension FIELD_DMN = new Dimension(500, 500);
    private static final String TITLE = "Tic tac toe";

    private final FrameSettings settings;
    private final Field field;
    private final JTextArea textArea;
    private final JPanel panelField = new JPanel();
    JButton btnStartGame;
    JButton btnExit;


    private void initButtons () {
        btnStartGame = new JButton("Start game");
        btnExit = new JButton("Exit");
        btnStartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settings.setCentered();
                settings.setVisible(true);
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameGame.this.close();
            }
        });
    }

    public void updateTextStatus(String message) {
        textArea.setText(String.format("%s%n%s", message, textArea.getText()));
    }
    public void updateTextStatus() {
        String message;
        message = GameLogic.getTextStatus();
        if (!message.trim().equals("")) {
            textArea.setText(String.format("%s%n%s", message, textArea.getText()));
            GameLogic.resetTextStatus();
        }
    }

    public void startNewGame (int mode, int size, int streak) throws Exception {
        try {
            GameLogic.initGame(mode, size, streak);
            field.initField(FIELD_DMN);
            field.repaintField();
            field.setVisible(true);
            updateTextStatus();
            GameLogic.startGame();
            field.repaintField();
            pack();
            setCentered();
            revalidate();
            repaint();
        } catch (Exception e) {
            throw new Exception(String.format("Can't start game: %s", e.getMessage()));
        }
    }

    FrameGame() {
        super (WIDTH, HEIGHT, TITLE);
        settings = new FrameSettings(this);
        textArea = new JTextArea(3,1);
        textArea.setPreferredSize(new Dimension(getWidth(), 50));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        initButtons();
        updateTextStatus(String.format("Press \"%s\" button to play", btnStartGame.getText()));
        field = new Field(this);
        panelField.setPreferredSize(FIELD_DMN);
        panelField.add(field, BorderLayout.CENTER);
        JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new GridLayout(1, 2));
        panelBottom.add(btnStartGame);
        panelBottom.add(btnExit);
        add(textArea, BorderLayout.NORTH);
        add(panelField,BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
        this.setVisible(true);
    }
}
