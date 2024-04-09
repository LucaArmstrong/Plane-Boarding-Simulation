
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
            if (passengers[i].isSitting() == false) return false;
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
            if (thisPassenger.isSitting()) continue;
            prevIndex = index;

            // distances are in row metric
            double distanceToNextPassenger = inFrontPassenger.row - thisPassenger.row - thisPassenger.PASSENGER_WIDTH;
            double distanceToTargetRow = thisPassenger.targetRow - thisPassenger.row;

            // still at beginning of simulation where all passengers are too close together
            if (distanceToNextPassenger < MIN_PASSENGER_SPACING) break;

            double speed = passengerSpeed(distanceToNextPassenger - MIN_PASSENGER_SPACING);
            double potentialDistance = Math.min(distanceToNextPassenger - MIN_PASSENGER_SPACING, speed * dt);
            double timeRemaining = dt;

            // hasn't yet reached their target row
            if (thisPassenger.isBoarding()) {
                if (potentialDistance < distanceToTargetRow) {
                    // still walking in aisle
                    thisPassenger.row += potentialDistance;
                    continue;
                }

                // walk to target row
                thisPassenger.row = thisPassenger.targetRow;
                timeRemaining -= distanceToTargetRow / speed;
                thisPassenger.stowLuggage();
            }

            if (thisPassenger.isStowing()) {
                if (timeRemaining < thisPassenger.timeUntilLuggageStored) {
                    // still stowing luggage
                    thisPassenger.timeUntilLuggageStored -= timeRemaining;
                    continue;
                }

                // finish stowing luggage, take seat
                timeRemaining -= thisPassenger.timeUntilLuggageStored;
                thisPassenger.timeUntilLuggageStored = 0;
                thisPassenger.takeSeat();
            }

            if (thisPassenger.isTakingSeat()) {
                if (timeRemaining < thisPassenger.timeUntilSitting) {
                    // still taking seat
                    thisPassenger.timeUntilSitting -= timeRemaining;
                    continue;
                }

                // finish taking seat, is now sitting
                timeRemaining -= thisPassenger.timeUntilSitting;
                thisPassenger.timeUntilSitting = 0;
                thisPassenger.sitDown();
            }
        }
    }


    public void makePassengers(int[] passengerIndices) {
        passengers = new Passenger[seatNum + 1];

        for (int i = 0; i < seatNum; i++) {
            int idx = passengerIndices[i];
            passengers[i] = new Passenger(idx / 6, idx % 6, -1);
        }

        // have an extra head node which acts as a reference to the end of the plane
        passengers[seatNum] = new Passenger(0, 0, seatNum + MIN_PASSENGER_SPACING);
    }
}
