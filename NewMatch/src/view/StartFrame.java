package view;
import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class StartFrame extends JFrame{
    static  ImageIcon welcomeIcon = new ImageIcon("./res/img_1.png");
    static final Image welcome = welcomeIcon.getImage();
    JPanel welcomeBackground = new JPanel(){
        @Serial
        private static final long serialVersionUID = 1L;
        public void paint(Graphics g){
            g.drawImage(welcome,0,0,this.getWidth(),this.getHeight(),null);
        }
    };
    public StartFrame(){
        this.setTitle("Let's Play Chess!");
        this.setLayout(null);
        this.setSize(500, 430);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JButton StartGame=new JButton("Start" );
        StartGame.setFocusPainted(false);
        StartGame.setBackground(Color.white);
        StartGame.setFont(new Font("Rockwell",Font.BOLD,20));
        StartGame.setSize(200,50);
        StartGame.setLocation(160,245);
        StartGame.addActionListener(e -> runMain());
        add(StartGame,BorderLayout.CENTER);
        welcomeBackground.setSize(500,400);
        welcomeBackground.setLocation(0,0);
        add(welcomeBackground);
    }
    public void runMain(){
        SwingUtilities.invokeLater(() -> {

            SwingUtilities.invokeLater(() -> {
                JStart mainFrame = new JStart();
                mainFrame.setVisible(true);
                this.setVisible(false);
            });
        });
    }
}
