import java.util.ArrayList;

public class ComputerPlayer extends Player {
    public ComputerPlayer(Color color) {
        super(color);
    }

    private double calculateGradingFunction(Board board, Move move) {
        double res = 0;
        int x = move.getX() - 1;
        int y = move.getY() - 1;
        if (x == 0 || x == 7) {
            res += 0.4;
        }
        if (y == 0 || y == 7) {
            res += 0.4;
        }
        for (int deltaX = -1; deltaX < 2; deltaX++) {
            for (int deltaY = -1; deltaY < 2; deltaY++) {
                if (deltaX == 0 && deltaY == 0) {
                    continue;
                }
                if (board.checkDirection(x, y, deltaX, deltaY, super.getColor())) {
                    res += board.countScoreForDirection(x, y, deltaX, deltaY, super.getColor());
                }
            }
        }
        return res;
    }

    @Override
    public Move getNextMove(Board board) {
        double maxFunctionResult = 0;
        Move bestMove = null;
        ArrayList<Move> possibleMoves = board.findPossibleMoves(super.getColor());
        for (Move move :
                possibleMoves) {
            if (calculateGradingFunction(board, move) > maxFunctionResult) {
                maxFunctionResult = calculateGradingFunction(board, move);
                bestMove = move;
            }
        }
        if (bestMove == null) {
            throw new RuntimeException("Нет возможных ходов!");
        }
        return bestMove;
    }

}
