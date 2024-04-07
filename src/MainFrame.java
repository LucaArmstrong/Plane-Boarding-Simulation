import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private DrawingPanel drawingPanel;

    public MainFrame() {
        setTitle("Drawing Example");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //drawingPanel = new DrawingPanel();
        //add(drawingPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame();
        });
    }
}