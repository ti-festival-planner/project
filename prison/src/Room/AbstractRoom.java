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
        double x1 = (this.Startcoords.getX()+6000)/2;
        double x2 = (this.Endcoords.getX()+6000)/2;
        double y1 = this.Startcoords.getY()/2;
        double y2 = this.Endcoords.getY()/2;
        return new Point2D.Double((x1+x2)/2, (y1+y2)/2);
    }

    public Point2D getPlace() {

        double x1 = (this.Startcoords.getX()+6000)/2;
        double x2 = (this.Endcoords.getX()+6000)/2;
        double y1 = this.Startcoords.getY()/2;
        double y2 = this.Endcoords.getY()/2;

        double xoffset = (x2-x1)*Math.random();
        double yoffset = (y2-y1)*Math.random();

        return new Point2D.Double(
                x1+xoffset,
                y1+yoffset
        );
    }
}