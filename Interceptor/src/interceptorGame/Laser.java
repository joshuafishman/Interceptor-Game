package interceptorGame;
import java.awt.*;
public class Laser extends Bullet {
	double angle;
	public Laser (int x, int y, double a){
		super(x,y,a);
		angle=a;
		size=15;
	}
    public void paint(Graphics g){
    	g.setColor(Color.CYAN);
    	g.drawLine(xcoord, ycoord, (int)(xcoord-size*Math.cos(angle)),(int)(ycoord+size*Math.sin(angle)));
    }
}
