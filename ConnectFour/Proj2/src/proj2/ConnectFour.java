/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proj2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



public class ConnectFour extends JFrame implements MouseListener, ActionListener{

    private int gameWidth = 700;
    private int gameHeight = 600;
    private int titleBarShift = 32;
    private int bottomHeight = 50;
    private int columns = 7;
    private int rows = 6;
    private int squareSpace = 100;
    private int circleLength = 100;
    private int grid[][] = new int[rows][columns];
    private int moveCounter = 0;
    private JButton message = new JButton("Directions");
    private JPanel panel = new JPanel();
    
   
    
    

    public ConnectFour() {
        
	// Board Setup		
        this.setBackground(Color.BLUE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);         
        addMouseListener(this);
        this.setSize(gameWidth, gameHeight + titleBarShift + bottomHeight);
        panel.setLayout(null);
        message.setBounds(30, gameHeight + 10, 100, 20);
        message.setActionCommand("message");
        message.addActionListener(this);
        panel.add(message);
	this.resetBoard(); 
        this.add(panel);
        
    }

    public void resetBoard() {

	// Reset grid squares
        
        // Iterate rows
        for(int r = 0; r < rows; r++){ 
            // Iterate columns
            for(int c = 0; c < columns; c++){
                // Set every grid space to empty => grid[rowNumber][columnNumber] == 0
                grid[r][c] = 0;  
                
            }            
        }
        
        // Reset moveCounter
        moveCounter = 0;

	// repaint the board
        repaint();
        
    }

    public void paint(Graphics canvas) {

        //draw the current circle states
        // Iterate rows
        for(int r = 0; r < rows; r++){
            
            // Iterate columns
            for(int c = 0; c < columns; c++){
 
                // DrawRect or FillOval (x, y, width, height)
                // x => columnNumber * length of grid space (100)
                // y => rowNumber * height of grid space (100) + height of top margin (20)
                // width => width of grid (100)
                // height => height of grid(100)
                
                // Set color Black
                // DrawRect
                canvas.setColor(Color.BLACK);
                canvas.drawRect(c * squareSpace, r * squareSpace + 20, squareSpace, squareSpace);
                
                // Default (white) = 0
                // Player 1 (yellow) = 1
                // Player 2 (red) = 2
                
                // If grid space = 0
                if(grid[r][c] == 0){
                    
                    // Set color White
                    // FillOval
                    canvas.setColor(Color.WHITE);
                    canvas.fillOval(c * squareSpace, r * squareSpace + 20, squareSpace, squareSpace);
                    
                }
                
                // If grid space = 1
                if(grid[r][c] == 1){
                    
                    // Set color Yellow
                    // FillOval
                    canvas.setColor(Color.YELLOW);
                    canvas.fillOval(c * squareSpace, r * squareSpace + 20, squareSpace, squareSpace);
                    
                }
                
                // If grid space = 2
                if(grid[r][c] == 2){
                    
                    // Set color Red
                    // FillOval
                    canvas.setColor(Color.RED);
                    canvas.fillOval(c * squareSpace, r * squareSpace + 20, squareSpace, squareSpace);
                    
                }    
            }       
        }

	// draw the bottom black area
        canvas.setColor(Color.BLACK);
        canvas.fillRect(0, gameHeight + 20, gameWidth, bottomHeight);
	   
        // draw the game name
	canvas.setColor(Color.RED);
        Font font = canvas.getFont().deriveFont(30.0f);
        canvas.setFont(font);
        canvas.drawString("CONNECT FOUR", gameWidth / 2 - 100, gameHeight + 55);

    }

    // Mouselistener
    public void mouseClicked(MouseEvent e) {
       
	// get mouse click position
	int x = e.getX();
        int y = e.getY();
        
        // Flag for winner
        boolean winner = false;
        // Flag for full column
        boolean noSpace = false;

        //System.out.println("X: " + x);
        //System.out.println("Y: " + y);
 
        // Iterate columns
        for(int c = 0; c < columns; c++){
            
            // If x position between
            // column start => column number * 100
            // and
            // column end => (column number + 1) * 100 
            if(x >= c * squareSpace && x <= (c + 1) * squareSpace){
                
                // Iterate rows
                // Start from bottom up
                for(int r = rows - 1; r >= 0; r--){
                    
                    // Check Full Column
                    // If first row in column is not empty => grid[0][columnNumber]
                    if(grid[0][c] != 0){

                        // Set noSpace to true
                        // DO NOT INCREASE MOVECOUNTER
                        noSpace = true;
                        break;

                    }
                    
                    // If grid space empty => grid[rowNumber][columnNumber] == 0
                    // and
                    // Number of total moves is even 
                    if(grid[r][c] == 0 && moveCounter % 2 == 0){ 
                        
                        // Set grid space to yellow (1) => grid[rowNumber][columnNumber] == 1
                        // Increase move counter
                        grid[r][c] = 1;
                        moveCounter += 1;
                        break; 
                        
                    }
                    
                    // If grid space empty => grid[rowNumber][columnNumber] == 0
                    // and
                    // Number of total moves is odd 
                    if(grid[r][c] == 0 && moveCounter % 2 != 0){
                        
                        // Set grid space to red (2) => grid[rowNumber][columnNumber] == 2
                        // Increase move counter
                        grid[r][c] = 2;
                        moveCounter += 1;
                        break;  
                        
                    }    
                }      
            }
        }
        
        // If Column Full and No Winner
        if(noSpace == true && winner == false){
            
            // Display Message 
            JOptionPane.showMessageDialog(this, "COLUMN FULL! CHOOSE AGAIN");
            
        }

	// CHECK FOR WINNERS
        // 4 of the same color spaces in a row       
        // Use try-catch for out of bounds (off grid) errors
        // Procedure to check for winner:
        // --> Iterate rows
        // --> Iterate columns
        // --> If grid space is not empty
        //     AND 
        //     3 adjacent spaces are same color 
        //     (vertically, horizontally, diagonal up, diagonal down)
        //--> Set winner to true
     
        // VERTICAL
        try{
            for(int r = 0; r < rows; r++){                
                for(int c = 0; c < columns; c++){
                    if(grid[r][c] != 0 && 
                       grid[r][c] == grid[r+1][c] &&
                       grid[r][c] == grid[r+2][c] &&
                       grid[r][c] == grid[r+3][c]){
                        winner = true;
                    }                   
                }               
            }            
        }
        catch (Exception error){System.out.println("Off Grid");}
        
        // HORIZONTAL      
        try{           
            for(int r = 0; r < rows; r++){               
                for(int c = 0; c < columns; c++){                    
                    if(grid[r][c] != 0 && 
                       grid[r][c] == grid[r][c+1] &&
                       grid[r][c] == grid[r][c+2] &&
                       grid[r][c] == grid[r][c+3]){
                        winner = true;
                    }
                }               
            }           
        }        
        catch (Exception error){System.out.println("Off Grid");}
        
        // DIAGONAL UP
        try{           
            for(int r = 0; r < rows; r++){               
                for(int c = 0; c < columns; c++){                   
                    if(grid[r][c] != 0 && 
                       grid[r][c] == grid[r+1][c-1] &&
                       grid[r][c] == grid[r+2][c-2] &&
                       grid[r][c] == grid[r+3][c-3]){
                        winner = true;   
                    }
                }               
            }          
        }
        catch (Exception error){System.out.println("Off Grid");}
        
        // DIAGONAL DOWN
        try{           
            for(int r = 0; r < rows; r++){                
                for(int c = 0; c < columns; c++){                   
                    if(grid[r][c] != 0 && 
                       grid[r][c] == grid[r+1][c+1] &&
                       grid[r][c] == grid[r+2][c+2] &&
                       grid[r][c] == grid[r+3][c+3]){
                        winner = true;  
                    }
                }                
            }            
        }
        catch (Exception error){System.out.println("Off Grid");}

        // repaint the board
        repaint();
        
        // If there is a Winner 
        if(winner == true){
            
            // Flag to exit JOptionPane
            boolean quit = false;
            
            // While quit = false 
            while(quit == false){
                
                // JOptionPane
                // Play Again = 1
                // Exit = 0
                Object[] options = {"Exit", "Play Again"};
                int optionNumber = JOptionPane.showOptionDialog(null,
                    "WINNER!!!",
                    "GAME OVER",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[1]);
                
                // If user selects Play Again
                if(optionNumber == 1){
                    
                    // Reset Board
                    // Exit JOptionPane
                    resetBoard();
                    quit = true;
                }
                
                // If user selects Exit
                if(optionNumber == 0){
                    
                    // Exit Program
                    System.exit(0);
                }
            }

        }
        
        // CHECK FOR TIE
        // ** 42 possible moves
        // If moveCounter reaches 42 and there is no winner        
        if(moveCounter >= 42 && winner != true){
            
            // Display Tie message
            // Reset Board
            JOptionPane.showMessageDialog(this, "TIE!! NO MORE MOVES");
	    resetBoard();    
            
        } 
        
    }
    
    // Unused mouse listeners
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
    // Button Listener
    @Override
    public void actionPerformed(ActionEvent e) {
        
        String directions = "WELCOME TO CONNECT 4.\n"
                + "Player 1 has Yellow Chip\n"
                + "Player 2 has Red Chip\n"
                + "Winner of the game is first player to\n"
                + "Connect 4 of their color chips in a row\n"
                + "GOOD LUCK!";
        if(e.getActionCommand().equals("message")){
            JOptionPane.showMessageDialog(this, directions);
        }
    }
   
}

