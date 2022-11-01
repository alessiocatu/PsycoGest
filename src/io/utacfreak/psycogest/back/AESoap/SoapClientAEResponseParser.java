package io.utacfreak.psycogest.back.AESoap;

import io.utacfreak.psycogest.back.Logger.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

public class SoapClientAEResponseParser {
    private SoapClientAEResponseParser(){}

    public static boolean evaluteResponse(String xml) {
        if(xml.startsWith("Error")){
            return false;
        }

        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            InputSource is = new InputSource(new StringReader(xml));
            Document doc = db.parse(is);
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("messaggio");

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;
                    // get response value
                    String codice = element.getElementsByTagName("codice").item(0).getTextContent();

                    return codice.equals("0");
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            Logger.Log(SoapClientAEResponseParser.class, e.toString());
            return false;
        }

        return true;
    }
}