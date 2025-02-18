import javax.swing.*; 
import java.awt.*; 
import java.awt.event.KeyAdapter; 
import java.awt.event.KeyEvent; 
 
public class KeyObserverApp extends JFrame { 
    private final KeySubject keySubject; 
 
    public KeyObserverApp() { 
        setTitle("Key Observer Application"); 
        setSize(400, 300); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLayout(new BorderLayout()); 
 
        keySubject = new KeySubject(); 
 
        JLabel keyDisplayLabel = new JLabel("Нажатая клавиша: ", SwingConstants.CENTER); 
        keyDisplayLabel.setFont(new Font("Arial", Font.BOLD, 32)); 
        add(keyDisplayLabel, BorderLayout.NORTH); 
 
        JTextArea keyLogArea = new JTextArea(); 
        keyLogArea.setEditable(false); 
        add(new JScrollPane(keyLogArea), BorderLayout.CENTER); 
 
        KeyDisplay keyDisplay = new KeyDisplay(keyDisplayLabel); 
        KeyLogger keyLogger = new KeyLogger(keyLogArea); 
        keySubject.attach(keyDisplay); 
        keySubject.attach(keyLogger); 
 
        addKeyListener(new KeyAdapter() { 
            @Override 
            public void keyPressed(KeyEvent e) { 
                String key; 
                if (Character.isDefined(e.getKeyChar())) { 
                    key = String.valueOf(e.getKeyChar()); 
                } else { 
                    key = KeyEvent.getKeyText(e.getKeyCode()); 
                } 
                keySubject.notifyObservers(key); 
            } 
        }); 
 
 
        setFocusable(true); 
        requestFocusInWindow(); 
    } 
 
    public static void main(String[] args) { 
        SwingUtilities.invokeLater(() -> { 
            KeyObserverApp app = new KeyObserverApp(); 
            app.setVisible(true); 
        }); 
    } 
}
