/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intxml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class INTXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        INTXML i = new INTXML();
        i.createXMLFile();
        i.validateXML();
    }

    public void createXMLFile() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element root = doc.createElement("employees");
            doc.appendChild(root);

                Element id = doc.createElement("id");
                id.setTextContent("100");
                root.appendChild(id);

                Element name = doc.createElement("name");
                name.setTextContent("ngoc");
                root.appendChild(name);
            

            TransformerFactory tff = TransformerFactory.newInstance();
            try {
                Transformer tf = tff.newTransformer();
                tf.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
                tf.setOutputProperty(OutputKeys.INDENT, "yes");

                StringWriter sw = new StringWriter();
                StreamResult sr = new StreamResult(sw);
                DOMSource ds = new DOMSource(doc);
                try {
                    tf.transform(ds, sr);

                    try {
                        //BufferedWriter bw = new BufferedWriter(fw);
                        try (FileWriter fw = new FileWriter(new File("C:\\Users\\nguye\\OneDrive\\Documents\\NetBeansProjects\\INTXML\\src\\intxml\\xmlData.xml"))) {
                            //BufferedWriter bw = new BufferedWriter(fw);
                            fw.write(sw.toString());
                        }
                        System.out.println(sw.toString());
                        System.out.println("tạo file thành công");
                    } catch (IOException ex) {
                        System.out.println("error" + ex.getMessage());
                    }
                } catch (TransformerException ex) {
                    System.out.println("error" + ex.getMessage());
                }

            } catch (TransformerConfigurationException ex) {
                System.out.println("error" + ex.getMessage());
            }
        } catch (ParserConfigurationException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }

    public void validateXML() {
        try {
            DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = parser.parse(new File("C:\\Users\\nguye\\OneDrive\\Documents\\NetBeansProjects\\INTXML\\src\\intxml\\xmlData.xml"));

            if (doc != null) {
                Schema schema = SchemaFactory.
                        newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).
                        newSchema(new StreamSource(new File("C:\\Users\\nguye\\OneDrive\\Documents\\NetBeansProjects\\INTXML\\src\\intxml\\xmlData.xml")));
                Validator validator = schema.newValidator();
                validator.validate(new DOMSource(doc));
            } else {
                System.out.println("file ko tồn tại");
            }

//            System.out.println("valid");
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
