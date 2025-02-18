import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

abstract class Series {
    double firstElement;
    double q;
    int n;

    Series(double firstElement, double q, int n) {
        this.firstElement = firstElement;
        this.q = q;
        this.n = n;
    }

    abstract double element(int num);

    double calculateSum() {
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += element(i);
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Series elements: ");
        for (int i = 0; i < n; i++) {
            result.append(element(i)).append(" ");
        }
        return result.toString();
    }

    public void saveToFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(toString());
            writer.write("\nSum of series: " + calculateSum());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Liner extends Series {

    Liner(double firstElement, double q, int n) {
        super(firstElement, q, n);
    }

    @Override
    double element(int num) {
        return firstElement + num * q;
    }
}

class Exponential extends Series {

    Exponential(double firstElement, double q, int n) {
        super(firstElement, q, n);
    }

    @Override
    double element(int num) {
        return firstElement * Math.pow(q, num);
    }
}


public class ProgressionApp extends JFrame {

    public static final String FILE_NAME = "seriesResult.txt";
    private JTextField firstElementField;
    private JTextField countField;
    private JTextField stepField;
    private JTextArea resultArea;
    private JRadioButton linerButton;
    private JRadioButton exponentialButton;

    public ProgressionApp() {

        setTitle("Progression Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("First element:"));
        firstElementField = new JTextField();
        add(firstElementField);

        add(new JLabel("Number of elements:"));
        countField = new JTextField();
        add(countField);

        add(new JLabel("Step (difference/ratio):"));
        stepField = new JTextField();
        add(stepField);


        linerButton = new JRadioButton("Arithmetic Progression");
        exponentialButton = new JRadioButton("Geometric Progression");
        ButtonGroup group = new ButtonGroup();
        group.add(linerButton);
        group.add(exponentialButton);

        add(linerButton);
        add(exponentialButton);


        JButton computeButton = new JButton("Compute");
        computeButton.addActionListener(new ComputeButtonListener());
        add(computeButton);


        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane);

        setVisible(true);
    }


    private class ComputeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double firstElement = Double.parseDouble(firstElementField.getText());
                int count = Integer.parseInt(countField.getText());
                double step = Double.parseDouble(stepField.getText());


                Series series;
                if (linerButton.isSelected()) {
                    series = new Liner(firstElement, step, count);
                } else if (exponentialButton.isSelected()) {
                    series = new Exponential(firstElement, step, count);
                } else {
                    resultArea.setText("Please select a progression type.");
                    return;
                }

                resultArea.setText(series.toString() + "\nSum of series: " + series.calculateSum());

                series.saveToFile(FILE_NAME);
            } catch (NumberFormatException ex) {
                resultArea.setText("Invalid input! Please enter valid numbers.");
            }
        }
    }

    public static void main(String[] args) {
        new ProgressionApp();
    }
}