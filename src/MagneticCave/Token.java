/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MagneticCave;

/**
 *
 * @author Greg
 */
public class Token
{

    private int col;
    private int row;

    /**
     * Default constructor
     * @param mx The X coordinates of the token
     * @param my The y coordinates of the token
     */
    Token(int mx, int my)
    {
        col = mx;
        row = my;
    }

    /**
     * Returns the column of the token
     * @return 
     */
    public int getCol()
    {
        return col;
    }

    /**
     * Returns the row of the token
     * @return 
     */
    public int getRow()
    {
        return row;
    }
}
