public class Passenger {
    public Seat targetSeat;
    public double row;
    public boolean inAisle;
    public double timeUntilLuggageStored = LUGGAGE_STORE_TIME;
    public static final int LUGGAGE_STORE_TIME = 20;

    public Passenger(Seat targetSeat, int row) {
        this.targetSeat = targetSeat;
        this.row = row;
        this.inAisle = false;
    }

    public void sitDown() {
        this.inAisle = false;
        this.targetSeat.isOccupied = true;
    }
}
