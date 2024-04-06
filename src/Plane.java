import java.util.Random;
import java.util.Arrays;

public class Plane {
    public static final int LUGGAGE_STORE_TIME = 20;
    public static final double SLOW_MOVEMENT_SPEED = 1.5;
    public int planeLength;
    public int seatNum;
    public Rendering Render;
    public LinkedList passengers;
    public Plane(int planeLength, int[] passengerIndices) {
        this.planeLength = planeLength;
        this.seatNum = planeLength * 6;

        makePassengers(passengerIndices);
    }

    public double passengerSpeed(double x /* distance to passenger in front (seat rows) */) {
        return (1 - 1/(1+Math.exp(-x))) * 2.5 * SLOW_MOVEMENT_SPEED;
    }

    public boolean allPassengersSeated() {
        return passengers.head.next == null;
    }

    // moves the timeframe forward slightly
    public void update(Node node, double dt) {
        if (node.next == null) return;

        Passenger thisPassenger = node.next.passenger;
        Passenger inFrontPassenger = node.passenger;

        double distanceToNextPassenger = inFrontPassenger.row - thisPassenger.row;
        double distanceToTargetRow = thisPassenger.targetSeat.location.row - thisPassenger.row;

        // still at beginning of simulation where all passengers are on the same row
        if (distanceToNextPassenger < thisPassenger.PASSENGER_WIDTH) return;

        double speed = passengerSpeed(distanceToNextPassenger);
        double potentialDistance = Math.min(distanceToNextPassenger, speed * dt);
        double timeRemaining = dt;

        // do walking
        if (potentialDistance < distanceToTargetRow) {
            // still walking in the aisle
            thisPassenger.row += potentialDistance;
        } else {
            // walk to row
            thisPassenger.row += distanceToTargetRow;
            timeRemaining -= distanceToTargetRow / speed;

            // time left to store luggage/sit down
            double storingTime = Math.min(thisPassenger.timeUntilLuggageStored, timeRemaining);
            if (thisPassenger.timeUntilLuggageStored > timeRemaining) {
                thisPassenger.timeUntilLuggageStored -= timeRemaining;
            } else {
                thisPassenger.timeUntilLuggageStored = 0;
                thisPassenger.sitDown();
            }
        }

        update(node.next, dt);
    }

    public void makePassengers(int[] passengerIndices) {
        passengers = new LinkedList();

        for (int i = 0; i < seatNum; i++) {
            int idx = passengerIndices[i];
            Location location = new Location(idx / 6, idx % 6);
            Seat seat = new Seat(location);
            passengers.add(new Passenger(seat, -1));
        }

        // have an extra head node which acts as a reference to the end of the plane
        passengers.add(new Passenger(null, seatNum));
    }
}
