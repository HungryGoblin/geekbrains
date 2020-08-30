import java.awt.*;

public class Background implements GameObject {
    private float time;
    private static final int COLORS = 1024;
    private static final int AMPLITUDE = 255 / 2;
    private static final double RAD_MULT = Math.PI * 2 / COLORS;
    private Color[] colorTable;
    private Color color;

    @Override
    public void update(GameCanvas gameCanvas, float deltaTime) {
        time += deltaTime;
        color = colorTable[(int) (AMPLITUDE + Math.round(Math.sin(time) * AMPLITUDE))];
    }

    @Override
    public void render(GameCanvas gameCanvas, Graphics g) {
        gameCanvas.setBackground(color);
    }

    public Background () {
        colorTable = new Color[COLORS];
        for (int i = 0; i < COLORS; i++) {
            int blue = Math.round(AMPLITUDE + AMPLITUDE * (float) Math.sin(i * RAD_MULT));
            int red = Math.round(AMPLITUDE + AMPLITUDE * (float) Math.sin(i * RAD_MULT * 2f));
            int green = Math.round(AMPLITUDE + AMPLITUDE * (float) Math.sin(i * RAD_MULT * 3f));
            colorTable[i] = new Color(red, green, blue);
        }
    }
}