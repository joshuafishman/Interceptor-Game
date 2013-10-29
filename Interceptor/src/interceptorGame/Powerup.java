package interceptorGame;
import java.awt.*;

public class Powerup {
	String type;
	double xcoord;
	int ycoord;
	double velX;
	static int size;
	
	public Powerup(int x,double velX,String t){
		xcoord=x;
		ycoord=0;
		this.velX=velX;
		type=t;
		size=30;
	}
	public double distanceFrom(int x,int y){
	    double d=Math.sqrt(Math.pow(x-xcoord,2)+Math.pow(y-ycoord,2));
	    return d;
	} 	
	public int getX(){
	    return (int)(xcoord);
	}
	public int getY(){
	    return ycoord;
	}
	public void move(){
		ycoord+=4;
		xcoord+=velX;
		if(xcoord<size||xcoord>Game.size-size){
			velX=-velX;
		}
	}
	public void paint (Graphics g){
		if(type=="H"){
		g.setColor(Color.RED);
		}
		if(type=="B"){
			g.setColor(Color.BLUE);
		}
	    g.fillOval((int)(xcoord-size/2),ycoord-size/2,size,size);
	    g.setColor(Color.WHITE);
	    g.setFont(new Font("Arial", Font.BOLD,20));
	    g.drawString(new String(type),(int)(xcoord-7), ycoord+10);	
	}
}
