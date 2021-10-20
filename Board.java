//Board

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener{

	Image apple;
	Image dot;
	Image head;

	int DOT_SIZE = 20; //size of apple & snake
	int ALL_DOTS = 2500; //maximum number of possible dots on the board 
	int RANDOM_POSITION = 25; //a random position for an apple

   // two arrays store the x and y coordinates of all joints of a snake
	int apple_x;
	int apple_y;

	int x[]= new int[ALL_DOTS];
	int y[]= new int[ALL_DOTS];

	boolean leftDirection = false;
	boolean rightDirection = false;
	boolean upDirection = false;
	boolean downDirection = true;
	boolean inGame = true;

	int dots;

	Timer timer;

	Board(){

		addKeyListener(new TAdapter());
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(500,500));

		setFocusable(true);
		try{

		loadImages();
		initGame();
	     }
	  catch (Exception e){
	  	System.out.println(e);
	  }

	}

	public void loadImages() throws Exception{
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("apple.png"));
		apple = i1.getImage();

		ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("dot.png"));
		dot = i2.getImage();

		ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("head.png"));
		head = i3.getImage();

	}

	public void initGame(){

		dots = 5;

		for(int z=0;z<dots;z++){
			x[z]=50-z*DOT_SIZE; 
			y[z]=50;
		}

		locateApple();

		timer = new Timer(300,this);
		timer.start();
	}

	public void locateApple(){

		int r = (int)(Math.random() * RANDOM_POSITION);
		apple_x = (r * DOT_SIZE);

		r = (int)(Math.random() * RANDOM_POSITION);
		apple_y = (r * DOT_SIZE);
	}

	public void checkApple(){
		if((x[0]==apple_x)&&(y[0]==apple_y)){
			dots++;
			locateApple();
		}
	}

	public void paintComponent(Graphics g){

		super.paintComponent(g);

		draw(g);
	}

	public void draw(Graphics g){
		if(inGame){
			g.drawImage(apple,apple_x,apple_y,this);

			for(int z=0; z<dots; z++){
				if(z==0){
					g.drawImage(head,x[z],y[z],this);
				}
				else{
					g.drawImage(dot,x[z],y[z],this);
				}
			}
			Toolkit.getDefaultToolkit().sync();
		}
		else{
			gameOver(g);
		}
	}

	public void gameOver(Graphics g){
		String msg = "Game Over";
		Font font = new Font("SAN_SERIF",Font.BOLD,20);
		FontMetrics metrices = getFontMetrics(font);

		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(msg,(500 - metrices.stringWidth(msg))/2,500/2);
	}

	public void checkCollision(){

		for(int z= dots;z>0;z--){
			if((z>4) && (x[0] == x[z]) && (y[0] == y[z])){
				inGame=false;
			}
		}

		if(y[0]>=500){
			inGame=false;
		}

		if(x[0]>=500){
			inGame=false;
		}

		if(x[0]<0){
			inGame=false;
		}

		if(y[0]<0){
			inGame=false;
		}

		if(!inGame){
			timer.stop();
		}
	}

	public void move(){

		for(int z = dots;z>0;z--){
			x[z]=x[z-1];
			y[z]=y[z-1];
		}

		if(leftDirection){
			x[0]=x[0] - DOT_SIZE;
		}

		if(rightDirection){
		    x[0]+= DOT_SIZE;
	    }

	    if(upDirection){
		    y[0]=y[0] - DOT_SIZE;
	    }

	    if(downDirection){
		    y[0]+= DOT_SIZE;
	    }
	}    

	public void actionPerformed(ActionEvent ae){
		if(inGame){
			checkApple();
			checkCollision();
			move();
		}

		repaint();
	}

	class TAdapter extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e){
			int key = e.getKeyCode();

			if(key==KeyEvent.VK_LEFT && (!rightDirection)){
				leftDirection=true;
				upDirection=false;
				downDirection=false;
			}

			if(key==KeyEvent.VK_RIGHT && (!leftDirection)){
				rightDirection=true;
				upDirection=false;
				downDirection=false;
			}

			if(key==KeyEvent.VK_UP && (!upDirection)){
				rightDirection=false;
				leftDirection=false;
				upDirection=true;
			}

			if(key==KeyEvent.VK_DOWN && (!downDirection)){
				rightDirection=false;
				leftDirection=false;
				downDirection=true;
			}
		}
	}
}