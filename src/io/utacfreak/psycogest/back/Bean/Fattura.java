package io.utacfreak.psycogest.back.Bean;

import java.util.Date;

public class Fattura {
    private Paziente paziente;
    private Psicologa psy;
    private Date emissione;
    private Date pagamento;
    private String importo;
    private String iva;

    public Paziente getPaziente() {
        return paziente;
    }

    public void setPaziente(Paziente paziente) {
        this.paziente = paziente;
    }

    public Psicologa getPsicologa() {
        return this.psy;
    }

    public void setPsicologa(Psicologa psy) {
        this.psy = psy;
    }

    public Date getDateEmissione() {
        return emissione;
    }

    public void setDateEmissione(Date emissione) {
        this.emissione = emissione;
    }

    public Date getDatePagamento() {
        return pagamento;
    }

    public void setDatePagamento(Date pagamento) {
        this.pagamento = pagamento;
    }

    public String getImporto() {
        return importo;
    }

    public void setImporto(String importo) {
        this.importo = importo;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }
}
