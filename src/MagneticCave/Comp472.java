package MagneticCave;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Grzegorz Gut <gregg.gut@gmail.com>
 *
 */
public class Comp472
{
    //Depth needs to be a minimum of 1

    static final int DEPTH = 5;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {

        int mode = displayGameOptions();
        Board mBoard; //Constructor have 2 inputs: Attack coefficient and defense coefficient
        switch (mode)
        {
            case 1:
                /**
                 * Heuristic is equally defensive as attacking
                 */
                mBoard = new Board(1, 1);
                break;
            case 2:
                /**
                 * The heuristic will be much more defensive then attacking
                 */
                mBoard = new Board(1, 5);
                break;
            case 3:
                /**
                 * The heuristic will be more defensive then attacking
                 */
                mBoard = new Board(4, 5);
                break;
            default:
                /**
                 * Heuristic is equally defensive as attacking
                 */
                mBoard = new Board(1, 1);
        }

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
            {
                int iteration = 0;
                while (!mBoard.doesWinnderExists())
                {
                    System.out.println("\nIteration number " + ++iteration);

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
                    System.out.println("\nIteration number " + ++iteration);
                    //We cannot start with a depth of 0
                    BestMove mBestMove = mBoard.startMiniMax(DEPTH);
                    //System.out.println("Chosen: " + mBestMove.getStrength());
                    mBoard.realMove(mBestMove);
                }
                break;
            }
            case 3:
            {
                int iteration = 0;
                while (!mBoard.doesWinnderExists())
                {
                    System.out.println("\nIteration number " + ++iteration);

                    //We cannot start with a depth of 0
                    BestMove mBestMove = mBoard.startMiniMax(DEPTH);
                    mBoard.realMove(mBestMove);

                    if (mBoard.doesWinnderExists())
                    {
                        break;
                    }

                    System.out.println("\nIteration number " + ++iteration);

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
            }
            case 4:
            {
                int iteration = 0;
                while (!mBoard.doesWinnderExists())
                {
                    //count++;
                    System.out.println("\nIteration number " + ++iteration);
                    //We cannot start with a depth of 0
                    BestMove mBestMove = mBoard.startMiniMax(DEPTH);
                    mBoard.realMove(mBestMove);
                }
                break;
            }
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
