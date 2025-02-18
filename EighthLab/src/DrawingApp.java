import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DrawingApp extends JFrame {
    private JPanel drawPanel;
    private Color penColor = Color.BLACK;
    private BufferedImage canvas;
    private JScrollPane scrollPane;
    private int prevX = -1, prevY = -1; 

    public DrawingApp(String title) {
        super(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Создаем холст для рисования
        canvas = new BufferedImage(1600, 1200, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = canvas.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Фон белый
        g2d.dispose();

        // Панель для рисования
        drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(canvas, 0, 0, null);
            }
        };
        drawPanel.setPreferredSize(new Dimension(canvas.getWidth(), canvas.getHeight()));
        drawPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                drawOnCanvas(e.getX(), e.getY());
            }
        });
        drawPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                prevX = -1;
                prevY = -1; // Сброс предыдущих координат
            }
        });

        // Добавление панели со скроллингом
        scrollPane = new JScrollPane(drawPanel);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Панель с кнопками
        JPanel buttonPanel = new JPanel();
        JButton redButton = new JButton("Red");
        JButton greenButton = new JButton("Green");
        JButton blueButton = new JButton("Blue");

        redButton.addActionListener(e -> penColor = Color.RED);
        greenButton.addActionListener(e -> penColor = Color.GREEN);
        blueButton.addActionListener(e -> penColor = Color.BLUE);

        buttonPanel.add(redButton);
        buttonPanel.add(greenButton);
        buttonPanel.add(blueButton);

        getContentPane().add(buttonPanel, BorderLayout.NORTH);

        // Меню для сохранения/открытия
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem openItem = new JMenuItem("Open");

        saveItem.addActionListener(e -> saveImage());
        openItem.addActionListener(e -> openImage());

        fileMenu.add(saveItem);
        fileMenu.add(openItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    // Рисование на холсте
    private void drawOnCanvas(int x, int y) {
        Graphics2D g2d = canvas.createGraphics();
        g2d.setColor(penColor);

        if (prevX != -1 && prevY != -1) { // Если предыдущие координаты существуют
            g2d.drawLine(prevX, prevY, x, y); // Рисуем линию от предыдущей точки к текущей
        }

        prevX = x; // Обновляем координаты
        prevY = y;

        g2d.dispose();
        drawPanel.repaint(); // Перерисовка панели
    }
    private void saveImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PNG Images", "png"));
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            // Добавляем расширение .png, если его нет
            if (!file.getName().toLowerCase().endsWith(".png")) {
                file = new File(file.getAbsolutePath() + ".png");
            }
            try {
                ImageIO.write(canvas, "png", file);
                JOptionPane.showMessageDialog(this, "Image saved successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving image: " + ex.getMessage());
            }
        }
    }

    private void openImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PNG Images", "png"));
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                BufferedImage loadedImage = ImageIO.read(file);
                if (loadedImage == null) {
                    throw new IOException("The selected file is not a valid PNG image.");
                }
                Graphics g = canvas.getGraphics();
                g.drawImage(loadedImage, 0, 0, null);
                g.dispose();
                drawPanel.repaint(); // Перерисовка панели
                JOptionPane.showMessageDialog(this, "Image loaded successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error loading image: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DrawingApp app = new DrawingApp("Drawing App");
            app.setVisible(true);
        });
    }
}