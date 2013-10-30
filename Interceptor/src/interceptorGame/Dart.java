package interceptorGame;
import java.awt.*;

public class Dart extends Missile {
	double speed;
	long tTurn;
	long tMove;
	double angle;
	boolean moving=false;
	boolean turning=true;
	int moves;
	public Dart(int x, int y,Ship s) {
		super(x, y, s);
		speed=10;
		color= new Color(125,125,0);
		angle=0;
	}
	public Dart(int x, int y, int spd, Ship s) {
		super(x, y, s);
		speed=spd;
		color=Color.DARK_GRAY;
		angle=0;
	}
	public void setAngle(double a){
		    angle= a;
	}
	public void move(){
		if (moves>30){ 
			if(target.xcoord!=xcoord){
		      if(target.xcoord<xcoord){
		        if(target.ycoord>ycoord){
		          setAngle(-Math.atan((target.ycoord-ycoord)/(target.xcoord-xcoord))+Math.PI);
		        }
		        else if(target.ycoord<ycoord){
		          setAngle(-Math.atan((target.ycoord-ycoord)/(target.xcoord-xcoord))+Math.PI);
		        }
		      }
		      if(target.xcoord>xcoord){
		      setAngle(-Math.atan((target.ycoord-ycoord)/(target.xcoord-xcoord)));
		      }
			}
		tMove=System.currentTimeMillis();
		moves=0;
		}
		velX=(Math.cos(angle)*speed);    
		velY=(Math.sin(angle)*-speed);
		if (System.currentTimeMillis()-tMove>1000){
			moves++;
			moveX(velX);
			moveY(velY);
		}
	    xvals[0]=(int)(xcoord+size*Math.cos(angle));
		xvals[1]=(int)(xcoord+size/3*Math.cos(angle-Math.PI/2));
		xvals[2]=(int)(xcoord+size/3*Math.cos(angle+Math.PI/2));
		yvals[0]=(int)(ycoord-size*Math.sin(angle));
		yvals[1]=(int)(ycoord-size/3*Math.sin(angle-Math.PI/2));
	    yvals[2]=(int)(ycoord-size/3*Math.sin(angle+Math.PI/2));
	}

}
