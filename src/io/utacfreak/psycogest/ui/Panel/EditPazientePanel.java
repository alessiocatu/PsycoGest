package io.utacfreak.psycogest.ui.Panel;

import io.utacfreak.psycogest.back.Bean.Paziente;
import io.utacfreak.psycogest.ui.GraphicConst;
import io.utacfreak.psycogest.ui.Components.GenericComponent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class EditPazientePanel extends JPanel implements Observer {
    private static EditPazientePanel d;
    private static JPanel form;

    private EditPazientePanel() {
        this.setBackground(GraphicConst.BACKGROUND);
        this.setLayout(new BorderLayout());
        JLabel title = GenericComponent.H1("Modifica i dati del paziente");
        title.setBorder(new EmptyBorder(100, 200, 60, 200));
        this.add(title, BorderLayout.PAGE_START);
        form = new JPanel();
        this.add(form, BorderLayout.CENTER);
    }

    public static EditPazientePanel getEditPanel(){
        if(d != null)
            return d;
        return d = new EditPazientePanel();
    }

    @Override
    public void update(Observable p, Object arg) {
        this.remove(1);
        Paziente temp = (Paziente)p;
        form = GenericComponent.Form(temp);
        form.setBorder(new EmptyBorder(10, 200, 10, 200));
        this.add(form, BorderLayout.CENTER);
    }
}
