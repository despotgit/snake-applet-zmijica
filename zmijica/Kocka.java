import java.util.*;


public class Kocka 
{
    public Kocka()
    {
    }
    
    public Kocka(int a,int b)
    {
        this.x=a;
        this.y=b;
    }
    public int x;
    public int y;
    
    public boolean tiklevood(Kocka kec)
    {
        if ((x==kec.x-1)&&(y==kec.y)) return true;
        else return false;
    }
    public boolean tikdesnood(Kocka kec)
    {
        if ((x==kec.x+1)&&(y==kec.y)) return true;
        else return false;
    }
    public boolean tikdoleod(Kocka kec)
    {
        if ((x==kec.x)&&(y==kec.y+1)) return true;
        else return false;
    }
    public boolean tikgoreod(Kocka kec)
    {
        if ((x==kec.x)&&(y==kec.y-1)) return true;
        else return false;
    }
    
    public boolean seche(ArrayList k1,ArrayList k2)
    {
        for(int i=0;i<k1.size();i++)
        for(int j=0;j<k2.size();j++)
        {
            Kocka a=(Kocka)k1.get(i);
            Kocka b=(Kocka)k2.get(j);
                        
            if(  (a.x==b.x)  &&  (a.y==b.y)   )
            return true;
                      
        }
        return false;
     }
       
    
}
