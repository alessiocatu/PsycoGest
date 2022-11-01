package io.utacfreak.psycogest.ui.Components;

import io.utacfreak.psycogest.back.Controller;
import io.utacfreak.psycogest.back.Bean.Paziente;
import io.utacfreak.psycogest.ui.GraphicConst;
import io.utacfreak.psycogest.ui.ViewController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class PazientiComponent extends JPanel {
    public PazientiComponent(){
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.setBackground(GraphicConst.BACKGROUND);
        this.add(GenericComponent.H1("Pazienti", 20), BorderLayout.PAGE_START);

        JList pazienti = new JList(Controller.getController().getPazientiAsArray());
        pazienti.setBackground(GraphicConst.BACKGROUND_LIGHT);
        pazienti.setForeground(GraphicConst.TEXT_DARK);
        pazienti.setSelectionBackground(GraphicConst.BACKGROUND_DARK);
        pazienti.setSelectionForeground(Color.WHITE);
        pazienti.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pazienti.setBorder(new EmptyBorder(20, 20, 20, 20));
        pazienti.setFont(new Font(Font.DIALOG,  Font.BOLD, 15));

        JScrollPane scrollPane = new JScrollPane(pazienti);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());

        if(Controller.getController().getPazientiAsArray().length > 0) {
            pazienti.setSelectedIndex(0);
            Paziente p = Controller.getController().getPazienti().get(0);
            p.clearChanged();
            p.notifyObservers(false);
            this.add(scrollPane, BorderLayout.CENTER);
        }

        if(Controller.getController().getPazientiAsArray().length == 0){
            this.add(zeroPazientiPanel(), BorderLayout.CENTER);
            Paziente p = new Paziente();
            ViewController.addObserver(p);
            p.clearChanged();
            p.notifyObservers(true);
        }

        this.add(GenericComponent.AddButton(), BorderLayout.PAGE_END);

        pazienti.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Paziente p = Controller.getController().getPazienti().get(pazienti.getSelectedIndex());
                p.clearChanged();
                p.notifyObservers(false);
            }
        });
    }

    private Container zeroPazientiPanel(){
        JEditorPane zeroPaz = new JEditorPane("text/html", "");
        zeroPaz.setBackground(GraphicConst.BACKGROUND_LIGHT);
        zeroPaz.setForeground(GraphicConst.TEXT_DARK);
        zeroPaz.setEditable(false);
        zeroPaz.setBorder(new EmptyBorder(20, 20, 20, 20));
        zeroPaz.setFont(new Font(Font.DIALOG,  Font.BOLD, 15));

        StringBuilder text = new StringBuilder();
        String hex = String.format("#%02X%02X%02X",
                GraphicConst.TEXT_DARK.getRed(),
                GraphicConst.TEXT_DARK.getGreen(),
                GraphicConst.TEXT_DARK.getBlue());
        text.append("<html><body style=\"color: " + hex + ";font-family: " + Font.DIALOG + ";\">");
        text.append("<br><br><h2 style=\"text-align: center;\">INSERISCI IL TUO PRIMO CLIENTE</h2>");
        text.append("<br>");
        text.append("<br><p style=\"text-align: center;\"><b>Usa il bottone \"+\" sottostante per aggiungere un nuovo paziente</b></p>");
        text.append("</body></html>");
        zeroPaz.setText(text.toString());

        return zeroPaz;
    }
}
