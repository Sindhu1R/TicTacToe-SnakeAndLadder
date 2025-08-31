import java.util.*;

class Move {
    int row, col;
    Move(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

class MoveList {
    List<Move> moves = new ArrayList<>();
    void addMove(int row, int col) {
        moves.add(new Move(row, col));
    }
    void printMoves() {
        int count = 1;
        for (Move m : moves) {
            System.out.println("Move " + count++ + ": " + m.row + ", " + m.col);
        }
    }
}

// -------------------- Tic Tac Toe --------------------
class TicTacToe {
    private char[][] board = new char[3][3];
    private MoveList moveList = new MoveList();

    public TicTacToe() {
        for (int i = 0; i < 3; i++)
            Arrays.fill(board[i], ' ');
    }

    private void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(" " + board[i][j] + " ");
                if (j < 2) System.out.print("|");
            }
            System.out.println();
            if (i < 2) System.out.println("---+---+---");
        }
    }

    private boolean checkWinner(char symbol) {
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) ||
                    (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol))
                return true;
        }
        return (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
                (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol);
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ')
                    return false;
        return true;
    }

    public void play() {
        Scanner sc = new Scanner(System.in);
        char player = 'X';
        while (true) {
            printBoard();
            System.out.print("Player " + player + ", enter row and column (0-2): ");
            int row = sc.nextInt();
            int col = sc.nextInt();

            if (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != ' ') {
                System.out.println("Invalid move!");
                continue;
            }

            board[row][col] = player;
            moveList.addMove(row, col);

            if (checkWinner(player)) {
                printBoard();
                System.out.println("Player " + player + " wins!");
                break;
            } else if (isBoardFull()) {
                printBoard();
                System.out.println("It's a draw!");
                break;
            }

            player = (player == 'X') ? 'O' : 'X';
        }
        System.out.println("Move history:");
        moveList.printMoves();
    }
}

// -------------------- Snake and Ladder --------------------
class SnakeAndLadder {
    private int player1 = 1, player2 = 1;
    private MoveList moveList = new MoveList();
    private Random rand = new Random();

    private int rollDice() {
        return rand.nextInt(6) + 1;
    }

    private int updatePosition(int pos) {
        int[] snakes = {16, 48, 62, 64, 93, 95, 98};
        int[] snakeTo = {6, 26, 18, 60, 68, 24, 78};
        int[] ladders = {1, 4, 9, 21, 28, 51, 72, 80};
        int[] ladderTo = {38, 14, 31, 42, 84, 67, 91, 99};

        for (int i = 0; i < snakes.length; i++) {
            if (pos == snakes[i]) {
                System.out.println("Oops! Bitten by a snake, back to " + snakeTo[i]);
                return snakeTo[i];
            }
        }
        for (int i = 0; i < ladders.length; i++) {
            if (pos == ladders[i]) {
                System.out.println("Yay! Climbed a ladder to " + ladderTo[i]);
                return ladderTo[i];
            }
        }
        return pos;
    }

    public void play() {
        Scanner sc = new Scanner(System.in);
        int currentPlayer = 1;

        System.out.println("\nWelcome to Snake and Ladder!");
        while (player1 < 100 && player2 < 100) {
            System.out.println("\nPlayer " + currentPlayer + "'s turn. Press Enter to roll the dice.");
            sc.nextLine();

            int dice = rollDice();
            System.out.println("Player " + currentPlayer + " rolled a " + dice);

            if (currentPlayer == 1) {
                player1 += dice;
                if (player1 > 100) player1 = 100;
                player1 = updatePosition(player1);
            } else {
                player2 += dice;
                if (player2 > 100) player2 = 100;
                player2 = updatePosition(player2);
            }

            moveList.addMove(player1, player2);
            System.out.println("Current Positions -> Player 1: " + player1 + ", Player 2: " + player2);

            if (player1 >= 100 || player2 >= 100) break;
            currentPlayer = (currentPlayer == 1) ? 2 : 1;
        }

        if (player1 >= 100)
            System.out.println("\nPlayer 1 wins the game!");
        else
            System.out.println("\nPlayer 2 wins the game!");

        System.out.println("Move history:");
        moveList.printMoves();
    }
}

// -------------------- Main Menu --------------------
public class GameHub {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nGame Menu:");
            System.out.println("1. Play Tic-Tac-Toe");
            System.out.println("2. Play Snake and Ladder");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1: new TicTacToe().play(); break;
                case 2: new SnakeAndLadder().play(); break;
                case 3: System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice!");
            }
        }
    }
}
