package interceptorGame;

import java.awt.*;
/**
 * Auto Generated Java Class.
 */
public class Missile {
  static int size;
  int xcoord;
  int ycoord;
  Color color;
  double accX=0;
  double velX=0;
  double accY=0;
  double velY=0;
  double acc;
  double angle;
  int[] xvals= new int[3];
  int[] yvals= new int[3];
  Ship target;

  public Missile(int x, int y,Ship s){
	size=30;
    xcoord=x;
    ycoord=y;
    acc=.1;
    target=s;
    color = Color.BLUE;
  }
  public Missile(int x, int y,double d,Ship s){
   xcoord=x;
   ycoord=y;
   acc=d;
   target=s;
   color = Color.BLUE;
  }
  public double distanceFrom(int x,int y){
    double d=Math.sqrt(Math.pow(x-xcoord,2)+Math.pow(y-ycoord,2));
    return d;
  } 
  public int getX(){
    return xcoord;
  }
  public int getY(){
    return ycoord;
  }
  public void explode(Graphics g){
	  for(int i=0;i<2000;i++){
		  g.setColor(Color.ORANGE);
		  g.fillOval((int)(xcoord),(int)(ycoord),size,size);
	  }
  }
  public void moveX(double x){
    xcoord+=x;
  } 
  public void moveY(double y){
    ycoord+=y;
  }
  public void setAngle(double a){
	    angle= a;
}
  public void move(){
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
	    velX+=Math.cos(angle)*acc;
	    velY+=-Math.sin(angle)*acc;
	    moveX(velX);
	    moveY(velY);
	    xvals[0]=(int)(xcoord+size*Math.cos(angle));
		xvals[1]=(int)(xcoord+size/3*Math.cos(angle-Math.PI/2));
		xvals[2]=(int)(xcoord+size/3*Math.cos(angle+Math.PI/2));
		yvals[0]=(int)(ycoord-size*Math.sin(angle));
		yvals[1]=(int)(ycoord-size/3*Math.sin(angle-Math.PI/2));
	    yvals[2]=(int)(ycoord-size/3*Math.sin(angle+Math.PI/2));

  }
  public void paint(Graphics g){  
    g.setColor(color);
    g.fillPolygon(xvals,yvals,3);
  }
}
