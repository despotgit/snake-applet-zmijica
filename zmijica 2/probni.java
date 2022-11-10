import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import com.sun.j3d.utils.geometry.Text2D;
import java.awt.geom.*;
import java.applet.*;

public class probni extends JApplet implements KeyListener
{
int x=10;
int y=10;

final static BasicStroke stroke = new BasicStroke(2.0f);    
 public Graphics2D g2;
boolean dali=false;

public void init()
{

addKeyListener(this);
setSize(800,600);
        setLocation(10,10);


}
public void paint(Graphics g)
{

          g2=(Graphics2D)g;
        g2.setStroke(stroke);



if(dali) {Rectangle2D koc=new Rectangle2D.Double(x*10,y*10,10,10);
        g2.setColor(Color.DARK_GRAY);
        g2.fill(koc);  }

}
public void keyPressed(KeyEvent e)
    {
        
dali=true;
repaint();
               
    }
    public void keyTyped(KeyEvent e)
    {
       
    }
    public void keyReleased(KeyEvent e)
    {
        
    }




}