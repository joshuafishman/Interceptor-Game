package interceptorGame;
import java.awt.*;
public class Boss {
double xcoord;
double ycoord;
Ship target;
int size;
int health;
double velX;
double angle;
long tprev;
	public Boss (int x,int y,Ship s){
		xcoord=x;
		ycoord=y;
		target=s;
		size=300;
		velX=1.5;
		health=10;
		angle=180;
		tprev= System.currentTimeMillis();
	}
	public void move(){
		if(ycoord<size/3){
			ycoord+=.5;
		}
		else {
			xcoord+=velX;
		}
		if(xcoord<size/2||xcoord>700-size){
			velX*=-1;
		}

	}
	public void aimAt(Graphics g, double x, double y){
		if(target.xcoord<x){
		   angle=-Math.atan((target.ycoord-y)/(target.xcoord-x))+Math.PI;

		}
		if(target.xcoord>x){
			angle=-Math.atan((target.ycoord-y)/(target.xcoord-x));
		}
		g.setColor(Color.BLACK);
	    g.drawLine((int)x,(int)y,(int)(x+30*Math.cos(angle)),(int)(y-30*Math.sin(angle)));
		
	}
	public Missile launchRight(){
		if(Math.random()>.5){
			return new Missile ((int)xcoord+size*4/10-size/50, (int)ycoord+size/8, target);
		}
		else{
			return new Dart ((int)xcoord+size*4/10-size/50, (int)ycoord+size/8, target);
		}
	}
	public Missile launchLeft(){
		if(Math.random()>.5){
			return new Missile ((int)xcoord+size/50, (int)ycoord+size/8, target);
		}
		else{
			return new Dart ((int)xcoord+size/50, (int)ycoord+size/8, target);
		}
	}
	public Laser shoot(){
		return new Laser((int)(xcoord+size*2/10+size/20+30*Math.cos(angle)),(int)(ycoord+size/8+size/20-30*Math.sin(angle)),angle);
	}
	public void paint(Graphics g){
		if(health>0){
		g.setColor(Color.LIGHT_GRAY);
		g.fillOval((int)xcoord, (int)ycoord, size/2, size/3);
		g.setColor(Color.DARK_GRAY);
		g.fillOval((int)xcoord+size/50, (int)ycoord+size/8, size/10, size/10);
		g.fillOval((int)xcoord+size*4/10-size/50, (int)ycoord+size/8, size/10, size/10);
		g.fillRect((int)xcoord+size*2/10, (int)ycoord+size/8, size/10, size/10);
		aimAt(g,xcoord+size*2/10+size/20, ycoord+size/8+size/20);
		g.setColor(Color.RED);
		g.fillRect(270,650,health*5,20);
		}
	}

}
