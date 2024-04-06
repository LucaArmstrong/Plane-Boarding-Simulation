public class Passenger {
    public Seat targetSeat;
    public double row;
    public boolean inAisle;
    public double timeUntilLuggageStored = LUGGAGE_STORE_TIME;
    public static final int LUGGAGE_STORE_TIME = 20;

    /* width of a passenger */
    /* have the option in the future to add variable (random) widths as well as random speeds for a more accurate simulation */
    public static final double PASSENGER_WIDTH = 0.8;

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
