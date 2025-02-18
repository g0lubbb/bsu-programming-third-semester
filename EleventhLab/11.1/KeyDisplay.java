import javax.swing.JLabel; 
 
public class KeyDisplay implements Observer { 
    private final JLabel displayLabel; 
 
    public KeyDisplay(JLabel displayLabel) { 
        this.displayLabel = displayLabel; 
    } 
 
    @Override 
    public void update(String key) { 
        displayLabel.setText("нажатая клавиша: " + key); 
    } 
}
