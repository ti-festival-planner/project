package Room;

import java.awt.geom.Point2D;

public class Cell extends AbstractRoom{
    private Boolean occupied;
    public Cell(Point2D startcoords, Point2D endcoords) {
        super(startcoords, endcoords);
        occupied = false;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }
}
