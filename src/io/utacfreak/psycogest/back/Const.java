package io.utacfreak.psycogest.back;

public class Const {
    private static final String MAC_PATH = "/Applications/PsycoGest/";
    private static final String WIN_PATH = "c:\\Program Files\\PsycoGest/";

    public static final String CONF_PATH = "conf/";
    public static final String CONF_NAME = "conf.properties";
    public static final String DB_PATH = "pazienti.db";
    public static final String DB_SQL_PATH = CONF_PATH + "pazienti-sql.db";

    public static final String TEMPLATE_PATH = CONF_PATH + "template.docx";
    public static final String PATH_730_XSD = CONF_PATH + "730_precompilata.xsd";
    public static final String PATH_SENITELCF_CER = CONF_PATH + "SanitelCF.cer";

    public static final String LOGGER_PATH = "log/history.log";

    private static final String IMAGES_PATH = "images/";
    public static final String IMAGES_LOADING = IMAGES_PATH + "loading.png";
    public static final String IMAGES_LOGO = IMAGES_PATH + "logo.png";

    public static final String FATTURE_PATH = "fatture/";
    public static final String FATTURE_XML_PATH = "fatture-xml/";
    public static final String FATTURE_XML_PATH_TOSEND = FATTURE_XML_PATH + "toSend/";
    public static final String FATTURE_XML_PATH_SENT = FATTURE_XML_PATH + "Sent/";
    public static final String FATTURE_XML_PATH_RESPONSE = FATTURE_XML_PATH + "Response/";

    public static final String FATTURE_CSV_PATH = "fatture-csv/";
    public static final String FATTURE_CSV_NAME = "fatture.csv";

    public static final String PSYCO_NOME = "psicologa.nome";
    public static final String PSYCO_COGN = "psicologa.cognome";
    public static final String PSYCO_CODFISC = "psicologa.codicefiscale";
    public static final String PSYCO_PARTIVA = "psicologa.partitaiva";
    public static final String PSYCO_PINCODE = "psicologa.pincode";
    public static final String PSYCO_PROGRESSIVO = "psicologa.progressivo";
    public static final String PSYCO_MAIL = "psicologa.mail";
    public static final String PSYCO_MAIL_PASSWORD = "psicologa.mail.16char.password";

    public static final String PSYCO_FUNC_SENDMAIL = "function.send.mail";
    public static final String PSYCO_FUNC_SENDAE = "function.send.ae";
    public static final String PSYCO_FUNC_KEEPHYS = "function.keep.history";

    public static final int MINORE = 0;
    public static final int GIOVANEADULTO = 1;
    public static final int ADULTO = 2;

    public static final float IVA = 2.0f;

    public static final float IMPORTO_MINORE = 50F;
    public static final float IMPORTO_DSA = 40F;
    public static final float IMPORTO_GIOVANEADULTO = 55F;
    public static final float IMPORTO_ADULTI = 60F;

    public static final String DSASTRING = "incontri di potenziamento delle abilità\nscolastiche in minore con difficoltà negli\napprendimenti";
    public static final String NODSASTRING = "Colloquio di supporto psicologico";
    public static final String NOCFSTRING = "Fattura trasmessa al Sistema TS senza indicazione del Codice Fiscale per opposizione\n" +
            "dell’assistito ai senti dell’Art. 4 del D.M. 16/09/2016 e del Decreto del MEF del 19/10/2020\n" +
            "art. 2 comma 2 sezione “c”";

    public static String prettyEta(int eta){
        String res = "";

        switch(eta){
            case MINORE:
                res = "Minore";
                break;
            case GIOVANEADULTO:
                res = "Giovane adulto";
                break;
            case ADULTO:
                res = "Adulto";
                break;
            default:
                res = "Non specificato";
                break;
        }

        return res;
    }

    public static int fromPrettyEta(String eta){
        int res;

        switch(eta){
            case "Minore":
                res = MINORE;
                break;
            case "Giovane adulto":
                res = GIOVANEADULTO;
                break;
            case "Adulto":
                res = ADULTO;
                break;
            default:
                res = MINORE;
                break;
        }

        return res;
    }

    public static String getPath(String path){
        if(System.getProperty("os.name").startsWith("Mac"))
            return MAC_PATH + path;
        else
            return WIN_PATH + path;
    }
}
