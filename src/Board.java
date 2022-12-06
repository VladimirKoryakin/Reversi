import java.util.ArrayList;

public class Board {
    private static final int N = 8;
    private final Chip[][] board;
    private final ArrayList<Move> possibleMoves;

    public Board() {
        board = new Chip[N][N];
        board[3][3] = new Chip(Color.WHITE);
        board[4][4] = new Chip(Color.WHITE);
        board[3][4] = new Chip(Color.BLACK);
        board[4][3] = new Chip(Color.BLACK);
        possibleMoves = new ArrayList<Move>();
    }

    public int getBlackScore() {
        int countOfBlack = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != null && board[i][j].getColor() == Color.BLACK) {
                    countOfBlack++;
                }
            }
        }
        return countOfBlack;
    }

    public int getWhiteScore() {
        int countOfWhite = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != null && board[i][j].getColor() == Color.WHITE) {
                    countOfWhite++;
                }
            }
        }
        return countOfWhite;
    }

    public boolean checkIfNoPossibleMoves() {
        return possibleMoves.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("  ");
        for (int k = 1; k < N + 1; k++) {
            res.append(k);
            res.append(' ');
        }
        res.append("\n  ");
        for (int k = 0; k < N; k++) {
            res.append("— ");
        }
        res.append("\n");
        for (int i = 0; i < N; i++) {
            res.append((i + 1) + "|");
            for (int j = 0; j < N; j++) {
                if (board[i][j] != null) {
                    res.append(board[i][j] + " ");
                } else if (checkIfInPossibleMoves(i, j)) {
                    res.append("0 ");
                } else {
                    res.append("* ");
                }
            }
            res.append("\n");
        }
        return res.toString();
    }

    public boolean checkDirection(int i, int j, int deltaI, int deltaJ, Color currentColor) {
        int countOfOpponentsChips = 0;
        boolean isFinishedWithCurrentColor = false;
        Color opponentsColor = Chip.reverseColor(currentColor);
        i += deltaI;
        j += deltaJ;
        while (Move.isInBounds(i + 1, j + 1) && board[i][j] != null) {
            if (board[i][j].getColor() == opponentsColor) {
                countOfOpponentsChips++;
            } else {
                isFinishedWithCurrentColor = true;
                break;
            }
            i += deltaI;
            j += deltaJ;
        }
        return (countOfOpponentsChips != 0 && isFinishedWithCurrentColor);
    }

    private void reverseDirection(int i, int j, int deltaI, int deltaJ, Color currentColor) {
        if (!checkDirection(i, j, deltaI, deltaJ, currentColor)) {
            return;
        }
        int countOfOpponentsChips = 0;
        Color opponentsColor = Chip.reverseColor(currentColor);
        i += deltaI;
        j += deltaJ;
        while (Move.isInBounds(i + 1, j + 1) && board[i][j] != null) {
            if (board[i][j].getColor() == opponentsColor) {
                countOfOpponentsChips++;
                board[i][j].reverse();
            } else {
                break;
            }
            i += deltaI;
            j += deltaJ;
        }
    }
    public double countScoreForDirection(int i, int j, int deltaI, int deltaJ, Color currentColor) {
        double res = 0;
        Color opponentsColor = Chip.reverseColor(currentColor);
        i += deltaI;
        j += deltaJ;
        while (Move.isInBounds(i + 1, j + 1) && board[i][j] != null) {
            if (board[i][j].getColor() == opponentsColor) {
                if (i == 0 || j == 0 || i == 7 || j == 7) {
                    res += 2;
                } else {
                    res += 1;
                }
            } else {
                break;
            }
            i += deltaI;
            j += deltaJ;
        }
        return res;
    }
    private boolean checkIfTheMoveIsPossible(Move move, Color currentColor) {
        int x = move.getX() - 1;
        int y = move.getY() - 1;
        if (board[x][y] != null) {
            return false;
        }
        Color oppositeColor = Chip.reverseColor(currentColor);
        for (int deltaX = -1; deltaX < 2; deltaX++) {
            for (int deltaY = -1; deltaY < 2; deltaY++) {
                if (deltaX == 0 && deltaY == 0) {
                    continue;
                }
                if (checkDirection(x, y, deltaX, deltaY, currentColor)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfInPossibleMoves(int x, int y) {
        x++;
        y++;
        for (Move m :
                possibleMoves) {
            if (m.getX() == x && m.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Move> findPossibleMoves(Color currentColor) {
        Move currentMove;
        Color oppositeColor = Chip.reverseColor(currentColor);
        possibleMoves.clear();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                currentMove = new Move(i + 1, j + 1);
                if (checkIfTheMoveIsPossible(currentMove, currentColor)) {
                    possibleMoves.add(currentMove);
                }
            }
        }
        return possibleMoves;
    }

    public void MakeMove(Move move, Chip chip) {
        if (!checkIfTheMoveIsPossible(move, chip.getColor())) {
            throw new IllegalArgumentException("Невозможный ход!");
        }
        int x = move.getX() - 1;
        int y = move.getY() - 1;
        Color currentColor = chip.getColor();
        Color oppositeColor = Chip.reverseColor(currentColor);
        board[x][y] = chip;
        for (int deltaX = -1; deltaX < 2; deltaX++) {
            for (int deltaY = -1; deltaY < 2; deltaY++) {
                if (deltaX == 0 && deltaY == 0) {
                    continue;
                }
                if (checkDirection(x, y, deltaX, deltaY, currentColor)) {
                    reverseDirection(x, y, deltaX, deltaY, currentColor);
                }
            }
        }
    }
}
