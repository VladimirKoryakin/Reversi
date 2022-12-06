public class Move {
    private final int x;
    private final int y;
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public Move(int a, int b) {
        if (!isInBounds(a, b)) {
            throw new IllegalArgumentException("Некорректные координаты хода!");
        }
        x = a;
        y = b;
    }
    public static boolean isInBounds(int x, int y) {
        return x <= 8 && x >= 1 && y <= 8 && y >= 1;
    }
    @Override
    public String toString() {
        return x + " " + y;
    }
}
