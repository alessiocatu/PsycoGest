package io.utacfreak.psycogest.ui.Panel;

import io.utacfreak.psycogest.ui.GraphicConst;
import io.utacfreak.psycogest.ui.Components.GenericComponent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddPazientePanel extends JPanel {

    public AddPazientePanel(){
        this.setBackground(GraphicConst.BACKGROUND);
        this.setLayout(new BorderLayout());

        JLabel h1 = GenericComponent.H1("Inserisci i dati di un nuovo paziente");
        h1.setBorder(new EmptyBorder(100, 200, 60, 200));
        this.add(h1, BorderLayout.PAGE_START);

        JPanel form = GenericComponent.Form();
        form.setBorder(new EmptyBorder(10, 200, 10, 200));
        this.add(form, BorderLayout.CENTER);
    }
}
