
public class Plane {
    public final double MIN_PASSENGER_SPACING = 0.2;
    public int planeLength, seatNum;
    public Passenger[] passengers;

    public Plane(int planeLength, int[] passengerIndices) {
        this.planeLength = planeLength;
        this.seatNum = planeLength * 6;

        makePassengers(passengerIndices);
    }

    public boolean allPassengersSeated() {
        for (int i = 0; i < seatNum; i++) {
            if (!passengers[i].isSitting()) return false;
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
            double minimumDistance = inFrontPassenger.LUGGAGE_SPACING + MIN_PASSENGER_SPACING;

            // still at beginning of simulation where all passengers are too close together
            if (distanceToNextPassenger < minimumDistance) break;

            double speed = Passenger.passengerSpeed(distanceToNextPassenger - minimumDistance);
            double potentialDistance = Math.min(distanceToNextPassenger - minimumDistance, speed * dt);
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
