public class Passenger {
    public Seat targetSeat;
    public double row;
    public boolean inAisle;
    public double timeUntilLuggageStored;

    public Passenger(Seat targetSeat, int row) {
        this.targetSeat = targetSeat;
        this.row = row;
        this.inAisle = false;
    }

    public boolean hasReachedSeatRow() {
        return targetSeat.location.row <= this.row;  // current row is further to the right of the target row
    }

    public int storeLuggage() {
        //return LUGGAGE_STORE_TIME;
        return 0;
    }

    public int sitDown() {
        this.targetSeat.isOccupied = true;
        //return SIT_DOWN_TIME;
        return 0;
    }
}
