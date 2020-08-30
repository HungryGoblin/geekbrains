//Полностью разобраться с кодом (попробовать полностью самостоятельно переписать одно из окон)
//Составить список вопросов и приложить в виде комментария к домашней работе
//* Раcчертить панель Map на поле для игры, согласно fieldSize

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class Frame extends JFrame implements AutoCloseable {
    public static final int DEF_WIDTH = 800;
    public static final int DEF_HEIGHT = 400;
    public static final boolean DEF_CENTERED = true;
    private static final int DEF_MARGIN = 10;

    private static int screenWidth;
    private static int screenHeight;

    private static Frame rootFrame;
    private Frame parentFrame;
    private boolean root;
    private Border border;
    protected JPanel layout;

    private boolean centered = DEF_CENTERED;

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static Frame getRootFrame() {
        return rootFrame;
    }

    private static int getCentered(int parentSize, int childSize) {
        return (parentSize - childSize) / 2;
    }

    public static void setCentered(Frame parent, Frame child) {
        child.setLocation(
                getCentered(parent.getWidth(), child.getWidth()) + parent.getX(),
                getCentered(parent.getHeight(), child.getHeight()) + parent.getY());
    }

    public void setCentered() {
        if (root) {
            setLocation(getCentered(screenWidth, this.getWidth()), getCentered(screenHeight, this.getHeight()));
        } else
            this.setLocation(
                    getCentered(parentFrame.getWidth(), this.getWidth()) + parentFrame.getX(),
                    getCentered(parentFrame.getHeight(), this.getHeight()) + parentFrame.getY());
    }

    protected void setRoot(Frame frm) throws Exception {
        if (rootFrame == null) {
            rootFrame = frm;
            root = true;
        } else throw new Exception("The root frame is already exists");
    }

    private void setFrameLayout() {
        if (layout == null) {
            border = BorderFactory.createEmptyBorder(DEF_MARGIN, DEF_MARGIN, DEF_MARGIN, DEF_MARGIN);
            layout = new JPanel();
            layout.setBorder(border);
            layout.setLayout(new BorderLayout(15, 15));
            super.add(layout, BorderLayout.CENTER);
        }
    }


    @Override
    public Component add(Component comp) {
        add(comp, BorderLayout.NORTH);
        return comp;
    }

    @Override
    public void add(Component comp, Object constr) {
        setFrameLayout();
        layout.add(comp, constr);
    }

    @Override
    public void setLayout(LayoutManager lo) {
        if (layout == null)
            super.setLayout(lo);
        else
            layout.setLayout(lo);
    }

    static {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int)screenSize.getWidth();
        screenHeight = (int)screenSize.getHeight();
    }

    {
        setFrameLayout();
    }

    // creates root frame
    Frame(int width, int height, String title) {
        try {
            setRoot(this);
        } catch (Exception e) {
            new FrameMessage(e.getMessage());
            return;
        }
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
        setTitle(title);
        if (centered) setLocation(getCentered(screenWidth, width), getCentered(screenHeight, height));
    }

    // creates child frame
    Frame(Frame parent, int width, int height, String title) {
        parentFrame = parent;
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
        setTitle(title);
        if (centered) setCentered(parent, this);
    }

    @Override
    public void close() {
        if (this.root) rootFrame = null;
        System.exit(0);
    }

}
