import javax.swing.JFrame;

public class Game_Frame extends JFrame{
    Game_Frame(){
        Game_Panel panel=new Game_Panel();
        this.add(panel);
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
