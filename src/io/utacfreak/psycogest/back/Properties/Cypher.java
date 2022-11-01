package io.utacfreak.psycogest.back.Properties;

import io.utacfreak.psycogest.back.Const;

import java.io.FileInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import javax.crypto.Cipher;

public class Cypher {
    private static Cypher cypher;

    private Cypher() {}

    public static Cypher getCypher(){
        if(cypher != null)
            return cypher;
        return cypher = new Cypher();
    }

    public String encryptToBase64(String plainText) {
        String encoded = "";
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            FileInputStream fis = new FileInputStream(Const.getPath(Const.PATH_SENITELCF_CER));
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            X509Certificate crt = (X509Certificate) cf.generateCertificate(fis);
            cipher.init(Cipher.ENCRYPT_MODE, crt);
            byte[] encrypted = cipher.doFinal(plainText.getBytes());
            encoded = Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encoded;
    }
}
