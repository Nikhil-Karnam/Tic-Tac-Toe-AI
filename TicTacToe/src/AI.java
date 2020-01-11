import java.util.ArrayList;
import java.util.Random;

public class AI {

	public static String winningSquare = "0";
	public static int crossCount = 0;
	
	public static void main(String[] args) 
	{
		Board board = new Board(new String[3][3]);
		
		board.fill("o", "11");
		board.fill("o", "02");
		board.fill("o", "12");
		
		board.fill("o", "00");
		board.fill("o", "20");

		System.out.println(avoidsDoubleCross(board));
	}

	//returns true if you can win a game in the next move, false otherwise
	public static boolean canWin(Board board, String player) 
	{	
		String player2 = "x";
		if(player.equals("x"))
			player2 = "o";
		
		int count = 0;
		boolean hasX = false;
		
		
		//checking row
		for(int r = 0; r < 3; r++) 
		{
			count = 0;
			hasX = false;
			for(int i = 0; i < 3; i++)
			{
				if(board.getValue(r, i).equals(player2)) 
					hasX = true;
				
				if(board.getValue(r, i).equals(player))
					count++;
			}
			if(count == 2 && !hasX)
				return true;
		}
		
		
		//checking column
		for(int c = 0; c < 3; c++) 
		{
			count = 0;
			hasX = false;
			for(int i = 0; i < 3; i++) 
			{
				if(board.getValue(i, c).equals(player2))
					hasX = true;
				
				if(board.getValue(i, c).equals(player))
					count++;
			}
			if(count == 2 && !hasX)
				return true;
		}	
		
		
		//diagonals
		count = 0;
		hasX = false;
		for(int r = 0; r < 3; r++) 
		{		
			if(board.getValue(r, r).equals(player2))
				hasX = true;
			
			if(board.getValue(r, r).equals(player))
				count++;
		}
		if(count == 2 && !hasX)
			return true;
	
		
		count = 0;
		hasX = false;
		for(int r = 0; r < 3; r++) 
		{		
			if(board.getValue(r, 2-r).equals(player2))
				hasX = true;
			
			if(board.getValue(r, 2-r).equals(player))
				count++;
		}
		if(count == 2 && !hasX)
			return true;
		
		
		return false;
	}
	
	
	//if possible, finds a square that can win the game
	public static void setWinningSquare(Board board, String player) 
	{		
		AI.crossCount = 0;
		String player2 = "x";
		if(player.equals("x"))
			player2 = "o";
			
		int count = 0;
		boolean hasX = false;
		boolean hasWinningSquare = false;
		
		//checking row
		for(int r = 0; r < 3; r++) 
		{
			count = 0;
			hasX = false;
			for(int i = 0; i < 3; i++)
			{
				if(board.getValue(r, i).equals(player2)) 
					hasX = true;
				
				if(board.getValue(r, i).equals(player))
					count++;
			}
			if(count == 2 && !hasX) 
			{
				if(player.equals("o"))
					crossCount++;
				
				hasWinningSquare = true;
								
				for(int i = 0; i < 3; i++) 
					if(board.getValue(r, i).equals("0"))
						winningSquare = r+""+i;
			}
		}
		
		
		//checking column
		for(int c = 0; c < 3; c++) 
		{
			count = 0;
			hasX = false;
			for(int i = 0; i < 3; i++) 
			{
				if(board.getValue(i, c).equals(player2))
					hasX = true;
				
				if(board.getValue(i, c).equals(player))
					count++;
			}
			if(count == 2 && !hasX) 
			{
				if(player.equals("o"))
					crossCount++;

				hasWinningSquare = true;
				
				for(int i = 0; i < 3; i++) 
					if(board.getValue(i, c).equals("0"))
						winningSquare = i+""+c;
			}
		}	
		
		
		//diagonals
		count = 0;
		hasX = false;
		for(int r = 0; r < 3; r++) 
		{		
			if(board.getValue(r, r).equals(player2))
				hasX = true;
			
			if(board.getValue(r, r).equals(player))
				count++;
		}
		if(count == 2 && !hasX) 
		{
			for(int r = 0; r < 3; r++)
				if(board.getValue(r, r).equals("0")) 
				{
					if(player.equals("o"))
						crossCount++;

					hasWinningSquare = true;

					winningSquare = r+""+r;
				}
		}
	
		
		count = 0;
		hasX = false;
		for(int r = 0; r < 3; r++) 
		{		
			if(board.getValue(r, 2-r).equals(player2))
				hasX = true;
			
			if(board.getValue(r, 2-r).equals(player))
				count++;
		}
		if(count == 2 && !hasX) 
		{
			for(int r = 0; r < 3; r++)
				if(board.getValue(r, 2-r).equals("0"))
				{
					if(player.equals("o"))
						crossCount++;

					hasWinningSquare = true;

					int a = 2-r;
					winningSquare = r+""+a;
				}
		}
		
		if(!hasWinningSquare) 
		{
			winningSquare = "0";
		}
	}
	
	
	//fills a random available square on the board
	public static Board fillRandomSquare(Board board) 
	{
		Random random = new Random();
		
		int r = random.nextInt(3);
		int c = random.nextInt(3);
		
		while(!board.squareIsEmpty(r, c)) 
		{
			r = random.nextInt(3);
			c = random.nextInt(3);
		}
		
		board.fill("x", r+""+c);
		
		return board;
	}
	
	//returns an ArrayList of squares that don't result in a doubleCross in the next move
	public static ArrayList<String> avoidsDoubleCross(Board board) 
	{
		ArrayList<String> list = board.emptySquares();
		ArrayList<String> listOfSquares = new ArrayList<String>();				
				
		for(String a : list) 
		{
			board.fill("x", a);
			
			boolean goodSquare = true;
			ArrayList<String> list2 = board.emptySquares();
			
			for(String b : list2) 
			{
				board.fill("o", b);
				
				setWinningSquare(board, "o");
				        				
				if((crossCount == 2 && !canWin(board, "x")) || Game.gameOver(board, "o")) 
				{
					goodSquare = false;
					board.remove(b);
					break;
				}

				board.remove(b);
			}
			
			if(goodSquare) 
				listOfSquares.add(a);
			
			board.remove(a);
		}
		
		return listOfSquares;
	}
	
}
