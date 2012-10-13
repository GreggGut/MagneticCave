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

/**
 *
 * @author Grzegorz Gut
 */
public class Board
{

    private final short COL = 8;
    private final short ROW = 8;
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
            System.out.print('\n');
        }
    }

    /**
     *
     * @param x - x coordinates of the new token placements
     * @param y - y coordinates of the new token placements (inverted --> 8 on
     * top and 1 at the bottom
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
     * @return
     */
    public int checkForWinner()
    {
        displayBoard();
        nextPlayer();
        if (!isBoardFull())
        {
            return -1;
        }

        return 0;
    }

    /**
     * Verify is the board contains any empty spaces
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
        winnderExists = true;
        return true;
    }

    /**
     * Is there a winner
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
     * @return The token that correspond to the place where the user wants to place a new token
     */
    public Token getUserMove()
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
            return mToken;
        }
        catch (IOException ex)
        {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Convert user input into a token with coordinates
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

        System.out.println("Wrong entry..." + x + " " + y);
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
                int strength = getMaxNodes(depth - 1, maxStrength);
                if (strength >= maxStrength)
                {
                    //System.out.println("Better");
                    maxStrength = strength;
                    mBestMove.setRow(row);
                    mBestMove.setCol(col);
                    mBestMove.setStrength(strength);
                }
                undoTryMove(col, row);//, col);//false);
            }

            //From right
            if (!isRowFull(row))
            {
                //System.out.println("Not full right");

                int col = tryMove(row, true, player);
                int strength = getMaxNodes(depth - 1, maxStrength);
                if (strength >= maxStrength)
                {
                    //System.out.println("Better");
                    maxStrength = strength;
                    mBestMove.setRow(row);
                    mBestMove.setCol(col);
                    mBestMove.setStrength(strength);
                }
                undoTryMove(col, row);//true);
            }
        }

        System.out.println("Player: " + player + "     with tokens: " + countNtokens());

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
        if (depth == 0)
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
                //System.out.print(strength + " ");
                if (strength > min)
                {
                    undoTryMove(col, row);
                    //System.out.println("Chosen max!: " + strength+" ");
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
                //System.out.print(strength + " ");
                if (strength > min)
                {
                    undoTryMove(col, row);
                    //System.out.println("Chosen max!: " + strength+" ");
                    return strength;
                }
                if (strength > max)
                {
                    max = strength;
                }
                undoTryMove(col, row);
            }


        }
        //System.out.println("Chosen max: " + maxStrength+" ");
        return max;
    }

    /**
     * Expand all the nodes and get the minimum heuristic possible
     * @param depth What is the depth to which we need to extend 
     * @param max The maximum value of the parent mode, used for alpha beta pruning
     * @return The minimum heuristic value
     */
    private int getMinNodes(int depth, int max)
    {
        if (depth == 0)
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

            if (!isRowFull(row))
            {
                int col = tryMove(row, false, mPlayer);
                int strength = getMaxNodes(depth - 1, min);
                //System.out.print(strength + " ");
                if (strength < max)
                {
                    //System.out.print("Chosen min!: "+strength+" ");
                    undoTryMove(col, row);
                    return strength;
                }
                if (strength < min)
                {
                    min = strength;
                }
                undoTryMove(col, row);
            }

            if (!isRowFull(row))
            {
                int col = tryMove(row, true, mPlayer);
                int strength = getMaxNodes(depth - 1, min);
                //System.out.print(strength + " ");
                if (strength < max)
                {
                    //System.out.print("Chosen min!: "+strength+" ");
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
        //System.out.print("Chosen min: "+minStrength+" ");
        return min;
    }

    /**
     * Get the heuristic value of a given token placement
     * @return 
     */
    private int heuristic()
    {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(1000);
    }

    /**
     * Check is a token can be placed in a given row
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
     *Place a token in the given row and adjust it to right or left
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
     * @param col - The column coordinates for the token that needs to be removed
     * @param row - The row coordinates for the token that needs to be removed
     */
    private void undoTryMove(int col, int row)
    {
        tokenPlacements[col][row] = 0;
    }

    /**
     * This is the last step of the MiniMax algorithm, it does the actual move
     * of the token
     *
     * @param mBestMove
     */
    public void realMove(BestMove mBestMove)
    {
        //Testing final choice
        System.out.println("col " + mBestMove.getCol() + " row " + mBestMove.getRow() + " strenght: " + mBestMove.getStrength());

        tokenPlacements[mBestMove.getCol()][mBestMove.getRow()] = player;
        checkForWinner();
    }
}
