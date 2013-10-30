package interceptorGame;

import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.util.*;

public class Game extends Frame {
	int missileNum=10;// max number of missiles
	int mNum;// current number of missiles
	int bNum=250;// number of enemies before boss appears 
	int score=0;
	static int size=700;
	static ArrayList<Missile> missiles=new ArrayList<Missile>();
	static ArrayList<Bullet> bullets=new ArrayList<Bullet>();
	static ArrayList<Laser> lasers=new ArrayList<Laser>();
	static ArrayList<Powerup> powerups=new ArrayList<Powerup>();
	Ship ship;
	Boss  boss;
	Background background;
	boolean paused;
	boolean newGame;
	boolean bossTime;//time to make a boss
	boolean bossKilled=true;//boss has just been killed (starts out true because it is only referenced if the boss's health is 0)
	long bTime;//time since boss has been destroyed
	long tStart;
	long tLaunch;
	long tShot;
	public Game(){
	    this.setVisible(true);
	    this.setSize(size,size);
	    this.setLocation(0,0);
	    this.setTitle("Interceptor");
	    this.setBackground(Color.BLACK);
	    this.addWindowListener(new HandleEvents());
	    this.addKeyListener(new KeyPress());
	    tStart=System.currentTimeMillis();
	}
	public Game getGame(){
	  return this;
	}
	public Ship getShip(){
	  return ship;
	}
	private void interact(Graphics g) {
			Iterator bIter = bullets.iterator();
		    while (bIter.hasNext()) {
		    	try{
		    		Bullet b = (Bullet) bIter.next();
		    		if(b.getX()>size||b.getX()<0||b.getY()>size||b.getY()<0){
		    			try{
		    				bIter.remove();
		    			}catch (IllegalStateException e){}
		    	    }
		    		Iterator mIter = missiles.iterator();
		    		while (mIter.hasNext()) {
		    			Missile m = (Missile) mIter.next();			
		    			if(m.distanceFrom(b.getX(),b.getY())<m.size/2+b.size/2){
		    				try{
		    					m.explode(g);
		    					mIter.remove();
		    					bIter.remove();
		    				}catch (IllegalStateException e){}
		    				mNum--;	
		    				score+=10 ;
		    			} 
		    		}	
		    		if(boss!=null){
		    			if(Math.abs(b.xcoord-(boss.xcoord+boss.size/4))<boss.size/4+b.size/3 && Math.abs(b.ycoord-(boss.ycoord+boss.size/6))<boss.size/6+b.size){
		    				boss.health--;
		    				try{
		    					b.explode(g);
		    					bIter.remove();
		    				}catch (IllegalStateException e){}
		    			}
		    		}
		    	} catch(java.util.ConcurrentModificationException e){}
		    }
		    Iterator mIter = missiles.iterator();
			while (mIter.hasNext()) {
				Missile m = (Missile) mIter.next();
				if(m.distanceFrom(ship.getX(),ship.getY())<ship.size/2+m.size/2){	          
					try{
						m.explode(g);
						mIter.remove();
					}catch (IllegalStateException e){}
					mNum--;
					ship.health-=2;
				}
			}
		    Iterator lIter = lasers.iterator();
		    while (lIter.hasNext()) {
		    	try{
		    		Laser l = (Laser) lIter.next();
		    		if(l.getX()>size||l.getX()<0||l.getY()>size||l.getY()<0){
		    			try{
		    				lIter.remove();
		    			}catch (IllegalStateException e){}
		    	    }
		    		if(l.distanceFrom(ship.getX(),ship.getY())<ship.size/2){	  
		    			try{
	    					lIter.remove();
	    				}catch (IllegalStateException e){}
		    			ship.health--;
		    		}
		    	} catch(java.util.ConcurrentModificationException e){}
		    }
		    Iterator pIter = powerups.iterator();
		    while (pIter.hasNext()) {
		    	try{
		    		Powerup p=(Powerup)pIter.next();
		    		if(p.getY()>size){
		    			try{
		    				pIter.remove();
		    			}catch (IllegalStateException e){}
		    	    }
		    		if(p.distanceFrom(ship.getX(),ship.getY())<ship.size/2+Powerup.size/2){	  
		    			try{
	    					pIter.remove();
	    				}catch (IllegalStateException e){}
    					if(p.type=="H"){
    						ship.health=15;
    					}
    					else if (p.type=="B"){
    						g.setColor(new Color(0,100,255));
    						for(double i=0;i<size*2;i+=1.5){
    							g.fillOval(p.getX()-(int)(i/2), p.getY()-(int)(i/2), (int)(i),(int)(i));
    						}
    						mNum=0;
    						missiles.clear();
    						bullets.clear();
    						lasers.clear();
    						powerups.clear();
    					}
		    		}
		    	} catch(java.util.ConcurrentModificationException e){}
		    }
	}
	public void run(){
	ship=new Ship(size/2,size/2);
	background=new Background();
	long tprev=System.currentTimeMillis();
	while(!newGame){
		    if(ship.health>0){
		    	if(System.currentTimeMillis()-tprev>750&&!paused){
		    		if(!bossTime){
		    		 if(boss==null){
		    			if(score%bNum==0 && score!=0){
		    				bossTime=true;
		    			}
		    			if(mNum<missileNum){ 
		    				mNum++;
		    				if(Math.random()<.5){
		    					Missile m=new Missile((int)(Math.random()*900),0,ship); 
		    					missiles.add(m);
		    				}
		    				else{
		    					Dart m=new Dart((int)(Math.random()*900),0, ship);
		    					missiles.add(m);
		    				}
		    			}
		    		 }
		    		}
		    		else if(mNum==0) {
		    			boss=new Boss (size/2-50,0,ship);
		    			tLaunch=System.currentTimeMillis();
		    			bossTime=false;
		    		}
		    		if(Math.random()<1.0/15.0){
		    			Powerup p;
		    			if(Math.random()>.5){
		    			p=new Powerup((int)(Math.random()*size),(int)(Math.random()*4),"H");
		    			}
		    			else p=new Powerup((int)(Math.random()*size),(int)(Math.random()*4),"B");
		    			powerups.add(p);
		    		}
					tprev=System.currentTimeMillis();
		    	} 
		    	long tPaint=System.currentTimeMillis();
				while(System.currentTimeMillis()-tPaint<25){}//I originally used thread.sleep(), but it significantly decreased the fps rate so I switched to this while loop
		    	repaint(); 
			} 
	 }
	}
	public void paint(Graphics g){
		if(System.currentTimeMillis()-tStart<2000){
			g.setColor(Color.WHITE);
			g.setFont(new Font("Serif", Font.BOLD, 55));
		    g.drawString("Interceptor",size/2-135,size/2+20);
		    return;
		}
		else if(System.currentTimeMillis()-tStart>2001 && System.currentTimeMillis()-tStart<7000){
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 50));
		    g.drawString("Instructions",size/2-150,100);
		    g.setFont(new Font("Arial", Font.BOLD, 25));
		    g.drawString("This is your ship: ",200,180);
		    g.setColor(Color.GREEN);
		    g.fillOval(450-40/2,170-40/2,40,40);
		    g.setColor(Color.DARK_GRAY);
		    g.drawLine(450,170,(int)(450+30*Math.cos(45)),(int)(170-30*Math.sin(45)));
		    g.setColor(Color.WHITE);
		    g.drawString("Control it with W, A, S and D",150,250);
		    g.drawString("Use the mouse to aim and the spacebar to shoot",50,300);
		    g.setColor(Color.GREEN);
		    g.fillOval(250-40/2,370-40/2,40,40);
		    g.setColor(Color.DARK_GRAY);
		    g.drawLine(250,370,(int)(250+30*Math.cos(0)),(int)(370-30*Math.sin(0)));
		    g.setColor(Color.DARK_GRAY);
		    int[] xvals= {330+13*-1/2,330-13*-2/2,330+13*-1/2};
	    	int[] yvals= {370-13/2,370,370+13/2};
	    	g.fillPolygon(xvals,yvals,3);
	    	int[] xvalsa= {400+13*-1/2,400-13*-2/2,400+13*-1/2};
	    	int[] yvalsa= {370-13/2,370,370+13/2};
	    	g.fillPolygon(xvalsa,yvalsa,3);
		    g.setColor(Color.WHITE);
		    g.drawString("Press P to pause and N to start a new game",100,450);
	    	g.drawString("These are missiles: ",170,550);
	    	g.setColor(new Color(125,125,0));
	    	int[] xvalsb= {440-20/2,440,440+20/2};
	    	int[] yvalsb= {545+20*1/2,545-20*2/2,545+20*1/2};	
	    	g.fillPolygon(xvalsb,yvalsb,3);
	    	g.setColor(Color.BLUE);
	    	int[] xvalsc= {490-20/2,490,490+20/2};
	    	int[] yvalsc= {545+20*1/2,545-20*2/2,545+20*1/2};		
	    	g.fillPolygon(xvalsc,yvalsc,3);
	    	g.setColor(Color.WHITE);
	    	g.drawString("Destroy them!",size/2-100,610);
		} 
		else if( System.currentTimeMillis()-tStart>7001){
			if(boss==null||boss.health>0){
					if(!paused){
						interact(g);
						Color cPrev=g.getColor();
						background.move();
						background.paint(g);
						for(int i=0;i<missiles.size();i++){
							missiles.get(i).move();
							missiles.get(i).paint(g);
						}
						for(int i=0;i<bullets.size();i++){
							bullets.get(i).move();
							bullets.get(i).paint(g);
						}
						for(int i=0;i<lasers.size();i++){
							lasers.get(i).move();
							lasers.get(i).paint(g);
						}
						for(int i=0;i<powerups.size();i++){
							powerups.get(i).move();
							powerups.get(i).paint(g);
						}
						if(boss!=null){
							if(System.currentTimeMillis()-tLaunch>4000){
								missiles.add(boss.launchLeft());
								missiles.add(boss.launchRight());
								tLaunch=System.currentTimeMillis();
							}
							if(System.currentTimeMillis()-tShot>1000){
								lasers.add(boss.shoot());
								tShot=System.currentTimeMillis();
							}
							boss.move();
							boss.paint(g);
						}
						ship.paint(g);
						g.setColor(Color.WHITE);  
						g.setFont(new Font("Arial", Font.BOLD, 20));
						g.drawString("SCORE:"+Integer.toString(score),575,675);
						g.setColor(cPrev);
						if(ship.health==0){
							g.setColor(Color.RED);  
						    g.setFont(new Font("Serif", Font.BOLD, 40));
						    g.drawString("GAME OVER",size/2-125,size/2);
						    g.setColor(Color.WHITE);
						    g.setFont(new Font("Arial", Font.BOLD, 22));
						    g.drawString("Press N to play again",size/2-107,size/2+50);
						 }
					}    
					else if(paused){ 
						g.setColor(Color.WHITE);  
						g.setFont(new Font("Serif", Font.BOLD, 35));
						g.drawString("PAUSED",size/2-60,size/2);
						Color cPrev=g.getColor();
						background.paint(g);
						for(int i=0;i<missiles.size();i++){
							missiles.get(i).paint(g);
							}
						for(int i=0;i<bullets.size();i++){
							bullets.get(i).paint(g);
						}
						for(int i=0;i<lasers.size();i++){
							lasers.get(i).paint(g);
						}
						if(boss!=null){
							boss.paint(g);
						}
						ship.paint(g);
						g.setColor(Color.WHITE);  
						g.setFont(new Font("Arial", Font.BOLD, 20));
						g.drawString("SCORE:"+Integer.toString(score),575,675);
						g.setColor(cPrev);
						}
					}
			else if (boss!=null && boss.health==0){
				if(bossKilled){
				bTime=System.currentTimeMillis();
				bossKilled=false;
				}
				if((System.currentTimeMillis()-bTime-1500)*.255/5<255){
					background.move();
					background.paint(g);
					for(int i=0;i<missiles.size();i++){
						missiles.get(i).paint(g);
						}
					for(int i=0;i<bullets.size();i++){
						bullets.get(i).paint(g);
					}
					for(int i=0;i<lasers.size();i++){
						lasers.get(i).paint(g);
					}
					ship.paint(g);
					g.setColor(Color.WHITE);  
					g.setFont(new Font("Arial", Font.BOLD, 20));
					g.drawString("SCORE:"+Integer.toString(score),575,675);
					g.setColor(Color.ORANGE);
					g.fillOval((int)(boss.xcoord),(int)(boss.ycoord), 200, 200);
					if(System.currentTimeMillis()-bTime>1000){
						g.fillOval((int)(boss.xcoord+100),(int)(boss.ycoord+100), 150, 150);
					}	
					if(System.currentTimeMillis()-bTime>2500){
						g.fillOval((int)(boss.xcoord-50),(int)(boss.ycoord+50), 150, 150);
					}
					if(System.currentTimeMillis()-bTime>3800){
						g.fillOval((int)(boss.xcoord),(int)(boss.ycoord+75), 150, 150);
					}
					if(System.currentTimeMillis()-bTime>5100){
						g.fillOval((int)(boss.xcoord+75),(int)(boss.ycoord-10), 150, 150);
					}
					if(System.currentTimeMillis()-bTime>6400){
						g.fillOval((int)(boss.xcoord),(int)(boss.ycoord-50), 150, 150);
					}
					if(System.currentTimeMillis()-bTime>1500&&(System.currentTimeMillis()-bTime-1500)*.255/6<255){
						g.setColor(new Color(255,255,255,(int)((System.currentTimeMillis()-bTime-1500)*.255/5)));//fade to white
						g.fillRect(0, 0, 700, 700);
					}	
				}	
				else{
		    		this.setBackground(Color.WHITE);
					g.setColor(Color.WHITE);
					g.fillRect(0, 0, 700, 700);
					g.setColor(Color.BLACK);  
				    g.setFont(new Font("Serif", Font.BOLD, 40));
				    g.drawString("Congratulations-You Win!",size/2-225,size/2);
				    g.setFont(new Font("Arial", Font.BOLD, 22));
				    g.drawString("Press N to play again",size/2-107,size/2+100);
				    g.setFont(new Font("Arial", Font.BOLD, 20));
					g.drawString("SCORE:"+Integer.toString(score),575,675);
				}
			}
		}			
	}     
	public static void main(String[] args) { 
	    while(true){
	    	Game g=new Game();
	    	g.run();
	    	if(g.newGame){
	    		Game.missiles.clear();
	    		Game.bullets.clear();
	    		Game.lasers.clear();
	    		g.dispose();
	    	}
	    }
	}
	  
	private class KeyPress extends KeyAdapter {
	        private final char W = 'w';
	        private final char A = 'a';
	        private final char S = 's';
	        private final char D = 'd';
	        private final char P = 'p';
	        private final char N = 'n';
	        private final char Space = ' ';
	        long tShot=0;
	
	        public void keyPressed (KeyEvent e) {
	            char key = e.getKeyChar();
	            switch (key){
	            case W:
	              ship.moveY(-15);
	              break;
	            case A:
	              ship.moveX(-15);
	              break;
	            case S:
	              ship.moveY(15);
	              break;
	            case D:
	              ship.moveX(15);
	              break;
	            case P:
	               if(paused==true) paused =false;
	               else paused = true;       
	               break;
	            case Space:
	              if(System.currentTimeMillis()-tShot>300){
	                Bullet b = ship.shoot();
	                bullets.add(b);
	                tShot=System.currentTimeMillis();
	              }
	              break;
	            case N:
	              newGame=true;
	              break;
	        }
	      }
	}
	public class HandleEvents extends WindowAdapter {
	    public void windowClosing(WindowEvent we) {
	        System.exit(0);
	    }
	}
}
