import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Tom Jager on 10/12/2014.
 */
public class DrawPiece extends JPanel {

    private int x;
    private int y;
    private String name;
    Image piece = new ImageIcon("C:/" + name).getImage();

    void setPieceName(String argName){
        name = argName;
    }
    void setX(int argX) {
        x = argX;
    }

    void setY(int argY) {
        y = argY;
    }

    public void paintComponent(Graphics g){
        g.drawImage(piece,(x-1)*100,(y-1)*100,this);
    }

    public Dimension getPreferredSize() {
        return new Dimension(100, 100);
    }

}
