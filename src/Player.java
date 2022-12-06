public abstract class Player {
    private final Color color;

    public Color getColor() {
        return color;
    }
    public Player(Color playersColor) {
        color = playersColor;
    }

    public abstract Move getNextMove(Board board);
}
