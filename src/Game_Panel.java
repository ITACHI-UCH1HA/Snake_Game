import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;

public class Game_Panel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH=750;
    static final int Screen_height=750;
    static final int unit_size=25;
    static final int game_units=(SCREEN_WIDTH*Screen_height)/unit_size;
    static final int delay=75;
    final int[] x=new int[game_units];
    final int[] y=new int[game_units];
    int bodyparts=3;
    int appleseaten;
    int appleX;
    int appleY;
    char direction='d';
    boolean running=false;
    Timer timer;
    Random random;
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    Game_Panel(){
        random=new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,Screen_height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new mykeyAdapter());
        StartGame();
    }
    public void StartGame(){
        newApple();
        running=true;
        timer=new Timer(delay,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
       if(running){
           for (int i = 0; i < Screen_height/unit_size; i++) {
               g.drawLine(i*unit_size,0,i*unit_size,Screen_height);
               g.drawLine(0,i*unit_size,SCREEN_WIDTH,i*unit_size);
           }
           for (int i = 0; i < SCREEN_WIDTH/unit_size; i++) {
               g.drawLine(i*unit_size,0,i*unit_size,Screen_height);
           }
           g.setColor(Color.RED);
//        g.fillOval(appleX,appleY,unit_size,unit_size);
           g.fillOval(appleX, appleY, unit_size, unit_size);
           for (int i = 0; i < bodyparts; i++) {
               if(i==0){
                   g.setColor(Color.GREEN);
                   g.fillRoundRect(x[i],y[i],unit_size,unit_size,15,15);
               }
               else{
                   g.setColor(new Color(45,180,0));
                   g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                   g.fillRoundRect(x[i],y[i],unit_size,unit_size,15,15);
               }
           }
           g.setColor(Color.RED);
           g.setFont(new Font("Ink Free",Font.BOLD,40));
           FontMetrics metrics=getFontMetrics(g.getFont());
           g.drawString("Score : "+appleseaten,(SCREEN_WIDTH-metrics.stringWidth("Score : "+appleseaten))/2,g.getFont().getSize());
       }else{
           Gameover(g);
       }
    }
    public void newApple(){
        appleX=random.nextInt((int)(SCREEN_WIDTH/unit_size))*unit_size;
        appleY=random.nextInt((int)(Screen_height/unit_size))*unit_size;
//        appleX=random.nextInt((int)(SCREEN_WIDTH/unit_size));
//        appleY=random.nextInt((int)(Screen_height/unit_size));
    }
    public void move(){
        for (int i = bodyparts; i >0 ; i--) {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch (direction){
            case 'w':
                y[0]=y[0]-unit_size;
                break;
            case 's':
                y[0]=y[0]+unit_size;
                break;
            case 'a':
                x[0]=x[0]-unit_size;
                break;
            case 'd':
                x[0]=x[0]+unit_size;
                break;
        }
        if(x[0]==appleX && y[0]==appleY){
            bodyparts++;
            appleseaten++;
            newApple();
        }
    }
    public void checkApple(){
        if(x[0]==appleX && y[0]==appleY){
            bodyparts++;
            appleseaten++;
            newApple();
        }
    }
    public void checkcollisions(){
//        checks if the head collides with the body
        for (int i = bodyparts; i >0; i--) {
            if(x[0]==x[i] && y[0]==y[i]){
                running=false;
            }
        }
//        checks if the head touches the left border
        if(x[0]<0){
            running=false;
        }
//        checks if the head touches the right border
        if(x[0]==SCREEN_WIDTH){
            running=false;
        }
//        checks if the head touches the top border
        if(y[0]<0){
            running=false;
        }
//        checks if the head touchs the bottom border
        if(y[0]==Screen_height){
            running=false;
        }
        if(!running){
            timer.stop();
        }
    }
    public void Gameover(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics1=getFontMetrics(g.getFont());
        g.drawString("Score : "+appleseaten,(SCREEN_WIDTH-metrics1.stringWidth("Score : "+appleseaten))/2,g.getFont().getSize());
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics=getFontMetrics(g.getFont());
        g.drawString("Game Over",(SCREEN_WIDTH-metrics.stringWidth("Game Over"))/2,Screen_height/2);
    }
    public class mykeyAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction!='d'){
                        direction='a';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='a'){
                        direction='d';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!='w'){
                        direction='s';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction!='s'){
                        direction='w';
                    }
                    break;
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkcollisions();
        }
        repaint();
    }
}
