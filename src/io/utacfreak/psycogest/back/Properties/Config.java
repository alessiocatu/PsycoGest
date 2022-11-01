package io.utacfreak.psycogest.back.Properties;

import io.utacfreak.psycogest.back.Const;
import io.utacfreak.psycogest.back.Logger.Logger;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

public class Config {
    private static Config g;
    private static Properties p;

    private final String prop_name = Const.getPath(Const.CONF_PATH) + Const.CONF_NAME;

    private Config(){
        try {
            FileReader reader = new FileReader(prop_name);
            p = new Properties();
            p.load(reader);
        } catch (Exception e){
            p = new Properties();
            Logger.Log(Config.class, "Error reading conf -> " + e);
        }
    }

    public static Config getConfig(){
        if(g != null)
            return g;
        return g = new Config();
    }

    public Properties getProperties(){
        return p;
    }

    public void storeProperties(){
        try {
            p.store(new FileWriter(prop_name), "");
        } catch (Exception e) {
            Logger.Log(Config.class, "Error store conf -> " + e);
        }
    }
}