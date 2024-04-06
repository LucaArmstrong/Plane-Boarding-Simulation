public class Seat {
    public Location location;
    public boolean isOccupied;
    public Seat(Location location) {
        this.location = location;
        this.isOccupied = false;
    }

    public void setOccupied() {
        this.isOccupied = true;
    }
}
