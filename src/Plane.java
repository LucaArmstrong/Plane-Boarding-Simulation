import java.util.Random;
import java.util.Arrays;

public class Plane {
    public static final int LUGGAGE_STORE_TIME = 20;
    public static final int SIT_DOWN_TIME = 4;
    public int planeLength;
    public int seatNum;
    public Rendering Render;
    public LinkedList passengers;
    public Plane(int planeLength, int[] passengerIndices) {
        this.planeLength = planeLength;
        this.seatNum = planeLength * 6;
        this.Render = new Rendering();

        makePassengers(passengerIndices);
    }

    // moves the timeframe forward slightly
    public void update(Node node) {
        if (node == null) return;



        update(node.next);
    }

    public void makePassengers(int[] passengerIndices) {
        passengers = new LinkedList();

        for (int i = 0; i < seatNum; i++) {
            int idx = passengerIndices[i];
            Location location = new Location(idx / 6, idx % 6);
            Seat seat = new Seat(location);
            passengers.add(new Passenger(seat, 0));
        }

        // have an extra head node which acts as a reference to the end of the plane
        passengers.add(new Passenger(null, seatNum));
    }
}
