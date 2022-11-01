package io.utacfreak.psycogest.ui.Panel;

import io.utacfreak.psycogest.back.Controller;
import io.utacfreak.psycogest.back.Bean.Paziente;
import io.utacfreak.psycogest.ui.GraphicConst;
import io.utacfreak.psycogest.ui.ViewController;
import io.utacfreak.psycogest.ui.Components.GenericComponent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class DeletePanel extends JPanel implements Observer {
    private static DeletePanel d;
    private static Paziente curr;
    private static JLabel paziente = new JLabel();

    private DeletePanel() {
        this.setBackground(GraphicConst.BACKGROUND);
        this.setLayout(new GridLayout(3, 0));
        this.add(GenericComponent.H1("Vuoi eliminare il seguente paziente?"));
        this.add(paziente);

        Container c = new Container();
        c.setLayout(new GridLayout(0,6));
        c.add(new Container());
        c.add(new Container());

        //Panel botton 1
        JPanel btn1 = new JPanel();
        JButton yes = GenericComponent.Button("SI", 150, 50);
        btn1.setOpaque(false);
        btn1.setBorder(new EmptyBorder(30, 30, 30, 30));
        btn1.add(yes);

        //Panel botton 2
        JPanel btn2 = new JPanel();
        JButton no = GenericComponent.Button("NO", 150, 50);
        btn2.setOpaque(false);
        btn2.setBorder(new EmptyBorder(30, 30, 30, 30));
        btn2.add(no);

        //Aggiunta dei due bottoni
        c.add(btn1);
        c.add(btn2);

        c.add(new Container());
        c.add(new Container());
        this.add(c);

        yes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Controller.getController().removePaziente(curr);
                ViewController.getViewController().setCurrentPanel(new MainPanel());
            }
        });

        no.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewController.getViewController().setCurrentPanel(new MainPanel());
            }
        });
    }

    public static DeletePanel getDeletePanel(){
        if(d != null)
            return d;
        return d = new DeletePanel();
    }

    @Override
    public void update(Observable p, Object arg) {
        Paziente temp = (Paziente)p;
        curr = temp;
        StringBuilder s = new StringBuilder();
        s.append(temp.getNome());
        s.append(" ");
        s.append(temp.getCognome());
        paziente.setFont(new Font(Font.DIALOG,  Font.BOLD, 50));
        paziente.setHorizontalAlignment(JLabel.CENTER);
        paziente.setForeground(GraphicConst.ERROR_TEXT);
        paziente.setText(s.toString());
    }
}
