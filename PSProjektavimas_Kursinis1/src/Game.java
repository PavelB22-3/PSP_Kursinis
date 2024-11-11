import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    public Game() {
        board = new Board();
        player1 = new Player('B');
        player2 = new Player('W');
        currentPlayer = player1;
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;

        while (!gameOver) {
            board.printBoard();
            System.out.println("Dabartinis zaidėjas: " + currentPlayer.getSymbol());
            List<int[]> validMoves = board.getValidMoves(currentPlayer.getSymbol());

            if (validMoves.isEmpty()) {
                System.out.println("Zaidejas " + currentPlayer.getSymbol() + " neturi galimu ejimu.");
                switchPlayer();
                validMoves = board.getValidMoves(currentPlayer.getSymbol());

                if (validMoves.isEmpty()) {
                    System.out.println("Abu zaidėjai neturi galimu ejimu. Zaidimas baigtas.");
                    gameOver = true;
                    break;
                } else {
                    continue;
                }
            }

            int row, col;
            while (true) {
                try {
                    System.out.print("Iveskite eilute: ");
                    row = scanner.nextInt()-1;
                    System.out.print("Iveskite stulpeli: ");
                    col = scanner.nextInt()-1;
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Klaida: iveskite tik skaicius.");
                    scanner.nextLine();
                }
            }

            if(row<0 || col<0 || row>7 || col>7) {
                System.out.println("Toks ejimas neegzistuoja. Bandykite dar karta.");
            }
            else if (board.isValidMove(row, col, currentPlayer.getSymbol())) {
                makeMove(row, col);
                switchPlayer();
            } else {
                System.out.println("Netinkamas ejimas. Bandykite dar karta.");
            }

            gameOver = isGameOver();
        }

        displayWinner();
        scanner.close();
    }

    private void makeMove(int row, int col) {
        board.updateBoard(row, col, currentPlayer.getSymbol());
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    private boolean isGameOver() {
        List<int[]> player1Moves = board.getValidMoves(player1.getSymbol());
        List<int[]> player2Moves = board.getValidMoves(player2.getSymbol());
        return player1Moves.isEmpty() && player2Moves.isEmpty();
    }

    private void displayWinner() {
        int blackCount = 0;
        int whiteCount = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getBoard()[i][j] == player1.getSymbol()) {
                    blackCount++;
                } else if (board.getBoard()[i][j] == player2.getSymbol()) {
                    whiteCount++;
                }
            }
        }

        System.out.println("Zaidimo rezultatai: ");
        System.out.println(" (B): " + blackCount);
        System.out.println(" (W): " + whiteCount);

        if (blackCount > whiteCount) {
            System.out.println("Player1 laimejo!");
        } else if (whiteCount > blackCount) {
            System.out.println("Player2 laimEjo!");
        } else {
            System.out.println("Lygiosios!");
        }
    }
}
