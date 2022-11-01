package io.utacfreak.psycogest.ui.Components;

import io.utacfreak.psycogest.back.Const;
import io.utacfreak.psycogest.ui.GraphicConst;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import org.jdesktop.swingx.border.DropShadowBorder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MenuComponent extends JPanel {
   private JToolBar toolbar;

    public MenuComponent(){
       this.toolbar = new JToolBar();
       this.toolbar.setFloatable(false);
       this.toolbar.setPreferredSize(GraphicConst.TOOLBAR_SIZE);
       this.toolbar.setLayout(new GridLayout(0,6));

       this.setBackground(GraphicConst.BACKGROUND);
       this.setLayout(new BorderLayout());
       addLogo();
       addButton();
       toolbar.setBackground(GraphicConst.BACKGROUND_LIGHT);
       this.add(toolbar, BorderLayout.CENTER);

       DropShadowBorder shadow = new DropShadowBorder(
               Color.BLACK,
               8,
               0.7f,
               1,
               false,
               false,
               true,
               false);
       this.setBorder(shadow);

    }
    private void addLogo(){
        JLabel logo = new JLabel("PSYCOGEST");
        logo.setIcon(new ImageIcon(new ImageIcon(Const.getPath(Const.IMAGES_LOGO)).getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        logo.setBorder(new EmptyBorder(30, 30, 30, 30));
        logo.setForeground(GraphicConst.TEXT_DARK);
        logo.setFont(new Font(Font.DIALOG,  Font.BOLD, 15));
        toolbar.add(logo);
    }

    private void addButton(){
        IconFontSwing.register(FontAwesome.getIconFont());
        toolbar.add(new Container());
        toolbar.add(new Container());
        toolbar.add(new Container());
        toolbar.add(GenericComponent.StatistcButton());
        toolbar.add(GenericComponent.UserButton());
    }
}
