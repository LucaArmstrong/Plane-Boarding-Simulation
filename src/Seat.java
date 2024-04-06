class Location {
    public int row, column;
    public Location(int row, int column) {
        this.row = row;
        this.column = column;
    }
}

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
