import java.util.ArrayList;

public class Board {

	private String[][] board;
	
	public Board(String[][] board) 
	{
		this.board = board;
		clear();
	}
	
	//clears the board
	public void clear() 
	{
		for(int i = 0; i < board.length; i++)
			for(int j = 0; j < board[i].length; j++)
				board[i][j] = "0";
	}
	
	//fills a certain square on the board
	public void fill(String str, String a) 
	{
		board[Integer.parseInt(a.split("")[0])][Integer.parseInt(a.split("")[1])] = str;
	}
	
	//removed a certain square on the board
	public void remove(String a) 
	{
		board[Integer.parseInt(a.split("")[0])][Integer.parseInt(a.split("")[1])] = "0";
	}
	
	//returns the value of a certain square
	public String getValue(int a, int b) 
	{
		return board[a][b];
	}
	
	//returns true if the square is empty
	public boolean squareIsEmpty(int a, int b) 
	{
		if(board[a][b].equals("0"))
			return true;
		
		return false;
	}
	
	//returns a list of all empty squares on the board
	public ArrayList<String> emptySquares() 
	{
		ArrayList<String> list = new ArrayList<>();
		
		for(int i = 0; i < board.length; i++) 
			for(int j = 0; j < board[i].length; j++) 
				if(squareIsEmpty(i, j))
					list.add(i+""+j);
		
		return list;
	}

	public Board clone() 
	{
		Board clone = new Board(new String[3][3]);
		
		for(int i = 0; i < board.length; i++)
			for(int j = 0; j < board[i].length; j++)
				clone.fill(board[i][j], i+""+j);
		
		return clone;
	}
}
