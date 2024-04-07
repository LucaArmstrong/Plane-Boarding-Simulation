import javax.swing.JFrame;
import java.awt.Color;
import java.util.*;
import java.util.List;

public class Rendering {
    public Plane plane;
    public int width, height;
    public JFrame frame;
    public DrawingPanel panel;

    public Rendering(Plane plane, int width, int height) {
        this.plane = plane;
        this.width = width;
        this.height = height;

        this.frame = renderFrame();
        this.panel = new DrawingPanel(this.width, this.height, this.plane.planeLength);
        this.frame.add(panel);
    }

    private JFrame renderFrame() {
        JFrame frame = new JFrame("Plane Boarding Simulation");
        frame.setSize(this.width, this.height);
        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.darkGray);
        frame.setVisible(true);
        return frame;
    }

    public void renderPlane() {
        this.panel.removeAll();
        this.panel.drawPlane();

        // give passengers in the aisle a colour of red
        // and passengers sitting down a colour of green
        List<Integer> availableSeats = new ArrayList<Integer>();

        for (Node passengerNode = plane.aislePassengers.head.next; passengerNode != null; passengerNode = passengerNode.next) {
            Passenger passenger = passengerNode.passenger;

            // add target seat to available seats
            Location passengerLocation = passenger.targetSeat.location;
            availableSeats.add(6 * passengerLocation.row + passengerLocation.column);

            // render passenger in aisle but only if in plane
            if (passenger.row < -0.5) continue;
            panel.drawAislePassenger(passenger.row, passenger.PASSENGER_WIDTH);
        }

        // draw seated passengers
        for (int i = 0; i < plane.seatNum; i++) {
            if (!availableSeats.contains(i)) {
                panel.drawSittingPassenger(i / 6, i % 6, 0.8);
            }
        }
    }
}
