import java.awt.*;
import java.awt.geom.*;
import javax.swing.JComponent;

public class DrawingPanel extends JComponent {
    public Graphics2D g2d;
    public int width, height, planeLength, rowLength;
    public int xPlane, yPlane, xAisle, yAisle;
    public int xSeatsAboveAisle, ySeatsAboveAisle, xSeatsBelowAisle, ySeatsBelowAisle;

    @Override
    public void paint (Graphics g) {}

    public DrawingPanel(int width, int height, int planeLength) {
        this.width = width;
        this.height = height;
        this.planeLength = planeLength;

        initPlaneValues();
        initialiseGraphics();
    }

    private void initialiseGraphics() {
        this.g2d = (Graphics2D) super.getGraphics();
    }

    private void initPlaneValues() {
        // need one row length on either side of the plane
        // plus a half a row length border round the inside of the plane
        this.rowLength = this.width / (this.planeLength + 3);

        // aisle width is one row length
        // plus 6 seats and a half row length border makes a height of 8 row lengths
        this.xPlane = this.rowLength;
        this.yPlane = this.height/2 - this.rowLength * 4;
        this.xAisle = this.xPlane + this.rowLength/2;
        this.yAisle = this.yPlane + this.rowLength * 7/2;

        this.xSeatsAboveAisle = this.xAisle;
        this.ySeatsAboveAisle = this.yPlane + this.rowLength/2;
        this.xSeatsBelowAisle = this.xAisle;
        this.ySeatsBelowAisle = this.yAisle + this.rowLength;
    }

    public void drawPlane() {
        Rectangle2D.Double planeRect = new Rectangle2D.Double(xPlane, yPlane, rowLength * (planeLength + 1), rowLength * 8);
        Rectangle2D.Double seatsAboveRect = new Rectangle2D.Double(xSeatsAboveAisle, ySeatsAboveAisle, rowLength * planeLength, rowLength * 3);
        Rectangle2D.Double seatsBelowRect = new Rectangle2D.Double(xSeatsBelowAisle, ySeatsBelowAisle, rowLength * planeLength, rowLength * 3);

        initialiseGraphics();
        g2d.setColor(Color.cyan);
        g2d.fill(planeRect);

        g2d.setColor(Color.lightGray);
        g2d.fill(seatsAboveRect);
        g2d.fill(seatsBelowRect);
    }

    public int rowToX(double row) {
        return (int)(this.xAisle + row * this.rowLength);
    }

    public int columnToY(int column) {
        return ySeatsAboveAisle + column * rowLength + (column >= 3 ? rowLength : 0);
    }

    public void drawSittingPassenger(int row, int column, double passengerWidth) {
        int widthPixels = (int)(passengerWidth * rowLength);
        int radius = widthPixels / 2;
        int x = rowToX(row) + rowLength/2 - radius;
        int y = columnToY(column) + rowLength/2 - radius;
        drawPassenger(x, y, widthPixels, Color.green);
    }

    public void drawAislePassenger(double row, double passengerWidth) {
        int rowPixels = rowToX(row);
        int widthPixels = (int)(passengerWidth * rowLength);
        int radius = widthPixels / 2;
        drawPassenger(rowPixels, yAisle + rowLength/2 - radius, widthPixels, Color.red);
    }

    public void drawPassenger(int x, int y, int diameter, Color colour) {
        Ellipse2D.Double passengerEllipse = new Ellipse2D.Double(x, y, diameter, diameter);
        initialiseGraphics();
        g2d.setColor(colour);
        g2d.fill(passengerEllipse);
    }
}
