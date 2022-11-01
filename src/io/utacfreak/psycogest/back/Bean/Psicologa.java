package io.utacfreak.psycogest.back.Bean;

public class Psicologa {
    private String nome = "";
    private String cognome = "";
    private String codiceFiscale = "";
    private String partitaIva = "";
    private String pincode = "";
    private String mail = "";
    private String mailPassword = "";
    private int progressivo;

    private boolean sendMail = false;
    private boolean sendAE = false;
    private boolean keepHistory = false;

    //GETTER
    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public String getPincode() {
        return pincode;
    }

    public int getProgressivo() {
        return progressivo;
    }

    public String getMail() {
        return mail;
    }

    public String getMailPassword() {
        return mailPassword;
    }

    public boolean getSendMail() { return sendMail; }

    public boolean getSendAE() { return sendAE; }

    public boolean getKeepHistory() { return keepHistory; }

    //SETTER
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public void setProgressivo(int progressivo) {
        this.progressivo = progressivo;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMailPassword(String mailPassword) {
        this.mailPassword = mailPassword;
    }

    public void setSendMail(boolean sendMail) {
        this.sendMail = sendMail;
    }

    public void setSendAE(boolean sendAE) {
        this.sendAE = sendAE;
    }

    public void setKeepHistory(boolean keepHistory) {
        this.keepHistory = keepHistory;
    }

    @Override
    public String toString() {
        return "Psicologa{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", codiceFiscale='" + codiceFiscale + '\'' +
                ", partitaIva='" + partitaIva + '\'' +
                ", pincode='" + pincode + '\'' +
                ", progressivo='" + progressivo + '\'' +
                ", mail ='" + mail + '\'' +
                ", mailPassword" + mailPassword + '\'' +
                ", sendMail ='" + sendMail + '\'' +
                ", sendAE ='" + sendAE + '\'' +
                ", keepHistory ='" + keepHistory +
                '}';
    }
}
