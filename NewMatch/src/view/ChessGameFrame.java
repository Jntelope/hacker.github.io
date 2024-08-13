package view;

import controller.GameController;
import javazoom.jl.player.Player;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {

    //todo:背景图------------------------------------------------------------------------
    JMenuItem sunSetItem = new JMenuItem("theme1");
    JMenuItem waveItem = new JMenuItem("theme2");
    public static ImageIcon backgroundIcon = Theme.wave.getBackgroundIcon();
    public static Image backgroundImage = backgroundIcon.getImage();
    JPanel backgroundPanel = new JPanel() {

        private static final long serialVersionUID = 1L;

        public void paint(Graphics g) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    };

    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    private final int ONE_CHESS_SIZE;

    private GameController gameController;

    private ChessboardComponent chessboardComponent;

    private JLabel statusLabel;
    private JButton confirmSwapButton;
    private JButton nextStepButton;
    private JButton tool1Button;
    private int nextStepState;
    private int refreshClickCount = 0;
    private int nextStepClickCount = 0;
    StartFrame startFrame;//todo：新加的开始界面
    JStart jStart = new JStart();//todo：新加的伪登陆界面
    private JButton tool2Button;


    //todo：初始化菜单----------------------------------------------------------------
    public void addinitMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Theme");
        menuBar.add(menu);
        menu.add(sunSetItem);
        menu.add(waveItem);
        setJMenuBar(menuBar);
        sunSetItem.addActionListener(e -> {
            backgroundIcon = Theme.sunSet.getBackgroundIcon();
            backgroundImage = backgroundIcon.getImage();
            backgroundPanel.repaint();
        });
        waveItem.addActionListener(e -> {
            backgroundIcon = Theme.wave.getBackgroundIcon();
            backgroundImage = backgroundIcon.getImage();
            backgroundPanel.repaint();
        });
    }

    //todo：bgm--------------------------------------------------------------------

    class MusicButtonChangeListener implements ChangeListener {
        public static PlayThread playThread = null;


        @Override
        public void stateChanged(ChangeEvent e) {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            checkBox.setText("bgm");
            checkBox.setFont(new Font("Rockwell", Font.BOLD, 15));
            checkBox.setSize(80, 80);
            checkBox.setMargin(new Insets(0, 0, 0, 0));
            if (checkBox.isSelected()) {
                if (playThread == null) {
                    System.out.println("played");
                    playThread = new PlayThread();
                    playThread.start();
                }
            } else {
                if (playThread != null) {
                    System.out.println("stopped");
                    playThread.stop();
                    playThread = null;
                }
            }
        }
    }

    class PlayThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    File file = new File("res/唐云歌 - 和兰花在一起 (钢琴曲) (V0).mp3");
                    //            System.out.println(file.getAbsolutePath());
                    Player player = new Player(new FileInputStream(file));
                    player.play();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

//todo：上面还是bgm-------------------------------------------------------------------------------
    public ChessGameFrame(int width, int height) {

//        try {
//            backgroundImage = ImageIO.read(new File("res/img.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }//todo：这里看看能不能用背景，不行的话就用tg

        setTitle("Happy Match"); //设置标题
        this.WIDTH = width+300;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addChessboard();
        addLabel();
        addRestartButton();
        addSwapConfirmButton();
        addNextStepButton();
        addLoadButton();
        addSaveButton();
        addSelectOtherLevelButton();
        addTool1Button();
        addTool2Button();
        addHintForRefreshButton();
        addHintButton();
        addWithdrawButton();

        addinitMenu();//todo：初始化菜单

        MusicButtonChangeListener musicListener = new MusicButtonChangeListener();
        JCheckBox musicBtn = new JCheckBox("Music");
        musicBtn.addChangeListener(musicListener);
        musicBtn.setSelected(true);
        musicBtn.setSize(50, 50);
        musicBtn.setLocation(1200, 600);
        musicBtn.setContentAreaFilled(false);
        add(musicBtn);

        backgroundPanel.setOpaque(false);//todo：背景把东西覆盖了，所以要设置透明

        backgroundPanel.setSize(1500,1000);
        backgroundPanel.setLocation(0, 0);
        add(backgroundPanel);
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {//todo：这个方法没用吗？
        this.chessboardComponent = chessboardComponent;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5 - 40, HEIGTH / 10 - 20);
        add(chessboardComponent);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        this.statusLabel = new JLabel("Happy Match");
        statusLabel.setLocation(HEIGTH, HEIGTH / 8);
        statusLabel.setSize(1000, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 16));
        add(statusLabel);
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Confirm restart game.");
            gameController.initialize();
        });
        button.setLocation(HEIGTH - 20, HEIGTH / 10 + 120);
        button.setSize(220, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addSwapConfirmButton() {
        confirmSwapButton = new JButton("Confirm Swap");
        confirmSwapButton.addActionListener((e) -> chessboardComponent.swapChess());
        confirmSwapButton.setLocation(HEIGTH - 20, HEIGTH / 10 + 200);
        confirmSwapButton.setSize(220, 60);
        confirmSwapButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(confirmSwapButton);
        nextStepState = 0;
    }

    private void addNextStepButton() {
        nextStepButton = new JButton("Next Step");
        nextStepButton.addActionListener((e) -> chessboardComponent.nextStep());
        nextStepButton.setLocation(HEIGTH - 20, HEIGTH / 10 + 280);
        nextStepButton.setSize(220, 60);
        nextStepButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(nextStepButton);
        nextStepButton.setEnabled(false);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH - 20, HEIGTH / 10 + 360);
        button.setSize(220, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            System.out.println(path);
            gameController.loadGameFromFile(path);
        });
    }

    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH - 20, HEIGTH / 10 + 440);
        button.setSize(220, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click save");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            System.out.println(path);
            gameController.saveGameToFile(path);
        });
    }

    private void addSelectOtherLevelButton() {
        JButton button = new JButton("Select Other Level");
        button.addActionListener(e -> {
            gameController.change();
        });
        button.setLocation(HEIGTH + 220, HEIGTH / 10 + 120);
        button.setSize(220, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addTool1Button() {
        tool1Button = new JButton("tool 1");
        tool1Button.addActionListener(e -> {
            gameController.tool1();
        });
        tool1Button.setLocation(HEIGTH + 220, HEIGTH / 10 + 200);
        tool1Button.setSize(100, 60);
        tool1Button.setForeground(Color.magenta);
        tool1Button.setFont(new Font("Rockwell", Font.ITALIC, 20));
        add(tool1Button);
    }
    private void addTool2Button() {
        tool2Button = new JButton("tool 2");
        tool2Button.addActionListener(e -> {
            gameController.tool2();
        });
        tool2Button.setLocation(HEIGTH + 340, HEIGTH / 10 + 200);
        tool2Button.setSize(100, 60);
        tool2Button.setForeground(Color.magenta);
        tool2Button.setFont(new Font("Rockwell", Font.ITALIC, 20));
        add(tool2Button);
    }

    private void addHintForRefreshButton() {
        JButton button = new JButton("Hint For Refresh");
        button.addActionListener(e -> {
            gameController.hintForRefresh();
        });
        button.setLocation(HEIGTH + 220, HEIGTH / 10 + 280);
        button.setSize(220, 60);
        button.setForeground(Color.magenta);
        button.setFont(new Font("Rockwell", Font.ITALIC, 20));
        add(button);
    }

    private void addHintButton() {
        JButton button = new JButton("Hint");
        button.addActionListener(e -> {
            gameController.hint();
        });
        button.setLocation(HEIGTH + 220, HEIGTH / 10 + 360);
        button.setSize(220, 60);
        button.setForeground(Color.magenta);
        button.setFont(new Font("Rockwell", Font.ITALIC, 20));
        add(button);
    }

    private void addWithdrawButton() {
        JButton button = new JButton("Withdraw");
        button.addActionListener(e -> {
            gameController.Withdraw();
        });
        button.setLocation(HEIGTH + 220, HEIGTH / 10 + 440);
        button.setSize(220, 60);
        button.setForeground(Color.magenta);
        button.setFont(new Font("Rockwell", Font.ITALIC, 20));
        add(button);
    }

    public int getNextStepState() {
        return nextStepState;
    }

    public void setNextStepState(int nextStepState) {
        this.nextStepState = nextStepState;
    }

    public void setRefreshClickCount(int refreshClickCount) {
        this.refreshClickCount = refreshClickCount;
    }

    public void setNextStepClickCount(int nextStepClickCount) {
        this.nextStepClickCount = nextStepClickCount;
    }

    public int getRefreshClickCount() {
        return refreshClickCount;
    }

    public int getNextStepClickCount() {
        return nextStepClickCount;
    }

    public JButton getNextStepButton() {
        return nextStepButton;
    }

    public JButton getConfirmSwapButton() {
        return confirmSwapButton;
    }

    public JButton getTool1Button() {
        return tool1Button;
    }

    public void setJFrame(JStart startFrame) {//todo：对应新加的set方法
        this.jStart = startFrame;
    }
}


