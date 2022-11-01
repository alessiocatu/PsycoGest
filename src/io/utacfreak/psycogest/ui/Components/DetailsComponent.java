package io.utacfreak.psycogest.ui.Components;

import io.utacfreak.psycogest.back.Const;
import io.utacfreak.psycogest.back.Bean.Paziente;
import io.utacfreak.psycogest.ui.GraphicConst;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class DetailsComponent extends JPanel implements Observer {
    private static JEditorPane pazienteDetails;
    private static DetailsComponent details;

    private JButton edit;
    private JButton remove;

    private DetailsComponent(){
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.setBackground(GraphicConst.BACKGROUND);
        this.add(GenericComponent.H1("Dettagli", 20), BorderLayout.PAGE_START);

        pazienteDetails = new JEditorPane("text/html", "");
        pazienteDetails.setBackground(GraphicConst.BACKGROUND_LIGHT);
        pazienteDetails.setForeground(GraphicConst.TEXT_DARK);
        pazienteDetails.setBorder(new EmptyBorder(30, 30, 30, 30));
        pazienteDetails.setEditable(false);
        this.add(pazienteDetails, BorderLayout.CENTER);
        Container c = new Container();
        c.setLayout(new GridLayout(0,2));
        edit = GenericComponent.EditButtonPazienti();
        edit.setEnabled(false);
        c.add(edit);
        remove = GenericComponent.RemoveButton();
        remove.setEnabled(false);
        c.add(remove);
        this.add(c, BorderLayout.PAGE_END);
    }
    public static DetailsComponent getDetailsComponent(){
        if(details != null)
            return details;
        return details = new DetailsComponent();
    }

    @Override
    public void update(Observable p, Object arg) {
        if((boolean)arg == true){
            pazienteDetails.setText("");
            edit.setEnabled(false);
            remove.setEnabled(false);
            return;
        }

        StringBuilder text = new StringBuilder();
        Paziente paziente = (Paziente)p;
        String hex = String.format("#%02X%02X%02X",
                GraphicConst.TEXT_DARK.getRed(),
                GraphicConst.TEXT_DARK.getGreen(),
                GraphicConst.TEXT_DARK.getBlue());
        text.append("<html><body style=\"color: " + hex + ";font-family: " + Font.DIALOG + ";\">");
        text.append("<h2 style=\"text-align: center;\">INFORMAZIONI PAZIENTE</h2>");
        text.append("<br>");
        text.append("<br><p><b>Nome: </b>" + paziente.getNome() + "</p>");
        text.append("<p><b>Cognome: </b>" + paziente.getCognome() + "</p>");
        text.append("<p><b>Cod.Fiscale: </b>" + paziente.getCodiceFiscale() + "</p>");
        text.append("<p><b>Telefono: </b>" + paziente.getTelefono() + "</p>");
        text.append("<p><b>Mail: </b>" + paziente.getMail() + "</p>");
        text.append("<p><b>Indirizzo: </b>" + paziente.getAddress().getIndirizzo() + "</p>");
        text.append("<p><b>Civico: </b>" + paziente.getAddress().getCivico() + "</p>");
        text.append("<p><b>CAP: </b>" + paziente.getAddress().getCap() + "</p>");
        text.append("<p><b>Città: </b>" + paziente.getAddress().getCitta() + "</p>");
        text.append("<p><b>Provincia: </b>" + paziente.getAddress().getProvincia() + "</p>");

        switch (paziente.getEta()){
            case Const.MINORE:
                text.append("<br><p>Il paziente è un minore</p>");
                break;
            case Const.GIOVANEADULTO:
                text.append("<br><p>Il paziente è un giovane adulto</p>");
                break;
            case Const.ADULTO:
                text.append("<br><p>Il paziente è un adulto</p>");
                break;
        }

        text.append(paziente.isDSA() ? "<p>Il paziente è un DSA</p>" : "<p>Il paziente NON è un DSA</p>");
        text.append(paziente.isNotSendCFtoAE() ? "<p>Il paziente NON invia il CF all'AE</p>" : "<p>Il paziente invia il CF all'AE</p>");
        text.append("</body></html>");
        pazienteDetails.setText(text.toString());

        edit.setEnabled(true);
        remove.setEnabled(true);
    }
}