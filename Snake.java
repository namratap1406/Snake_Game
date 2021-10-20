//Snake



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Snake extends JFrame{

	Snake(){
		super("Snake Game");
		Board b= new Board();
		add(b); 

		pack();

		setLocationRelativeTo(null);
		setTitle("Snake Game");
		setResizable(false);
	}
	
	public static void main(String[] args) throws Exception{
		new Snake().setVisible(true);				
		}
}

