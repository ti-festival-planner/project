package Room;

import java.awt.geom.Point2D;

public abstract class AbstractRoom {

    public Point2D Startcoords;
    public Point2D Endcoords;

    public AbstractRoom(Point2D startcoords, Point2D endcoords) {
        Startcoords = startcoords;
        Endcoords = endcoords;
    }

    public Point2D getCenter(){
        return new Point2D.Double((this.Startcoords.getX()+this.Endcoords.getX())/2, (this.Startcoords.getY()+this.Endcoords.getY())/2);
    }
}
