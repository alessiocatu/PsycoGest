package io.utacfreak.psycogest.back.AESoap;

import io.utacfreak.psycogest.back.Const;
import io.utacfreak.psycogest.back.Logger.Logger;
import io.utacfreak.psycogest.ui.ViewController;

import java.io.File;
import java.io.FileWriter;
import java.util.Observable;
import java.util.Scanner;

public class SoapClientAEProcessor extends Observable implements Runnable {
    private static SoapClientAEProcessor processor;

    private SoapClientAEProcessor(){};

    public static SoapClientAEProcessor getProcessor(){
        if(processor != null)
            return  processor;
        return processor = new SoapClientAEProcessor();
    }

    public static void setObserver(){
        processor.addObserver(ViewController.getNewsObserver());
    }

    @Override
    public void run() {
        Logger.Log(SoapClientAEProcessor.class, "RUN - START ");
        int fatt_n = 0;
        int fatt_ok = 0;
        int fatt_ko = 0;

        this.setChanged();
        File folder = new File(Const.getPath(Const.FATTURE_XML_PATH_TOSEND));
        File[] listOfFiles = folder.listFiles();
        try {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile() && !listOfFiles[i].getName().startsWith(".")) {
                    fatt_n++;
                    StringBuilder xml = new StringBuilder();

                    Scanner reader = new Scanner(new File(listOfFiles[i].getAbsolutePath()));
                    while (reader.hasNextLine()) {
                        xml.append(reader.nextLine());
                    }
                    reader.close();
                    String res = SoapClientAE.getSoapClientAE().DocumentoSpesa730pAsincrono(xml.toString());
                    if(SoapClientAEResponseParser.evaluteResponse(res)) {
                        saveResponse(res, listOfFiles[i].getName(), true);
                        fatt_ok++;
                    } else {
                        saveResponse(res, listOfFiles[i].getName(), false);
                        fatt_ko++;
                    }
                    listOfFiles[i].renameTo(new File(Const.getPath(Const.FATTURE_XML_PATH_SENT) + listOfFiles[i].getName()));
                } else if (listOfFiles[i].isDirectory()) {
                    Logger.Log(SoapClientAEProcessor.class, "Directory" + listOfFiles[i].getName());
                }
            }
        } catch (Exception e) {
            Logger.Log(SoapClientAEProcessor.class, "EXC: " + e.getMessage());
        }

        Logger.Log(SoapClientAEProcessor.class, "Fatture inviate "+fatt_n + " - OK: " + fatt_ok + " - ERR: " + fatt_ko);
        this.setChanged();
        this.notifyObservers("Fatture inviate "+fatt_n + " - OK: " + fatt_ok + " - ERR: " + fatt_ko);
        Logger.Log(SoapClientAEProcessor.class, "RUN - INTERRUPT ");
        Thread.currentThread().interrupt();
    }

    private static void saveResponse(String xml, String nomeFattura, boolean success){
        try {
            File file= new File (
                    Const.getPath(Const.FATTURE_XML_PATH_RESPONSE) +
                            (success ? "success_" : "error_") +
                            nomeFattura);
            file.createNewFile();
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(xml);
            writer.flush();
            writer.close();
            Logger.Log(SoapClientAE.class, "OK: " + nomeFattura);
        } catch(Exception e){
            Logger.Log(SoapClientAE.class, "EXC: " + e.getMessage());
        }
    }
}
