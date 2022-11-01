package io.utacfreak.psycogest.back.Mail;

import io.utacfreak.psycogest.back.Const;
import io.utacfreak.psycogest.back.Logger.Logger;
import io.utacfreak.psycogest.back.Bean.Fattura;
import io.utacfreak.psycogest.ui.ViewController;

import java.io.File;
import java.util.Observable;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class MailController extends Observable {

    private static MailController f;

    private MailController(){}

    public static void setObserver(){
        f.addObserver(ViewController.getNewsObserver());
    }

    public static MailController getMailController(){
        if(f != null)
            return f;
        return f = new MailController();
    }

    public void SendMail(Fattura fatt, String pathFattura){
        f.setChanged();

        if(!fatt.getPsicologa().getSendMail()){
            Logger.Log(MailController.class, "Invio fattura per Mail - Disattivato");
            return;
        }
        Logger.Log(MailController.class, "Invio fattura per Mail - Attivato");

        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fatt.getPsicologa().getMail(), fatt.getPsicologa().getMailPassword());
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fatt.getPsicologa().getMail()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(fatt.getPaziente().getMail()));
            message.setSubject("La tua Fattura - NON RISPONDERE");

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Ciao,\n\n" +
                    "Ti allego la tua fattura in data " + fatt.getDateEmissione() +
                    ". \nPer qualsiasi dubbio non esitare a conattatarmi\n\n" +
                    "Telefono: 323232323233\nEmail: Viviana.Catullo@gmail.com" +
                    "\nSaluti\nDott.Viviana Catullo");

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(Const.getPath(Const.FATTURE_PATH + pathFattura + ".docx")));

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);
            message.setContent(multipart);

            // Send message
            Transport.send(message);
            f.notifyObservers("OK: " + "Mail inviata con successo");
        } catch (Exception e) {
            Logger.Log(MailController.class, e.toString());
            f.notifyObservers("EXC: " + "Mail non inviata");
        }

    }
}
