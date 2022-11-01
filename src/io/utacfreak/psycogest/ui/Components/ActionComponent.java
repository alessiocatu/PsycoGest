package io.utacfreak.psycogest.ui.Components;

import io.utacfreak.psycogest.back.Controller;
import io.utacfreak.psycogest.back.Bean.Paziente;
import io.utacfreak.psycogest.ui.GraphicConst;
import io.utacfreak.psycogest.ui.ViewController;
import jiconfont.icons.font_awesome.FontAwesome;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class ActionComponent extends JPanel implements Observer {
    private static ActionComponent ac;
    private static Paziente current;

    private JButton fattura;
    private JButton sendAE;

    private ActionComponent(){
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.setBackground(GraphicConst.BACKGROUND);
        Container c = new Container();
        c.setLayout(new GridLayout(5,0));
        c.add(new Container());
        c.add(NewsComponent.getNewsComponent());
        fattura = GenericComponent.IconButton(FontAwesome.FILE_TEXT,60, GraphicConst.TEXT_DARK);
        if(current == null)
            fattura.setEnabled(false);
        c.add(fattura);
        sendAE = GenericComponent.IconButton(FontAwesome.CLOUD_UPLOAD,60, GraphicConst.TEXT_DARK);
        if(current == null)
            sendAE.setEnabled(false);
        c.add(sendAE);
        c.add(new Container());
        this.add(c);

        fattura.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewController.getViewController().waitScreen(true);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        Controller.getController().generateAndSendFattura(current);
                        ViewController.getViewController().waitScreen(false);
                    }
                });
            }
        });

        sendAE.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewController.getViewController().waitScreen(true);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        Controller.getController().processFatture();
                        ViewController.getViewController().waitScreen(false);
                    }
                });
            }
        });
    }

    public static ActionComponent getActionComponent(){
        if(ac !=null)
            return ac;
        return ac = new ActionComponent();
    }

    @Override
    public void update(Observable o, Object arg) {
        if((boolean)arg == true){
            fattura.setEnabled(false);
            sendAE.setEnabled(false);
            return;
        }
        fattura.setEnabled(true);

        if(Controller.getController().getPsicologa().getSendAE()) {
            sendAE.setEnabled(true);
        }

        current = (Paziente) o;
    }
}
