import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 2L;

    public static final int WIDTH = 900, HEIGHT = 950;
    private Thread thread;
    private boolean running = false;
    public boolean paintcheck = false;
    
    public static Board board = new Board(new String[3][3]);
    Mouse mouse = new Mouse();
    
    public static boolean yourTurn = true;
    
    Scanner sc = new Scanner(System.in);
    Random r = new Random();

    public Main()
    {
    	this.addMouseListener(mouse);
         
    	new Window(WIDTH, HEIGHT, "Tic Tac Toe", this);
    }
    
    
    public synchronized void start()
    {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop()
    {
        try
        {
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void run()
    {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >=1)
            {
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000)
            {
                timer += 1000;
                //System.out.println("FPS: "+ frames);
                frames = 0;
            }
        }
        stop();
    }
    
    public void tick() 
    {
    	board = Game.newGame(board);
    	
    	if(!yourTurn) 
    	{    		
    		if(AI.canWin(board, "x")) 
    		{
    			AI.setWinningSquare(board, "x");
    			board.fill("x", AI.winningSquare);
    		}
    		
    		else if(AI.canWin(board, "o")) 
    		{
    			AI.setWinningSquare(board, "o");
    			board.fill("x", AI.winningSquare);
    		}
    		else
    		{
    			//thinking ahead
    			ArrayList<String> firstMove = AI.avoidsDoubleCross(board);
    			
    			Board lookAhead = board.clone();
				//System.out.println(firstMove);

    			for(int i = 0; i < firstMove.size(); i++) 
    			{
    				String a = firstMove.get(i);
    				
    				lookAhead.fill("x", a);
    				ArrayList<String> emptySquares = lookAhead.emptySquares();
    				
    				for(String b : emptySquares) 
    				{
    					//System.out.println(a + "  " + b);
    					lookAhead.fill("o", b);
    					ArrayList<String> secondMove = AI.avoidsDoubleCross(lookAhead);
    								
    					if(secondMove.isEmpty() && !AI.canWin(lookAhead, "x")) 
    					{
    						//System.out.println("stops here");
    						firstMove.set(i, "0");
    						lookAhead.remove(b);
    						break;
    					}
    					
    					lookAhead.remove(b);
    				}
    				
    				lookAhead.remove(a);
    			}
    			    			
    			ArrayList<String> newFirstMove = new ArrayList<>();
    			for(String a : firstMove) 
    			{
    				if(!a.equals("0"))
    					newFirstMove.add(a);
    			}
    			
    			//System.out.println(newFirstMove);
    			
    			if(!newFirstMove.isEmpty()) 
    			{
    				String square = newFirstMove.get(r.nextInt(newFirstMove.size()));
        			board.fill("x", square);
    			}
    			else
    				board = AI.fillRandomSquare(board);
    			
    		}
    		
    		yourTurn = true;
    	}
    }
    
    public void render() 
    {
    	BufferStrategy bs = this.getBufferStrategy();
        if(bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        
        //actual code starts here
        if(!paintcheck) 
        {
        	g.setColor(Color.white);
        	g.fillRect(0,0, WIDTH, HEIGHT);
        	
        	g.setColor(Color.black);
        	for(int i = 1; i < 3; i++)
        		g.fillRect(300*i, 0, 5, 900);
        	for(int i = 1; i < 3; i++)
        		g.fillRect(0, 300*i, 900, 5);
        	
        	paintcheck = false;
        }
       
        for(int i = 0; i < 3; i++)
        	for(int j = 0; j < 3; j++) 
        	{
        		if(board.getValue(i, j).equals("o"))
        			g.drawOval(300*j+50, 300*i+50, 200, 200);
        		
        		if(board.getValue(i, j).equals("x")) 
        		{
        			for(int k = 0; k < 40; k++)
        				g.fillRect(300*j+5*k + 50, 300*i + 5*k + 50, 5, 5);
        			
        			for(int k = 0; k < 40; k++)
        				g.fillRect(300*j+5*k + 50, 300*i - 5*k + 250, 5, 5);
        		}
        	}
        
        //ends code
        g.dispose();
        bs.show();
    }
    
    public static void main(String[] args) 
    {
    	new Main();
    }
}
