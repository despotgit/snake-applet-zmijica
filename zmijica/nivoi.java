import java.util.*;

public class nivoi 
{
   public ArrayList[] zidovi=new ArrayList[15];
   
   public nivoi()
   {
       
       ArrayList kocke1=new ArrayList();
       
       for(int i=1;i<78;i++) /* gornja shtangla okvira */
       {
           Kocka k=new Kocka(i,1);
           kocke1.add(k);
       }
       for(int j=1;j<58;j++)    /* leva shtangla okvira */
       {
           Kocka k=new Kocka(1,j);
           kocke1.add(k);          
       }
       for(int i=1;i<79;i++)    /* donja shtangla okvira */
       {
           Kocka k=new Kocka(i,58);
           kocke1.add(k);
       }
       for(int j=1;j<58;j++)    /* desna shtangla okvira */
       {
           Kocka k=new Kocka(78,j);
           kocke1.add(k);         
       }
       zidovi[0]=kocke1;   /* Ubacivanje okvira nivoa u niz zidova */
             
       ArrayList kocke2=new ArrayList();
       
       for(int i=30;i<50;i++)    /* sredisnja shtangla za 2. nivo */
       {
           Kocka k=new Kocka(i,29);
           kocke2.add(k);
       }
       
       zidovi[1]=kocke2;    /* sredisnja shtangla */
       
       ArrayList kocke3=new ArrayList();
       
       for(int j=20;j<40;j++)    /* leva i desna sht. za 3. nivo */
       {
           Kocka k1=new Kocka(20,j);
           Kocka k2=new Kocka(58,j);
           
           kocke3.add(k1);
           kocke3.add(k2);      
       }
       
       zidovi[2]=kocke3;     /* leva i desna vert. shtangla */
   
       ArrayList kocke4=new ArrayList();
       
       for(int j=2;j<27;j++)        /* gornje leva shtangla 4. nivoa */
       {
           Kocka k1=new Kocka(20,j);
           kocke4.add(k1);
       }
       for(int i=50;i<78;i++)   /* gornje desna */
       {
           Kocka k2=new Kocka(i,20);
           kocke4.add(k2);          
       }
       for(int i=2;i<30;i++)        /* donje leva */
       {
           Kocka k3=new Kocka(i,40);
           kocke4.add(k3);
       }
       for(int j=33;j<58;j++)          /* donje desna */
       {
           Kocka k4=new Kocka(60,j);
           kocke4.add(k4);          
       }   
       
       zidovi[3]=kocke4;
       
       //Ovde pocinjem sa gradnjom 5. nivoa
       ArrayList kocke5=new ArrayList();
       
       for(int j=2;j<15;j++)
       {
           Kocka k5=new Kocka(40,j);
           kocke5.add(k5);       
       }
       for(int i=2;i<30;i++)
       {
          Kocka k5=new Kocka(i,10);
          kocke5.add(k5);          
       }
       for(int i=50;i<78;i++)
       {
           Kocka k5=new Kocka(i,10);
           kocke5.add(k5);
       }
       for(int j=10;j<25;j++)
       {
           Kocka k51=new Kocka(30,j);
           Kocka k52=new Kocka(50,j);
           kocke5.add(k51);
           kocke5.add(k52);
           
           
       }
       for(int i=2;i<20;i++)
       {
           Kocka k5=new Kocka(i,20);
           kocke5.add(k5);
       }
       for(int i=60;i<78;i++)
       {
           Kocka k5=new Kocka(i,20);
           kocke5.add(k5);
               
       }
       for(int j=20;j<35;j++)
       {
           Kocka k1=new Kocka(20,j);
           Kocka k2=new Kocka(60,j);
           kocke5.add(k1);
           kocke5.add(k2);
       }
       
       for(int j=30;j<50;j++)
       {
           Kocka k1=new Kocka(10,j);
           Kocka k2=new Kocka(70,j);
           kocke5.add(k1);
           kocke5.add(k2);
       }
       
       for(int i=2;i<10;i++)
       {
           Kocka k1=new Kocka(i,30);
           kocke5.add(k1);
                      
           
       }
       for(int i=70;i<78;i++)
       {
           Kocka k1=new Kocka(i,30);
           kocke5.add(k1);
           
           
           
           
       }
       for(int i=30;i<50;i++)
       {
           Kocka k1=new Kocka(i,35);
           kocke5.add(k1);
           
           
           
       }
       for(int i=20;i<60;i++)
       {
           Kocka k1=new Kocka(i,45);
           kocke5.add(k1);
       }
       
       
       
       
       
       
       
       
       
       
           
       zidovi[4]=kocke5;
       
       
       
   }
     
}
