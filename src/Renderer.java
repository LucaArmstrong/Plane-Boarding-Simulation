import javax.swing.JFrame;
import java.awt.*;

public class Renderer {
    private int width, height;
    private DrawingPanel panel;
    public Renderer(Plane plane, int width, int height) {
        this.width = width;
        this.height = height;
        this.panel = new DrawingPanel(plane, width, height);
    }

    public void renderFrame() {
        JFrame frame = new JFrame("Plane Boarding Simulation");
        frame.setSize(this.width, this.height);
        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.darkGray);
        frame.add(this.panel);
        frame.setVisible(true);
    }

    public void renderPlane() {
        this.panel.repaint();
    }
}
