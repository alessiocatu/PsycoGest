package io.utacfreak.psycogest.main;

import io.utacfreak.psycogest.back.Logger.Logger;
import io.utacfreak.psycogest.ui.ViewController;

import java.net.URISyntaxException;

public class Application {

    private static final String DATA = "12/04/2022";
    private static final String VERSION = "1.0.0.0.0";

    public static void main(String[] args) throws URISyntaxException {
        Logger.getLogger().Log(Application.class, "##############################");
        Logger.getLogger().Log(Application.class, "VERSION " + VERSION + " - " + DATA);
        Logger.getLogger().Log(Application.class, "##############################");
        ViewController.getViewController();
    }
}

