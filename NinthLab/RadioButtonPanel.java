import javax.swing.*;
import java.awt.*;
//следующая версия за 25 ноября
public class RadioButtonPanel extends JPanel {
    public RadioButtonPanel() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 1)); 
        ButtonGroup group = new ButtonGroup();
        String[] cities = {"Минск", "Гомель", "Брест"};

        Icon defaultIcon = createIcon(Color.LIGHT_GRAY, "Default");
        Icon rolloverIcon = createIcon(Color.ORANGE, "Rollover");
        Icon pressedIcon = createIcon(Color.RED, "Pressed");
        Icon selectedIcon = createIcon(Color.GREEN, "Selected");

        for (String city : cities) {
            JRadioButton radioButton = new JRadioButton(city);
            radioButton.setIcon(defaultIcon);
            radioButton.setRolloverIcon(rolloverIcon);
            radioButton.setPressedIcon(pressedIcon);
            radioButton.setSelectedIcon(selectedIcon);

            group.add(radioButton);
            panel.add(radioButton);
        }
        add(panel, BorderLayout.CENTER);
    }

    private static Icon createIcon(Color color, String text) {
        int width = 120;
        int height = 30;

        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                g.setColor(color);
                g.fillRect(x, y, width, height);

                g.setColor(Color.BLACK);
                g.drawRect(x, y, width - 1, height - 1);

                g.setFont(new Font("Arial", Font.PLAIN, 13));
                FontMetrics metrics = g.getFontMetrics();
                int textX = x + (width - metrics.stringWidth(text)) / 2;
                int textY = y + ((height - metrics.getHeight()) / 2) + metrics.getAscent();
                g.drawString(text, textX, textY);
            }

            @Override
            public int getIconWidth() {
                return width;
            }

            @Override
            public int getIconHeight() {
                return height;
            }
        };
    }
}