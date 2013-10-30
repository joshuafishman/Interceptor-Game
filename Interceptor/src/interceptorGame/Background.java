package interceptorGame;
import java.awt.*;
import java.util.*;
public class Background {
	ArrayList <Star> stars=new ArrayList <Star>();	
	
	public Background(){
		for(int i=0;i<1000;i++){
			Star s=new Star((int)(Math.random()*Game.size),(int)(Math.random()*Game.size),(int)(Math.random()*2.5));
			stars.add(s);			
		}
	}
	public void move(){
		if(Math.random()>.0007){
			Star s=new Star((int)(Math.random()*Game.size),0,(int)(Math.random()*4));
			stars.add(s);
		}
		Iterator sIter=stars.iterator();
		while(sIter.hasNext()){
			Star s=(Star)sIter.next();
			s.y++;
			if(s.y>Game.size){
				sIter.remove(); 
			}
		}
	}
	public void paint(Graphics g){
		for(int i=0;i<stars.size();i++){
			stars.get(i).paint(g);
		}
	}
	
	public class Star{
	int radius;
	int x;
	int y;
	double b;
		public Star(int x, int y, int r){
			this.x=x;
			this.y=y;
			radius=r;
			b=Math.random();
		}
		public void paint(Graphics g){
			g.setColor(new Color(255,255,255,(int)(b*255)));
			g.fillOval(x, (int)(y), radius, radius);
		}
	}
	
}
