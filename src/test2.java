import java.awt.*;

      import javax.swing.*;
         public class test2 extends JFrame  { 
            public static void main(String args[]) { 
               long beforeTime = System.currentTimeMillis();
               long count = 60;
               //JLabel Time = new JLabel(count); //뒤로가기 버튼
               
               JFrame frame = new JFrame("Hey");
               JPanel panel = new JPanel();
               JLabel label = new JLabel("Counter");
               panel.add(label);
               JLabel label1 = new JLabel("" + count); 
               panel.add(label1);
               frame.add(panel); frame.setSize(300, 200);
               frame.setTitle("My Counter");
               frame.setVisible(true); 
               while(true) {
                  long afterTime = System.currentTimeMillis(); 
                  count = ((beforeTime+60000) - afterTime)/1000;
                  //System.out.println("시간차이(m) : "+secDiffTime);
                  label1.setText(count + "");
               }
            } 
         }