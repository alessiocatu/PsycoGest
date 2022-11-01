package io.utacfreak.psycogest.back.Statistic;

import io.utacfreak.psycogest.back.Const;
import io.utacfreak.psycogest.back.Controller;
import io.utacfreak.psycogest.back.DataBase.DataBaseController;
import io.utacfreak.psycogest.back.Logger.Logger;
import io.utacfreak.psycogest.back.Bean.Address;
import io.utacfreak.psycogest.back.Bean.Fattura;
import io.utacfreak.psycogest.back.Bean.Paziente;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticController {
    private static StatisticController f;

    private List<Fattura> fatture = new ArrayList<>();

    private StatisticController(){}

    public static StatisticController getStatisticController(){
        if(f != null)
            return f;
        return f = new StatisticController();
    }

    public void elaborateFattureCSV(){
        try{
            Logger.Log(StatisticController.class, "START - elaborateFattureCSV");
            fatture.clear();
            try {
                Scanner reader = new Scanner(new File(Const.getPath(Const.FATTURE_CSV_PATH + Const.FATTURE_CSV_NAME)));
                reader.nextLine(); //SKIP HEADER
                while (reader.hasNextLine()) {
                    Fattura f = parseFattura(reader.nextLine());
                    fatture.add(f);
                }
                reader.close();
            } catch (Exception e) {
                Logger.Log(DataBaseController.class, "ERR - Fatture non caricate -> " + e.toString());
                Logger.Log(DataBaseController.class, "ERR - Fatture non caricate -> " + e.getMessage());
            }

            Logger.Log(DataBaseController.class, "END - elaborateFattureCSV");
        } catch (Exception e){
            Logger.Log(StatisticController.class, e.toString());
        }
    }

    public Map<String,Long> getEtaDataSet(){
        Map<String, Long> res = new HashMap<>();
        List<Paziente> pazienti = Controller.getController().getPazienti();

        if(pazienti.isEmpty()){
            res.put("Nessuna informazione", 1l);
            return res;
        }

        long count = pazienti.stream().filter(p -> p.getEta() == Const.MINORE).count();
        if (count > 0)
            res.put(Const.prettyEta(Const.MINORE), count);

        count = pazienti.stream().filter(p -> p.getEta() == Const.GIOVANEADULTO).count();
        if (count > 0)
            res.put(Const.prettyEta(Const.GIOVANEADULTO), count);

        count = pazienti.stream().filter(p -> p.getEta() == Const.ADULTO).count();
        if (count > 0)
            res.put(Const.prettyEta(Const.ADULTO), count);

        return res;
    }

    public Map<String,Long> getDSADataSet(){
        Map<String, Long> res = new HashMap<>();
        List<Paziente> pazienti = Controller.getController().getPazienti();

        if(pazienti.isEmpty()){
            res.put("Nessuna informazione", 1l);
            return res;
        }

        long count = pazienti.stream().filter(p -> p.isDSA()).count();
        if (count > 0)
            res.put("DSA", count);

        count = pazienti.stream().filter(p -> !p.isDSA()).count();
        if (count > 0)
            res.put("NON DSA", count);

        return res;
    }

    public Map<String,Long> getCFDataSet(){
        Map<String, Long> res = new HashMap<>();
        List<Paziente> pazienti = Controller.getController().getPazienti();

        if(pazienti.isEmpty()){
            res.put("Nessuna informazione", 1l);
            return res;
        }

        long count = pazienti.stream().filter(p -> !p.isNotSendCFtoAE()).count();
        if (count > 0)
            res.put("Invia CF", count);

        count = pazienti.stream().filter(p -> p.isNotSendCFtoAE()).count();
        if (count > 0)
            res.put("Non invia CF", count);

        return res;
    }

    public Map<String,Long> getCityDataSet(){
        Map<String, Long> res = new HashMap<String, Long>();
        List<Paziente> pazienti = Controller.getController().getPazienti();

        if(pazienti.isEmpty()){
            res.put("Nessuna informazione", 0l);
            return res;
        }

        List<String> citta = pazienti.stream().map(p -> p.getAddress().getCitta()).collect(Collectors.toList());;
        List<String> cittaDist = citta.stream().distinct().collect(Collectors.toList());

        for(String c: cittaDist){
            res.put(c, citta.stream().filter(elem -> elem.equals(c)).count());
        }

        return res;
    }

    public List<String> getFattureMenuList(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM - yyyy");
        List<String> menu = fatture.stream().map(f -> sdf.format(f.getDateEmissione())).distinct().collect(Collectors.toList());

        if(menu.size() == 0){
            menu.add(sdf.format(new Date()));
        }

        Collections.reverse(menu);
        return menu;
    }

    public String getNumeroFatture(String choose){
        String[] date = choose.split(" - ");
        return Long.toString(fatture.stream().filter(f -> intoRange(f.getDateEmissione(), date[0], date[1])).count());
    }

    public double getTotaleFatture(String choose){
        String[] date = choose.split(" - ");
        double tot = 0;
        List<Fattura> filtered = fatture.stream().filter(f -> intoRange(f.getDateEmissione(), date[0], date[1])).collect(Collectors.toList());
        for(Fattura f: filtered){
            tot += Double.valueOf(f.getImporto());
        }
        BigDecimal bd = new BigDecimal(tot).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public double getIvaFatture(String choose){
        String[] date = choose.split(" - ");
        double tot = 0;
        List<Fattura> filtered = fatture.stream().filter(f -> intoRange(f.getDateEmissione(), date[0], date[1])).collect(Collectors.toList());
        for(Fattura f: filtered){
            tot += Double.valueOf(f.getIva());
        }
        BigDecimal bd = new BigDecimal(tot).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public String getNumeroPazienti(){
        return String.valueOf(Controller.getController().getPazienti().size());
    }

    private boolean intoRange(Date d, String month, String year) {
        boolean res = false;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate = new Date();
            if(month.isEmpty()){
                startDate = sdf.parse("01/01/" + year);
            } else
                startDate = sdf.parse("01/" + month + "/" + year);
            Calendar startDateCal = Calendar.getInstance();
            startDateCal.setTime(startDate);

            Calendar dcal = Calendar.getInstance();
            dcal.setTime(d);

            if (dcal.get(Calendar.MONTH) == startDateCal.get(Calendar.MONTH) &&
                    dcal.get(Calendar.YEAR) == startDateCal.get(Calendar.YEAR)) {
               res = true;
            }

            if(month.isEmpty() && dcal.get(Calendar.YEAR) == startDateCal.get(Calendar.YEAR)){
                res = true;
            }

        } catch (Exception e){
            Logger.Log(StatisticController.class, e.toString());
        }

        return res;
    }

    private static Fattura parseFattura(String fattura){
        Fattura f = new Fattura();

        try {
            String[] split = fattura.split(";");
            Paziente p = new Paziente();
            p.setNome(split[1]);
            p.setCognome(split[2]);
            p.setCodiceFiscale(split[3]);
            p.setMail(split[4]);
            p.setEta(Const.fromPrettyEta(split[5]));

            //INDIRIZZO
            String[] indirizzo = split[6].split(",");
            Address a = new Address();
            a.setIndirizzo(indirizzo[0]);
            a.setCivico(indirizzo[1]);
            a.setCitta(indirizzo[2]);
            a.setProvincia(indirizzo[3]);
            a.setCap(indirizzo[4]);
            p.setAddress(a);

            p.setIsDSA(split[7].equals("SI"));
            p.setisNotSendCFtoAE(split[8].equals("NO"));
            f.setPaziente(p);

            f.setImporto(split[9]);
            f.setIva(split[10]);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            f.setDateEmissione(sdf.parse(split[12]));
            f.setDatePagamento(sdf.parse(split[13]));
        } catch(Exception e){
            Logger.Log(DataBaseController.class, "ERR - Parsing errato -> " + e.toString());
            Logger.Log(DataBaseController.class, "ERR - Parsing errato -> " + e.getMessage());
            f = new Fattura();
        }
        return f;
    }
}
