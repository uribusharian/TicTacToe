
import java.util.Scanner;

class Game {
    static class Move {
        int row, col;

        Move() {
        }

        Move(int pos) {
            this.row = (pos - 1) / 3;
            this.col = (pos - 1) % 3;
        }
    }

    Scanner scanner = new Scanner(System.in);

    private int currentPlayer;
    private Board board;

    static char computer = 'x', player = 'o';

    static Boolean isMoveInvalid(char[][] board, Move move) {
        if (move.row > 2 || move.row < 0 || move.col > 2 || move.col < 0) {
            return true;
        }

        return board[move.row][move.col] != '_';
    }

    static Boolean isMovesLeft(char[][] board) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '_')
                    return true;
        return false;
    }

    static int evaluate(char[][] b) {
        for (int row = 0; row < 3; row++) {
            if (b[row][0] == b[row][1] &&
                    b[row][1] == b[row][2]) {
                if (b[row][0] == computer)
                    return +10;
                else if (b[row][0] == player)
                    return -10;
            }
        }

        for (int col = 0; col < 3; col++) {
            if (b[0][col] == b[1][col] &&
                    b[1][col] == b[2][col]) {
                if (b[0][col] == computer)
                    return +10;

                else if (b[0][col] == player)
                    return -10;
            }
        }

        if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
            if (b[0][0] == computer)
                return +10;
            else if (b[0][0] == player)
                return -10;
        }

        if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
            if (b[0][2] == computer)
                return +10;
            else if (b[0][2] == player)
                return -10;
        }

        return 0;
    }

    static int minimax(char[][] board, Boolean isMax) {
        int score = evaluate( board );
        if (score == 10 || score==-10)
            return score;

        if (!isMovesLeft( board ))
            return 0;

        if (isMax) {
            int best = -1000;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '_') {
                        board[i][j] = computer;
                        best = Math.max( best, minimax( board,
                                 false ) );

                        board[i][j] = '_';
                    }
                }
            }
            return best;
        } else {
            int best = 1000;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '_') {
                        board[i][j] = player;

                        best = Math.min( best, minimax( board,
                                 true ) );

                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }
    }

    static Move findBestMove(char[][] board) {
        int bestVal = -1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_') {
                    board[i][j] = computer;

                    int moveVal = minimax( board,  false );

                    board[i][j] = '_';

                    if (moveVal > bestVal) {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        return bestMove;
    }

    private void doMove() {
        Move move;
        char playerSign;

        if (this.currentPlayer == 1) {
            System.out.println( "please choose a position (1-9)" );
            int pos = scanner.nextInt();
            move = new Move(pos);

            playerSign = 'o';
        } else {
            System.out.println("Computer's move:");
            move = findBestMove(board.getState());

            playerSign = 'x';
        }

        if (isMoveInvalid( board.getState(), move )) {
            System.out.println("This move is invalid. Please choose another position (1-9).");
            return;
        }

        this.currentPlayer = (this.currentPlayer == 1 ? 2 : 1);

        board.move(move.row, move.col, playerSign);

        board.draw();
    }

    private boolean isGameOver() {
        int result = evaluate( board.getState() );

        if (result == 10) {
            System.out.println( "Computer wins this time... sorry!" );
            return true;
        } else if (!isMovesLeft( board.getState() )) {
            System.out.println( "It's a tie" );
            return true;
        } else if (result == -10) {
            System.out.println( "congratulations you won!" );
            return true;
        }

        return false;
    }

    public void start() {
        board = new Board();
        System.out.println("Welcome to Tic!Tac!Toe!\n");
        board.draw();

        System.out.println("who would you  like to start? \n"+
                "1) player \n" +
                "2) computer");
        this.currentPlayer = scanner.nextInt();

        while (true) {
            this.doMove();

            if (this.isGameOver()) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        Game myGame = new Game();
        myGame.start();
    }
}
