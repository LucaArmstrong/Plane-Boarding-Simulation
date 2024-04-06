import javax.swing.JFrame;
import javax.swing.JComponent;
import java.awt.*;

public class Rendering extends JComponent {
    public Plane plane;
    public int width, height;
    public Rendering(Plane plane, int width, int height) {
        this.plane = plane;
        this.width = width;
        this.height = height;

        renderFrame();
    }

    public void renderFrame() {
        JFrame frame = new JFrame("Plane Boarding Simulation");
        frame.setSize(this.width, this.height);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.black);
        frame.setResizable(false);
        frame.add(new DrawingPanel());
        frame.setVisible(true);
    }

    public void renderPlane() {

    }
}
