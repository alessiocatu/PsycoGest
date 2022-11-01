package io.utacfreak.psycogest.ui.Components;

import io.utacfreak.psycogest.back.Controller;
import io.utacfreak.psycogest.back.Logger.Logger;
import io.utacfreak.psycogest.back.Bean.Address;
import io.utacfreak.psycogest.back.Bean.Paziente;
import io.utacfreak.psycogest.back.Bean.Psicologa;
import io.utacfreak.psycogest.ui.GraphicConst;
import io.utacfreak.psycogest.ui.ViewController;
import io.utacfreak.psycogest.ui.Panel.*;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.security.Key;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class GenericComponent {
    private static final String[] ETA_CHOICE = {"Minore", "GiovaneAdulto", "Adulto"};

    public static JButton Button(String text, int width, int height) {
        JButton btn = new JButton(text);
        btn.setForeground(GraphicConst.TEXT_DARK);
        btn.setBackground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setFont(new Font(Font.DIALOG,  Font.BOLD, 15));
        btn.setPreferredSize(new Dimension(width,height));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(GraphicConst.BACKGROUND_LIGHT);
            }
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(Color.WHITE);
            }
        });
        return btn;
    }
    public static JButton IconButton(FontAwesome ic, int size, Color color) {
        JButton btn = new JButton();
        Icon icon = IconFontSwing.buildIcon(ic, size, color);
        btn.setIcon(icon);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                Color c = Color.BLACK;
                if(color == GraphicConst.BUTTON_LIGHT)
                    c = Color.WHITE;
                Icon icon = IconFontSwing.buildIcon(ic, size, c);
                btn.setIcon(icon);
            }
            public void mouseExited(MouseEvent evt) {
                Icon icon = IconFontSwing.buildIcon(ic, size, color);
                btn.setIcon(icon);
            }
        });
        return btn;
    }
    public static JButton getBackButton() {
        IconFontSwing.register(FontAwesome.getIconFont());
        JButton btn = GenericComponent.IconButton(FontAwesome.ARROW_CIRCLE_LEFT, 50, GraphicConst.TEXT_DARK);

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewController.getViewController().setCurrentPanel(new MainPanel());
            }
        });

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                Icon icon = IconFontSwing.buildIcon(FontAwesome.ARROW_CIRCLE_LEFT, 50, Color.BLACK);
                btn.setIcon(icon);
            }
            public void mouseExited(MouseEvent evt) {
                Icon icon = IconFontSwing.buildIcon(FontAwesome.ARROW_CIRCLE_LEFT, 50, GraphicConst.TEXT_DARK);
                btn.setIcon(icon);
            }
        });
        return btn;
    }
    public static JButton AddButton(){
        JButton btn = GenericComponent.IconButton(FontAwesome.PLUS_SQUARE, 30, GraphicConst.TEXT_DARK);
        btn.setContentAreaFilled(true);
        btn.setBackground(GraphicConst.BACKGROUND_LIGHT);
        btn.setBorder(new EmptyBorder(20, 20, 20, 20));

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewController.getViewController().setCurrentPanel(new AddPazientePanel());
            }
        } );
        return btn;
    }
    public static JButton EditButtonPazienti(){
        JButton btn = GenericComponent.IconButton(FontAwesome.PENCIL, 30, GraphicConst.TEXT_DARK);
        btn.setContentAreaFilled(true);
        btn.setBackground(GraphicConst.BACKGROUND_LIGHT);
        btn.setBorder(new EmptyBorder(20, 20, 20, 20));
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewController.getViewController().setCurrentPanel(EditPazientePanel.getEditPanel());
            }
        } );
        return btn;
    }
    public static JButton EditButtonPsicologa(){
        IconFontSwing.register(FontAwesome.getIconFont());
        JButton btn = GenericComponent.IconButton(FontAwesome.PENCIL, 50, GraphicConst.TEXT_DARK);

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewController.getViewController().setCurrentPanel(EditPsicologaPanel.getEditPanel());
            }
        } );
        return btn;
    }
    public static JButton RemoveButton(){
        JButton btn = GenericComponent.IconButton(FontAwesome.TRASH, 30, GraphicConst.TEXT_DARK);
        btn.setContentAreaFilled(true);
        btn.setBackground(GraphicConst.BACKGROUND_LIGHT);
        btn.setBorder(new EmptyBorder(20, 20, 20, 20));

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewController.getViewController().setCurrentPanel(DeletePanel.getDeletePanel());
            }
        } );
        return btn;
    }
    public static JButton UserButton(){
        JButton btn = GenericComponent.IconButton(FontAwesome.USER_CIRCLE_O, 30, GraphicConst.TEXT_DARK);

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewController.getViewController().setCurrentPanel(new PersonalInfoPanel());
            }
        } );

        return btn;
    }
    public static JButton StatistcButton(){
        JButton btn = GenericComponent.IconButton(FontAwesome.BAR_CHART , 30, GraphicConst.TEXT_DARK);

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewController.getViewController().setCurrentPanel(new StatisticPanel(0));
            }
        } );

        return btn;
    }

    public static JLabel H1(String text){
        JLabel lb = new JLabel(text, SwingConstants.CENTER);
        lb.setForeground(GraphicConst.TEXT_DARK);
        lb.setFont(new Font(Font.DIALOG,  Font.BOLD, 25));
        return lb;
    }
    public static JLabel H1( String text, int padding){
        JLabel j = H1(text);
        j.setBorder(new EmptyBorder(padding, padding, padding, padding));
        return j;
    }

    public static JLabel H2(String text, int align){
        JLabel lb = new JLabel(text, align);
        lb.setForeground(GraphicConst.TEXT_DARK);
        lb.setFont(new Font(Font.DIALOG,  Font.BOLD, 15));
        return lb;
    }

    public static Container TextField(String name, String value, boolean editable){
        Container c = new Container();
        c.setLayout(new GridLayout(0,2));
        c.add(H2(name, SwingConstants.RIGHT));
        JTextField lb = new JTextField(value);
        lb.setBackground(GraphicConst.BACKGROUND_LIGHT);
        lb.setEditable(editable);
        c.add(lb);
        return c;
    }
    public static Container TextField(String name, String value){
        return TextField(name, value, false);
    }
    public static Container TextFieldNumeric(String name, String value, int maxSize){
        Container c = new Container();
        c.setLayout(new GridLayout(0,2));
        c.add(H2(name, SwingConstants.RIGHT));
        JTextField lb = new JTextField(value);
        lb.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(maxSize != 0 && lb.getText().length() >= maxSize) {
                    e.setKeyChar(Character.MIN_VALUE);
                    return;
                }
                if(e.getKeyChar() >= '0' && e.getKeyChar() <= '9')
                    return;

                if(e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE |
                        e.getExtendedKeyCode() == KeyEvent.VK_CANCEL ||
                        e.getExtendedKeyCode() == KeyEvent.VK_DELETE ||
                        e.getExtendedKeyCode() == KeyEvent.VK_LEFT ||
                        e.getExtendedKeyCode() == KeyEvent.VK_RIGHT){
                    return;
                }
                e.setKeyChar(Character.MIN_VALUE);
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        lb.setBackground(GraphicConst.BACKGROUND_LIGHT);
        lb.setEditable(true);
        c.add(lb);
        return c;
    }
    public static Container TextFieldCodFiscale(String name, String value, boolean editable){
        Container c = new Container();
        c.setLayout(new GridLayout(0,2));
        c.add(H2(name, SwingConstants.RIGHT));
        MaskFormatter mask = null;
        try {
            mask = new MaskFormatter("UUUUUU##U##UHHHU");
            mask.setPlaceholder(value);
        } catch (ParseException e) {
            Logger.Log(GenericComponent.class, e.toString());
        }
        JFormattedTextField lb = new JFormattedTextField(mask);
        lb.setBackground(GraphicConst.BACKGROUND_LIGHT);
        lb.setEditable(editable);
        c.add(lb);
        return c;
    }
    public static Container TextFieldPassw(String name, String value){
        String masked = value.charAt(0) + "****" + value.charAt(value.length()-1);
        return TextField(name, masked, false);
    }
    public static String getTextFieldValue(Container textField){
        JTextField text = (JTextField)textField.getComponent(1);
        return text.getText();
    }

    public static Container CheckBox(String text){
        JPanel c = new JPanel();
        c.setLayout(new GridLayout(0,2));
        c.add(H2(text, SwingConstants.RIGHT));
        JCheckBox checkbox = new JCheckBox();
        c.add(checkbox);
        c.setOpaque(false);
        return c;
    }
    public static Container CheckBox(String text, Boolean isChecked){
        JPanel c = new JPanel();
        c.setLayout(new GridLayout(0,2));
        c.add(H2(text, SwingConstants.RIGHT));
        JCheckBox checkbox = new JCheckBox("",isChecked);
        c.add(checkbox);
        c.setOpaque(false);
        return c;
    }
    public static Container CheckBox(Icon icon, Boolean isChecked, ActionListener listener){
        JPanel c = new JPanel();
        c.setLayout(new GridLayout(0,2));
        JLabel label = new JLabel();
        label.setIcon(icon);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        c.add(label);
        JCheckBox checkbox = new JCheckBox("",isChecked);
        checkbox.addActionListener(listener);
        c.add(checkbox);
        c.setOpaque(false);
        return c;
    }
    public static boolean isCheckBox(Container checkbox){
        JCheckBox box = (JCheckBox)checkbox.getComponent(1);
        return box.isSelected();
    }

    public static Container ComboBox(String text, String[] choices){
        JPanel c = new JPanel();
        c.setLayout(new GridLayout(0,2));
        c.add(H2(text, SwingConstants.RIGHT));
        JComboBox<String> jComboBox = new JComboBox<>(choices);
        c.add(jComboBox);
        c.setOpaque(false);
        return c;
    }
    public static Container ComboBox(String text, String[] choices, int choose){
        JPanel c = new JPanel();
        c.setLayout(new GridLayout(0,2));
        c.add(H2(text, SwingConstants.RIGHT));
        JComboBox<String> jComboBox = new JComboBox<>(choices);
        jComboBox.setSelectedIndex(choose);
        c.add(jComboBox);
        c.setOpaque(false);
        return c;
    }
    public static Container ComboBoxWithListener(String text, String[] choices, int choose){
        Container c = new Container();
        c.setLayout(new GridLayout(0,2));

        c.add(H2(text, SwingConstants.RIGHT));
        JComboBox<String> jComboBox = new JComboBox<>(choices);

        if(choices.length != 0)
            jComboBox.setSelectedIndex(choose);
        c.add(jComboBox);

        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = jComboBox.getSelectedIndex();//get the selected item
                ViewController.getViewController().setCurrentPanel(new StatisticPanel(index));
            }
        });
        return c;
    }
    public static int isComboBox(Container comboBox){
        JComboBox box = (JComboBox)comboBox.getComponent(1);
        return box.getSelectedIndex();
    }

    public static JPanel Form(){
        JPanel c = new JPanel();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        c.setOpaque(false);
        Container text = new Container();
        text.setLayout(new GridLayout(6,2));
        //1 ROW
        Container nameField = TextField("Nome* ","", true);
        text.add(nameField);
        Container cognField = TextField("Cognome* ","", true);
        text.add(cognField);
        //2 ROW
        Container codFiscField = TextFieldCodFiscale("Codice Fiscale* ","", true);
        text.add(codFiscField);

        Container telefono = TextFieldNumeric("Telefono* ", "", 10);
        text.add(telefono);
        //3 ROW
        Container indirizzo = TextField("Indirizzo* ","", true);
        text.add(indirizzo);
        Container civico = TextField("Civico* ", "", true);
        text.add(civico);
        //4 ROW
        Container citta = TextField("Città* ", "", true);
        text.add(citta);
        Container cap = TextFieldNumeric("Cap* ","", 5);
        text.add(cap);
        //5 ROW
        Container provincia = TextField("Provincia ","", true);
        text.add(provincia);
        text.add(new Container());
        c.add(text);
        //6 ROW
        Container mail = TextField("Mail* ","", true);
        text.add(mail);
        text.add(new Container());
        c.add(text);

        Container checkbox = new Container();
        checkbox.setLayout(new BoxLayout(checkbox, 2));
        Container eta = ComboBox("Età? ", ETA_CHOICE);
        checkbox.add(eta);
        Container isDSA = CheckBox("E' un DSA? ");
        checkbox.add(isDSA);
        Container isSendCFtoAE = CheckBox("NON Invia Cod.Fiscale? ");
        checkbox.add(isSendCFtoAE);
        c.add(checkbox);

        Container btns = new Container();
        btns.setLayout(new GridLayout(0,4));
        btns.add(new Container());

        //Panel botton 1
        JPanel btn1 = new JPanel();
        JButton yes = GenericComponent.Button("Conferma", 150, 50);
        btn1.setOpaque(false);
        btn1.setBackground(Color.YELLOW);
        btn1.setBorder(new EmptyBorder(30, 30, 30, 30));
        btn1.add(yes);

        //Panel botton 2
        JPanel btn2 = new JPanel();
        JButton no = GenericComponent.Button("Annulla", 150, 50);
        btn2.setOpaque(false);
        btn2.setBackground(Color.RED);
        btn2.setBorder(new EmptyBorder(30, 30, 30, 30));
        btn2.add(no);

        //Aggiunta dei due bottoni
        btns.add(btn1);
        btns.add(btn2);

        btns.add(new Container());
        c.add(btns);

        List<Container> textFields = new ArrayList<>();
        textFields.add(nameField);
        textFields.add(cognField);
        textFields.add(codFiscField);
        textFields.add(mail);
        textFields.add(telefono);
        textFields.add(indirizzo);
        textFields.add(citta);
        textFields.add(mail);
        if(fieldsIsEmpty(textFields))
            yes.setEnabled(false);
        setButtonEnablingListener(yes, textFields);

        yes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Paziente p = new Paziente();
                //getting value form the textFields
                p.setId(Controller.getController().getCountPazienti());
                p.setNome(getTextFieldValue(nameField));
                p.setCognome(getTextFieldValue(cognField));
                p.setCodiceFiscale(getTextFieldValue(codFiscField));
                p.setTelefono(getTextFieldValue(telefono));
                p.setMail(getTextFieldValue(mail));
                Address add = new Address();
                add.setIndirizzo(getTextFieldValue(indirizzo));
                add.setCivico(getTextFieldValue(civico));
                add.setCitta(getTextFieldValue(citta));
                add.setCap(getTextFieldValue(cap));
                add.setProvincia(getTextFieldValue(provincia));
                p.setAddress(add);
                p.setEta(isComboBox(eta));
                p.setIsDSA(isCheckBox(isDSA));
                p.setisNotSendCFtoAE(isCheckBox(isSendCFtoAE));
                Controller.getController().addPaziente(p);
                ViewController.getViewController().setCurrentPanel(new MainPanel());
            }
        });

        no.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewController.getViewController().setCurrentPanel(new MainPanel());
            }
        });


        return c;
    }
    public static JPanel Form(Paziente p){
        JPanel c = new JPanel();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        c.setOpaque(false);
        Container text = new Container();
        text.setLayout(new GridLayout(6,2));
        //1 ROW
        Container nameField = TextField( "Nome ",p.getNome(), true);
        text.add(nameField);
        Container cognField = TextField("Cognome ",p.getCognome(), true);
        text.add(cognField);
        //2 ROW
        Container codFiscField = TextFieldCodFiscale("Codice Fiscale ",p.getCodiceFiscale(), true);
        text.add(codFiscField);
        Container telefono = TextFieldNumeric("Telefono ", p.getTelefono(), 10);
        text.add(telefono);
        //3 ROW
        Container indirizzo = TextField("Indirizzo ",p.getAddress().getIndirizzo(), true);
        text.add(indirizzo);
        Container civico = TextField("Civico ", p.getAddress().getCivico(), true);
        text.add(civico);
        //4 ROW
        Container citta = TextField("Città ", p.getAddress().getCitta(), true);
        text.add(citta);
        Container cap = TextFieldNumeric("Cap ",p.getAddress().getCap(), 5);
        text.add(cap);
        //5 ROW
        Container provincia = TextField("Provincia ",p.getAddress().getProvincia(), true);
        text.add(provincia);
        text.add(new Container());
        c.add(text);
        //6 ROW
        Container mail = TextField("Mail ",p.getMail(), true);
        text.add(mail);
        text.add(new Container());
        c.add(text);

        Container checkbox = new Container();
        checkbox.setLayout(new BoxLayout(checkbox, 2));
        Container eta = ComboBox("Età? ", ETA_CHOICE, p.getEta());
        checkbox.add(eta);
        Container isDSA = CheckBox("E' un DSA? ", p.isDSA());
        checkbox.add(isDSA);
        Container isSendCFtoAE = CheckBox("NON Invia Cod.Fiscale? ", p.isNotSendCFtoAE());
        checkbox.add(isSendCFtoAE);
        c.add(checkbox);

        Container btns = new Container();
        btns.setLayout(new GridLayout(0,4));
        btns.add(new Container());

        //Panel botton 1
        JPanel btn1 = new JPanel();
        JButton yes = GenericComponent.Button("Conferma", 150, 50);
        btn1.setOpaque(false);
        btn1.setBackground(Color.YELLOW);
        btn1.setBorder(new EmptyBorder(30, 30, 30, 30));
        btn1.add(yes);

        //Panel botton 2
        JPanel btn2 = new JPanel();
        JButton no = GenericComponent.Button("Annulla", 150, 50);
        btn2.setOpaque(false);
        btn2.setBackground(Color.RED);
        btn2.setBorder(new EmptyBorder(30, 30, 30, 30));
        btn2.add(no);

        //Aggiunta dei due bottoni
        btns.add(btn1);
        btns.add(btn2);

        btns.add(new Container());
        c.add(btns);

        List<Container> textFields = new ArrayList<>();
        textFields.add(nameField);
        textFields.add(cognField);
        textFields.add(codFiscField);
        textFields.add(mail);
        textFields.add(telefono);
        textFields.add(indirizzo);
        textFields.add(citta);
        textFields.add(mail);
        if(fieldsIsEmpty(textFields))
            yes.setEnabled(false);
        setButtonEnablingListener(yes, textFields);

        yes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Paziente newp = new Paziente();
                //getting value form the textFields
                newp.setId(p.getId());
                newp.setNome(getTextFieldValue(nameField));
                newp.setCognome(getTextFieldValue(cognField));
                newp.setCodiceFiscale(getTextFieldValue(codFiscField));
                newp.setTelefono(getTextFieldValue(telefono));
                newp.setMail(getTextFieldValue(mail));
                Address add = new Address();
                add.setIndirizzo(getTextFieldValue(indirizzo));
                add.setCivico(getTextFieldValue(civico));
                add.setCitta(getTextFieldValue(citta));
                add.setCap(getTextFieldValue(cap));
                add.setProvincia(getTextFieldValue(provincia));
                newp.setAddress(add);
                newp.setEta(isComboBox(eta));
                newp.setIsDSA(isCheckBox(isDSA));
                newp.setisNotSendCFtoAE(isCheckBox(isSendCFtoAE));
                Controller.getController().editPaziente(newp);
                ViewController.getViewController().setCurrentPanel(new MainPanel());
            }
        });

        no.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewController.getViewController().setCurrentPanel(new MainPanel());
            }
        });

        return c;
    }
    public static JPanel Form(Psicologa p, boolean isWelcome){
        JPanel c = new JPanel();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        c.setOpaque(false);
        Container form = new Container();
        form.setLayout(new GridLayout(8,0));

        Container nameField = TextField( "Nome ",p.getNome(), true);
        form.add(nameField);
        Container cognField = TextField("Cognome ",p.getCognome(), true);
        form.add(cognField);
        Container codFiscField = TextFieldCodFiscale("Codice Fiscale ",p.getCodiceFiscale(), true);
        form.add(codFiscField);
        Container mail = TextField("Mail ",p.getMail(), true);
        form.add(mail);
        Container mailPassw = TextField("Mail Password ",p.getMailPassword(), true);
        form.add(mailPassw);
        Container partitaIva = TextFieldNumeric("Partita iva ", p.getPartitaIva(), 10);
        form.add(partitaIva);
        Container pincode = TextField("Pincode ",p.getPincode(), true);
        form.add(pincode);
        Container progressivo = TextField("Progressivo ", String.valueOf(p.getProgressivo()), true);
        form.add(progressivo);

        c.add(form);
        Container btns = new Container();
        if(!isWelcome)
            btns.setLayout(new GridLayout(0,4));
        else
            btns.setLayout(new GridLayout(0,3));
        btns.add(new Container());

        //Panel botton 1
        JPanel btn1 = new JPanel();
        JButton yes = GenericComponent.Button("Conferma", 150, 50);
        btn1.setOpaque(false);
        btn1.setBackground(Color.YELLOW);
        btn1.setBorder(new EmptyBorder(30, 30, 30, 30));
        btn1.add(yes);
        btns.add(btn1);

        //Panel botton 2
        JPanel btn2 = new JPanel();
        JButton no = GenericComponent.Button("Annulla", 150, 50);
        btn2.setOpaque(false);
        btn2.setBackground(Color.RED);
        btn2.setBorder(new EmptyBorder(30, 30, 30, 30));
        btn2.add(no);
        if(!isWelcome)
            btns.add(btn2);

        btns.add(new Container());
        c.add(btns);

        List<Container> textFields = new ArrayList<>();
        textFields.add(nameField);
        textFields.add(cognField);
        textFields.add(codFiscField);
        textFields.add(mail);
        textFields.add(mailPassw);
        textFields.add(partitaIva);
        textFields.add(progressivo);
        textFields.add(pincode);
        if(fieldsIsEmpty(textFields))
            yes.setEnabled(false);
        setButtonEnablingListener(yes, textFields);

        yes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Psicologa newp = new Psicologa();
                //getting value form the textFields
                newp.setNome(getTextFieldValue(nameField));
                newp.setCognome(getTextFieldValue(cognField));
                newp.setCodiceFiscale(getTextFieldValue(codFiscField));
                newp.setMail(getTextFieldValue(mail));
                newp.setMailPassword(getTextFieldValue(mailPassw));
                newp.setPartitaIva(getTextFieldValue(partitaIva));
                newp.setProgressivo(Integer.valueOf(getTextFieldValue(progressivo).trim()));
                newp.setPincode(getTextFieldValue(pincode));
                newp.setKeepHistory(Controller.getController().getPsicologa().getKeepHistory());
                newp.setSendAE(Controller.getController().getPsicologa().getSendAE());
                newp.setSendMail(Controller.getController().getPsicologa().getSendMail());
                newp.setMailPassword(getTextFieldValue(mailPassw));
                newp.setPincode(getTextFieldValue(pincode));
                Controller.getController().editPsicologa(newp);
                if(isWelcome) {
                    ViewController.getViewController().setCurrentPanel(new MainPanel());
                    ViewController.getViewController().showMenu();
                } else {
                    ViewController.getViewController().setCurrentPanel(new PersonalInfoPanel());
                }
            }
        });

        no.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewController.getViewController().setCurrentPanel(new PersonalInfoPanel());
            }
        });

        return c;
    }

    private static void setButtonEnablingListener(JButton confirm, List<Container> textFields) {
        DocumentListener dl = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(fieldsIsEmpty(textFields)){
                    confirm.setEnabled(false);
                } else
                    confirm.setEnabled(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (fieldsIsEmpty(textFields)) {
                    confirm.setEnabled(false);
                } else
                    confirm.setEnabled(true);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (fieldsIsEmpty(textFields)) {
                    confirm.setEnabled(false);
                } else
                    confirm.setEnabled(true);
            }
        };

        for(Container tf: textFields){
            JTextField text = (JTextField)tf.getComponent(1);
            text.getDocument().addDocumentListener(dl);
        }
    }
    private static boolean fieldsIsEmpty(List<Container> textFields) {
        for(Container tf: textFields){
            if(getTextFieldValue(tf).trim().isEmpty())
                return true;
        }
        return false;
    }
}
