import java.util.Set;

public interface XmlHandler {
    void readXml(String filePath, Set<String> subjects);
    void saveXml(Set<String> subjects, String filePath);
}