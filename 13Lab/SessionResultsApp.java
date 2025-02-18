import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SessionResultsApp {
    private JFrame frame;
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JTextField entryField;
    private JButton addButton;
    private JMenuBar menuBar;
    private JMenu fileMenu, strategyMenu;
    private JMenuItem openDomMenuItem, openSaxMenuItem, saveMenuItem;
    private JMenuItem streamStrategyMenuItem, basicStrategyMenuItem;

    private Set<String> subjects = new HashSet<>();
    private SubjectProcessor subjectProcessor;

    public SessionResultsApp() {
        subjectProcessor = new BasicStrategy();

        frame = new JFrame("Session Results");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        inputArea = new JTextArea();
        inputArea.setEditable(false);
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        menuBar = new JMenuBar();
        setupFileMenu();
        setupStrategyMenu();

        frame.setJMenuBar(menuBar);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(inputArea),
                new JScrollPane(outputArea));
        splitPane.setDividerLocation(400);
        frame.add(splitPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        entryField = new JTextField();
        addButton = new JButton("Add Entry");
        addButton.addActionListener(this::handleAddEntry);

        inputPanel.add(entryField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        frame.add(inputPanel, BorderLayout.SOUTH);
    }

    private void setupFileMenu() {
        fileMenu = new JMenu("File");
        openDomMenuItem = new JMenuItem("Open with DOM");
        openDomMenuItem.addActionListener(e -> handleOpenFile(new XmlDomReader()));
        openSaxMenuItem = new JMenuItem("Open with SAX");
        openSaxMenuItem.addActionListener(e -> handleOpenFile(new XmlSaxReader()));
        saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(this::handleSaveFile);
        
        fileMenu.add(openDomMenuItem);
        fileMenu.add(openSaxMenuItem);
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);
    }

    private void setupStrategyMenu() {
        strategyMenu = new JMenu("Strategy");

        streamStrategyMenuItem = new JMenuItem("Stream Strategy");
        streamStrategyMenuItem.addActionListener(e -> {
            subjectProcessor = new StreamStrategy();
            updateOutputArea();
        });

        basicStrategyMenuItem = new JMenuItem("Basic Strategy");
        basicStrategyMenuItem.addActionListener(e -> {
            subjectProcessor = new BasicStrategy();
            updateOutputArea();
        });

        strategyMenu.add(streamStrategyMenuItem);
        strategyMenu.add(basicStrategyMenuItem);
        menuBar.add(strategyMenu);
    }

    private void handleOpenFile(XmlHandler xmlHandler) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                subjects.clear();
                xmlHandler.readXml(file.getPath(), subjects);
                updateInputArea(file);
                updateOutputArea();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error reading file: " + ex.getMessage(),
                        "File Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateInputArea(File file) throws IOException {
        String fileContent = new String(java.nio.file.Files.readAllBytes(file.toPath()));
        inputArea.setText(fileContent);
    }

    private void handleSaveFile(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XmlHandler xmlHandler = subjectProcessor instanceof StreamStrategy ? new XmlDomReader() : new XmlSaxReader();
            xmlHandler.saveXml(subjects, file.getPath());
            JOptionPane.showMessageDialog(frame, "File saved successfully!", "Save", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleAddEntry(ActionEvent e) {
        String entry = entryField.getText().trim();
        if (!entry.isEmpty()) {
            String[] parts = entry.split(",");
            if (parts.length >= 3) {
                subjects.add(parts[2].trim());
                inputArea.append(entry + "\n");
                updateOutputArea();
            }
            entryField.setText("");
        }
    }

    private void updateOutputArea() {
        outputArea.setText(subjectProcessor.processSubjects(subjects));
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SessionResultsApp().show();
    }
}
