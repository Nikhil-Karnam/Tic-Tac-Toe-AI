
public class Game {

	public static void main(String args[]) 
	{
		
	}
	
	//returns true if the game is over
	public static boolean gameOver(Board board, String winner) 
	{
		int count = 0;
		
		//checking row
		for(int r = 0; r < 3; r++) 
		{
			count = 0;
			for(int i = 0; i < 3; i++)
				if(board.getValue(r, i).equals(winner))
					count++;

			if(count == 3)
				return true;
		}
		
		
		//checking column
		for(int c = 0; c < 3; c++) 
		{
			count = 0;
			for(int i = 0; i < 3; i++) 
				if(board.getValue(i, c).equals(winner))
					count++;
			
			if(count == 3)
				return true;
		}	
		
		
		//diagonals
		count = 0;
		for(int r = 0; r < 3; r++) 	
			if(board.getValue(r, r).equals(winner))
				count++;
		
		if(count == 3)
			return true;
	
		
		count = 0;
		for(int r = 0; r < 3; r++) 
			if(board.getValue(r, 2-r).equals(winner))
				count++;

		if(count == 3)
			return true;

		
		//no winner but the game is still going on
		for(int i = 0; i < 3; i++) 
			for(int j = 0; j < 3; j++) 
				if(board.getValue(i, j).equals("0"))
					return false;
		
		
		//no winner but the game is over
		if(!winner.equals("o") && !winner.equals("x"))
			return true;
		
		return false;
	}
	
	public static Board newGame(Board board) 
	{
		if(gameOver(board, "o")) 
    	{
    		System.out.println("O wins!");
    		Main.yourTurn = true;
    		board.clear();
    	}
		
    	else if(gameOver(board, "x")) 
    	{
    		System.out.println("X wins!");
    		Main.yourTurn = true;
    		board.clear();
    	}
		
    	else if(gameOver(board, "tie")) 
    	{
    		System.out.println("It's a Tie!");
    		Main.yourTurn = true;
    		board.clear();
    	}
		
		return board;
	}
}
