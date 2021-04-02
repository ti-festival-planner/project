package Util;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Prisoner {

    private Point2D position;
    private Point2D direction;

    private double angle;
    private double speed;

    private ArrayList<BufferedImage> sprites;
    private Point2D target;
    Prisoner(Point2D position, ArrayList<BufferedImage> images) {
        this.position = position;
        this.angle = 0;
        this.speed = 90;
        this.target = position;
        this.direction = new Point2D.Double(0,0);
        this.sprites = images;
    }

    public void update(double deltaTime){
        this.direction = new Point2D.Double(
                Math.signum(this.target.getX() - this.position.getX())*speed*deltaTime,
                Math.signum(this.target.getY() - this.position.getY())*speed*deltaTime
        );

        Point2D newPosition = new Point2D.Double(
                this.position.getX()+this.direction.getX(),
                this.position.getY()+this.direction.getY()
        );

        this.position = newPosition;
    }

    public void draw(Graphics2D g2d){
        int centerX = sprites.get(0).getWidth()/2;
        int centerY = sprites.get(0).getHeight()/2;

        AffineTransform tx = new AffineTransform();
        tx.translate(position.getX() - centerX, position.getY()-centerY);
        g2d.drawImage(this.sprites.get(1), tx,null);
    }

    public void setTarget(Point2D newTarget) {
//        this.target = newTarget;
        this.target = new Point2D.Double(newTarget.getX()*2, newTarget.getY()*2);
    }

    public void debugDraw(Graphics2D g2d) {
        Shape cursor = new Ellipse2D.Double(this.target.getX()-16, this.target.getY()-16, 32, 32);
        g2d.draw(cursor);
    }

}
