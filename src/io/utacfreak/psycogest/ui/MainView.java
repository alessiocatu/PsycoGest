package io.utacfreak.psycogest.ui;

import io.utacfreak.psycogest.back.Const;

import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.*;

public class MainView {
    private static MainView v;
    private static JFrame mainFrame;
    private static ImageIcon loading;

    private MainView(){
        mainFrame = new JFrame();
        mainFrame.setVisible(false);
        mainFrame.setSize(GraphicConst.HEIGHT,GraphicConst.WIDTH);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        loading = new ImageIcon(Const.getPath(Const.IMAGES_LOADING));
        JComponent gPane = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(255,255,255,100));
                g.fillRect(0,0, getWidth(), getHeight());
            }
        };
        gPane.setLayout(new BorderLayout());

        gPane.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {}
        });

        gPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        gPane.add(new JLabel("", loading, JLabel.CENTER));
        mainFrame.setGlassPane(gPane);
    }

    public MainView getMainView(){
        if(v != null)
            return v;
        return v = new MainView();
    }

    public static MainView getView(){
        if(v != null)
            return v;
        return v = new MainView();
    }

    public void show(){
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public void setView(JComponent p){
        mainFrame.setContentPane(p);
        mainFrame.invalidate();
        mainFrame.validate();
    }

    public void waitScreen(boolean needWaiting){
        mainFrame.getGlassPane().setVisible(needWaiting);
        mainFrame.repaint();
    }
}
