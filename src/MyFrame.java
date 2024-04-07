import javax.swing.JFrame;
import java.awt.*;

public class MyFrame {
    public JFrame frame;

    public MyFrame(int width, int height, DrawingPanel panel) {
        this.frame = renderFrame(width, height);
        this.frame.add(panel);
    }

    private JFrame renderFrame(int width, int height) {
        JFrame frame = new JFrame("Plane Boarding Simulation");
        frame.setSize(width, height);
        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.darkGray);
        frame.setVisible(true);
        return frame;
    }
}
