/*
 * Author: Grzegorz Gut gregg.gut@gmail.com
 */
package comp472;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Greg
 */
public class Options
{

    Options()
    {
    }

    public int displayGameOptions()
    {

        int mode;
        System.out.println("Magnetic Cave");
        System.out.println();
        System.out.println();
        while (true)
        {
            System.out.println("Please choose mode of play");
            System.out.println("1 - Manual entry for both players");
            System.out.println("2 - Manual entry for player 1 moves & automatic moves for player 2 moves");
            System.out.println("3 - Manual entry for player 2 moves & automatic moves for player 1 moves");

            try
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                mode = Integer.parseInt(br.readLine());
                //System.out.println("Mode: " + mode);
                if (mode > 0 && mode < 4)
                {
                    return mode;
                }
                System.out.println(mode + " is not a valid choice, try again\n");
            }
            catch (IOException ex)
            {
                Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            catch (NumberFormatException ex)
            {
                
            }

        }
    }
}
