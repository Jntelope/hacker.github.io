package view;

import controller.GameController;
import model.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class JStart extends JFrame{
    public static ChessGameFrame gameFrame;
    static  ImageIcon welcomeIcon = new ImageIcon("./res/img.png");
    static final Image welcome = welcomeIcon.getImage();
    JPanel welcomeBackground = new JPanel(){
        @Serial
        private static final long serialVersionUID = 1L;
        public void paint(Graphics g){
            g.drawImage(welcome,0,0,this.getWidth(),this.getHeight(),null);
        }
    };
    public JStart(){
        this.setTitle("Let's Play Happy Match!");
        this.setLayout(null);
        this.setSize(500, 430);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JButton JStart=new JButton("Loading..");
        JStart.setFocusPainted(false);
        JStart.setBackground(Color.white);
        JStart.setFont(new Font("Rockwell",Font.BOLD,20));
        JStart.setSize(180,50);
        JStart.setLocation(50,50);
        JStart.addActionListener(e -> runMain());
        add(JStart,BorderLayout.CENTER);
        welcomeBackground.setSize(500,400);
        welcomeBackground.setLocation(0,0);
        add(welcomeBackground);
    }
    public void runMain(){
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);//todo
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(),mainFrame);
            mainFrame.setGameController(gameController);
            gameController.setStatusLabel(mainFrame.getStatusLabel());
//            mainFrame.setVisible(true);//todo

            gameFrame=mainFrame;
            mainFrame.setJFrame(this);
            mainFrame.setVisible(true);
            this.setVisible(false);
        });  this.setVisible(false);
    }
}
