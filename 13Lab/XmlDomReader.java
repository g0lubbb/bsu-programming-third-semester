import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Set;

public class XmlDomReader implements XmlHandler {
    @Override
    public void readXml(String filePath, Set<String> subjects) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));
            NodeList subjectList = document.getElementsByTagName("subject");
            for (int i = 0; i < subjectList.getLength(); i++) {
                Element subject = (Element) subjectList.item(i);
                subjects.add(subject.getTextContent());
            }
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
