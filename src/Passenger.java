public class Passenger {
    public int targetRow, targetColumn;
    public double row;
    public boolean hasLuggage;
    public double timeUntilLuggageStored, timeUntilSitting;
    public double PASSENGER_WIDTH, LUGGAGE_SPACING;
    private final double LUGGAGE_PROBABILITY = 0.85;
    private final double LUGGAGE_STORE_TIME = 15; // 20 seconds
    private final double SITTING_TIME = 2; // 2 seconds to get out of aisle
    private static final double SLOW_MOVEMENT_SPEED = 1;  // in rows per second

    private enum State {
        BOARDING, STOWING, TAKING_SEAT, SITTING
    }
    private State state;

    public Passenger(int targetRow, int targetColumn, double row) {
        this.targetRow = targetRow;
        this.targetColumn = targetColumn;
        this.row = row;
        this.state = State.BOARDING;

        this.PASSENGER_WIDTH = 0.6; //random.nextDouble(0.5, 0.7);
        this.timeUntilSitting = SITTING_TIME;

        this.hasLuggage = (Math.random() < LUGGAGE_PROBABILITY);
        this.timeUntilLuggageStored = this.hasLuggage ? LUGGAGE_STORE_TIME : 0;
        this.LUGGAGE_SPACING = this.hasLuggage ? 0.3 : 0;
    }

    public boolean isBoarding() {
        return this.state == State.BOARDING;
    }

    public boolean isStowing() {
        return this.state == State.STOWING;
    }

    public boolean isTakingSeat() {
        return this.state == State.TAKING_SEAT;
    }

    public boolean isSitting() {
        return this.state == State.SITTING;
    }

    public void takeSeat() {
        this.state = State.TAKING_SEAT;
    }

    public void sitDown() {
        this.state = State.SITTING;
    }

    public void stowLuggage() {
        this.state = State.STOWING;
    }

    public static double passengerSpeed(double x /* distance to passenger in front (seat rows) */) {
        return (0.5 + 1/(1+Math.exp(-x))) * 2 * SLOW_MOVEMENT_SPEED;
    }
}
