import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter{
		
	public void mousePressed(MouseEvent e)
	{
		int mx = e.getX();
		int my = e.getY();
		
		if(mouseOver(mx, my, 1, 1, 900, 900) && Main.board.getValue(my/300, mx/300).equals("0")) 
		{
			Main.board.fill("o", my/300 + "" + mx/300);
			
			Main.yourTurn = false;
		}
	}
	
	public void mouseReleased(MouseEvent e)
    {

    }
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height)
    {
        if(mx > x && mx < x + width)
        {
            if(my > y && my < y + height)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
	
	
}
