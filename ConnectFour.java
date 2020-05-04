import java.util.*;

public class ConnectFour
{
  static final char UNINITIALIZED = ' ';
  ArrayList<Integer> player1Moves = new ArrayList<>();
  int [] player1Rows = new int [6];
  int [] player1Cols = new int [7];

  ArrayList<Integer> player2Moves = new ArrayList<>();
  int [] player2Rows = new int [6];
  int [] player2Cols = new int [7];


  public char [][] initializeBoard()
  {
    char [][] matrix = new char[6][7];
    for (int i = 0; i < matrix.length; i++)
      Arrays.fill(matrix[i], UNINITIALIZED);

    return matrix;
  }

  public void printBoard(char [][] board)
  {
    int count = 1;
    int pieceRow = 0;
    int pieceCol = 0;


    System.out.print("  " + count++);
    while (count < 8)
      System.out.print("   " + count++);

    System.out.println();

    for (int row = 0; row < 13; row++)
    {
      for (int col = 0; col < 29; col++)
      {
        if (row % 2 == 0 || row == 0)
          System.out.print("=");
        else if (col % 4 == 0)
          System.out.print("|");
        else if (col % 4 == 1 || col % 4 == 3)
          System.out.print(" ");

        else
        {
          if (pieceCol > 6)
          {
            pieceCol = 0;
            pieceRow++;
          }

          System.out.print(board[pieceRow][pieceCol++]);

        }
      }
      System.out.println();
    }
  }

  public char [][] makeMove(char [][] board, char currentPlayer, char player1, char player2)
  {

    System.out.println();
    System.out.println("Pick a column to place your piece in: ");

    Scanner move = new Scanner(System.in);
    int moveColumn = move.nextInt() - 1;

    // Start at bottom to see if there is a piece there
    for (int row = 5; row >= 0; row --)
    {
      if (board[row][moveColumn] == UNINITIALIZED)
      {
        board[row][moveColumn] = currentPlayer;

        if (currentPlayer == player1)
        {
          player1Moves.add((row + 1) * 10 + moveColumn + 1);
          player1Rows[row]++;
          player1Cols[moveColumn]++;
        }
        else
        {
          player2Moves.add((row + 1) * 10 + moveColumn + 1);
          player2Rows[row]++;
          player2Cols[moveColumn]++;
        }
        return board;
      }
      if (row == 0 && board[row][moveColumn] != UNINITIALIZED)
      {
        System.out.println("Invalid Move: Please pick another one");
        makeMove(board, currentPlayer, player1, player2);
      }

    }

    return board;
  }

  public boolean winGame(char [][] board)
  {
    // if four in a row
      // return true;

    // TODO: trigger if there is 4 on the same row
      // if row is the same and 4 consecutive columns
        // return true;

    // TODO: trigger if there is 4 on the same column
      // if column is the same and 4 consecutive rows
        // return true;

      // if


    return false;
  }



  public static void main(String [] args)
  {
    ConnectFour game = new ConnectFour();

    char player1, player2;

    int count = 0;

    Scanner scan = new Scanner(System.in);
    System.out.println("Player 1: What is the first letter of your name? ");
    player1 = scan.next().charAt(0);
    System.out.println("Player 2: What is the first letter of your name? ");
    player2 = scan.next().charAt(0);

    // Allows for comparison in makeMoves to store moves in respective arrays
    char p1, p2;
    p1 = player1;
    p2 = player2;

    char gameBoard [][] = game.initializeBoard();

    game.printBoard(gameBoard);

    while (count < 5)
    {
      gameBoard = game.makeMove(gameBoard, player1, p1, p2);
      game.printBoard(gameBoard);

      gameBoard = game.makeMove(gameBoard, player2, p1, p2);
      game.printBoard(gameBoard);

      count++;
    }

    System.out.println("\n\n" + game.player1Moves);
    System.out.print("P1 Rows: ");
    for (int i = 0; i < game.player1Rows.length; i++)
      System.out.print(game.player1Rows[i]);

    System.out.println();
    System.out.print("P1 Cols: ");
    for (int i = 0; i < game.player1Cols.length; i++)
        System.out.print(game.player1Cols[i]);

    System.out.println();
    System.out.println(game.player2Moves);
    for (int i = 0; i < game.player2Rows.length; i++)
      System.out.print(game.player2Rows[i]);

    System.out.println();
    System.out.print("P1 Cols: ");
    for (int i = 0; i < game.player2Cols.length; i++)
        System.out.print(game.player2Cols[i]);

    Collections.sort(game.player1Moves);
    Collections.sort(game.player2Moves);

    System.out.println("\n\n" + game.player1Moves);
    System.out.println(game.player2Moves);

    /*for (int i = 0; i < gameBoard.length; i++)
    {
      for (int j = 0; j < gameBoard.length + 1; j++)
        System.out.print(gameBoard[i][j] + " . ");

      System.out.println();
      */
    }

}

/*      1   2   3   4   5   6   7
      =============================
      |   |   |   |   |   |   |   |
      =============================
      |   |   |   |   |   |   |   |
      =============================
      |   |   |   |   |   |   |   |
      =============================
      |   |   |   |   |   |   |   |
      =============================
      |   |   |   |   |   |   |   |
      =============================
      |   |   |   |   |   |   |   |
      =============================
      /\                         /\
*/
