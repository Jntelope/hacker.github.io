package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComboLIst {
    JFrame f;
    int x;
    ComboLIst(){
        f=new JFrame("请选择游戏等级");
        String level[]={"1","2","3","4","5"};
        JComboBox cb=new JComboBox(level);
        cb.setBounds(50, 50,90,20);
        f.add(cb);
        f.setLayout(null);
        f.setSize(400,500);
        f.setVisible(true);
        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                x = Integer.valueOf( String.valueOf(cb.getSelectedItem() ) );
                System.out.println(x);
            }
        });
        x =Integer.valueOf( String.valueOf(cb.getSelectedItem() ) );
        System.out.println(x);
    }
    public int getX(){
        return x;
    }

    public static void main(String[] args) {
        ComboLIst tt = new ComboLIst();
        System.out.println(tt.getX());
    }
}