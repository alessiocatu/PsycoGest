package io.utacfreak.psycogest.back.DataBase;

import io.utacfreak.psycogest.back.Const;
import io.utacfreak.psycogest.back.Controller;
import io.utacfreak.psycogest.back.Logger.Logger;
import io.utacfreak.psycogest.back.Bean.Address;
import io.utacfreak.psycogest.back.Bean.Paziente;
import io.utacfreak.psycogest.ui.ViewController;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.*;

public class DataBaseController extends Observable {
    private static DataBaseController db;
    private static String url = "jdbc:sqlite:"+ Const.getPath(Const.DB_SQL_PATH);
    private static Connection conn;

    private final static String prop_name = Const.getPath(Const.CONF_PATH) + Const.DB_PATH;
    private final static String prop_name_temp = Const.getPath(Const.CONF_PATH) + "temp.txt";

    private DataBaseController(){
        connect();
    }

    public static void setObserver(){
        db.addObserver(ViewController.getNewsObserver());
    }

    public static DataBaseController getDataBase(){
        if(db != null)
            return db;
        return db = new DataBaseController();
    }

    private static void connect() {
        try {
            conn = DriverManager.getConnection(url);

            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
            }
        } catch (SQLException e) {
            Logger.Log(DataBaseController.class, e.getMessage());
        }
    }

    public static List<Paziente> loadDB() {
        return loadDB(true);
    }
    public static List<Paziente> loadDB(Boolean notify) {
        Logger.Log(DataBaseController.class, "START - loadDB - notify: " + notify);
        List<Paziente> lst = new ArrayList();

        if(conn == null){
            connect();
            Logger.Log(DataBaseController.class, "ERR - DB non caricato perchè connessione è null");
            if(notify)
                db.notifyObservers("ERRORE - DB non caricato");
            return lst;
        }

        db.setChanged();
        /*
        try {
            Scanner reader = new Scanner(new File(prop_name));
            while (reader.hasNextLine()) {
                Paziente p = parsePaziente(reader.nextLine());
                lst.add(p);
            }
            reader.close();
            if(notify) {
                Logger.Log(DataBaseController.class, "OK - DB caricato correttamente");
                db.notifyObservers("OK - DB caricato correttamente");
            }
        } catch (Exception e) {
            Logger.Log(DataBaseController.class, "ERR - DB non caricato -> " + e.toString());
            Logger.Log(DataBaseController.class, "ERR - DB non caricato -> " + e.getMessage());
            if(notify)
                db.notifyObservers("ERRORE - DB non caricato");
        }
         */

        String sql = "SELECT * FROM Pazienti";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                Paziente p = parsePaziente(rs);
                lst.add(p);
            }

            if(notify) {
                Logger.Log(DataBaseController.class, "OK - DB caricato correttamente");
                db.notifyObservers("OK - DB caricato correttamente");
            }
        } catch (SQLException e) {
            Logger.Log(DataBaseController.class, "ERR - DB non caricato -> " + e.toString());
            Logger.Log(DataBaseController.class, "ERR - DB non caricato -> " + e.getMessage());
            if(notify)
                db.notifyObservers("ERRORE - DB non caricato");
        }

        //Collections.sort(lst);

        Logger.Log(DataBaseController.class, "END - loadDB - notify: " + notify);
        return lst;
    }

    private static Paziente parsePaziente(String paziente){
        Paziente p = new Paziente();
        try {
            String[] split = paziente.split(";");
            p.setId(Integer.parseInt(split[0]));
            p.setNome(split[1]);
            p.setCognome(split[2]);
            p.setCodiceFiscale(split[3]);
            p.setTelefono(split[4]);
            p.setIsVisibile(split[5].equals("1"));
            p.setEta(Integer.parseInt(split[6]));
            p.setIsDSA(split[7].equals("1"));
            p.setisNotSendCFtoAE(split[8].equals("1"));
            Address a = new Address();
            a.setIndirizzo(split[9]);
            a.setCivico(split[10]);
            a.setCap(split[11]);
            a.setCitta(split[12]);
            a.setProvincia(split[13]);
            p.setAddress(a);
            p.setMail(split[14]);
        } catch(ArrayIndexOutOfBoundsException e){
            Logger.Log(DataBaseController.class, "ERR - Parsing errato -> " + e.toString());
            Logger.Log(DataBaseController.class, "ERR - Parsing errato -> " + e.getMessage());
            p = new Paziente();
        }
        return p;
    }

    private static Paziente parsePaziente(ResultSet rs){
        Paziente p = new Paziente();
        try {
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("Nome"));
            p.setCognome(rs.getString("Cognome"));
            p.setCodiceFiscale(rs.getString("CodFisc"));
            p.setTelefono(rs.getString("Telefono"));
            p.setIsVisibile(rs.getInt("isVisibile") == 1);
            p.setEta(rs.getInt("Eta"));
            p.setIsDSA(rs.getInt("DSA") == 1);
            p.setisNotSendCFtoAE(rs.getInt("NoSendCF") == 1);
            p.setMail(rs.getString("Mail"));

            String sql = "SELECT * FROM Indirizzi WHERE id=" + rs.getInt("Indirizzo");
            Statement stmt = conn.createStatement();
            ResultSet addr = stmt.executeQuery(sql);

            Address a = new Address();
            a.setIndirizzo(addr.getString("Via"));
            a.setCivico(addr.getString("Civico"));
            a.setCap(addr.getString("Cap"));
            a.setCitta(addr.getString("Citta"));
            a.setProvincia(addr.getString("Provincia"));
            p.setAddress(a);
            
        } catch(SQLException e){
            Logger.Log(DataBaseController.class, "ERR - Parsing errato -> " + e.toString());
            Logger.Log(DataBaseController.class, "ERR - Parsing errato -> " + e.getMessage());
            p = new Paziente();
        }
        return p;
    }

    private static String toCSV(Paziente p){
        if(!p.isPazienteValid()){
            throw new IllegalArgumentException("Paziente non inserito correttamente");
        }

        StringBuilder s =  new StringBuilder();
        s.append(p.getId()+";");
        s.append(p.getNome()+";");
        s.append(p.getCognome()+";");
        s.append(p.getCodiceFiscale()+";");
        s.append(p.getTelefono()+";");
        s.append((p.isVisible() ? '1':'0')+";");
        s.append(p.getEta()+ ";");
        s.append((p.isDSA() ? '1':'0')+";");
        s.append((p.isNotSendCFtoAE() ? '1':'0')+";");
        s.append(p.getAddress().getIndirizzo()+";");
        s.append(p.getAddress().getCivico()+";");
        s.append(p.getAddress().getCap()+";");
        s.append(p.getAddress().getCitta()+";");
        s.append(p.getAddress().getProvincia()+";");
        s.append(p.getMail() + "\n");
        return s.toString();
    }

    public static void editPaziente(Paziente p){
        Logger.Log(DataBaseController.class, "START - editPaziente");
        Logger.Log(DataBaseController.class, p.toString());
        db.setChanged();
        try {
            File f = new File(prop_name_temp);
            FileWriter writer = new FileWriter(f);
            boolean res = false;
            for (Paziente temp : Controller.getController().getPazienti()) {
                if(temp.getId() < p.getId()){
                    writer.append(toCSV(temp));
                    continue;
                }
                if(temp.getId() > p.getId()) {
                    writer.append(toCSV(temp));
                    continue;
                }
                if(temp.getId() == p.getId()){
                    res = true;
                    writer.append(toCSV(p));
                    continue;
                }
            }
            Logger.Log(DataBaseController.class,"Trovato il paziente da modificare? " + res);
            writer.close();
            Path from = Paths.get(prop_name_temp);
            Path to = Paths.get(prop_name);
            Files.move(from, to, StandardCopyOption.REPLACE_EXISTING);

            db.notifyObservers("OK - Modifica");
            Controller.getController().loadPazienti(false);
            Logger.Log(DataBaseController.class, "OK - Modifica");
        } catch(Exception e){
            Logger.Log(DataBaseController.class, "ERR - Modifica failed -> " + e.toString());
            Logger.Log(DataBaseController.class, "ERR - Modifica failed -> " + e.getMessage());
            db.notifyObservers("ERRORE - Modifica non effettuata");
        }

        Logger.Log(DataBaseController.class, "END - editPaziente");
    }

    public static void insertPaziente(Paziente p){
        Logger.Log(DataBaseController.class, "START - insertPaziente -> " + p.toString());
        db.setChanged();

        if(conn == null){
            connect();
            Logger.Log(DataBaseController.class, "ERR - Insert failed for connection");
            db.notifyObservers("ERRORE - Inserimento non effettuato");
            return;
        }

        try {
            String sql = "INSERT INTO Indirizzi(Citta, Provincia, Via, Civico, Cap) " +
                    "VALUES (?,?,?,?,?);";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, p.getAddress().getCitta());
            pstmt.setString(2, p.getAddress().getProvincia());
            pstmt.setString(3, p.getAddress().getIndirizzo());
            pstmt.setString(4, p.getAddress().getCivico());
            pstmt.setString(5, p.getAddress().getCap());
            pstmt.executeUpdate();

            sql = "SELECT last_insert_rowid();";
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            int id_indirizzo = rs.getInt("last_insert_rowid()");

            sql = "INSERT INTO Pazienti(Nome, Cognome, CodFisc, Telefono, IsVisibile, Eta, DSA, NoSendCF, Indirizzo, Mail) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, p.getNome());
            pstmt.setString(2, p.getCognome());
            pstmt.setString(3, p.getCodiceFiscale());
            pstmt.setString(4, p.getTelefono());
            pstmt.setInt(5, p.isVisible() ? 1:0);
            pstmt.setInt(6, p.getEta());
            pstmt.setInt(7, p.isDSA() ? 1:0);
            pstmt.setInt(8, p.isNotSendCFtoAE() ? 1:0);
            pstmt.setInt(9, id_indirizzo);
            pstmt.setString(10, p.getMail());
            pstmt.executeUpdate();

            db.notifyObservers("OK - Inserimento");
            Controller.getController().loadPazienti(false);
            Logger.Log(DataBaseController.class, "OK - Inserimento");

        } catch (SQLException e) {
            Logger.Log(DataBaseController.class, "ERR - Insert failed -> " + e.toString());
            Logger.Log(DataBaseController.class, "ERR - Insert failed -> " + e.getMessage());
            db.notifyObservers("ERRORE - Inserimento non effettuato");
        }

        Logger.Log(DataBaseController.class, "END - insertPaziente");
    }

    public static void removePaziente(Paziente p){
        Logger.Log(DataBaseController.class, "START - removePaziente -> " + p.toString());
        db.setChanged();

        if(conn == null){
            connect();
            Logger.Log(DataBaseController.class, "ERR - Rimozione failed for connection");
            db.notifyObservers("ERRORE - Rimozione non effettuato");
            return;
        }

        try {
            String sql = "DELETE FROM Pazienti WHERE id=" + p.getId();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

            db.notifyObservers("OK - Rimozione");
            Controller.getController().loadPazienti(false);
            Logger.Log(DataBaseController.class, "OK - Rimozione");
        } catch (SQLException e) {
            Logger.Log(DataBaseController.class, "ERR - Rimozione failed -> " + e.toString());
            Logger.Log(DataBaseController.class, "ERR - Rimozione failed -> " + e.getMessage());
            db.notifyObservers("ERRORE - Rimozione");
        }

        Logger.Log(DataBaseController.class, "END - removePaziente");
    }
}
