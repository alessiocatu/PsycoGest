package io.utacfreak.psycogest.back.Bean;

import io.utacfreak.psycogest.back.Const;

import java.util.Observable;

public class Paziente extends Observable implements Comparable<Paziente>{
    private int id;
    private String nome = "";
    private String cognome = "";
    private String codiceFiscale = "";
    private Address address = new Address();
    private String telefono = "";
    private String mail = "";

    private int eta = Const.MINORE;
    private boolean isDSA = false;
    private boolean isNotSendCFtoAE = false;
    private boolean isVisible = true;

    //GETTER
    public int getId(){
        return this.id;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public Address getAddress() {
        return address;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getMail() {
        return mail;
    }

    public int getEta() {
        return eta;
    }

    public boolean isDSA() {
        return isDSA;
    }

    public boolean isNotSendCFtoAE() {
        return isNotSendCFtoAE;
    }

    public boolean isVisible() {
        return isVisible;
    }

    //SETTER
    public void setId(int id){this.id = id;}

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    public void setIsDSA(boolean isDSA) {
        this.isDSA = isDSA;
    }

    public void setisNotSendCFtoAE(boolean isNotSendCFtoAE) {
        this.isNotSendCFtoAE = isNotSendCFtoAE;
    }

    public void setIsVisibile(boolean visibile) {
        isVisible = visibile;
    }

    @Override
    public void clearChanged() {
        this.setChanged();
    }

    @Override
    public int compareTo(Paziente e) {
        return this.getNome().compareTo(e.getNome());
    }

    public boolean isPazienteValid() {
        boolean res = true;
        if(nome.isEmpty()) res = false;
        if(cognome.isEmpty()) res = false;
        if(codiceFiscale.isEmpty()) res = false;
        if(!address.isValid()) res = false;
        if(telefono.trim().isEmpty()) res = false;
        if(mail.isEmpty()) res = false;
        return res;
    }

    @Override
    public String toString() {
        return "Paziente{" +
                id +
                ", " + nome + '\'' +
                ", " + cognome + '\'' +
                ", " + codiceFiscale + '\'' +
                ", " + address +
                ", " + telefono + '\'' +
                ", " + mail + '\'' +
                ", " + eta +
                ", isDSA=" + isDSA +
                ", isNotSendCFtoAE=" + isNotSendCFtoAE +
                ", isVisible=" + isVisible +
                '}';
    }
}
