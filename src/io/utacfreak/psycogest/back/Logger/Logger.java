package io.utacfreak.psycogest.back.Logger;

import io.utacfreak.psycogest.back.Const;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static Logger l;
    private static FileWriter writer;
    private static SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss:SSS");

    private Logger(){
        try {
            File file = new File (Const.getPath(Const.LOGGER_PATH));
            file.createNewFile();
            writer = new FileWriter(file, true);
        } catch (Exception e) {
            writer = null;
        }
    }

    public static Logger getLogger(){
        if(l != null)
            return l;
        return l = new Logger();
    }

    public static void Log(Class c, String s){
        try {
            if(writer == null){
                return;
            }
            Date d = new Date();
            writer.write(sdf.format(d) + "::" + c.toString() + " : " + s + "\n");
            writer.flush();
        } catch (IOException e){
            return;
        }
    }
}
