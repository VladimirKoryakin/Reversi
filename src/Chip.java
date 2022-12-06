public class Chip {
    private Color color;

    public Chip(Color chipColor) {
        color = chipColor;
    }

    public Color getColor() {
        return color;
    }

    public static Color reverseColor(Color color) {
        if (color == Color.BLACK) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    public void reverse() {
        color = reverseColor(color);
    }

    @Override
    public String toString() {
        if (color == Color.BLACK) {
            return "1";
        } else {
            return "2";
        }
    }
}
