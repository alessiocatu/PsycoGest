package io.utacfreak.psycogest.back.Bean;

public class Address {
    private String indirizzo = "";
    private String civico = "";
    private String cap = "";
    private String citta = "";
    private String provincia = "";

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public boolean isValid(){
        boolean res = true;
        if(indirizzo.isEmpty()) res = false;
        if(civico.isEmpty()) res = false;
        if(citta.isEmpty()) res = false;
        if(cap.trim().isEmpty()) res = false;
        return res;
    }

    @Override
    public String toString(){
        return  indirizzo +
                ", " + civico +
                ", " + citta +
                ", " + provincia +
                ", " + cap;
    }
}
