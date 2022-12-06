import java.util.Scanner;

public class GameController {
    private int bestScore = -1;
    private int getBestPlayerVersusComputerScore() {
        return bestScore;
    }

    public void startGame() {
        System.out.println("""
                Добро пожаловать в игру 'Реверси'! Надеюсь, Вы знаете правила.
                Начинают чёрные(их фишки помечаются символом '1', белые -  '2').
                В координатах хода первое число отвечает за строку, а второе за столбец.""");
        chooseMode();
    }

    public void chooseMode() {
        System.out.println("""
                Выберите режим игры или посмотрите Ваш лучший счёт в режиме 'Игрок против компьютера'(введите 1, 2, 3 или 0):
                1) Игрок против компьютера
                2) Игрок против игрока
                3) Просмотр лучшего результата
                0) Выход""");
        Scanner in = new Scanner(System.in);
        int commandNumber = -1;
        try {
            commandNumber = in.nextInt();
        } catch (Exception e) {
            System.out.println("Некорректный номер команды! Попробуйте ещё раз:");
        }
        while (commandNumber < 0 || commandNumber > 3) {
            try {
                in = new Scanner(System.in);
                commandNumber = in.nextInt();
                if (commandNumber < 0 || commandNumber > 3) {
                    System.out.println("Некорректный номер команды! Попробуйте ещё раз:");
                }
            } catch (Exception e) {
                System.out.println("Некорректный номер команды! Попробуйте ещё раз:");
            }
        }
        switch (commandNumber) {
            case 1:
                startPlayerVersusComputer();
                chooseMode();
                break;
            case 2:
                startPlayerVersusPlayer();
                chooseMode();
                break;
            case 3:
                printBestScore();
                chooseMode();
                break;
            default:
                return;
        }
    }

    public void printBestScore() {
        if (getBestPlayerVersusComputerScore() == -1) {
            System.out.println("Вы ещё не играли против компьютера в этой сессии :(");
        } else {
            System.out.println("Ваш лучший счёт за текущую сессию: " + getBestPlayerVersusComputerScore() +
                    ". Поздравляю!");
        }
    }

    public void startPlayerVersusComputer() {
        Board board = new Board();
        Player human = new HumanPlayer(Color.BLACK);
        Player bot = new ComputerPlayer(Color.WHITE);
        Player currentPlayer;
        int countOfTurns = 0;
        while (true) {
            currentPlayer = human;
            if (countOfTurns % 2 == 1) {
                currentPlayer = bot;
                System.out.println("Ход компьютера:");
            }
            board.findPossibleMoves(currentPlayer.getColor());
            if (board.checkIfNoPossibleMoves() && (board.getBlackScore() + board.getWhiteScore()) == 64) {
                break;
            } else if (board.checkIfNoPossibleMoves()) {
                System.out.println("Пропуск хода, так как нет возможных ходов!");
                countOfTurns++;
                continue;
            }
            System.out.println(board);
            if (countOfTurns % 2 == 1) {
                System.out.println("Компьютер сделал ход " + currentPlayer.getNextMove(board));
            }
            board.MakeMove(currentPlayer.getNextMove(board), new Chip(currentPlayer.getColor()));
            countOfTurns++;
        }
        int playersScore = board.getBlackScore();
        int botsScore = board.getWhiteScore();
        if (playersScore > bestScore) {
            bestScore = playersScore;
        }
        if (playersScore > botsScore) {
            System.out.println("Поздравляю, Вы победили со счётом " + playersScore + ":" + botsScore + " !");
        } else if (playersScore < botsScore) {
            System.out.println("Вы достойно бились, но, к сожалению, проиграли со счётом " +
                    playersScore + ":" + botsScore + " !");
        } else {
            System.out.println("Ого, редкий случай, Вы сыграли вничью со счётом " +
                    playersScore + ":" + botsScore + " !");
        }
    }

    public void startPlayerVersusPlayer() {
        Board board = new Board();
        Player player1 = new HumanPlayer(Color.BLACK);
        Player player2 = new HumanPlayer(Color.WHITE);
        Player currentPlayer;
        int countOfTurns = 0;
        while (true) {
            currentPlayer = player1;
            if (countOfTurns % 2 == 1) {
                currentPlayer = player2;
            }
            board.findPossibleMoves(currentPlayer.getColor());
            if (board.checkIfNoPossibleMoves() && (board.getBlackScore() + board.getWhiteScore()) == 64) {
                break;
            } else if (board.checkIfNoPossibleMoves()) {
                System.out.println("Пропуск хода, так как нет возможных ходов!");
                countOfTurns++;
                continue;
            }
            System.out.println("Ход игрока номер " + (countOfTurns % 2 + 1) + "!");
            System.out.println(board);
            board.MakeMove(currentPlayer.getNextMove(board), new Chip(currentPlayer.getColor()));
            countOfTurns++;
        }
        int player1Score = board.getBlackScore();
        int player2Score = board.getWhiteScore();
        if (player1Score > player2Score) {
            System.out.println("Первый игрок победил со счётом " + player1Score + ":" + player2Score + " !");
        } else if (player1Score < player2Score) {
            System.out.println("Второй игрок победил со счётом " + player1Score + ":" + player2Score + " !");
        } else {
            System.out.println("Ого, редкий случай, Вы сыграли вничью со счётом " +
                    player1Score + ":" + player2Score + " !");
        }
    }
}
