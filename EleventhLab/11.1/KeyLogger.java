import javax.swing.JTextArea; 
 
public class KeyLogger implements Observer { 
    private final JTextArea logArea; 
 
    public KeyLogger(JTextArea logArea) { 
        this.logArea = logArea; 
    } 
 
    @Override 
    public void update(String key) { 
        logArea.append(key + "\n"); 
        logArea.setCaretPosition(logArea.getDocument().getLength()); 
    } 
}
