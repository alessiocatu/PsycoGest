package io.utacfreak.psycogest.back.AESoap;

import io.utacfreak.psycogest.back.Const;
import io.utacfreak.psycogest.back.Logger.Logger;
import io.utacfreak.psycogest.back.Bean.Fattura;
import io.utacfreak.psycogest.back.Properties.Cypher;
import io.utacfreak.psycogest.ui.ViewController;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Observable;

public class SoapClientAE extends Observable {
    private static SoapClientAE s;

    private SoapClientAE() {
    }

    public static void setObserver(){
        s.addObserver(ViewController.getNewsObserver());
    }

    public static SoapClientAE getSoapClientAE() {
        if (s != null)
            return s;
        return s = new SoapClientAE();
    }

    private static String DocumentoSpesa730pXML(Fattura fatt) {
        String pincode = Cypher.getCypher().encryptToBase64(fatt.getPsicologa().getPincode());
        String cfProprietario = Cypher.getCypher().encryptToBase64(fatt.getPsicologa().getCodiceFiscale());
        String cfCittadino = Cypher.getCypher().encryptToBase64(fatt.getPaziente().getCodiceFiscale());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String xml =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:doc=\"http://documentospesap730.sanita.finanze.it\">" +
                "   <soapenv:Header/>" +
                "   <soapenv:Body>" +
                "      <doc:inserimentoDocumentoSpesaRequest>" +
                "         <doc:pincode>" + pincode + "</doc:pincode>" +
                "         <doc:Proprietario>" +
                "            <doc:cfProprietario>" + cfProprietario + "</doc:cfProprietario>" +
                "          </doc:Proprietario>" +
                "          <doc:idInserimentoDocumentoFiscale>" +
                "             <doc:idSpesa>" +
                "               <doc:pIva>" + fatt.getPsicologa().getPartitaIva() + "</doc:pIva>" +
                "               <doc:dataEmissione>" + formatter.format(fatt.getDateEmissione()) + "</doc:dataEmissione>" +
                "               <doc:numDocumentoFiscale>" +
                "                  <doc:dispositivo>1</doc:dispositivo>" +
                "                  <doc:numDocumento>P" + fatt.getPsicologa().getProgressivo() + "</doc:numDocumento>" +
                "               </doc:numDocumentoFiscale>" +
                "            </doc:idSpesa>" +
                "            <doc:dataPagamento>" + formatter.format(fatt.getDatePagamento()) + "</doc:dataPagamento>" +
                        (fatt.getPaziente().isNotSendCFtoAE() ? "" : ("<doc:cfCittadino>" + cfCittadino + "</doc:cfCittadino>")) +
                "            <doc:voceSpesa>" +
                "               <doc:tipoSpesa>SP</doc:tipoSpesa>" +
                "               <doc:importo>" + fatt.getImporto() + "</doc:importo>" +
                "               <doc:aliquotaIVA>" + fatt.getIva() + "</doc:aliquotaIVA>" +
                "            </doc:voceSpesa>" +
                "            <doc:pagamentoTracciato>SI</doc:pagamentoTracciato>" +
                "            <doc:tipoDocumento>F</doc:tipoDocumento>" +
                "            <doc:flagOpposizione>"+ (fatt.getPaziente().isNotSendCFtoAE() ? 1 : 0 ) + "</doc:flagOpposizione>" +
                "         </doc:idInserimentoDocumentoFiscale>" +
                "      </doc:inserimentoDocumentoSpesaRequest>" +
                "   </soapenv:Body>" +
                "</soapenv:Envelope>";
        return xml;
    }

    public String DocumentoSpesa730pAsincrono(String xml) {
        Logger.Log(SoapClientAE.class, "START - DocumentoSpesa730pAsincrono");
        String res = "";
        try {
            String url = "https://invioSS730pTest.sanita.finanze.it/DocumentoSpesa730pWeb/DocumentoSpesa730pPort";
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            URL obj = new URL(url);
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Encoding", "gzip,deflate");
            con.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            con.setRequestProperty("SOAPAction", "inserimento.documentospesap730.sanita.finanze.it");
            con.setRequestProperty("Authorization", "Basic TVRPTVJBNjZBNDFHMjI0TTpTYWx2ZTEyMw==");
            con.setRequestProperty("Host", "invioSS730pTest.sanita.finanze.it");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("User-Agent", "Apache-HttpClient/4.5.2 (Java/16.0.1)");
            con.setRequestProperty("User-Agent", "Apache-HttpClient/4.5.2 (Java/16.0.1)");
            con.setConnectTimeout(1000);
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(xml);
            wr.flush();
            wr.close();
            String responseStatus = con.getResponseMessage();

            if(!responseStatus.equals("OK")) {
                Logger.Log(SoapClientAE.class, "Error: " + con.getResponseMessage());
                Logger.Log(SoapClientAE.class, "END - DocumentoSpesa730pAsincrono");
                res = responseStatus;
                return res;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            res = response.toString();
            Logger.Log(SoapClientAE.class, res);
        } catch (Exception e) {
            Logger.Log(SoapClientAE.class, "EXC: " + e.getMessage());
        }
        Logger.Log(SoapClientAE.class, "END - DocumentoSpesa730pAsincrono");
        return res;
    }

    public static void saveXMLFattura(String nameFattura, Fattura fatt){
        s.setChanged();

        if(!fatt.getPsicologa().getSendAE()){
            Logger.Log(SoapClientAE.class, "Invio all'agenzia delle entrate - Disattivato");
            return;
        }
        Logger.Log(SoapClientAE.class, "Invio all'agenzia delle entrate - Attivato");

        try {
            File file= new File (Const.getPath(Const.FATTURE_XML_PATH_TOSEND) + nameFattura);
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(DocumentoSpesa730pXML(fatt));
            writer.flush();
            writer.close();
            Logger.Log(SoapClientAE.class, "OK: " + nameFattura);
        } catch(Exception e){
            Logger.Log(SoapClientAE.class, "EXC: " + e.getMessage());
            s.notifyObservers("EXC: " + e.getMessage());
        }
    }

    TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    //No need to implement.
                }
                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    //No need to implement.
                }
            }
    };
}