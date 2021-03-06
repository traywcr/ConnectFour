// Fun Project Developed by Cody Traywick
// Checks from player moves rather than iterating across the board every time

import java.util.*;

public class ConnectFour
{
  // Provide colors for distinction of players and previous placement
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_GREEN = "\u001B[32m";

  static final char UNINITIALIZED = ' ';

  // Stores player 1's moves
  ArrayList<Integer> player1Moves = new ArrayList<>();
  int [] player1Rows = new int [6];
  int [] player1Cols = new int [7];

  // Stores player 2's moves
  ArrayList<Integer> player2Moves = new ArrayList<>();
  int [] player2Rows = new int [6];
  int [] player2Cols = new int [7];

  // Helps with highlighting previous moves and the winning moves in printBoard()
  int previousMove = 0;
  int [] winningPositions = new int [4];


  public char [][] initializeBoard()
  {
    char [][] matrix = new char[6][7];
    for (int i = 0; i < matrix.length; i++)
      Arrays.fill(matrix[i], UNINITIALIZED);

    return matrix;
  }

  // Prints the board after a move is made
  public void printBoard(char [][] board, int previousMove, char player2)
  {

    int pieceRow = 0;
    int pieceCol = 0;
    int iterator = 0;

    // Iterates through the winning positions array to highlight in green
    int winningPostionsIterator = 0;

    int count = 1;
    // Add column number above each column to place pieces in
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
          // Reset the columns to not go out of bounds and increment row
          if (pieceCol > 6)
          {
            pieceCol = 0;
            pieceRow++;
          }

          if (pieceRow == winningPositions[iterator] / 10 - 1 && pieceCol
                 == winningPositions[iterator] % 10 - 1)
          {
            System.out.print(ANSI_GREEN + board[pieceRow][pieceCol++] + ANSI_RESET);
            iterator++;
            if (iterator >= 4)
              iterator = 0;
          }
          // Print last move as green to indicate the position
          else if (pieceRow == previousMove / 10 - 1 && pieceCol == previousMove % 10 - 1)
          {
            System.out.print(ANSI_GREEN + board[previousMove / 10 - 1][previousMove % 10 - 1] + ANSI_RESET);;
            pieceCol++;
          }

          // Print player 2's pieces as red
          else if (board[pieceRow][pieceCol] == player2)
            System.out.print(ANSI_RED + board[pieceRow][pieceCol++] + ANSI_RESET);
          else
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

    // If move is out of bounds pick a new one
    while (moveColumn < 0 || moveColumn > 6)
    {
      System.out.println("Invalid input, please pick a new column");
      moveColumn = move.nextInt() - 1;
    }

    // Start at bottom of column to see if there is a piece there
    for (int row = 5; row >= 0; row --)
    {
      if (board[row][moveColumn] == UNINITIALIZED)
      {
        board[row][moveColumn] = currentPlayer;
        previousMove = (row + 1) * 10 + moveColumn + 1;

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
      // If column is full call method again
      if (row == 0 && board[row][moveColumn] != UNINITIALIZED)
      {
        System.out.println("Invalid Move: Please pick another one");
        makeMove(board, currentPlayer, player1, player2);
      }
    }

    return board;
  }

  // Method to make random move in a random game
  public char [][] makeRandomMove(char [][] board, int randomNumber,
                                  char currentPlayer, char player1, char player2)
  {
    int moveColumn = randomNumber - 1;
    // Start at bottom to see if there is a piece there
    for (int row = 5; row >= 0; row --)
    {
      if (board[row][moveColumn] == UNINITIALIZED)
      {
        board[row][moveColumn] = currentPlayer;
        previousMove = (row + 1) * 10 + moveColumn + 1;

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
        randomNumber = (int)(Math.random() * 7) + 1;
        System.out.println(randomNumber);
        System.out.println("Invalid Move: Please pick another one");
        makeRandomMove(board, randomNumber, currentPlayer, player1, player2);
      }
    }
      return board;
  }

  // Helper function to call the right player's win check
  public boolean winGame(char [][] board, int player)
  {
    if (player == 0)
      return winGame(board, player1Moves, player1Rows, player1Cols);

    return winGame(board, player2Moves, player2Rows, player2Cols);
  }

  private boolean winGame(char [][] board, ArrayList<Integer> playerMoves,
                          int [] rows, int[] cols)
  {
    // Check rows first
    if (checkRows(board, playerMoves, rows, cols))
    {
      System.out.println("You won on a row!");
      return true;
    }

    // Check cols next
    if (checkCols(board, playerMoves, rows, cols))
    {
      System.out.println("You won on a column!");
      return true;
    }

    // Check diagonals
    if (checkDownRightDiagonal(board, playerMoves) || checkUpRightDiagonal(board, playerMoves))
    {
      System.out.println("You won on a diagonal!");
      return true;
    }

    // No four in a row found
    return false;
  }

  private boolean checkRows(char [][] board, ArrayList<Integer> playerMoves,
                            int [] rows, int[] cols)
  {
    // Stores positions to check for win
    ArrayList<Integer> winCheckRow = new ArrayList<>();

    for (int i = 0; i < rows.length; i++)
      if (rows[i] >= 4)
      {
        for (int j = 1; j <= cols.length; j++)
        {
          if(playerMoves.contains((i + 1) * 10 + j))
            winCheckRow.add((i + 1) * 10 + j);

          // Allows to check elements to the right rather than both ways
          Collections.sort(winCheckRow);

          for (int k = 0; k < winCheckRow.size(); k++)
          {
            // Stop inner loop if there are not 4 since there can't be 4 in a row
            if (winCheckRow.size() < 4)
              break;
            // If the next space has been used and is not the player's piece remove it
            else if (winCheckRow.get(0) + 1 != winCheckRow.get(1) &&
                     board[i][winCheckRow.get(1) % 10 - 1] != UNINITIALIZED)
            {
              winCheckRow.remove(0);
              continue;
            }
            // If 4 in a row, there is a winner
            else if (winCheckRow.get(0) + 1 == winCheckRow.get(1) && winCheckRow.get(1) + 1 ==
                     winCheckRow.get(2) && winCheckRow.get(2) + 1 == winCheckRow.get(3))
            {
              // Add winning positions to the array for printing
              for (int z = 0; z < winningPositions.length; z++)
                winningPositions[z] = winCheckRow.get(z);

              return true;
            }
          }

        }
      }
      return false;
  }

  private boolean checkCols(char [][] board, ArrayList<Integer> playerMoves,
                            int [] rows, int[] cols)
  {
    // Stores positions to check for win
    ArrayList<Integer> winCheckCol = new ArrayList<>();

    for (int i = 0; i < cols.length; i++)
      if (cols[i] >= 4)
      {
        for (int j = 1; j <= rows.length; j++)
        {
          if(playerMoves.contains((j) * 10 + (i + 1)))
            winCheckCol.add((j) * 10 + (i + 1));

          // Allows to check elements to the right rather than both ways
          Collections.sort(winCheckCol);

          for (int k = 0; k < winCheckCol.size(); k++)
          {
            // Stop inner loop if there are not 4 since there can't be 4 in a row
            if (winCheckCol.size() < 4)
              break;
            // If the next space has been used and is not the player's piece remove it
            else if (winCheckCol.get(0) + 10 != winCheckCol.get(1) &&
                     board[winCheckCol.get(1) / 10 - 1][i] != UNINITIALIZED)
            {
              winCheckCol.remove(0);
              continue;
            }
            // If 4 in a column, there is a winner
            else if (winCheckCol.get(0) + 10 == winCheckCol.get(1) && winCheckCol.get(1) + 10 ==
                     winCheckCol.get(2) && winCheckCol.get(2) + 10 == winCheckCol.get(3))
            {
              // Add winning positions to the array for printing
              for (int z = 0; z < winningPositions.length; z++)
                winningPositions[z] = winCheckCol.get(z);

              return true;
            }
          }

        }
      }
      return false;
  }

  private boolean checkDownRightDiagonal(char [][] board, ArrayList<Integer> playerMoves)
  {
    // Stores all the down right diagonal values to get frequency
    ArrayList<Integer> topLeftDownRight = new ArrayList<>();
    ArrayList<Integer> winTLDR = new ArrayList<>();

    int numColumns = 7;

    for (int i = 0; i < playerMoves.size(); i++)
      topLeftDownRight.add(playerMoves.get(i) % 10 - playerMoves.get(i) / 10 +
                           (numColumns - 1));

    // Iterate over the down right diagonals that can cause a winning condition
    for (int i = 4; i <= 9; i++)
    {
      if (Collections.frequency(topLeftDownRight, i) >= 4)
      {
        // Only 4 pieces can fit in diagonals 4 and 9 so if it is filled then they win
        if (i == 4 || i == 9)
        {
          if (i == 4)
          {
            winningPositions[0] = 31;
            for (int j = 1; j < winningPositions.length; j++)
              winningPositions[j] = winningPositions[j - 1] + 11;

            return true;
          }
          if (i == 9)
          {
            winningPositions[0] = 14;
            for (int j = 1; j < winningPositions.length; j++)
              winningPositions[j] = winningPositions[j - 1] + 11;

            return true;
          }
            return true;
        }

        // If the player's moves are equal to the desired diagonal add them to the check
        for (int row = 0; row < playerMoves.size(); row++)
            if (playerMoves.get(row) % 10 - playerMoves.get(row) / 10 +
               (numColumns - 1) == (i))
                winTLDR.add(playerMoves.get(row));

        // Sort array to check elements in the right down diagonal
        Collections.sort(winTLDR);
        System.out.println("TLDR: " + winTLDR);

        for (int j = 0; j < winTLDR.size(); j++)
        {
          // If the win check size gets below 4 then break out of loop
          if (winTLDR.size() < 4)
            break;

          // If the next piece in the diagonal is not equal to the player's piece
          // then remove the first piece and continue loop
          else if (winTLDR.get(0) + 11 != winTLDR.get(1) &&
                     board[(winTLDR.get(0) / 10)][winTLDR.get(0) % 10] != UNINITIALIZED)
          {
            winTLDR.remove(0);
            continue;
          }
          // If four consecutive pieces are found, we have a winner
          else if (winTLDR.get(0) + 11 == winTLDR.get(1) && winTLDR.get(1) + 11 ==
                   winTLDR.get(2) && winTLDR.get(2) + 11 == winTLDR.get(3))
          {
            // Add winning positions to the array for printing
            for (int z = 0; z < winningPositions.length; z++)
              winningPositions[z] = winTLDR.get(z);

            return true;
          }
        }
      }
    }


    return false;
  }

  private boolean checkUpRightDiagonal(char [][] board, ArrayList<Integer> playerMoves)
  {
    // Stores all the up right diagonal values to get frequency
    ArrayList<Integer> bottomLeftUpRight = new ArrayList<>();

    ArrayList<Integer> winBLUR = new ArrayList<>();



    for (int i = 0; i < playerMoves.size(); i++)
      bottomLeftUpRight.add(playerMoves.get(i) % 10 + playerMoves.get(i) / 10);


    // Iterate over the up right diagonals that can cause a winning condition
    for (int i = 5; i <= 10; i++)
    {
      if (Collections.frequency(bottomLeftUpRight, i) >= 4)
      {
        // Only 4 pieces can fit in diagonals 5 and 10 so if it is filled then they win
        if (i == 5 || i == 10)
        {
          if (i == 5)
          {
            winningPositions[0] = 14;
            for (int j = 1; j < winningPositions.length; j++)
              winningPositions[j] = winningPositions[j - 1] + 9;

            return true;
          }
          if (i == 10)
          {
            winningPositions[0] = 37;
            for (int j = 1; j < winningPositions.length; j++)
              winningPositions[j] = winningPositions[j - 1] + 9;

            return true;
          }
          return true;
        }

      // If the player's moves are equal to the desired diagonal add them to the check
      for (int row = 0; row < playerMoves.size(); row++)
          if (playerMoves.get(row) % 10 + playerMoves.get(row) / 10  == i)
              winBLUR.add(playerMoves.get(row));

      // Sort array to check elements in the right down diagonal
      Collections.sort(winBLUR);

      for (int j = 0; j < winBLUR.size(); j++)
      {
        // If the win check size gets below 4 then break out of loop
        if (winBLUR.size() < 4)
          break;
        // If the rows or the columns getting checked are out of bounds, continue
        else if (winBLUR.get(0) / 10 - 2 < 0 || winBLUR.get(0) / 10 - 2 > 6
                || winBLUR.get(0) % 10 < 0 || winBLUR.get(0) % 10 > 6)
          continue;
        // If the next piece in the diagonal is not equal to the player's piece
        // then remove the first piece and continue loop
        else if (winBLUR.get(0) + 9 != winBLUR.get(1) &&
                   board[(winBLUR.get(0) / 10 - 2)][winBLUR.get(0) % 10] != UNINITIALIZED)
        {
          winBLUR.remove(0);
          continue;
        }
        // If four consecutive pieces are found, we have a winner
        else if (winBLUR.get(0) + 9 == winBLUR.get(1) && winBLUR.get(1) + 9 ==
                 winBLUR.get(2) && winBLUR.get(2) + 9 == winBLUR.get(3))
        {
          // Add winning positions to the array for printing
          for (int z = 0; z < winningPositions.length; z++)
            winningPositions[z] = winBLUR.get(z);

          return true;
        }
      }
    }
  }
    return false;
  }

  // Clears screen to print a new board
  // It makes it look like it's updating the without printing a new one
  public static void clearScreen()
  {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public static void playConnect4()
  {
    ConnectFour game = new ConnectFour();

    char player1, player2;
      clearScreen();

    Scanner scan = new Scanner(System.in);
    System.out.println("Player 1: What is the first letter of your name? ");
    player1 = scan.next().charAt(0);
    System.out.println("Player 2: What is the first letter of your name? ");
    player2 = scan.next().charAt(0);
    while (player2 == player1)
    {
      System.out.println("Sorry this is the same as player 1. Please pick another character.");
      player2 = scan.next().charAt(0);
    }

    // Allows for comparison in makeMoves to store moves in respective arrays
    char p1, p2;
    p1 = player1;
    p2 = player2;

    char gameBoard [][] = game.initializeBoard();
    clearScreen();
    game.printBoard(gameBoard, 11 , player2);

    // Calls the correct winGame method for the respective player
    int playerNumber = 0;
    boolean somebodyWon = false;

    while (!somebodyWon && playerNumber < 42)
    {
      if (playerNumber % 2 == 0)
      {
        gameBoard = game.makeMove(gameBoard, player1, p1, p2);
        clearScreen();
        game.printBoard(gameBoard, game.previousMove, player2);
        if (game.winGame(gameBoard, ((playerNumber % 2))))
          somebodyWon = true;
      }

      else
      {
        gameBoard = game.makeMove(gameBoard, player2, p1, p2);
        clearScreen();
        game.printBoard(gameBoard, game.previousMove, player2);
        if (game.winGame(gameBoard, ((playerNumber % 2))))
          somebodyWon = true;
      }

      playerNumber++;
    }
    clearScreen();

    game.printBoard(gameBoard, game.previousMove, player2);

    if (playerNumber < (gameBoard.length * (gameBoard.length + 1)))
      {
        System.out.println("Player " + (((playerNumber - 1) % 2) + 1) + " Won!!!");
        System.out.print("Winning Postions: ");
        for (int i = 0; i < game.winningPositions.length; i++)
          System.out.print(game.winningPositions[i] + " ");
      }
    else
      System.out.println("Nobody Wins...");

    System.out.println();
  }


  public static void randomGame()
  {
    ConnectFour gamerrr = new ConnectFour();
    char player1 = '1', player2 = '2';
    clearScreen();

    char p1, p2;
    p1 = player1;
    p2 = player2;

    char gameBoard [][] = gamerrr.initializeBoard();
    clearScreen();
    gamerrr.printBoard(gameBoard, 11 , player2);

    int playerNumber = 0;
    boolean somebodyWon = false;


    while (!somebodyWon && playerNumber < 42)
    {
      try
      {
        Thread.sleep(200);
      }
      catch(InterruptedException ex)
      {
        Thread.currentThread().interrupt();
      }

      int r = (int)(Math.random() * 7) + 1;
      if (playerNumber % 2 == 0)
      {
        gameBoard = gamerrr.makeRandomMove(gameBoard, r, player1, p1, p2);
        clearScreen();
        gamerrr.printBoard(gameBoard, gamerrr.previousMove, player2);
        if (gamerrr.winGame(gameBoard, ((playerNumber % 2))))
          somebodyWon = true;
      }

      else
      {
        gameBoard = gamerrr.makeRandomMove(gameBoard, r, player2, p1, p2);
        clearScreen();
        gamerrr.printBoard(gameBoard, gamerrr.previousMove, player2);
        if (gamerrr.winGame(gameBoard, ((playerNumber % 2))))
          somebodyWon = true;
      }

      playerNumber++;
    }
    clearScreen();

    gamerrr.printBoard(gameBoard, gamerrr.previousMove, player2);

    if (playerNumber < (gameBoard.length * (gameBoard.length + 1)))
    {
      System.out.println("Player " + (((playerNumber - 1) % 2) + 1) + " Won!!!");
      System.out.print("Winning Postions: ");
      for (int i = 0; i < gamerrr.winningPositions.length; i++)
        System.out.print(gamerrr.winningPositions[i] + " ");
    }
    else
      System.out.println("Nobody Wins...");

    System.out.println();
  }


  public static void main(String [] args)
  {
    playConnect4();


    // Makes 100 random games
    /*
      for (int i = 0; i < 100; i++)
        {
          randomGame();
          try
          {
            Thread.sleep(2000);
          }
          catch(InterruptedException ex)
          {
            Thread.currentThread().interrupt();
          }
        }
      */
  }

}
