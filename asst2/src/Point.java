public class Point {
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String spaceSeparated() {
        return x + " " + y;
    }

    public int distanceTo(Point other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    private int x, y;
}
