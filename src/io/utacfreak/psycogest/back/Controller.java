package io.utacfreak.psycogest.back;

import io.utacfreak.psycogest.back.AESoap.SoapClientAE;
import io.utacfreak.psycogest.back.AESoap.SoapClientAEProcessor;
import io.utacfreak.psycogest.back.DataBase.DataBaseController;
import io.utacfreak.psycogest.back.Excel.ExcelController;
import io.utacfreak.psycogest.back.Logger.Logger;
import io.utacfreak.psycogest.back.Mail.MailController;
import io.utacfreak.psycogest.back.Bean.Fattura;
import io.utacfreak.psycogest.back.Bean.Paziente;
import io.utacfreak.psycogest.back.Bean.Psicologa;
import io.utacfreak.psycogest.back.Properties.Config;
import io.utacfreak.psycogest.ui.MainView;
import io.utacfreak.psycogest.ui.ViewController;

import javax.swing.*;
import java.util.*;

public class Controller {
    private static Controller c;

    private static Psicologa psicologa;
    private static List<Paziente> pazienti;

    private Controller(){
        loadPsicologa();
        DataBaseController.getDataBase();
        DataBaseController.setObserver();
        FattureGenerator.getFattureGenerator();
        FattureGenerator.setObserver();
        ExcelController.getExcelController();
        ExcelController.setObserver();
        SoapClientAE.getSoapClientAE();
        SoapClientAE.setObserver();
        SoapClientAEProcessor.getProcessor();
        SoapClientAEProcessor.setObserver();
        MailController.getMailController();
        MailController.setObserver();
        loadPazienti();
    }

    public static Controller getController(){
        if(c != null)
            return c;
        return c = new Controller();
    }

    public Psicologa getPsicologa() {
        return psicologa;
    }

    public boolean isValidPsicologa() {
        boolean res = true;
        if(psicologa.getNome().isEmpty()) res = false;
        if(psicologa.getCognome().isEmpty()) res = false;
        if(psicologa.getCodiceFiscale().isEmpty()) res = false;
        if(psicologa.getPartitaIva().isEmpty()) res = false;
        if(psicologa.getMail().isEmpty()) res = false;
        if(psicologa.getMailPassword().isEmpty()) res = false;
        if(psicologa.getPincode().isEmpty()) res = false;
        return res;
    }

    public int getCountPazienti() {
        return pazienti.size();
    }

    public List<Paziente> getPazienti() {
        return pazienti;
    }
    public Object[] getPazientiAsArray() {
        List<String> lst = new ArrayList<>();
        for (Paziente p : pazienti) {
            lst.add(p.getNome() + " " + p.getCognome());
        }
        return lst.toArray();
    }

    private void loadPsicologa(){
        try {
            Logger.getLogger().Log(Controller.class, "START - loadPsicologa");
            Psicologa p = new Psicologa();
            p.setNome(Config.getConfig().getProperties().getProperty(Const.PSYCO_NOME));
            p.setCognome(Config.getConfig().getProperties().getProperty(Const.PSYCO_COGN));
            p.setCodiceFiscale(Config.getConfig().getProperties().getProperty(Const.PSYCO_CODFISC));
            p.setPartitaIva(Config.getConfig().getProperties().getProperty(Const.PSYCO_PARTIVA));
            p.setPincode(Config.getConfig().getProperties().getProperty(Const.PSYCO_PINCODE));
            p.setProgressivo(Integer.parseInt(Config.getConfig().getProperties().getProperty(Const.PSYCO_PROGRESSIVO)));
            p.setMail(Config.getConfig().getProperties().getProperty(Const.PSYCO_MAIL));
            p.setMailPassword(Config.getConfig().getProperties().getProperty(Const.PSYCO_MAIL_PASSWORD));
            p.setSendMail(Boolean.valueOf(Config.getConfig().getProperties().getProperty(Const.PSYCO_FUNC_SENDMAIL)));
            p.setSendAE(Boolean.valueOf(Config.getConfig().getProperties().getProperty(Const.PSYCO_FUNC_SENDAE)));
            p.setKeepHistory(Boolean.valueOf(Config.getConfig().getProperties().getProperty(Const.PSYCO_FUNC_KEEPHYS)));
            psicologa = p;
            Logger.getLogger().Log(Controller.class, p.toString());
            Logger.getLogger().Log(Controller.class, "END - loadPsicologa");
        } catch (Exception e){
            psicologa = new Psicologa();
        }
    }

    public void loadPazienti(Boolean notify){
        Logger.getLogger().Log(Controller.class, "START - loadPazienti - notify");
        pazienti = DataBaseController.getDataBase().loadDB(notify);
        for (Paziente p : pazienti) {
            //Logger.getLogger().Log(Controller.class, p.toString());
            ViewController.addObserver(p);
        }
        Logger.getLogger().Log(Controller.class, "END - loadPazienti - nofity");
    }

    public void loadPazienti(){
        Logger.getLogger().Log(Controller.class, "START - loadPazienti");
        pazienti = DataBaseController.getDataBase().loadDB();
        for (Paziente p : pazienti) {
            Logger.getLogger().Log(Controller.class, p.toString());
            ViewController.addObserver(p);
        }
        Logger.getLogger().Log(Controller.class, "END - loadPazienti");
    }

    public void editPsicologa(Psicologa p){
        Config.getConfig().getProperties().setProperty(Const.PSYCO_NOME, p.getNome());
        Config.getConfig().getProperties().setProperty(Const.PSYCO_COGN, p.getCognome());
        Config.getConfig().getProperties().setProperty(Const.PSYCO_CODFISC, p.getCodiceFiscale());
        Config.getConfig().getProperties().setProperty(Const.PSYCO_PARTIVA, p.getPartitaIva());
        Config.getConfig().getProperties().setProperty(Const.PSYCO_PINCODE, p.getPincode());
        Config.getConfig().getProperties().setProperty(Const.PSYCO_PROGRESSIVO, String.valueOf(p.getProgressivo()));
        Config.getConfig().getProperties().setProperty(Const.PSYCO_MAIL, p.getMail());
        Config.getConfig().getProperties().setProperty(Const.PSYCO_MAIL_PASSWORD, String.valueOf(p.getMailPassword()));
        Config.getConfig().getProperties().setProperty(Const.PSYCO_FUNC_SENDMAIL, String.valueOf(p.getSendMail()));
        Config.getConfig().getProperties().setProperty(Const.PSYCO_FUNC_SENDAE, String.valueOf(p.getSendAE()));
        Config.getConfig().getProperties().setProperty(Const.PSYCO_FUNC_KEEPHYS, String.valueOf(p.getKeepHistory()));
        Config.getConfig().storeProperties();
        psicologa = p;
    }

    private void incrementaProgressivo(){
        psicologa.setProgressivo(psicologa.getProgressivo()+1);
        editPsicologa(psicologa);
    }

    public void addPaziente(Paziente p){
        DataBaseController.getDataBase().insertPaziente(p);
    }

    public void editPaziente(Paziente p){
        DataBaseController.getDataBase().editPaziente(p);
    }

    public void removePaziente(Paziente p){
        DataBaseController.getDataBase().removePaziente(p);
    }

    public void generateAndSendFattura(Paziente p){
        Fattura fatt = new Fattura();
        fatt.setPaziente(p);
        fatt.setPsicologa(Controller.getController().getPsicologa());
        fatt.setDateEmissione(new Date());
        fatt.setDatePagamento(new Date());
        fatt.setImporto(String.format(Locale.US, "%.2f", calculateImporto(p)));
        fatt.setIva(String.format(Locale.US, "%.2f", calculateIva(p)));
        String nameFattura = genNameFattura(p);
        FattureGenerator.getFattureGenerator().generateFattura(nameFattura + ".docx", fatt);
        ExcelController.getExcelController().registerFattura(nameFattura + ".docx", fatt);
        SoapClientAE.getSoapClientAE().saveXMLFattura(nameFattura + ".xml", fatt);
        MailController.getMailController().SendMail(fatt, nameFattura);
        incrementaProgressivo();
    }

    public void processFatture(){
        SoapClientAEProcessor.getProcessor().run();
    }

    private String genNameFattura(Paziente p){
        StringBuilder s = new StringBuilder();
        s.append("Fattura_");
        s.append(Config.getConfig().getProperties().getProperty(Const.PSYCO_PROGRESSIVO));
        s.append("_");
        s.append(p.getNome());
        s.append("-");
        s.append(p.getCognome());
        return s.toString();
    }

    private float calculateValoreFattura(Paziente p){
        float importo = Const.IMPORTO_ADULTI;
        if(p.isDSA())
            importo = Const.IMPORTO_DSA;
        else if(p.getEta() == Const.MINORE)
            importo = Const.IMPORTO_MINORE;
        else if(p.getEta() == Const.GIOVANEADULTO)
            importo = Const.IMPORTO_GIOVANEADULTO;
        return importo;
    }

    private float calculateImporto(Paziente p) {
        return (100*calculateValoreFattura(p))/(100+Const.IVA);
    }
    private float calculateIva(Paziente p){
        return calculateValoreFattura(p)-calculateImporto(p);
    }
}
