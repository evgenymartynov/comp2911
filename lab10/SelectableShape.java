import java.awt.Graphics2D;

/**
 * A shape that manages its selection state.
 */
public abstract class SelectableShape implements SceneShape {
    public void setSelected(boolean b) {
        selected = b;
    }

    public boolean isSelected() {
        return selected;
    }

    public void drawSelection(Graphics2D g2) {
        translate(1, 1);
        draw(g2);
        translate(1, 1);
        draw(g2);
        translate(-2, -2);
    }

    public void translate(int dx, int dy) {
        x += dx;
        y += dy;
    }

    protected int x, y;
    private boolean selected;
}
