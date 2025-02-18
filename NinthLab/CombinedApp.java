import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CombinedApp extends JFrame {
    public CombinedApp() {
        setTitle("Combined App");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("BorderLayout", createBorderLayoutPanel());
        tabbedPane.addTab("GridLayout", createGridLayoutPanel());
        tabbedPane.addTab("RadioButtons", new RadioButtonPanel());

        add(tabbedPane);
    }

    private JPanel createBorderLayoutPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        DefaultListModel<String> leftListModel = new DefaultListModel<>();
        leftListModel.addElement("Item 1");
        leftListModel.addElement("Item 2");
        leftListModel.addElement("Item 3");
        JList<String> leftList = new JList<>(leftListModel);

        DefaultListModel<String> rightListModel = new DefaultListModel<>();
        JList<String> rightList = new JList<>(rightListModel);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton moveToRight = new JButton(">");
        JButton moveToLeft = new JButton("<");

        moveToRight.addActionListener(e -> {
            int[] selectedIndices = leftList.getSelectedIndices();
            for (int i = selectedIndices.length - 1; i >= 0; i--) {
                int index = selectedIndices[i];
                rightListModel.addElement(leftListModel.get(index));
                leftListModel.remove(index);
            }
        });

        moveToLeft.addActionListener(e -> {
            int[] selectedIndices = rightList.getSelectedIndices();
            for (int i = selectedIndices.length - 1; i >= 0; i--) {
                int index = selectedIndices[i];
                leftListModel.addElement(rightListModel.get(index));
                rightListModel.remove(index);
            }
        });

        buttonPanel.add(moveToRight);
        buttonPanel.add(moveToLeft);

        JScrollPane leftScrollPane = new JScrollPane(leftList);
        JScrollPane rightScrollPane = new JScrollPane(rightList);
        JPanel buttonWrapper = new JPanel(new BorderLayout());
        buttonWrapper.add(buttonPanel, BorderLayout.CENTER);

        JSplitPane leftAndButtons = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftScrollPane, buttonWrapper);
        leftAndButtons.setResizeWeight(0.45);
        leftAndButtons.setDividerSize(5);

        JSplitPane fullSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftAndButtons, rightScrollPane);
        fullSplitPane.setResizeWeight(0.7);
        fullSplitPane.setDividerSize(5);

        mainPanel.add(fullSplitPane, BorderLayout.CENTER);
        return mainPanel;
    }

    private JPanel createGridLayoutPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 3, 5, 5));
        for (int i = 0; i < 9; i++) {
            int buttonIndex = i;
            JButton button = new JButton("Button " + (buttonIndex + 1));
            button.setOpaque(true);
            button.setBorderPainted(false);
            Color originalColor = button.getBackground();

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(Color.YELLOW);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(originalColor);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    button.setText("Clicked!");
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    button.setText("Button " + (buttonIndex + 1));
                }
            });
            panel.add(button);
        }
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CombinedApp app = new CombinedApp();
            app.setVisible(true);
        });
    }
}