import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
 // Java extension packages
import javax.swing.*;
import javax.swing.event.*;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.applet.*;

public class zmija 
       extends JApplet 
       implements KeyListener,ActionListener
{
    boolean rikverc=false;
    public int[][] matrica=new int[80][60];
    final static BasicStroke stroke = new BasicStroke(2.0f);
    int novox,novoy,brisix,brisiy;
    Graphics2D g2;
    boolean vari=false;           /* da li vari */
    int varijos=5;             /* koliko jos vari */
    public Timer tajmer=new Timer(40,this);
    char smer='d';            /* u pocetku ide na desno */
    public ArrayList kocke=new ArrayList();    
    int zazid=0;
    int jelo;
    
    public void init()
    {
        setSize(800,600);
        setLocation(10,10);
              
        kocke.add(new Kocka(27,30));
        kocke.add(new Kocka(38,30));
        kocke.add(new Kocka(39,30));
        kocke.add(new Kocka(40,30));
        kocke.add(new Kocka(41,30));
        
        addKeyListener(this);
        tajmer.start();
        jelo=1;
                
    }
    public void paint(Graphics g)
    {
        g2=(Graphics2D)g;
        g2.setStroke(stroke);
        if (zazid==0) {crtajzid(g2);zazid++;}
        if(1>2)
        for(int i=0;i<kocke.size();i++)
        {
            Kocka kockica=(Kocka)kocke.get(i);
            kockana(g2,kockica.x,kockica.y); 
        }
        kockana(g2,((Kocka)kocke.get(kocke.size()-1)).x,((Kocka)kocke.get(kocke.size()-1)).y);
        if(brisix!=-1) praznona(g2,brisix,brisiy);
                       
    } 
    public void stop()
    {
             
    }
    public void start()
    {
        zazid=0;
        repaint();
    }
    
    public void kockana(Graphics2D g2,int x,int y)
    {
        Rectangle2D koc=new Rectangle2D.Double(x*10,y*10,10,10);
        g2.setColor(Color.DARK_GRAY);
        g2.draw(koc); 
        g2.setColor(Color.GREEN);
        g2.fill(koc);  
    }
    public void praznona(Graphics2D g2,int x,int y)
    {
        Rectangle2D prazno=new Rectangle2D.Double(x*10,y*10,10,10);
        g2.setColor(Color.WHITE);
        g2.draw(prazno);
        g2.setColor(Color.WHITE);
        g2.fill(prazno);
                
    }
    public void novojelo(Graphics2D g2,int broj)
    {
        int xpos=1+(int)(Math.random()*80);
        int ypos=1+(int)(Math.random()*60);
        Rectangle2D jelce=new Rectangle2D.Double(xpos*10,ypos*10,10,10);
        g2.setColor(Color.ORANGE);
        g2.fill(jelce);
                    
    }
    
    public void crtajzid(Graphics2D g2)
    {
        for(int i=1;i<78;i++) kockana(g2,i,1);    /* gornja shtangla */
        for(int j=1;j<58;j++) kockana(g2,1,j);    /* leva shtangla  */
        for(int i=1;i<78;i++) kockana(g2,i,58);   /* donja shtangla */
        for(int j=1;j<58;j++) kockana(g2,78,j);   /* desna shtangla */
        kockana(g2,78,58);
        
        
        
    }
    public void keyPressed(KeyEvent e)
    {
        if ( ((e.getKeyChar()=='a')&&(smer=='d')) ||  
                ((e.getKeyChar()=='d')&&(smer=='a')) ||  
                ((e.getKeyChar()=='w')&&(smer=='s')) ||
                ((e.getKeyChar()=='s')&&(smer=='w')) )  
           {
               rikverc=true;
               ArrayList inv=new ArrayList(kocke.size());
               
               for(int i=0;i<kocke.size();i++)
               {
                   inv.add(kocke.get(i));                    
               }
                                      
               for(int i=kocke.size()-1;i>-1;i--)
               {
                   inv.set(kocke.size()-1-i,kocke.get(i));
               }
               kocke=inv;
               
           }
           
           smer=e.getKeyChar(); 
    }
    public void keyTyped(KeyEvent e)
    {
        
    }
    public void keyReleased(KeyEvent e)
    {
        
    }
    
    public void actionPerformed(ActionEvent e)
    {
        Kocka nova;
        /* provera da li zmija vari nesto */
       if(varijos==0)
       {
           int glavax=((Kocka)kocke.get(kocke.size()-1)).x;
           int glavay=((Kocka)kocke.get(kocke.size()-1)).y;
           switch(smer)
           {
           case 'w':nova=new Kocka(glavax,glavay-1);
                    
                    brisix=((Kocka)kocke.get(0)).x;
                    brisiy=((Kocka)kocke.get(0)).y;
                    kocke.remove(0);
                    kocke.add(nova);
                    break;
                    
           case 's':nova=new Kocka(glavax,glavay+1);
                    kocke.add(nova);
                    brisix=((Kocka)kocke.get(0)).x;
                    brisiy=((Kocka)kocke.get(0)).y;
                    kocke.remove(0);break;
                    
           case 'a':nova=new Kocka(glavax-1,glavay);
                    kocke.add(nova);
                    brisix=((Kocka)kocke.get(0)).x;
                    brisiy=((Kocka)kocke.get(0)).y;
                    kocke.remove(0);break;
               
           case 'd':nova=new Kocka(glavax+1,glavay);
                    kocke.add(nova);
                    brisix=((Kocka)kocke.get(0)).x;
                    brisiy=((Kocka)kocke.get(0)).y;
                    kocke.remove(0);break;
               
           }  /* end switch */
                     
           if (kuc())
           {
               JOptionPane.showMessageDialog(null,"Sudario si se.Jedan zivot manje.");
           }
       }
       
       /* u sledecem slucaju samo nakacinjemo novu kocku */
       if(varijos>0)
       {
           int glavax=((Kocka)kocke.get(kocke.size()-1)).x;
           int glavay=((Kocka)kocke.get(kocke.size()-1)).y;
           switch(smer)
           {
           case 'w':nova=new Kocka(glavax,glavay-1);
                    kocke.add(nova);
                    break;
                    
           case 's':nova=new Kocka(glavax,glavay+1);
                    kocke.add(nova);
                    break;
                    
           case 'a':nova=new Kocka(glavax-1,glavay);
                    kocke.add(nova);
                    break;
               
           case 'd':nova=new Kocka(glavax+1,glavay);
                    kocke.add(nova);
                    break;
               
           }  /* end switch */
                     
           if (kuc())
           {
               JOptionPane.showMessageDialog(null,"Izgino si");
           }
           varijos--;
       }
                     
           repaint();
    }
    public boolean kuc()   /* proverava da li je bio kuc */
    {
        for (int i=0;i<kocke.size();i++)
        for (int j=0;i<kocke.size();i++)
        {
            Kocka a=(Kocka)kocke.get(i);
            Kocka b=(Kocka)kocke.get(j);
            if ((a.x==b.x)&&(a.y==b.y)&&(i!=j)) return true;            
            
        }
        return false;      
    }
}

     

