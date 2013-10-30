package interceptorGame;
import java.awt.*;
/**
 * Auto Generated Java Class.
 */
public class Ship  {
  static Color color;
  int xcoord;
  int ycoord;
  static int size;
  int length;
  double angle;
  int health;
  
  public Ship(int  x, int y){
    color=Color.GREEN;
    xcoord=x;
    ycoord=y; 
    size=40;
    length=30;
    angle=0;
    health=10;
  } 
  public int getX(){
    return xcoord;
  }
  public int getY(){
    return ycoord;
  }
  public void moveX(int x){
    if(x>0){
      if(xcoord<Game.size-size){
        xcoord+=x;
      }
    }
    if(x<0){
      if(xcoord>size){
        xcoord+=x;
      }
    }
  }
  public void moveY(int y){
    if(y>0){
      if(ycoord<Game.size-size){
        ycoord+=y;
      }
    }
    if(y<0){
      if(ycoord>size){
        ycoord+=y;
      }
    }
  }
  public void setAngle(double a){
    angle= a;
  }
  public void pointAt(double x,double y){
    if(x!=xcoord){
      if(x<xcoord){
          setAngle(-Math.atan((y-ycoord)/(x-xcoord))+Math.PI);
      }
      if(x>xcoord){
    	  setAngle(-Math.atan((y-ycoord)/(x-xcoord)));
      }
    }
  }
  public Bullet shoot(){
    int d=1;
    if(Math.cos(angle)>0){
     d=-1;
    }
    else if(Math.cos(angle)<0){
     d=1;
    }
     return new Bullet((int)(xcoord+length*Math.cos(angle)),(int)(ycoord-length*Math.sin(angle)),angle);
  }
  public void paint(Graphics g){
	pointAt(MouseInfo.getPointerInfo().getLocation().getX(),MouseInfo.getPointerInfo().getLocation().getY());
    g.setColor(color);
    g.fillOval(xcoord-size/2,ycoord-size/2,size,size);
    g.setColor(Color.DARK_GRAY);
    g.drawLine(xcoord,ycoord,(int)(xcoord+length*Math.cos(angle)),(int)(ycoord-length*Math.sin(angle)));
    g.setColor(Color.RED);
 	g.fillRect(Game.size-10*12-30,40,health*12,20);
  }
  
}
