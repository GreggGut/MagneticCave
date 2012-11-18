/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comp472;

//Test 2
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Greg
 */
public class Comp472
{
    //Depth needs to be a minimum of 1

    static final int DEPTH = 5;//;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {

        //Options mOption = new Options();
        int mode = displayGameOptions(); ///*mOption.*/
        Board mBoard = new Board();

        int count = 0;
        switch (mode)
        {
            case 1:
                while (!mBoard.doesWinnderExists())
                {
                    Token mToken = mBoard.getUserMove();
                    if (mToken != null)
                    {
                        while (!mBoard.addToken(mToken))
                        {
                            mToken = mBoard.getUserMove();
                        }
                    }
                    if (mBoard.doesWinnderExists())
                    {
                        break;
                    }

                    mToken = mBoard.getUserMove();
                    if (mToken != null)
                    {
                        while (!mBoard.addToken(mToken))
                        {
                            mToken = mBoard.getUserMove();
                        }
                    }

                }
                break;

            case 2:
                while (!mBoard.doesWinnderExists())
                {
                    Token mToken = mBoard.getUserMove();
                    if (mToken != null)
                    {
                        while (!mBoard.addToken(mToken))
                        {
                            mToken = mBoard.getUserMove();
                        }
                    }
                    if (mBoard.doesWinnderExists())
                    {
                        break;
                    }
                    //We cannot start with a depth of 0
                    BestMove mBestMove = mBoard.startMiniMax(DEPTH);
                    //System.out.println("Chosen: " + mBestMove.getStrength());
                    mBoard.realMove(mBestMove);
                }
                break;

            case 3:

                while (!mBoard.doesWinnderExists())
                {
                    //We cannot start with a depth of 0
                    BestMove mBestMove = mBoard.startMiniMax(DEPTH);
                    //System.out.println("Chosen: " + mBestMove.getStrength());
                    mBoard.realMove(mBestMove);

                    if (mBoard.doesWinnderExists())
                    {
                        break;
                    }

                    Token mToken = mBoard.getUserMove();
                    if (mToken != null)
                    {
                        while (!mBoard.addToken(mToken))
                        {
                            mToken = mBoard.getUserMove();
                        }
                    }
                }
                break;
            case 4:
                int iteration = 0;
                while (!mBoard.doesWinnderExists())
                {
                    //count++;
                    System.out.println(++iteration);
                    //We cannot start with a depth of 0
                    BestMove mBestMove = mBoard.startMiniMax(DEPTH);
                    mBoard.realMove(mBestMove);
                }
                break;
        }
    }

    /**
     * Displays the menu available to the user
     *
     * @return
     */
    public static int displayGameOptions()
    {

        int mode;
        System.out.println("Magnetic Cave");
        System.out.println();
        System.out.println();

        /**
         * This will loop until the user chooses a valid game option
         */
        while (true)
        {
            System.out.println("Please choose mode of play");
            System.out.println("1 - Manual entry for both players");
            System.out.println("2 - Manual entry for player 1 (Black) moves & automatic moves for player 2 (White) moves");
            System.out.println("3 - Manual entry for player 2 (White) moves & automatic moves for player 1 (Black) moves");
            System.out.println("4 - Full automatic play");

            try
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                mode = Integer.parseInt(br.readLine());
                //System.out.println("Mode: " + mode);
                if (mode > 0 && mode < 5)
                {
                    return mode;
                }
                System.out.println(mode + " is not a valid choice, try again\n");
            }
            catch (IOException | NumberFormatException ex)
            {
            }
        }
    }
}
