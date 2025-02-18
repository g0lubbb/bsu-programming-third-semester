import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SessionResultsApp {
    private JFrame frame;
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JTextField entryField;
    private JButton addButton;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem openMenuItem;
    private Set<String> subjects = new HashSet<>();

    public SessionResultsApp() {
        frame = new JFrame("Session Results");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        inputArea = new JTextArea();
        inputArea.setEditable(false);
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener(new OpenFileAction());
        fileMenu.add(openMenuItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(inputArea),
                new JScrollPane(outputArea));
        splitPane.setDividerLocation(400);
        frame.add(splitPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        entryField = new JTextField();
        addButton = new JButton("Add Entry");
        addButton.addActionListener(new AddEntryAction());

        inputPanel.add(entryField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        frame.add(inputPanel, BorderLayout.SOUTH);
    }

    public void show() {
        frame.setVisible(true);
    }

    private class OpenFileAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    subjects.clear();
                    String fileContent = readFileAndProcessContent(file);
                    inputArea.setText(fileContent);
                    updateOutputArea();
                } catch (IOException ex) {
                    outputArea.setText("Ошибка чтения файла: " + ex.getMessage());
                } catch (IllegalArgumentException ex) {
                    outputArea.setText("Ошибка формата файла: " + ex.getMessage());
                }
            }
        }
    }

    private class AddEntryAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String entry = entryField.getText().trim();
            if (!entry.isEmpty()) {
                try {
                    validateLineFormat(entry);  // Проверка формата строки перед добавлением

                    addSubjectFromEntry(entry);
                    inputArea.append(entry + "\n");
                    updateOutputArea();
                    entryField.setText("");

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, "Ошибка: " + ex.getMessage(),
                            "Недопустимая строка", JOptionPane.ERROR_MESSAGE);
                    outputArea.setText("Ошибка: " + ex.getMessage());
                }
            }
        }
    }

    private String readFileAndProcessContent(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");

                try {
                    validateLineFormat(line); // Проверка формата каждой строки
                    String[] parts = line.split(",");
                    subjects.add(parts[2].trim()); // Добавление предмета в Set
                } catch (IllegalArgumentException ex) {
                    throw new IllegalArgumentException("Ошибка в строке " + lineNumber + ": " + ex.getMessage());
                }
                lineNumber++;
            }
        }
        return content.toString();
    }

    private void validateLineFormat(String line) {
        if (line.trim().isEmpty()) {
            return;
        }

        String[] parts = line.split(",");
        if (parts.length < 4) {
            throw new IllegalArgumentException("Неверный формат данных: " + line + ". Ожидается формат: name,age,subject,grade");
        }

        String grade = parts[3].trim();
        try {
            int parsedGrade = Integer.parseInt(grade);
            if (parsedGrade < 1 || parsedGrade > 5) {
                throw new IllegalArgumentException("Оценка должна быть между 1 и 5: " + line);
            }
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Недопустимая оценка: " + grade + " в строке: " + line);
        }
    }

    private void addSubjectFromEntry(String entry) {
        String[] parts = entry.split(",");
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid entry format. Expected format: name,age,subject,grade");
        }

        String grade = parts[3].trim();
        try {
            int parsedGrade = Integer.parseInt(grade);
            if (parsedGrade < 1 || parsedGrade > 5) {
                throw new IllegalArgumentException("Grade should be between 1 and 5.");
            }
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid grade. Please enter a number between 1 and 5.");
        }

        subjects.add(parts[2].trim());
    }

    private void updateOutputArea() {
        List<String> sortedSubjects = subjects.stream()
                .sorted()
                .collect(Collectors.toList());
        outputArea.setText(String.join("\n", sortedSubjects));
    }

    public static void main(String[] args) {
        SessionResultsApp app = new SessionResultsApp();
        app.show();
    }
}
