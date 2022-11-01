package io.utacfreak.psycogest.back.Excel;

import io.utacfreak.psycogest.back.Const;
import io.utacfreak.psycogest.back.Logger.Logger;
import io.utacfreak.psycogest.back.Bean.Fattura;
import io.utacfreak.psycogest.ui.ViewController;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Observable;

public class ExcelController extends Observable {

    private static ExcelController f;

    private ExcelController(){}

    public static void setObserver(){
        f.addObserver(ViewController.getNewsObserver());
    }

    public static ExcelController getExcelController(){
        if(f != null)
            return f;
        return f = new ExcelController();
    }

    public void registerFattura(String nomeFattura, Fattura fatt){
        f.setChanged();

        if(!fatt.getPsicologa().getKeepHistory()){
            Logger.Log(ExcelController.class, "Mantenimento storico fatture - Disattivato");
        }
        Logger.Log(ExcelController.class, "Mantenimento storico fatture - Attivato");

        checkingCsvFile();

        try{
            File file = new File(Const.getPath(Const.FATTURE_CSV_PATH + Const.FATTURE_CSV_NAME));
            FileWriter fw = new FileWriter(file, true);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            StringBuilder line = new StringBuilder();
            line.append(fatt.getPsicologa().getProgressivo() + ";");                //#Fattura
            line.append(fatt.getPaziente().getNome() + ";");                        //Nome
            line.append(fatt.getPaziente().getCognome() + ";");                     //Cognome
            line.append(fatt.getPaziente().getCodiceFiscale() + ";");               //CF
            line.append(fatt.getPaziente().getMail() + ";");                        //Mail
            line.append(Const.prettyEta(fatt.getPaziente().getEta()) + ";");        //Eta
            line.append(fatt.getPaziente().getAddress().toString() + ";");          //Citta
            line.append((fatt.getPaziente().isDSA() ? "SI" : "NO") + ";");          //DSA
            line.append((fatt.getPaziente().isNotSendCFtoAE() ? "NO":"SI") + ";");  //InviaCF
            line.append(fatt.getImporto() + ";");                                   //Importo
            line.append(fatt.getIva() + ";");                                       //Iva
            line.append(fatt.getImporto() + fatt.getIva() + ";");                   //Totale
            line.append(sdf.format(fatt.getDateEmissione()) + ";");                 //Data Emissione
            line.append(sdf.format(fatt.getDatePagamento()) + ";");                 //Data Pagamento
            line.append(nomeFattura);                                               //NomeFattura
            line.append("\n");

            fw.write(line.toString());
            fw.flush();
            fw.close();

            f.notifyObservers("OK: " + "Storico aggiornato");
        } catch (Exception e){
            Logger.Log(ExcelController.class, e.toString());
            f.notifyObservers("EXC: " + "Storico non aggiornato");
        }
    }

    private void registerHeaderLine(File file){
        try{
            FileWriter fw = new FileWriter(file);

            StringBuilder line = new StringBuilder();
            line.append("#Fattura;");
            line.append("Nome;");
            line.append("Cognome;");
            line.append("CF;");
            line.append("Mail;");
            line.append("Eta;");
            line.append("Citta;");
            line.append("DSA;");
            line.append("InviaCF;");
            line.append("Netto;");
            line.append("Iva;");
            line.append("Totale;");
            line.append("Data Emissione;");
            line.append("Data Pagamento;");
            line.append("NomeFattura");
            line.append("\n");

            fw.write(line.toString());
            fw.flush();
            fw.close();
        } catch (Exception e){
            Logger.Log(ExcelController.class, e.toString());
        }
    }

    private void checkingCsvFile(){
        File csv = new File(Const.getPath(Const.FATTURE_CSV_PATH + Const.FATTURE_CSV_NAME));

        if(!csv.exists()){
            Logger.Log(ExcelController.class, "CSV FILE - fatture.csv - doesn't exist.");
            try{
                csv.createNewFile();
                Logger.Log(ExcelController.class, "CSV FILE - fatture.csv - Created file");
                registerHeaderLine(csv);
                Logger.Log(ExcelController.class, "CSV FILE - fatture.csv - Added Header line");
            } catch (Exception e){
                Logger.Log(ExcelController.class, e.toString());
            }
        }
    }
}
