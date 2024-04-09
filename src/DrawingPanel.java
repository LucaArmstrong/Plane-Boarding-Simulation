import java.awt.*;
import java.awt.geom.*;
import javax.swing.JComponent;

public class DrawingPanel extends JComponent {
    //public Graphics2D g2d;
    private Plane plane;
    private int width, height, rowLength;
    private int xPlane, yPlane, xAisle, yAisle;
    private int xSeatsAboveAisle, ySeatsAboveAisle;
    private int xSeatsBelowAisle, ySeatsBelowAisle;

    public DrawingPanel(Plane plane, int width, int height) {
        this.width = width;
        this.height = height;
        this.plane = plane;

        MyFrame frame = new MyFrame(width, height, this);

        initPlaneValues();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // antialiasing
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
        );

        drawPlane(g2d);
        renderPassengers(g2d);
    }

    private void initPlaneValues() {
        // need one row length on either side of the plane
        // plus a half a row length border round the inside of the plane
        this.rowLength = this.width / (this.plane.planeLength + 3);

        // aisle width is one row length
        // plus 6 seats and a half row length border makes a height of 8 row lengths
        this.xPlane = this.rowLength;
        this.yPlane = this.height / 2 - this.rowLength * 4;
        this.xAisle = this.xPlane + this.rowLength / 2;
        this.yAisle = this.yPlane + this.rowLength * 7 / 2;

        this.xSeatsAboveAisle = this.xAisle;
        this.ySeatsAboveAisle = this.yPlane + this.rowLength / 2;
        this.xSeatsBelowAisle = this.xAisle;
        this.ySeatsBelowAisle = this.yAisle + this.rowLength;
    }

    private void drawPlane(Graphics2D g2d) {
        Rectangle2D.Double planeRect = new Rectangle2D.Double(xPlane, yPlane, rowLength * (plane.planeLength + 1), rowLength * 8);
        Rectangle2D.Double seatsAboveRect = new Rectangle2D.Double(xSeatsAboveAisle, ySeatsAboveAisle, rowLength * plane.planeLength, rowLength * 3);
        Rectangle2D.Double seatsBelowRect = new Rectangle2D.Double(xSeatsBelowAisle, ySeatsBelowAisle, rowLength * plane.planeLength, rowLength * 3);

        //initialiseGraphics();
        g2d.setColor(Color.white);
        g2d.fill(planeRect);

        g2d.setColor(Color.lightGray);
        g2d.fill(seatsAboveRect);
        g2d.fill(seatsBelowRect);
    }

    private void renderPassengers(Graphics2D g2d) {
        for (int i = 0; i < plane.seatNum; i++) {
            Passenger passenger = plane.passengers[i];
            if (passenger.row < -0.5) continue;

            if (passenger.isSitting()) {
                drawSittingPassenger(g2d, passenger.targetRow, passenger.targetColumn, passenger.PASSENGER_WIDTH);
            } else {
                drawAislePassenger(g2d, passenger.row, passenger.PASSENGER_WIDTH);
            }
        }
    }

    private int rowToX(double row) {
        return (int)(this.xAisle + row * this.rowLength);
    }

    private int columnToY(int column) {
        return ySeatsAboveAisle + column * rowLength + (column >= 3 ? rowLength : 0);
    }

    private void drawSittingPassenger(Graphics2D g2d, int row, int column, double passengerWidth) {
        int widthPixels = (int)(passengerWidth * rowLength);
        int radius = widthPixels / 2;
        int x = rowToX(row) + rowLength/2 - radius;
        int y = columnToY(column) + rowLength/2 - radius;
        drawPassenger(g2d, x, y, widthPixels, new Color(9, 72, 219));
    }

    private void drawAislePassenger(Graphics2D g2d, double row, double passengerWidth) {
        int rowPixels = rowToX(row);
        int widthPixels = (int)(passengerWidth * rowLength);
        int radius = widthPixels / 2;
        drawPassenger(g2d, rowPixels, yAisle + rowLength/2 - radius, widthPixels, Color.red);
    }

    private void drawPassenger(Graphics2D g2d, int x, int y, int diameter, Color colour) {
        Ellipse2D.Double passengerEllipse = new Ellipse2D.Double(x, y, diameter, diameter);
        g2d.setColor(colour);
        g2d.fill(passengerEllipse);
    }
}
