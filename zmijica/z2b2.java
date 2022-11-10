import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import com.ms.awt.image.Image;
import com.sun.j3d.utils.geometry.Text2D;
import java.awt.geom.*;
import java.applet.*;

public class z2b2 
       extends JApplet 
       implements KeyListener,ActionListener
{
    final static BasicStroke stroke = new BasicStroke(2.0f);
    int   novox,novoy,   brisix,brisiy,   jelox,jeloy;
    public Graphics2D g2;
    boolean rikverc=false;           /* da li je rikverc */
    int varijos=0;              /* koliko jos vari */
    Timer tajmer=new Timer(50,this);
    char smer='d';  /* u pocetku zmija ide na desno */
    char smertmp='d';
    ArrayList kocke=new ArrayList();    
    int jelo;
    int zazid=0;
    ArrayList stare=new ArrayList();
    int worth=1;
    boolean serve=true;
    int lives=8;
    boolean imastara=false;
    int nivo;
    nivoi n=new nivoi();
    boolean novinivo=false;
    ArrayList[] zid=n.zidovi;
    private java.awt.Image mrlja;
    int ukupnonivoa=5;
    int lastmeal=11;
    
    public void start()
    {
        repaint();
        
    }
    
    public void init()
    {
        int dilej=Integer.parseInt(JOptionPane.showInputDialog("Unesi delay za timer"));
        tajmer.setDelay(dilej);
       // System.out.println("ulaz u init");
        varijos=5;              /* koliko jos vari */
        smer='d';  /* u pocetku zmija ide na desno */
        zazid=0;
                  
        setSize(800,600);
        setLocation(1,1);
        
        kocke.clear();
        kocke.add(new Kocka(37,31));
        kocke.add(new Kocka(38,31));
        kocke.add(new Kocka(39,31));
        kocke.add(new Kocka(40,31));
        kocke.add(new Kocka(41,31));
        
        addKeyListener(this);
        tajmer.start();
        jelo=1;
        System.out.println("izlaz iz inita");
        nivo=1;        
    }
    public void paint(Graphics g)
    {
       // System.out.println("ulaz u paint");
        g2=(Graphics2D)g;
        g2.setStroke(stroke);
        if (zazid==0) 
        {
            crtajzid(g2);
            zazid=1;
            for(int i=0;i<kocke.size();i++)
            {
                Kocka k=(Kocka)kocke.get(i);
                mrljana(g2,k.x,k.y); 
            }
            crtajzivote(g2);        
            
        }
        if(novinivo)
        {
            
            
            tajmer.stop();
          //  JOptionPane.showMessageDialog(null,"Novi nivo pokrenut.");
            zbrisi(g2);
            novinivo=false;
            crtajzid(g2);
            crtajzivote(g2);
            
           
           
            System.out.println("Novi nivo pokrenut.");
            
            varijos=5;              /* koliko jos vari */
            smer='d';               /* u pocetku zmija ide na desno */
            zazid=0;
            smertmp='d';
            serve=true;
            imastara=false;
            
            for(int i=0;i<kocke.size()-1;i++) stare.add((Kocka)kocke.get(i));          
            stare.clear();
            jelo=1;
            
            kocke.clear();
            
            kocke.add(new Kocka(37,31));
            kocke.add(new Kocka(38,31));
            kocke.add(new Kocka(39,31));
            kocke.add(new Kocka(40,31));
            kocke.add(new Kocka(41,31));
            
            tajmer.start();
                 
        }
              
        if  (kuc())
        {
            kucaj();
        }
        
        
        /* ispis (nove) glistice: */
        Kocka k=(Kocka)kocke.get(kocke.size()-1);
        mrljana(g2,k.x,k.y);
        
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
        
        /* Ako smo dosli do poslednjeg jela,prethodno definisanog,pobeda: */
        if(jelo==lastmeal) pobeda();
       // System.out.println("izlaz iz painta");
        
    }
   
    public void zivotna(Graphics2D gr2,int x,int y)
    {
        Ellipse2D el=new Ellipse2D.Double(x*10,y*10,10,10);
        gr2.setColor(Color.GREEN);
        gr2.fill(el);
                
    }
        
    public void kockana(Graphics2D gr2,int x,int y)
    {
        float r=Float.parseFloat("0.875");
        float g=Float.parseFloat("0.380");
        float b=Float.parseFloat("0.086");
        
        Rectangle2D koc=new Rectangle2D.Double(x*10,y*10,10,10);
        gr2.setColor(new Color(r,g,b));
        gr2.fill(koc);  
    }
    public void praznona(Graphics2D gr2,int x,int y)
    {
        Rectangle2D prazno=new Rectangle2D.Double(x*10,y*10,10,10);
        gr2.setColor(Color.WHITE);
        gr2.fill(prazno);
                
    }
    public void zutona(Graphics2D gr2,int x,int y)
    {
        Rectangle2D prazno=new Rectangle2D.Double(x*10,y*10,10,10);
        gr2.setColor(Color.WHITE);
        gr2.fill(prazno);
        
              
    }
    public void mrljana(Graphics2D im2,int x ,int y)
    {
        mrlja=getImage(getDocumentBase(),"mrlja.gif");
        im2.drawImage(mrlja,x*10,y*10,this);
                
    }
    
    public void kucaj()
    {
        
        tajmer.stop();
        zbrisi(g2);
        if(!novinivo)
        lives--;
        crtajzid(g2);
        crtajzivote(g2);
        if(!novinivo)
        {
          //  JOptionPane.showMessageDialog(null,"Gubis 1 zivot.");
            if (lives==0)
            {
                JOptionPane.showMessageDialog(null,"GAME OVER");
                System.exit(0);
            }
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
        
        kocke.add(new Kocka(37,31));
        kocke.add(new Kocka(38,31));
        kocke.add(new Kocka(39,31));
        kocke.add(new Kocka(40,31));
        kocke.add(new Kocka(41,31));
        tajmer.start();
        
        
        
        
    }
    
    public void novojelo(Graphics2D gr2,int broj)
    {
                    AudioClip zvuk=getAudioClip(getDocumentBase(),"gulp.wav");
                    zvuk.play();
        
                 //   System.out.println("ulaz u novo jelo");
                    Kocka zajelo;
                    ArrayList ljelo=new ArrayList();
                    do
                    {
                        jelox=2+(int)(Math.random()*76);
                        jeloy=3+(int)(Math.random()*56);
                        zajelo=new Kocka(jelox,jeloy-1);
                        ljelo.add(zajelo);
                    }
                    while(zajelo.seche(ljelo,zid[nivo-1]));
                            
                    gr2.setColor(Color.BLUE);
                    gr2.setFont(new Font("Arial",Font.BOLD,12));
      if (broj>9)   gr2.setFont(new Font("Arial",Font.BOLD,10));
                    gr2.drawString(String.valueOf(broj),jelox*10,jeloy*10);
                    
                    
                    System.out.println("novo jelo stavljeno,"+broj+" "+jelox+" "+jeloy+",izlaz iz novojelo");
                    gr2.drawString(String.valueOf(broj),jelox*10,jeloy*10);
                    gr2.drawString(String.valueOf(broj),jelox*10,jeloy*10);
                    
     
      praznona(gr2,10,0);praznona(gr2,11,0);praznona(g2,12,0);
      gr2.setColor(Color.BLUE);
      gr2.setFont(new Font("Arial",Font.BOLD,12));
      gr2.drawString(String.valueOf(jelox)+","+String.valueOf(jeloy),100,10);
             
    }
    public void crtajzid(Graphics2D gr2)
    {
        /* Okvir u svakom sluchaju iscrtavamo */
        int dokle=zid[0].size();
        for(int i=0;i<dokle;i++)
        {
            Kocka k=(Kocka)zid[0].get(i);
            kockana(gr2,k.x,k.y);
        }
        
        dokle=zid[nivo-1].size();
        
        for(int i=0;i<dokle;i++)
        {
            Kocka k=(Kocka)zid[nivo-1].get(i);
            kockana(gr2,k.x,k.y);
        }
        
              
        
    }
    public void crtajzivote(Graphics2D gr2)
    {
        for (int i=1;i<lives+2;i++)
        {
            zivotna(gr2,i,0);    
        }
        for(int i=lives+1;i<10;i++)
        {
            praznona(gr2,i,0);
        }
        
    }
    public void zbrisi(Graphics2D z)
    {
       
        
        
        for (int m=2;m<78;m++)
        {
            for (int n=2;n<58;n++)  zutona(z,m,n);  
        }
        
    }
    
    
    public void keyPressed(KeyEvent e)
    {
       // System.out.println("ulaz u keypressed");
                   
           smertmp=(char)e.getKeyChar(); 
       //    System.out.println("izlaz iz keypresseda");
               
    }
    public void keyTyped(KeyEvent e)
    {
       
    }
    public void keyReleased(KeyEvent e)
    {
        
    }
    public void actionPerformed(ActionEvent e)
    {
           // System.out.println("ulaz u actionperformed");
                        
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
       // System.out.println("izlaz iz actionperformed-a");
            
    }
    public boolean kuc()   /* proverava da li je bio kuc */
    {
        //System.out.println("ulaz u kuc");
        
        /* sledi provera sudara sa samim sobom */
        
        for (int i=0;i<kocke.size();i++)
        for (int j=0;j<kocke.size();j++)
        {
            Kocka a=(Kocka)kocke.get(i);
            Kocka b=(Kocka)kocke.get(j);
            if ( (a.x==b.x) && (a.y==b.y) && (i!=j) ) 
            {
            //   System.out.println("izlaz iz kuca,sudar sa samim sobom"+i+" "+j);
               return true;            
            }
        }
        
        /* sledi provera sudara sa zidovima */
        Kocka cube=new Kocka();
        if (cube.seche(kocke,zid[0])) return true;
        if (cube.seche(kocke,zid[nivo-1])) return true;
             
     //   System.out.println("izlaz iz kuca,false");
        return false;      
    }
    public void pobeda()
    {
        tajmer.stop();
        zbrisi(g2);
    //  System.out.println("ulaz u pobedu");
        if (nivo==ukupnonivoa)
        {
            JOptionPane.showMessageDialog(null,"Obrnuli ste moju extra igricu.");
            
            System.exit(0);
        }
                
        
        nivo++;
       // JOptionPane.showMessageDialog(null,"Preshli ste nivo,svaka chast.");
        
        novinivo=true;
        
        serve=true;
        jelo=1;
        tajmer.start();
                  
    }
    public boolean gulp()
    {
        
     //   System.out.println("ulaz u gulp");
        for (int i=0;i<kocke.size();i++)
        {
            Kocka k=(Kocka)kocke.get(i);
            if ( (k.x==jelox) && (k.y+1==jeloy) )
                {
                    //System.out.println("izlaz iz gulpa,vrednost true");
                    return true;
                }
               
        }
      //  System.out.println("izlaz iz gulpa,vrednost false");
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
        
        kocke.add(new Kocka(37,31));
        kocke.add(new Kocka(38,31));
        kocke.add(new Kocka(39,31));
        kocke.add(new Kocka(40,31));
        kocke.add(new Kocka(41,31));
        jelo=1;
        
      // System.out.println("izlazimo iz reseta");
    }
    
    
    
}
            
     
