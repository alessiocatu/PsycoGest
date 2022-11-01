package io.utacfreak.psycogest.back;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.HeaderFooter;
import com.spire.doc.Section;
import com.spire.doc.Table;
import com.spire.doc.TableCell;
import com.spire.doc.TableRow;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.documents.TextSelection;
import com.spire.doc.fields.DocPicture;
import com.spire.doc.fields.TextRange;
import io.utacfreak.psycogest.back.Bean.Fattura;
import io.utacfreak.psycogest.back.Properties.Config;
import io.utacfreak.psycogest.ui.ViewController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Observable;

public class FattureGenerator extends Observable {
    private static FattureGenerator f;

    private FattureGenerator(){}

    public static void setObserver(){
        f.addObserver(ViewController.getNewsObserver());
    }

    public static FattureGenerator getFattureGenerator(){
        if(f != null)
            return f;
        return f = new FattureGenerator();
    }

    public synchronized void generateFattura(String nameFattura, Fattura fatt){
        f.setChanged();
        //Load the template document
        Document document = new Document(Const.getPath(Const.TEMPLATE_PATH));
        //Get the first section
        Section section = document.getSections().get(0);
        //Get the first table in the section
        Table table = section.getTables().get(0);

        //Create a map of values for the template
        Map<String, String> map = new HashMap<String, String>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("it"));
        map.put("data","Milano, " + dtf.format(now));
        map.put("articolo", fatt.getPaziente().getEta() > 0 ? "Spett.le" : "Ai genitori del minore");
        map.put("nome", fatt.getPaziente().getNome() + " " + fatt.getPaziente().getCognome());
        map.put("codfisc", fatt.getPaziente().getCodiceFiscale());
        map.put("indirizzo", fatt.getPaziente().getAddress().getIndirizzo());
        map.put("civico", fatt.getPaziente().getAddress().getCivico());
        map.put("cap", fatt.getPaziente().getAddress().getCap());
        map.put("citta", fatt.getPaziente().getAddress().getCitta());
        if(fatt.getPaziente().getAddress().getProvincia().length() > 0)
            map.put("provincia", "("+fatt.getPaziente().getAddress().getProvincia() + ")");
        else
            map.put("provincia", "");
        map.put("progressivo", Config.getConfig().getProperties().getProperty(Const.PSYCO_PROGRESSIVO));
        map.put("type", fatt.getPaziente().isDSA() ? Const.DSASTRING : Const.NODSASTRING);
        map.put("importo", fatt.getImporto());
        map.put("iva", fatt.getIva());
        map.put("totale", String.valueOf(Float.valueOf(fatt.getImporto()) + Float.valueOf(fatt.getIva())));
        dtf = DateTimeFormatter.ofPattern("yyyy");
        map.put("anno", dtf.format(now));
        map.put("noCF", fatt.getPaziente().isNotSendCFtoAE() ? Const.NOCFSTRING : "");
        replaceTextinTable(map, table);
        replaceTextinDocumentBody(map, document);

        //Save the result document
        document.saveToFile(Const.getPath(Const.FATTURE_PATH) + nameFattura, FileFormat.Docx_2013);
        f.notifyObservers("OK: " + "Fattura generata");
    }

    //Replace text in table
    static void replaceTextinTable(Map<String, String> map, Table table){
        for(TableRow row:(Iterable<TableRow>)table.getRows()){
            for(TableCell cell : (Iterable<TableCell>)row.getCells()){
                for(Paragraph para : (Iterable<Paragraph>)cell.getParagraphs()){
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        para.replace("${" + entry.getKey() + "}", entry.getValue(), false, true);
                    }
                }
            }
        }
    }

    //Replace text with image
    static  void replaceTextWithImage(Document document, String stringToReplace, String imagePath){
        TextSelection[] selections = document.findAllString("${" + stringToReplace + "}", false, true);
        int index = 0;
        TextRange range = null;
        for (Object obj : selections) {
            TextSelection textSelection = (TextSelection)obj;
            DocPicture pic = new DocPicture(document);
            pic.loadImage(imagePath);
            range = textSelection.getAsOneRange();
            index = range.getOwnerParagraph().getChildObjects().indexOf(range);
            range.getOwnerParagraph().getChildObjects().insert(index,pic);
            range.getOwnerParagraph().getChildObjects().remove(range);
        }
    }

    //Replace text in document body
    static void replaceTextinDocumentBody(Map<String, String> map, Document document){
        for(Section section : (Iterable<Section>)document.getSections()) {
            for (Paragraph para : (Iterable<Paragraph>) section.getParagraphs()) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    para.replace("${" + entry.getKey() + "}", entry.getValue(), false, true);
                }
            }
        }
    }

    //Replace text in header or footer
    static  void replaceTextinHeaderorFooter(Map<String, String> map, HeaderFooter headerFooter){
        for(Paragraph para : (Iterable<Paragraph>)headerFooter.getParagraphs()){
            for (Map.Entry<String, String> entry : map.entrySet()) {
                para.replace("${" + entry.getKey() + "}", entry.getValue(), false, true);
            }
        }
    }
}