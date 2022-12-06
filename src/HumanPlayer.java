import java.util.ArrayList;
import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(Color color) {
        super(color);
    }

    @Override
    public Move getNextMove(Board board) {
        ArrayList<Move> possibleMoves = board.findPossibleMoves(super.getColor());
        System.out.println("Возможные варианты ходов (отмечены на поле символом '0'):");
        for (Move move :
                possibleMoves) {
            System.out.println(move);
        }
        System.out.println("Введите Ваш ход:");
        Scanner in = new Scanner(System.in);
        int x = -1;
        int y = -1;
        try {
            x = in.nextInt();
            y = in.nextInt();
        } catch (Exception ignored) {
        }
        while (!Move.isInBounds(x, y) || !board.checkIfInPossibleMoves(x - 1, y - 1)) {
            System.out.println("Некорректный ход, попробуйте ещё раз! Введите Ваш ход:");
            try {
                in = new Scanner(System.in);
                x = in.nextInt();
                y = in.nextInt();
            } catch (Exception e) {
                continue;
            }
        }
        return new Move(x, y);
    }
}
