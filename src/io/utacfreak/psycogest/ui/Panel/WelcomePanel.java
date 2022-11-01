package io.utacfreak.psycogest.ui.Panel;

import io.utacfreak.psycogest.back.Bean.Psicologa;
import io.utacfreak.psycogest.ui.GraphicConst;
import io.utacfreak.psycogest.ui.Components.GenericComponent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class WelcomePanel extends JPanel {
    private static WelcomePanel d;

    private WelcomePanel() {
        this.setBackground(GraphicConst.BACKGROUND);
        this.setLayout(new BorderLayout());

        JPanel titleBox = new JPanel();
        titleBox.setBackground(GraphicConst.BACKGROUND);
        titleBox.setOpaque(true);
        titleBox.setLayout(new GridLayout(2,0));

        JLabel title = GenericComponent.H1("Benvenuto in Psycogest\n(dei poveri)");
        titleBox.add(title);

        JLabel instruction = GenericComponent.H2("Inscerisci le tue informazioni nel form qui sotto", SwingConstants.CENTER);
        titleBox.add(instruction);
        titleBox.setBorder(new EmptyBorder(100, 200, 60, 200));
        this.add(titleBox, BorderLayout.PAGE_START);

        JPanel form = GenericComponent.Form(new Psicologa(), true);
        form.setBorder(new EmptyBorder(10, 200, 5, 200));
        this.add(form, BorderLayout.CENTER);
    }

    public static WelcomePanel getWelcomePanel(){
        if(d != null)
            return d;
        return d = new WelcomePanel();
    }
}
