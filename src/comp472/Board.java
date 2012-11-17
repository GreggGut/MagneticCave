/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comp472;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

//Test
/**
 *
 * @author Grzegorz Gut
 */
public class Board
{

    private final int DEFENCE = 2;
    private final short COL = 8;
    private final short ROW = 8;
    private Random randomGenerator = new Random();
    /*
     * 0 - empty 
     * 1 - player 1
     * 2 - player 2
     */
    private int tokenPlacements[][] = new int[COL][ROW];
    private int player = 1; //can only be 1 or 2
    private boolean winnderExists = false;

    Board()
    {
        initTokens();
    }

    /**
     * Initializing the board to no tokens
     */
    private void initTokens()
    {
        //Set the whole board to no tokens
        for (int col = 0; col < COL; col++)
        {
            for (int row = 0; row < ROW; row++)
            {
                tokenPlacements[col][row] = 0;
            }
        }

        //Display board
        displayBoard();
    }

    /**
     * Change to next player
     */
    private void nextPlayer()
    {
        if (player == 1)
        {
            player = 2;
        }
        else
        {
            player = 1;
        }
    }

    /**
     * Display the board with all of it tokens
     */
    public void displayBoard()
    {
        System.out.println("\n  A B C D E F G H");
        for (int row = ROW - 1; row > -1; row--)
        {
            System.out.print((row + 1) + " ");
            for (int col = 0; col < COL; col++)
            {
                if (tokenPlacements[col][row] == 0)
                {
                    System.out.print("0 ");
                }
                else if (tokenPlacements[col][row] == 1)
                {
                    System.out.print("B ");
                }
                else
                {
                    System.out.print("W ");
                }
            }
            System.out.print((row + 1));
            if (row == 0)
            {
                break;
            }
            System.out.print('\n');
        }
        System.out.println("\n  A B C D E F G H");
    }

    /**
     *
     * @param x - x coordinates of the new token placements
     * @param y - y coordinates of the new token placements (inverted --> 8 on top and 1 at the bottom
     * @return True if the placement of the token was successful, otherwise false
     */
    public boolean addToken(Token mToken)
    {
        if ((mToken.getCol() == COL - 1) || (mToken.getCol() == 0))
        {
            if (tokenPlacements[mToken.getCol()][mToken.getRow()] == 0)
            {
                tokenPlacements[mToken.getCol()][mToken.getRow()] = player;
                checkForWinner();
                return true;
            }
            else
            {
                cannotPlaceToken();
                return false;
            }
        }

        if ((tokenPlacements[mToken.getCol() - 1][mToken.getRow()] != 0) || tokenPlacements[mToken.getCol() + 1][mToken.getRow()] != 0)
        {
            if (tokenPlacements[mToken.getCol()][mToken.getRow()] == 0)
            {
                tokenPlacements[mToken.getCol()][mToken.getRow()] = player;
                checkForWinner();
                return true;
            }
            else
            {
                cannotPlaceToken();
                return false;
            }
        }
        else
        {
            cannotPlaceToken();
            return false;
        }
    }

    /**
     * Verify if a winner exists and if the board is full
     *
     * @return
     */
    public int checkForWinner()
    {

        //int whichPlayer = 1;
        String winner;
        if (player == 1)
        {
            System.out.println("After Black turn:");
            winner = "Black";
        }
        else
        {
            System.out.println("After White turn:");
            winner = "White";
        }
        displayBoard();
        for (int col = 0; col < COL; col++)
        {
            for (int row = 0; row < ROW; row++)
            {
                //Checking for vertical wins
                if (checkVerticalWin(col, row, player))
                {
                    System.out.println("col " + (col + 1) + " row " + (row + 1) + " end row " + (row + 5) + " Player " + winner + " wins!");
                    System.out.println("Vertical win");
                    winnderExists = true;
                    return player;
                }


                //checking for horizontal wins
                if (checkHorizontalWin(col, row, player))
                {
                    System.out.println("col " + (col + 1) + " row " + (row + 1) + " end col " + (col + 5) + " Player " + winner + " wins!");
                    System.out.println("Horizontal win");
                    winnderExists = true;
                    return player;
                }

                //checking for diagonal win from bottom left to upper right
                if (checkDiagonalWin1(col, row, player))
                {
                    System.out.println("col " + (col + 1) + " row " + (row + 1) + " end col " + (col + 5) + " end row " + (row + 5) + " Player " + winner + " wins!");
                    System.out.println("Diagonal win, bottom left to upper right");
                    winnderExists = true;
                    return player;
                }

                //checking for diagonal win from bottom right to upper left
                if (checkDiagonalWin2(col, row, player))
                {
                    System.out.println("col " + (col + 1) + " row " + (row + 1) + " end col " + (col + 5) + " end row " + (row + 5) + " Player " + winner + " wins!");
                    System.out.println("Diagonal win, bottom right to upper left");
                    winnderExists = true;
                    return player;
                }
            }
        }

        nextPlayer();
        if (!isBoardFull())
        {
            return -1;
        }
        else
        {
            winnderExists = true;
        }

        return 0;
    }

    boolean checkForPossibleWinner(int who)
    {
        for (int col = 0; col < COL; col++)
        {
            for (int row = 0; row < ROW; row++)
            {
                //Checking for vertical wins
                if (checkVerticalWin(col, row, who))
                {
                    return true;
                }


                //checking for horizontal wins
                if (checkHorizontalWin(col, row, who))
                {
                    return true;
                }

                //checking for diagonal win from bottom left to upper right
                if (checkDiagonalWin1(col, row, who))
                {
                    return true;
                }

                //checking for diagonal win from bottom right to upper left
                if (checkDiagonalWin2(col, row, who))
                {
                    return true;
                }
            }
        }
        return false;
    }

    boolean checkVerticalWin(int col, int row, int who)
    {
        if (row < 4 && tokenPlacements[col][row] == who && tokenPlacements[col][row + 1] == who && tokenPlacements[col][row + 2] == who && tokenPlacements[col][row + 3] == who && tokenPlacements[col][row + 4] == who)
        {
            return true;
        }
        return false;
    }

    boolean checkHorizontalWin(int col, int row, int who)
    {
        if (col < 4 && tokenPlacements[col][row] == who && tokenPlacements[col + 1][row] == who && tokenPlacements[col + 2][row] == who && tokenPlacements[col + 3][row] == who && tokenPlacements[col + 4][row] == who)
        {
            return true;
        }
        return false;
    }

    boolean checkDiagonalWin1(int col, int row, int who)
    {
        if (row < 4 && col < 4 && tokenPlacements[col][row] == who && tokenPlacements[col + 1][row + 1] == who && tokenPlacements[col + 2][row + 2] == who && tokenPlacements[col + 3][row + 3] == who && tokenPlacements[col + 4][row + 4] == who)
        {
            return true;
        }

        return false;
    }

    boolean checkDiagonalWin2(int col, int row, int who)
    {
        if (row > 3 && col < 4 && tokenPlacements[col][row] == who && tokenPlacements[col + 1][row - 1] == who && tokenPlacements[col + 2][row - 2] == who && tokenPlacements[col + 3][row - 3] == who && tokenPlacements[col + 4][row - 4] == who)
        {
            return true;
        }

        return false;
    }

    /**
     * Verify is the board contains any empty spaces
     *
     * @return True is the board is full, otherwise false
     */
    private boolean isBoardFull()
    {
        for (int col = 0; col < COL; col++)
        {
            for (int row = 0; row < ROW; row++)
            {
                if (tokenPlacements[col][row] == 0)
                {
                    return false;
                }
            }
        }
        //winnderExists = true;
        return true;
    }

    /**
     * Is there a winner
     *
     * @return True if a winner exists, otherwise false
     */
    public boolean doesWinnderExists()
    {
        return winnderExists;
    }

    /**
     * Error message displayed each time a token cannot be placed in a given place
     */
    private void cannotPlaceToken()
    {
        System.out.println("Cannot place a token here");
    }

    /**
     * Get user input as to where to place a new token
     *
     * @return The token that correspond to the place where the user wants to place a new token
     */
    public Token getUserMove()
    {
        while (true)
        {
            try
            {
                String playerC;
                if (player == 1)
                {
                    playerC = "Black";
                }
                else
                {
                    playerC = "White";
                }
                System.out.println("Player " + player + " (" + playerC + ") turn. (eg. A1)");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                char mXc = (char) br.read();
                int mY = (int) (br.read());

                Token mToken = convertToToken(mXc, mY);
                if (mToken == null)
                {
                    continue;
                }
                return mToken;
            }
            catch (IOException ex)
            {
                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        //return null;
    }

    /**
     * Convert user input into a token with coordinates
     *
     * @param mXc The character corresponding to the column of the token
     * @param mY The integer corresponding to the row of the token
     * @return A token with the row and column coordinates of the new token
     */
    private Token convertToToken(char mXc, int mY)
    {
        int x, y;
        //Convert letter to number
        if (mXc == 'a' || mXc == 'A')
        {
            x = 0;
        }
        else if (mXc == 'b' || mXc == 'B')
        {
            x = 1;
        }
        else if (mXc == 'c' || mXc == 'C')
        {
            x = 2;
        }
        else if (mXc == 'd' || mXc == 'D')
        {
            x = 3;
        }
        else if (mXc == 'e' || mXc == 'E')
        {
            x = 4;
        }
        else if (mXc == 'f' || mXc == 'F')
        {
            x = 5;
        }
        else if (mXc == 'g' || mXc == 'G')
        {
            x = 6;
        }
        else if (mXc == 'h' || mXc == 'H')
        {
            x = 7;
        }
        else
        {
            x = -1;
        }

        //From ASCII to integer
        y = mY - 49;
        if (x >= 0 && x < 8 && y >= 0 && y < 8)
        {
            Token mToken = new Token(x, y);
            return mToken;
        }

        System.out.println("Wrong entry...");// + x + " " + y);
        return null;
    }

    public BestMove startMiniMax(int depth)
    {
        BestMove mBestMove = new BestMove();

        int maxStrength = Integer.MIN_VALUE;

        for (int row = 0; row < ROW; row++)
        {
            //From left
            if (!isRowFull(row))
            {
                //System.out.println("Not full left");

                int col = tryMove(row, false, player);
                int strength = getMinNodes(depth - 1, maxStrength);
                if (checkForPossibleWinner(player))
                {
                    //maxStrength = Integer.MAX_VALUE;
                    mBestMove.setRow(row);
                    mBestMove.setCol(col);
                    mBestMove.setStrength(strength);
                    undoTryMove(col, row);
                    return mBestMove;
                }
                if (strength >= maxStrength)
                {
                    //System.out.println("Better");
                    maxStrength = strength;
                    mBestMove.setRow(row);
                    mBestMove.setCol(col);
                    mBestMove.setStrength(strength);
                }
                undoTryMove(col, row);//, col);//false);

                //System.out.println("\nNew ROW\n");
            }

            //From right
            if (!isRowFull(row))
            {
                //System.out.println("Not full right");

                int col = tryMove(row, true, player);
                int strength = getMinNodes(depth - 1, maxStrength);
                if (checkForPossibleWinner(player))
                {
                    //maxStrength = Integer.MAX_VALUE;
                    mBestMove.setRow(row);
                    mBestMove.setCol(col);
                    mBestMove.setStrength(strength);
                    undoTryMove(col, row);
                    return mBestMove;
                }
                if (strength >= maxStrength)
                {
                    //System.out.println("Better");
                    maxStrength = strength;
                    mBestMove.setRow(row);
                    mBestMove.setCol(col);
                    mBestMove.setStrength(strength);
                }
                undoTryMove(col, row);//true);
                //System.out.println("\nNew ROW\n");
            }
        }

        //System.out.println("Player: " + player + "     with tokens: " + countNtokens());
        return mBestMove;
    }

    private int countNtokens()
    {
        int count = 0;
        for (int col = 0; col < COL; col++)
        {
            for (int row = 0; row < ROW; row++)
            {
                if (tokenPlacements[col][row] != 0)
                {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     *
     * @param depth - the depth we want to go to
     * @param min - Used for alpha beta pruning
     * @return - The maximum heuristic possible
     */
    public int getMaxNodes(int depth, int min)
    {
        if (depth == 0)// || isBoardFull())
        {
            return heuristic();
        }

        int max = Integer.MIN_VALUE;

        for (int row = 0; row < ROW; row++)
        {
            //From left
            if (!isRowFull(row))
            {
                int col = tryMove(row, false, player);
                int strength = getMinNodes(depth - 1, max);

                //Testing now
                if (checkForPossibleWinner(player))
                {
                    strength = Integer.MAX_VALUE;
                }
                //System.out.print(strength + " ");
                if (strength > min)
                {
                    undoTryMove(col, row);
                    //System.out.println("Chosen max (alpha-beta): " + strength+" ");
                    return strength;
                }
                if (strength > max)
                {
                    max = strength;
                }
                undoTryMove(col, row);
            }

            //From right
            if (!isRowFull(row))
            {
                int col = tryMove(row, true, player);
                int strength = getMinNodes(depth - 1, max);
                //Testing now
                if (checkForPossibleWinner(player))
                {
                    strength = Integer.MAX_VALUE;
                }
                //System.out.print(strength + " ");
                if (strength > min)
                {
                    undoTryMove(col, row);
                    //System.out.println("Chosen max (alpha-beta): " + strength+" ");
                    return strength;
                }
                if (strength > max)
                {
                    max = strength;
                }
                undoTryMove(col, row);
            }
        }
        //System.out.println("\nChosen max: " + max+" ");
        return max;
    }

    /**
     * Expand all the nodes and get the minimum heuristic possible
     *
     * @param depth What is the depth to which we need to extend
     * @param max The maximum value of the parent mode, used for alpha beta pruning
     * @return The minimum heuristic value
     */
    private int getMinNodes(int depth, int max)
    {
        if (depth == 0 || isBoardFull())
        {
            return heuristic();
        }

        int min = Integer.MAX_VALUE;

        int mPlayer;
        if (player == 1)
        {
            mPlayer = 2;
        }
        else
        {
            mPlayer = 1;
        }

        for (int row = 0; row < ROW; row++)
        {

            //For left
            if (!isRowFull(row))
            {
                int col = tryMove(row, false, mPlayer);
                int strength = getMaxNodes(depth - 1, min);
                //Testing now
                if (checkForPossibleWinner(mPlayer))
                {
                    //strength = Integer.MIN_VALUE;
                    undoTryMove(col, row);
                    return Integer.MIN_VALUE;
                }
                //System.out.print(strength + " ");
                if (strength < max)
                {
                    //System.out.print("Chosen min (alpha-beta): "+strength+" ");
                    undoTryMove(col, row);
                    return strength;
                }
                if (strength < min)
                {
                    min = strength;
                }
                undoTryMove(col, row);
            }

            //for right
            if (!isRowFull(row))
            {
                int col = tryMove(row, true, mPlayer);
                int strength = getMaxNodes(depth - 1, min);
                //Testing now
                if (checkForPossibleWinner(mPlayer))
                {
                    //strength = Integer.MIN_VALUE;
                    undoTryMove(col, row);
                    return Integer.MIN_VALUE;
                }
                //System.out.print(strength + " ");
                if (strength < max)
                {
                    //System.out.print("Chosen min (alpha-beta): "+strength+" ");
                    undoTryMove(col, row);
                    return strength;
                }
                if (strength < min)
                {
                    min = strength;
                }
                undoTryMove(col, row);
            }
        }
        //System.out.println("\nChosen min: "+min+" ");
        return min;
    }

    /**
     * Get the heuristic value of a given token placement
     *
     * @return
     */
    private int heuristic()
    {
        int me = player;
        int opponent;
        if (me == 1)
        {
            opponent = 2;
        }
        else
        {
            opponent = 1;
        }

        int score = 0;

        for (int col = 0; col < COL; col++)
        {
            //int points = 0;
            for (int row = 0; row < ROW; row++)
            {
                //System.out.println("Score: " + score);
                score += verticalScoreUp(col, row, player);
                score -= DEFENCE * (verticalScoreUp(col, row, opponent));
                score += verticalScoreDown(col, row, player);
                score -= DEFENCE * (verticalScoreDown(col, row, opponent));

                score += horizontalScoreRight(col, row, player);
                score -= DEFENCE * (horizontalScoreRight(col, row, opponent));
                score += horizontalScoreLeft(col, row, player);
                score -= DEFENCE * (horizontalScoreLeft(col, row, opponent));

                score += diagonalScore1Up(col, row, player);
                score -= DEFENCE * (diagonalScore1Up(col, row, opponent));
                score += diagonalScore1Down(col, row, player);
                score -= DEFENCE * (diagonalScore1Down(col, row, opponent));

                score += diagonalScore2Up(col, row, player);
                score -= DEFENCE * (diagonalScore2Up(col, row, opponent));
                score += diagonalScore2Down(col, row, player);
                score -= DEFENCE * (diagonalScore2Down(col, row, opponent));


            }
        }
        return score;
    }

    int getPossibleScore(int col, int row, int who)
    {
        int score = 0;


        return score;
    }

    int verticalScoreUp(int col, int row, int who)
    {
        int myTokens = 0;
        if (row + 4 < ROW)
        {
            for (int i = 0; i < 5; i++)
            {
                if (tokenPlacements[col][row + i] == who)
                {
                    myTokens++;
                }
                else if (tokenPlacements[col][row + i] != 0)
                {
                    return 0;
                }
            }
        }
        else
        {
            return 0;
        }

        return (int) java.lang.Math.pow(10, myTokens - 1);
    }

    int verticalScoreDown(int col, int row, int who)
    {
        int myTokens = 0;
        if (row - 4 > -1)
        {
            for (int i = 0; i < 5; i++)
            {
                if (tokenPlacements[col][row - i] == who)
                {
                    myTokens++;
                }
                else if (tokenPlacements[col][row - i] != 0)
                {
                    return 0;
                }
            }
        }
        else
        {
            return 0;
        }

        return (int) java.lang.Math.pow(10, myTokens - 1);
    }

    int horizontalScoreRight(int col, int row, int who)
    {
        int myTokens = 0;
        if (col + 4 < COL)
        {
            for (int i = 0; i < 5; i++)
            {
                if (tokenPlacements[col + i][row] == who)
                {
                    myTokens++;
                }
                else if (tokenPlacements[col + i][row] != 0)
                {
                    return 0;
                }
            }
        }
        else
        {
            return 0;
        }

        return (int) java.lang.Math.pow(10, myTokens - 1);
    }

    int horizontalScoreLeft(int col, int row, int who)
    {
        int myTokens = 0;
        if (col - 4 > -1)
        {
            for (int i = 0; i < 5; i++)
            {
                if (tokenPlacements[col - i][row] == who)
                {
                    myTokens++;
                }
                else if (tokenPlacements[col - i][row] != 0)
                {
                    return 0;
                }
            }
        }
        else
        {
            return 0;
        }

        return (int) java.lang.Math.pow(10, myTokens - 1);
    }

    int diagonalScore1Up(int col, int row, int who)
    {
        int myTokens = 0;
        if ((col + 4 < COL) && (row + 4 < ROW))
        {
            for (int i = 0; i < 5; i++)
            {
                if (tokenPlacements[col + i][row + i] == who)
                {
                    myTokens++;
                }
                else if (tokenPlacements[col + i][row + i] != 0)
                {
                    return 0;
                }
            }
        }
        else
        {
            return 0;
        }

        return (int) java.lang.Math.pow(10, myTokens - 1);
    }

    int diagonalScore1Down(int col, int row, int who)
    {
        int myTokens = 0;
        if ((col - 4 > -1) && (row - 4 > -1))
        {
            for (int i = 0; i < 5; i++)
            {
                if (tokenPlacements[col - i][row - i] == who)
                {
                    myTokens++;
                }
                else if (tokenPlacements[col - i][row - i] != 0)
                {
                    return 0;
                }
            }
        }
        else
        {
            return 0;
        }

        return (int) java.lang.Math.pow(10, myTokens - 1);
    }

    int diagonalScore2Up(int col, int row, int who)
    {
        int myTokens = 0;
        if ((col - 4 > -1) && (row + 4 < ROW))
        {
            for (int i = 0; i < 5; i++)
            {
                if (tokenPlacements[col - i][row + i] == who)
                {
                    myTokens++;
                }
                else if (tokenPlacements[col - i][row + i] != 0)
                {
                    return 0;
                }
            }
        }
        else
        {
            return 0;
        }

        return (int) java.lang.Math.pow(10, myTokens - 1);
    }

    int diagonalScore2Down(int col, int row, int who)
    {
        int myTokens = 0;
        if ((col + 4 < COL) && (row - 4 > -1))
        {
            for (int i = 0; i < 5; i++)
            {
                if (tokenPlacements[col + i][row - i] == who)
                {
                    myTokens++;
                }
                else if (tokenPlacements[col + i][row - i] != 0)
                {
                    return 0;
                }
            }
        }
        else
        {
            return 0;
        }

        return (int) java.lang.Math.pow(10, myTokens - 1);
    }

    /**
     * Check is a token can be placed in a given row
     *
     * @param row The row to be checked for an empty space
     * @return True if an empty space exists in row, otherwise false;
     */
    private boolean isRowFull(int row)
    {
        for (int col = 0; col < COL; col++)
        {
            if (tokenPlacements[col][row] == 0)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Place a token in the given row and adjust it to right or left
     *
     * @param row - The row number
     * @param leftRight - False is left, true is right
     */
    private int tryMove(int row, boolean leftRight, int mPlayer)
    {
        //Left adjust
        if (!leftRight)
        {
            for (int col = 0; col < COL; col++)
            {
                if (tokenPlacements[col][row] == 0)
                {
                    tokenPlacements[col][row] = mPlayer;
                    return col;
                }
            }
        }
        else //Right adjust
        {
            for (int col = COL - 1; col >= 0; col--)
            {
                if (tokenPlacements[col][row] == 0)
                {
                    tokenPlacements[col][row] = mPlayer;
                    return col;
                }
            }
        }

        System.out.println("Should not be here!!!!");
        return -1;
    }

    /**
     * Undo the last move
     *
     * @param col - The column coordinates for the token that needs to be removed
     * @param row - The row coordinates for the token that needs to be removed
     */
    private void undoTryMove(int col, int row)
    {
        tokenPlacements[col][row] = 0;
    }

    /**
     * This is the last step of the MiniMax algorithm, it does the actual move of the token
     *
     * @param mBestMove
     */
    public void realMove(BestMove mBestMove)
    {
        //Testing final choice
        char mCol;
        if (mBestMove.getCol() == 0)
        {
            mCol = 'A';
        }
        else if (mBestMove.getCol() == 1)
        {
            mCol = 'B';
        }
        else if (mBestMove.getCol() == 2)
        {
            mCol = 'C';
        }
        else if (mBestMove.getCol() == 3)
        {
            mCol = 'D';
        }
        else if (mBestMove.getCol() == 4)
        {
            mCol = 'E';
        }
        else if (mBestMove.getCol() == 5)
        {
            mCol = 'F';
        }
        else if (mBestMove.getCol() == 6)
        {
            mCol = 'G';
        }
        else
        {
            mCol = 'H';
        }
        System.out.println("My move: " + mCol + (mBestMove.getRow() + 1));
        ////// + mBestMove.getStrength());

        tokenPlacements[mBestMove.getCol()][mBestMove.getRow()] = player;
        checkForWinner();
    }

//    int getPlayer()
//    {
//        return player;
//    }
    
    Random generator = new Random();

    public BestMove randomMove()
    {
        BestMove b = new BestMove();
        
        boolean side = generator.nextBoolean();
        int row = generator.nextInt(ROW-1);
        
        int result=-1;
        while(result==-1)
        {
            result = tryMove(row, side, player);
        }
        
        b.setCol(result);
        b.setRow(row);
        //(int row, boolean leftRight, int mPlayer
        
        
        return b;
    }
}
