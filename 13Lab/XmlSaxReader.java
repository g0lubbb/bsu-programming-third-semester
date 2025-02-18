import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.util.Set;

public class XmlSaxReader implements XmlHandler {
    @Override
    public void readXml(String filePath, Set<String> subjects) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {
                boolean subject = false;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("subject")) {
                        subject = true;
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    if (subject) {
                        subjects.add(new String(ch, start, length));
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.equalsIgnoreCase("subject")) {
                        subject = false;
                    }
                }
            };
            saxParser.parse(filePath, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveXml(Set<String> subjects, String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element root = document.createElement("subjects");
            document.appendChild(root);

            for (String subject : subjects) {
                Element subjectElement = document.createElement("subject");
                subjectElement.appendChild(document.createTextNode(subject));
                root.appendChild(subjectElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
