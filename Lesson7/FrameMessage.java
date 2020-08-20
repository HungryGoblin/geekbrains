import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameMessage extends Frame {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 200;
    private static final String DEF_TITLE = "Message";

    JButton btnExit;
    JTextArea textArea;
    JScrollPane textPane;

    private void initButtons () {
        btnExit = new JButton("OK");
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameMessage.this.setVisible(false);
            }
        });
    }

    FrameMessage(String message) {
        this(message, DEF_TITLE);
    }

    FrameMessage(String message, String title) {
        super (Frame.getRootFrame(), WIDTH, HEIGHT, title);
        textArea = new JTextArea(message);
        textArea.setEditable(false);
        textArea.setPreferredSize(new Dimension(300, 100));
        textArea.setLineWrap(true);
        JScrollPane scrollText = new JScrollPane(textArea);
        scrollText.setEnabled(true);
        scrollText.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        initButtons();
        setLayout(new BorderLayout());
        add(scrollText, BorderLayout.NORTH);
        add(btnExit, BorderLayout.SOUTH);
        pack();
        setResizable(false);
        setVisible(true);
    }
}
