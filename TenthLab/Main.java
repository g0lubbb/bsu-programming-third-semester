import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("CustomSet Demonstration");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());

            DefaultListModel<String> listModel = new DefaultListModel<>();
            JList<String> list = new JList<>(listModel);
            panel.add(new JScrollPane(list), BorderLayout.CENTER);

            JTextField inputField = new JTextField();
            JButton addButton = new JButton("Add");
            JButton removeButton = new JButton("Remove");
            JButton clearButton = new JButton("Clear");

            CustomSet<String> customSet = new CustomSet<>();

            addButton.addActionListener(e -> {
                String text = inputField.getText();
                if (!text.isEmpty() && customSet.add(text)) {
                    listModel.addElement(text);
                }
                inputField.setText("");
            });

            removeButton.addActionListener(e -> {
                String selected = list.getSelectedValue();
                if (selected != null && customSet.remove(selected)) {
                    listModel.removeElement(selected);
                }
            });

            clearButton.addActionListener(e -> {
                customSet.clear();
                listModel.clear();
            });

            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new FlowLayout());
            inputPanel.add(inputField);
            inputPanel.add(addButton);
            inputPanel.add(removeButton);
            inputPanel.add(clearButton);

            panel.add(inputPanel, BorderLayout.SOUTH);
            frame.add(panel);

            frame.setVisible(true);
        });
    }
}
