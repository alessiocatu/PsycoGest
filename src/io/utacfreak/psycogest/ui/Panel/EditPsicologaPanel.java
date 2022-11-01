package io.utacfreak.psycogest.ui.Panel;

import io.utacfreak.psycogest.back.Controller;
import io.utacfreak.psycogest.ui.GraphicConst;
import io.utacfreak.psycogest.ui.Components.GenericComponent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EditPsicologaPanel extends JPanel {
    private static EditPsicologaPanel d;

    private EditPsicologaPanel() {
        this.setBackground(GraphicConst.BACKGROUND);
        this.setLayout(new BorderLayout());
        JLabel title = GenericComponent.H1("Modifica i tuoi dati");
        title.setBorder(new EmptyBorder(100, 200, 60, 200));
        this.add(title, BorderLayout.PAGE_START);

        JPanel form = GenericComponent.Form(Controller.getController().getPsicologa(), false);
        form.setBorder(new EmptyBorder(10, 200, 10, 200));
        this.add(form, BorderLayout.CENTER);
    }

    public static EditPsicologaPanel getEditPanel(){
        if(d != null)
            return d;
        return d = new EditPsicologaPanel();
    }
}
