package view;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class LevelDialog extends JDialog{
    private  int levelSelect;
    public LevelDialog (){
        super();
        setTitle("please select level:");
        setModal(true);
        Container cp = getContentPane();
        cp.setLayout(null);
        String level[]={"1","2","3","4","5"};
        JComboBox cb=new JComboBox(level);
        cb.setLocation(100, 100);
        cb.setSize(100, 60);
        cb.setToolTipText("please select level here:");
        //cb.setName("Level:");
        cb.setEnabled(true);
        cp.add(cb);

        levelSelect = 1;
        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //levelSelect = Integer.valueOf( String.valueOf(cb.getSelectedItem() ) );
                System.out.println(levelSelect);
            }
        });
        JButton  ok = new JButton("OK");
        ok.setSize(100,60);
        ok.setLocation(100,300);
        ok.setFont(new Font("Rockwell", Font.BOLD, 20));
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                levelSelect = Integer.valueOf( String.valueOf(cb.getSelectedItem() ) );
                dispose();
            }
        });
        cp.add(ok);
        setSize(600,450);
        cb.setVisible(true);

    }
    public int getLevelSelect()
    {
        return levelSelect;
    }
}
