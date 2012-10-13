/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comp472;

/**
 *
 * @author Greg
 */
public class Token
{

    private int col;
    private int row;

    Token(int mx, int my)
    {
        col = mx;
        row = my;
    }

    public int getCol()
    {
        return col;
    }

    public int getRow()
    {
        return row;
    }
}
