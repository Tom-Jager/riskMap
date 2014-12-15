import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by test on 07/12/2014.
 */
public class DrawBoard extends JComponent {
   private Image boardPic;
    private int actCheck = 2;
    int[] fromTile;
    int[] toTile;

     MouseListener listener = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            int[] coords = new int[2];
            coords[0] = e.getX();
            coords[1] = e.getY();
            /*
            if(actCheck%2==0) {
                fromTile = parseTile(coords[0], coords[1]);
            }
            else{
                toTile = parseTile(coords[0], coords[1]);
            }
            */
            toTile = parseTile(coords[0], coords[1]);
            System.out.println(toTile[0]+","+toTile[1]);

        }
    };

   DrawBoard(){
       addMouseListener(listener);
       boardPic = new ImageIcon("C:/test.jpg").getImage();
   }

    public void paintComponent(Graphics g){
        g.drawImage(boardPic,0,0,this);
    }

    public Dimension getPreferredSize() {
        return new Dimension(800, 800);
    }

    public int[] parseTile(int x, int y){
        int[] tile = new int[2];
        tile[0] = (int) (Math.floor(x/100.0))+1;
        tile[1] = (int) Math.floor(8-(y/100.0))+1;
        return tile;
    }

}

