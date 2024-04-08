
public class Plane {
    public static final double SLOW_MOVEMENT_SPEED = 1;
    public static final double MIN_PASSENGER_SPACING = 0.4;
    public int planeLength;
    public int seatNum;
    public Passenger[] passengers;

    public Plane(int planeLength, int[] passengerIndices) {
        this.planeLength = planeLength;
        this.seatNum = planeLength * 6;

        makePassengers(passengerIndices);
    }

    public double passengerSpeed(double x /* distance to passenger in front (seat rows) */) {
        return (0.5 + 1/(1+Math.exp(-x))) * 2 * SLOW_MOVEMENT_SPEED;
    }

    public boolean allPassengersSeated() {
        for (int i = 0; i < seatNum; i++) {
            if (passengers[i].inAisle) return false;
        }
        return true;
    }

    // moves the timeframe forward slightly
    public void update(double dt) {
        int prevIndex = seatNum;

        for (int index = seatNum - 1; index >= 0; index--) {
            Passenger thisPassenger = passengers[index];
            Passenger inFrontPassenger = passengers[prevIndex];

            // this passenger is seated so move to next passenger
            if (!thisPassenger.inAisle) continue;
            prevIndex = index;

            // distances are in row metric
            double distanceToNextPassenger = inFrontPassenger.row - thisPassenger.row - thisPassenger.PASSENGER_WIDTH;
            double distanceToTargetRow = thisPassenger.targetSeat.location.row - thisPassenger.row;

            // still at beginning of simulation where all passengers are too close together
            if (distanceToNextPassenger < MIN_PASSENGER_SPACING) break;

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
                thisPassenger.timeUntilLuggageStored -= storingTime;

                if (thisPassenger.timeUntilLuggageStored == 0) {
                    thisPassenger.sitDown();
                }
            }
        }
    }


    public void makePassengers(int[] passengerIndices) {
        passengers = new Passenger[seatNum + 1];

        for (int i = 0; i < seatNum; i++) {
            int idx = passengerIndices[i];
            Location location = new Location(idx / 6, idx % 6);
            Seat seat = new Seat(location);
            passengers[i] = new Passenger(seat, -1);
        }

        // have an extra head node which acts as a reference to the end of the plane
        passengers[seatNum] = new Passenger(null, seatNum + MIN_PASSENGER_SPACING);
    }
}
