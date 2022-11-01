package io.utacfreak.psycogest.ui.Panel;

import io.utacfreak.psycogest.ui.GraphicConst;
import io.utacfreak.psycogest.ui.Components.ActionComponent;
import io.utacfreak.psycogest.ui.Components.DetailsComponent;
import io.utacfreak.psycogest.ui.Components.PazientiComponent;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    public MainPanel(){
        this.setLayout(new GridLayout(0,3));
        this.setBackground(GraphicConst.BACKGROUND);
        this.add(new PazientiComponent());
        this.add(DetailsComponent.getDetailsComponent());
        this.add(ActionComponent.getActionComponent());
    }
}
