package MagneticCave;

/**
 * This class is used to keep track of the player best move as chosen by the minimax
 *
 * @author Grzegorz Gut <gregg.gut@gmail.com>
 */
public class BestMove
{

    /**
     * Default constructor
     */
    BestMove()
    {
        row = -1;
        col = -1;
        strength = Integer.MIN_VALUE;
    }
    private int row;
    private int col;
    private int strength;

    public void setCol(int col)
    {
        this.col = col;
    }

    public int getCol()
    {
        return col;
    }

    public void setStrength(int strength)
    {
        this.strength = strength;
    }

    public int getStrength()
    {
        return strength;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public int getRow()
    {
        return row;
    }
}
