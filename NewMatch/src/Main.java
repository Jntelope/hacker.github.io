import view.StartFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartFrame mainFrame = new StartFrame();
            mainFrame.setVisible(true);
        });

            /*
            leveldialog.LevelDialog dlg = new leveldialog.LevelDialog();
            dlg.setModal(true);
            dlg.setTitle("please select match3 level:");
            dlg.show();
            int levelselect = dlg.getLevelSelect();*/
//            StartFrame mainFrame1 = new StartFrame();
      //
}
}
