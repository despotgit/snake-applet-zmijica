import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import com.sun.j3d.utils.geometry.Text2D;
import java.awt.geom.*;
import java.applet.*;

public class bekap 
       extends JApplet 
       implements KeyListener,ActionListener
{
    final static BasicStroke stroke = new BasicStroke(2.0f);
    public int   novox,novoy,   brisix,brisiy,   jelox,jeloy;
    public Graphics2D g2;
    boolean rikverc=false;           /* da li je rikverc */
    public int varijos=5;              /* koliko jos vari */
    public Timer tajmer=new Timer(30,this);
    public char smer='d';  /* u pocetku zmija ide na desno */
    public char smertmp='d';
    public ArrayList kocke=new ArrayList();    
    public int jelo;
    public int zazid=0;
    public ArrayList stare=new ArrayList();
    public int worth=3;
    public boolean serve=true;
    public int lives=3;
    public boolean imastara=false;
        
    public void init()
    {
        System.out.println("ulaz u init");
        varijos=5;              /* koliko jos vari */
        smer='d';  /* u pocetku zmija ide na desno */
        zazid=0;
                  
        setSize(800,600);
        setLocation(10,10);
        
        kocke.clear();
        kocke.add(new Kocka(37,30));
        kocke.add(new Kocka(38,30));
        kocke.add(new Kocka(39,30));
        kocke.add(new Kocka(40,30));
        kocke.add(new Kocka(41,30));
        
        addKeyListener(this);
        tajmer.start();
        jelo=1;
        System.out.println("izlaz iz inita");
        int nivo=1;
        
    }
    public void paint(Graphics g)
    {
        System.out.println("ulaz u paint");
        g2=(Graphics2D)g;
        g2.setStroke(stroke);
        if (zazid==0) 
        {
            crtajzid(g2);zazid=1;
            for(int i=0;i<kocke.size();i++)
            {
                Kocka k=(Kocka)kocke.get(i);
                kockana(g2,k.x,k.y); 
            }
            
            crtajzivote(g2);        
            
        }
              
        if (kuc())
        {
            crtajzid(g2);
            tajmer.stop();
            lives--;
            JOptionPane.showMessageDialog(null,"Gubis 1 zivot.");
            if (lives==-1)
            {
                JOptionPane.showMessageDialog(null,"GAME OVER");
                System.exit(0);
            }
            varijos=5;              /* koliko jos vari */
            smer='d';               /* u pocetku zmija ide na desno */
            zazid=0;
            smertmp='d';
            serve=true;
            imastara=true;
            
            for(int i=0;i<kocke.size()-1;i++) stare.add((Kocka)kocke.get(i));          
            jelo=1;
            
            kocke.clear();
            
            kocke.add(new Kocka(37,30));
            kocke.add(new Kocka(38,30));
            kocke.add(new Kocka(39,30));
            kocke.add(new Kocka(40,30));
            kocke.add(new Kocka(41,30));
             tajmer.start();
        }
        
        
        /* ispis (nove) glistice: */
        Kocka k=(Kocka)kocke.get(kocke.size()-1);
        kockana(g2,k.x,k.y);
        
        /*  brisanje stare glistice(koja je prethodno napravljena u reset-u) */
        /*  ,ako postoji,kao i starog broja jela:    */
        if(imastara)     
        {  
            for(int i=0;i<stare.size();i++)
            {
                Kocka r=(Kocka)stare.get(i);
                praznona(g2,r.x,r.y);
                
            }
            praznona(g2,jelox,jeloy-1);
            imastara=false;
            stare.clear();
            
        }
        if(brisix!=-1) praznona(g2,brisix,brisiy);
               
        /* Ako je potrebno postaviti novi broj: */
        if(serve)
        {
           novojelo(g2,jelo);
           jelo=jelo+1;
           serve=false;
        }
        
        /* Ako smo dosli do 12,pobeda: */
        if(jelo==22) pobeda();
        System.out.println("izlaz iz painta");
        
    }
   
    public void kockana(Graphics2D gr2,int x,int y)
    {
        Rectangle2D koc=new Rectangle2D.Double(x*10,y*10,10,10);
        gr2.setColor(Color.DARK_GRAY);
        gr2.fill(koc);  
    }
    public void praznona(Graphics2D gr2,int x,int y)
    {
        Rectangle2D prazno=new Rectangle2D.Double(x*10,y*10,10,10);
        gr2.setColor(Color.WHITE);
        gr2.fill(prazno);
                
    }
    public void novojelo(Graphics2D gr2,int broj)
    {
                    System.out.println("ulaz u novo jelo");
                    jelox=2+(int)(Math.random()*76);
                    jeloy=3+(int)(Math.random()*56);
                    gr2.setColor(Color.BLUE);
                    gr2.setFont(new Font("Arial",Font.BOLD,12));
      if (broj>9)   gr2.setFont(new Font("Arial",Font.BOLD,10));
                    gr2.drawString(String.valueOf(broj),jelox*10,jeloy*10);
                    
                    
                    System.out.println("novo jelo stavljeno,"+broj+" "+jelox+" "+jeloy+",izlaz iz novojelo");
                    gr2.drawString(String.valueOf(broj),jelox*10,jeloy*10); 
                    
    }
    public void crtajzid(Graphics2D gr2)
    {
        for(int i=1;i<78;i++) kockana(gr2,i,1);    /* gornja shtangla */
        for(int j=1;j<58;j++) kockana(gr2,1,j);    /* leva shtangla  */
        for(int i=1;i<79;i++) kockana(gr2,i,58);   /* donja shtangla */
        for(int j=1;j<58;j++) kockana(gr2,78,j);   /* desna shtangla */
               
    }
    public void crtajzivote(Graphics2D gr2)
    {
        for (int i=0;i<6;i++)
        {
            if (i<lives) kockana(gr2,i,0);    
            else praznona(gr2,i,0);
        
        }
        
    }
    public void keyPressed(KeyEvent e)
    {
        System.out.println("ulaz u keypressed");
                   
           smertmp=(char)e.getKeyChar(); 
           System.out.println("izlaz iz keypresseda");
               
    }
    public void keyTyped(KeyEvent e)
    {
       
    }
    public void keyReleased(KeyEvent e)
    {
        
    }
    public void actionPerformed(ActionEvent e)
    {
            System.out.println("ulaz u actionperformed");
                        
            if (    ((smertmp=='a')&&(smer=='d')) ||  
                    ((smertmp=='d')&&(smer=='a')) ||  
                    ((smertmp=='w')&&(smer=='s')) ||
                    ((smertmp=='s')&&(smer=='w'))       )  
            {
                   Kocka nula=(Kocka)kocke.get(0);
                   Kocka kec=(Kocka)kocke.get(1);
                   
                   if (nula.tikgoreod(kec)) {smer='w';smertmp=smer;}
                   if (nula.tikdoleod(kec)) {smer='s';smertmp=smer;}
                   if (nula.tiklevood(kec)) {smer='a';smertmp=smer;}
                   if (nula.tikdesnood(kec)) {smer='d';smertmp=smer;}
                   
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
            else smer=smertmp;
            
            Kocka nova;
         /* provera da li zmija vari nesto */
        if(varijos==0)
        {
            int glavax=((Kocka)kocke.get(kocke.size()-1)).x;
            int glavay=((Kocka)kocke.get(kocke.size()-1)).y;
            switch(smer)
            {
            case 'w':nova=new Kocka(glavax,glavay-1);
                     kocke.add(nova);
                     if (gulp()) {serve=true;varijos+=worth*jelo;}
                     brisix=((Kocka)kocke.get(0)).x;
                     brisiy=((Kocka)kocke.get(0)).y;
                     kocke.remove(0);break;
                     
            case 's':nova=new Kocka(glavax,glavay+1);
                     kocke.add(nova);
                     if (gulp()) {serve=true;varijos+=worth*jelo;}
                     brisix=((Kocka)kocke.get(0)).x;
                     brisiy=((Kocka)kocke.get(0)).y;
                     kocke.remove(0);break;
                     
            case 'a':nova=new Kocka(glavax-1,glavay);
                     kocke.add(nova);
                     if (gulp()) {serve=true;varijos+=worth*jelo;}
                     brisix=((Kocka)kocke.get(0)).x;
                     brisiy=((Kocka)kocke.get(0)).y;
                     kocke.remove(0);break;
                
            case 'd':nova=new Kocka(glavax+1,glavay);
                     kocke.add(nova);
                     if (gulp()) {serve=true;varijos+=worth*jelo;}
                     brisix=((Kocka)kocke.get(0)).x;
                     brisiy=((Kocka)kocke.get(0)).y;
                     kocke.remove(0);break;
                
            }  /* end switch */
            
        }   /* end if (varijos==0) */
        else
        /* u sledecem slucaju samo nakacinjemo novu kocku */
        if(varijos>0)
        {
            int glavax=((Kocka)kocke.get(kocke.size()-1)).x;
            int glavay=((Kocka)kocke.get(kocke.size()-1)).y;
            switch(smer)
            {
            case 'w':nova=new Kocka(glavax,glavay-1);
                     kocke.add(nova);
                     if (gulp()) {serve=true;varijos+=worth*jelo;}
                     break;
                     
            case 's':nova=new Kocka(glavax,glavay+1);
                     kocke.add(nova);
                     if (gulp()) {serve=true;varijos+=worth*jelo;}
                     break;
                     
            case 'a':nova=new Kocka(glavax-1,glavay);
                     kocke.add(nova);
                     if (gulp()) {serve=true;varijos+=worth*jelo;}
                     break;
                
            case 'd':nova=new Kocka(glavax+1,glavay);
                     kocke.add(nova);
                     if (gulp()) {serve=true;varijos+=worth*jelo;}
                     break;
                
            }  /* end switch */
            
            varijos--;
            brisix=-1;
        }
        
        
        if(!(tajmer.isRunning())) tajmer.start();        
        repaint();        
        System.out.println("izlaz iz actionperformed-a");
            
    }
    public boolean kuc()   /* proverava da li je bio kuc */
    {
        System.out.println("ulaz u kuc");
        
        /* sledi provera sudara sa samim sobom */
        
        for (int i=0;i<kocke.size();i++)
        for (int j=0;j<kocke.size();j++)
        {
            Kocka a=(Kocka)kocke.get(i);
            Kocka b=(Kocka)kocke.get(j);
            if ( (a.x==b.x) && (a.y==b.y) && (i!=j) ) 
            {
               System.out.println("izlaz iz kuca,true,"+i+" "+j);
               return true;            
            }
            
        }
        
        /* sledi provera sudara sa zidom */
        
        for (int i=0;i<kocke.size();i++)
        {
            Kocka c=(Kocka)kocke.get(i);
                        
            for(int j=1;j<78;j++)
                {
                   if ((c.x==j)&&(c.y==1)) return true;;    /* gornja shtangla */
                }
            for(int j=1;j<58;j++) 
                {
                    if ((c.x==1)&&(c.y==j)) return true;    /* leva shtangla  */
                }
            for(int j=1;j<79;j++) 
                {
                    if((c.x==j)&&(c.y==58)) return true;   /* donja shtangla */
                }
            for(int j=1;j<58;j++) 
                {
                    if((c.x==78)&&(c.y==j)) return true;  
                }
                        
        }
          
        
        
        System.out.println("izlaz iz kuca,false");
        return false;      
    }
    public void pobeda()
    {
        System.out.println("ulaz u pobedu");
        tajmer.stop();
        JOptionPane.showMessageDialog(null,"Obrnuli ste igricu.");
        System.exit(0);
    }
    public boolean gulp()
    {
        
        System.out.println("ulaz u gulp");
        for (int i=0;i<kocke.size();i++)
        {
            Kocka k=(Kocka)kocke.get(i);
            if ( (k.x==jelox) && (k.y+1==jeloy) )
                {System.out.println("izlaz iz gulpa,vrednost true");
                return true;}
               
        }
        System.out.println("izlaz iz gulpa,vrednost false");
        return false;
        
    }
    public void reset()
    {
        System.out.println("ulazimo u reset");
        varijos=5;              /* koliko jos vari */
        smer='d';  /* u pocetku zmija ide na desno */
        zazid=0;
        smertmp='d';
        serve=true;
       
        imastara=true;
        stare=(ArrayList)kocke.clone();
        kocke.clear();
        
        kocke.add(new Kocka(37,30));
        kocke.add(new Kocka(38,30));
        kocke.add(new Kocka(39,30));
        kocke.add(new Kocka(40,30));
        kocke.add(new Kocka(41,30));
        jelo=1;
        
       System.out.println("izlazimo iz reseta");
    }
    
}
            
     



