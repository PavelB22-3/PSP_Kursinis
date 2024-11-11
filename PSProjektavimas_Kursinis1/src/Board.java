import java.util.ArrayList;
import java.util.List;

public class Board {

    private char[][] board;

    public Board() {
        board = new char[8][8];
        initializeBoard();
    }

    public void initializeBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = '.';
            }
        }
        board[3][3] = 'B';
        board[3][4] = 'W';
        board[4][3] = 'W';
        board[4][4] = 'B';
    }

    public void printBoard() {
        System.out.println("  1 2 3 4 5 6 7 8");
        for (int i = 0; i < 8; i++) {
            System.out.print((i+1) +" ");
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean isValidMove(int row, int col, char playerSymbol) {
        if (board[row][col] != '.') return false;

        char opponentSymbol = (playerSymbol == 'B') ? 'W' : 'B';

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;

                if (canFlipInDirection(row, col, i, j, playerSymbol, opponentSymbol)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void updateBoard(int row, int col, char playerSymbol) {
        board[row][col] = playerSymbol;
        char opponentSymbol = (playerSymbol == 'B') ? 'W' : 'B';

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;

                if (canFlipInDirection(row, col, i, j, playerSymbol, opponentSymbol)) {
                    flipInDirection(row, col, i, j, playerSymbol);
                }
            }
        }
    }

    private boolean canFlipInDirection(int row, int col, int rowI, int colJ, char playerSymbol, char opponentSymbol) {
        int r = row + rowI;
        int c = col + colJ;
        boolean foundOpponent = false;

        while (r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] == opponentSymbol) {
            r += rowI;
            c += colJ;
            foundOpponent = true;
        }
        return foundOpponent && r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] == playerSymbol;
    }

    private void flipInDirection(int row, int col, int rowI, int colJ, char playerSymbol) {
        int r = row + rowI;
        int c = col + colJ;

        while (r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] != playerSymbol) {
            board[r][c] = playerSymbol;
            r += rowI;
            c += colJ;
        }
    }

    public List<int[]> getValidMoves(char playerSymbol) {
        List<int[]> validMoves = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (isValidMove(row, col, playerSymbol)) {
                    validMoves.add(new int[]{row, col});
                }
            }
        }
        return validMoves;
    }

    public char[][] getBoard() {
        return board;
    }
}
