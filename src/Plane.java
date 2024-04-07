
public class Plane {
    public static final double SLOW_MOVEMENT_SPEED = 1;
    public static final double MIN_PASSENGER_SPACING = 0.4;
    public int planeLength;
    public int seatNum;
    public PassengerList aislePassengers;

    public Plane(int planeLength, int[] passengerIndices) {
        this.planeLength = planeLength;
        this.seatNum = planeLength * 6;

        makePassengers(passengerIndices);
    }

    public double passengerSpeed(double x /* distance to passenger in front (seat rows) */) {
        return (0.5 + 1/(1+Math.exp(-x))) * 2 * SLOW_MOVEMENT_SPEED;
    }

    public boolean allPassengersSeated() {
        return aislePassengers.head.next == null;
    }

    // moves the timeframe forward slightly
    public void update(Node passengerNode, double dt) {
        if (passengerNode == null) return;   // only need this for start condition when only one passenger is unseated
        if (passengerNode.next == null) return;

        Passenger thisPassenger = passengerNode.next.passenger;
        Passenger inFrontPassenger = passengerNode.passenger;

        double distanceToNextPassenger = inFrontPassenger.row - thisPassenger.row - thisPassenger.PASSENGER_WIDTH;
        double distanceToTargetRow = thisPassenger.targetSeat.location.row - thisPassenger.row;

        // still at beginning of simulation where all passengers are too close together
        if (distanceToNextPassenger < MIN_PASSENGER_SPACING) return;

        double speed = passengerSpeed(distanceToNextPassenger - MIN_PASSENGER_SPACING);
        double potentialDistance = Math.min(distanceToNextPassenger - MIN_PASSENGER_SPACING, speed * dt);
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
                aislePassengers.delete(thisPassenger);
            }
        }

        update(passengerNode.next, dt);
    }

    public void makePassengers(int[] passengerIndices) {
        aislePassengers = new PassengerList();

        for (int i = 0; i < seatNum; i++) {
            int idx = passengerIndices[i];
            Location location = new Location(idx / 6, idx % 6);
            Seat seat = new Seat(location);
            aislePassengers.add(new Passenger(seat, -1));
        }

        // have an extra head node which acts as a reference to the end of the plane
        aislePassengers.add(new Passenger(null, seatNum + MIN_PASSENGER_SPACING));
    }
}
