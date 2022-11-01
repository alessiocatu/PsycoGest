package io.utacfreak.psycogest.ui.Panel;

import io.utacfreak.psycogest.back.Controller;
import io.utacfreak.psycogest.back.Bean.Psicologa;
import io.utacfreak.psycogest.ui.GraphicConst;
import io.utacfreak.psycogest.ui.Components.GenericComponent;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonalInfoPanel extends JPanel {
    public PersonalInfoPanel(){
        Psicologa p = Controller.getController().getPsicologa();
        this.setBackground(GraphicConst.BACKGROUND);
        this.setLayout(new GridLayout(3,0));
        this.add(GenericComponent.H1("I tuoi dati"));

        Container infoRow = new Container();
        infoRow.setLayout(new GridLayout(0,3));
        //COL 1
        infoRow.add(new Container());

        //COL 2
        Container info = new Container();
        info.setLayout(new GridLayout(8, 0));
        info.add(GenericComponent.TextField("Nome  ", p.getNome()));
        info.add(GenericComponent.TextField("Cognome  ", p.getCognome()));
        info.add(GenericComponent.TextField("Codice Fiscale  ", p.getCodiceFiscale()));
        info.add(GenericComponent.TextField("Mail  ", String.valueOf(p.getMail())));
        info.add(GenericComponent.TextFieldPassw("Mail Password ", String.valueOf(p.getMailPassword())));
        info.add(GenericComponent.TextField("Partita iva  ", p.getPartitaIva()));
        info.add(GenericComponent.TextField("Pincode ", String.valueOf(p.getPincode())));
        info.add(GenericComponent.TextField("Progressivo  ", String.valueOf(p.getProgressivo())));

        infoRow.add(info);

        //COL 3
        Container funcBtns = new Container();
        funcBtns.setLayout(new GridLayout(3, 0));

        funcBtns.add(GenericComponent.CheckBox(
                IconFontSwing.buildIcon(FontAwesome.ENVELOPE, 40, GraphicConst.TEXT_DARK),
                p.getSendMail(),
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JCheckBox checkbox = (JCheckBox) e.getSource();
                        Psicologa p = Controller.getController().getPsicologa();
                        if(checkbox.isSelected()){
                            p.setSendMail(true);
                        } else {
                            p.setSendMail(false);
                        }
                        Controller.getController().editPsicologa(p);
                    }
                }));

        funcBtns.add(GenericComponent.CheckBox(
                IconFontSwing.buildIcon(FontAwesome.CLOUD_UPLOAD,40, GraphicConst.TEXT_DARK),
                p.getSendAE(),
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JCheckBox checkbox = (JCheckBox) e.getSource();
                        Psicologa p = Controller.getController().getPsicologa();
                        if(checkbox.isSelected()){
                            p.setSendAE(true);
                        } else {
                            p.setSendAE(false);
                        }
                        Controller.getController().editPsicologa(p);
                    }
                }));

        funcBtns.add(GenericComponent.CheckBox(
                IconFontSwing.buildIcon(FontAwesome.DATABASE,40, GraphicConst.TEXT_DARK),
                p.getKeepHistory(),
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JCheckBox checkbox = (JCheckBox) e.getSource();
                        Psicologa p = Controller.getController().getPsicologa();
                        if(checkbox.isSelected()){
                            p.setKeepHistory(true);
                        } else {
                            p.setKeepHistory(false);
                        }
                        Controller.getController().editPsicologa(p);
                    }
                }));
        infoRow.add(funcBtns);

        this.add(infoRow);

        Container btns = new Container();
        btns.setLayout(new GridLayout(0, 4));
        btns.add(new Container());
        btns.add(GenericComponent.getBackButton());
        btns.add(GenericComponent.EditButtonPsicologa());
        btns.add(new Container());
        this.add(btns);
    }
}
