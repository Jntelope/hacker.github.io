package view;
import javax.swing.*;

public enum Theme {
    sunSet(new ImageIcon("./res/img_2.png")),wave(new ImageIcon("./res/img_3.png"));
    private final ImageIcon backgroundIcon;
    Theme(ImageIcon backgroundIcon) {
        this.backgroundIcon=backgroundIcon;
    }
    public ImageIcon getBackgroundIcon(){
        return backgroundIcon;
    }
}
/*
 public static ImageIcon backgroundIcon = Theme.sunSet.getBackgroundIcon();
    public static Image backgroundImage = backgroundIcon.getImage();
    JPanel backgroundPanel = new JPanel() {

        private static final long serialVersionUID = 1L;

        public void paint(Graphics g) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    };


    sunSet(new ImageIcon("D:/proj/res/sunSet.png")),wave(new ImageIcon("D:/proj/res/wave.png"));
    private final ImageIcon backgroundIcon;
    Theme(ImageIcon backgroundIcon) {
        this.backgroundIcon=backgroundIcon;
    }
    public ImageIcon getBackgroundIcon(){
        return backgroundIcon;
    }
 */