import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by test on 07/12/2014.
 */



public class LayoutPage extends JFrame implements ActionListener{
    // the following are global as they need to access both go() and both drawGameBoard
    //makes an area to talk to player in that can be accessed from anywhere
    JTextArea logArea;
    //creates a new GameBoard object
    static GameBoard board;
    //creates the frame in which I place my components.
    JFrame gameFrame;
    //creates an empty 8x8 array for making the gui of my board
    JLabel picBoard[][] = new JLabel[8][8];
    //creates the panel where my board will be placed.
    JPanel boardPanel;

    public static void main (String[] args) {
        //creates a new object of this class.
        LayoutPage gui = new LayoutPage();
        board = new GameBoard();
        board.initGameBoard();

        // board.setAllies(board.getGameBoard());
        // board.setAxis(board.getGameBoard());

        //calls the function that sets up my gui.
        gui.go();
    }

    public void go(){
        //sets up a frame to put components in that will close down once the x in corner is pressed
        gameFrame = new JFrame();
        gameFrame.setLayout(null);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //sets up allied actions panel
        JPanel allPan = new JPanel();
        // fills the allied panel with components.
        buildTeamPan("Allies", allPan);

        //sets up axis actions panel
        JPanel axPan = new JPanel();
        // fills the Axis panel with components
        buildTeamPan("Axis",axPan);

        //creates a JPanel for the board to go into
        boardPanel = new JPanel();
        //sets no layout so I can specify the placement of the pieces
        boardPanel.setLayout(null);
       //calls the function that runs through the data board and draws the game board appropriately
        drawPicBoard(board.getGameBoard());
        //adds the panel that holds the board game to the frame
        boardPanel.setBounds(155,0,900,850);
        gameFrame.getContentPane().add(boardPanel);

       logArea = new JTextArea("Welcome to Risk!",10,10);
        logArea.append("\n" + "Allied placement phase");
        logArea.setLineWrap(true);
        logArea.setCaretPosition(logArea.getDocument().getLength());

        JScrollPane logPane = new JScrollPane(logArea);
        logPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        logPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        logPane.setBounds(233,850,800,100);

        gameFrame.getContentPane().add(logPane);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gs = ge.getDefaultScreenDevice();
        gs.setFullScreenWindow(gameFrame);
        gameFrame.setVisible(true);
    }

    private void buildTeamPan(String team, JPanel panel) {
        Color teamCol;
        int posX = 0;
        int posY = 0;
        int width = 175;
        int height = 600;
        if(team == "Allies"){
            teamCol = Color.blue;
        }
        else{
            teamCol = Color.red;
            posX = 1089;
        }
        panel.setBorder(BorderFactory.createLineBorder(teamCol));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(team,JLabel.CENTER);
        title.setFont(new Font("Arial Black",Font.BOLD,20));
        title.setForeground(teamCol);

        String[] troopList = {"0","1","2","3","4","5","6","7","8","9","10"};
        JList troopsBox = new JList(troopList);
        troopsBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane troopScroller = new JScrollPane(troopsBox);
        troopScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        troopScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        troopScroller.setMaximumSize(new Dimension(200,200));

        JLabel troopCounter1 = new JLabel("Troops left");
        troopCounter1.setFont(new Font("Arial",Font.PLAIN,16));

        JLabel troopCounter2 = new JLabel("to place:");
        troopCounter2.setFont(new Font("Arial",Font.PLAIN,16));
        troopCounter2.setText(troopCounter2.getText()+ " 13");

        JLabel troopNo = new JLabel("Troops to place");
        troopNo.setFont(new Font("Arial",Font.PLAIN,16));
        troopNo.setLabelFor(troopsBox);

        JButton endBut = new JButton("End " + team + " Phase");
        endBut.setPreferredSize(new Dimension(140,40));
        endBut.addActionListener(this);

        panel.setBounds(posX,posY,width,height);

        panel.add(title);
        panel.add(troopNo);
        panel.add(troopScroller);
        panel.add(troopCounter1);
        panel.add(troopCounter2);
        panel.add(endBut);

        gameFrame.getContentPane().add(panel);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String name = "";
        name = e.getActionCommand();
        if(name.equals("End Allied Phase")){
            allPhaseEnd();
        }
        else if(name.equals("End Axis Phase")){
            axPhaseEnd();
        }
    }

    public void drawPicBoard(String[][] dataBoard){
        //dataBoard = board.getGameBoard();
        for(int y=0;y<8;y++){
            for(int x=0;x<8;x++){
                picBoard[y][x] = new JLabel();
                picBoard[y][x].setBounds((x * 100)+80, ((8-y) * 100)-90, 100, 100);
                picBoard[y][x].setVerticalTextPosition(SwingConstants.CENTER);
                picBoard[y][x].setHorizontalTextPosition(SwingConstants.CENTER);
                if(dataBoard[y][x].contains("O")) {
                    picBoard[y][x].setIcon(new ImageIcon("C:/riskRes/obj.png"));
                    picBoard[y][x].setText("V");
                    picBoard[y][x].setFont(new Font("Arial Black",Font.BOLD,64));
                    if(dataBoard[y][x].contains("A")) {
                        picBoard[y][x].setForeground(Color.BLUE);
                    }
                    else{
                        picBoard[y][x].setForeground(Color.RED);
                    }
                }
                else if(dataBoard[y][x].contains("#")){
                    if(dataBoard[y][x].contains("/")){
                        picBoard[y][x].setIcon(new ImageIcon("C:/riskRes/slash.png"));
                    }
                    else{
                        picBoard[y][x].setIcon(new ImageIcon("C:/riskRes/green.png"));
                    }
                }
                else{
                    if(dataBoard[y][x].contains("A")){
                        picBoard[y][x].setIcon(new ImageIcon("C:/riskRes/blue.png"));
                        dataBoard[y][x].replace("A:" ,"");
                    }
                    else{
                        picBoard[y][x].setIcon(new ImageIcon("C:/riskRes/red.png"));
                        dataBoard[y][x].replace("X:" ,"");
                    }
                    picBoard[y][x].setForeground(Color.WHITE);
                }
                boardPanel.add(picBoard[y][x]);
            }
        }

    }

    private void axPhaseEnd() {
        logArea.append("\n" + "Axis Phase Ended");
    }

    private void allPhaseEnd() {
        logArea.append(("\n"+"Allied Phase Ended"));
    }
}
