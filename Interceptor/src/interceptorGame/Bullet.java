package interceptorGame;
import java.awt.*;
public class Bullet {
  static int size;
  int xcoord;
  int ycoord;
  static Color color;
  double velX;
  double velY;
  double acc;
  int dirX=1;
  int dirY=1;
  double speed=10;
  int[] xvals= new int[3];
  int[] yvals= new int[3];
  double angle;

  public Bullet(int x, int y,double a){
    size=20;
    xcoord=x;
    ycoord=y;
    angle=a;
    velX=Math.cos(a)*speed;
    velY=-Math.sin(a)*speed;
    color=Color.DARK_GRAY;
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
  public void moveX(double x){
    xcoord+=x;
  } 
  public void moveY(double y){
    ycoord+=y;
  } 
  public void explode(Graphics g){
	  for(int i=0;i<700;i++){
	  g.setColor(Color.ORANGE);
	  g.drawOval(xcoord+dirX*size/2,ycoord+dirY*size/2,size,size);
	  }
  }
  public void move(){
	  if(velX<0){
			dirX=1;
		}
		else {
			dirX=-1;
		}
		if(velY<0){
			dirY=1;
		}
		else {
			dirY=-1;
		}	  
	    moveX((int)(velX));
	    moveY((int)(velY));
	    xvals[0]=(int)(xcoord+size*Math.cos(angle));
	    xvals[1]=(int)(xcoord+size/3*Math.cos(angle-Math.PI/2));
	    xvals[2]=(int)(xcoord+size/3*Math.cos(angle+Math.PI/2));
	    yvals[0]=(int)(ycoord-size*Math.sin(angle));
	    yvals[1]=(int)(ycoord-size/3*Math.sin(angle-Math.PI/2));
        yvals[2]=(int)(ycoord-size/3*Math.sin(angle+Math.PI/2));
  }
  public void paint(Graphics g){
    Color cPrev=color;
    g.setColor(color);
    g.fillPolygon(xvals,yvals,3);
    g.setColor(cPrev);
  }
  
}
