package io.utacfreak.psycogest.ui;

import io.utacfreak.psycogest.back.Controller;
import io.utacfreak.psycogest.back.Bean.Paziente;
import io.utacfreak.psycogest.ui.Components.ActionComponent;
import io.utacfreak.psycogest.ui.Components.DetailsComponent;
import io.utacfreak.psycogest.ui.Components.MenuComponent;
import io.utacfreak.psycogest.ui.Components.NewsComponent;
import io.utacfreak.psycogest.ui.Panel.DeletePanel;
import io.utacfreak.psycogest.ui.Panel.EditPazientePanel;
import io.utacfreak.psycogest.ui.Panel.MainPanel;
import io.utacfreak.psycogest.ui.Panel.WelcomePanel;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import java.awt.*;
import java.util.Observer;

public class ViewController {
    private static ViewController c;
    private JPanel main, currentPanel;

    private ViewController(){
        main = new JPanel();
        main.setLayout(new BorderLayout());
        IconFontSwing.register(FontAwesome.getIconFont());
        if(Controller.getController().isValidPsicologa()){
            main.add(new MenuComponent(), BorderLayout.PAGE_START);
            currentPanel = new MainPanel();
        } else {
            currentPanel = WelcomePanel.getWelcomePanel();
        }
        main.add(currentPanel, BorderLayout.CENTER);
        MainView.getView().setView(main);
        MainView.getView().show();
    }

    public static ViewController getViewController(){
        if(c != null)
            return c;
        return c = new ViewController();
    }

    public static void addObserver(Paziente p) {
        p.addObserver(ActionComponent.getActionComponent());
        p.addObserver(DetailsComponent.getDetailsComponent());
        p.addObserver(DeletePanel.getDeletePanel());
        p.addObserver(EditPazientePanel.getEditPanel());
    }

    public static Observer getNewsObserver(){
        return NewsComponent.getNewsComponent();
    }

    public void setCurrentPanel(JPanel p){
        main.remove(currentPanel);
        currentPanel = p;
        main.add(currentPanel, BorderLayout.CENTER);
        MainView.getView().setView(this.main);
    }

    public void showMenu(){
        if(Controller.getController().isValidPsicologa())
            main.add(new MenuComponent(), BorderLayout.PAGE_START);
    }

    public void waitScreen(boolean needWaiting){
        MainView.getView().waitScreen(needWaiting);
    }
}
