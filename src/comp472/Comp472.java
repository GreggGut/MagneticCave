/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comp472;

//Test 2
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
        
        Options mOption = new Options();
        int mode = mOption.displayGameOptions();
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
                    if(mBoard.doesWinnderExists())
                    {
                        break;
                    }
                    //We cannot start with a depth of 0
                    BestMove mBestMove = mBoard.startMiniMax(DEPTH);
                    System.out.println("Chosen: " + mBestMove.getStrength());
                    mBoard.realMove(mBestMove);
                }
                break;

            case 3:

                while (!mBoard.doesWinnderExists())
                {
                    //We cannot start with a depth of 0
                    BestMove mBestMove = mBoard.startMiniMax(DEPTH);
                    System.out.println("Chosen: " + mBestMove.getStrength());
                    mBoard.realMove(mBestMove);
                    
                    if(mBoard.doesWinnderExists())
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
                    
//                    if(mBoard.doesWinnderExists())
//                    {
//                        break;
//                    }
//                    BestMove nextmove=mBoard.randomMove();
//                    mBoard.realMove(nextmove);
                }
                break;
        }
    }
}
