package Util;

import GUI.Simulator;

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

    private boolean panic;

    private ArrayList<BufferedImage> sprites;
    private Point2D target;
    Prisoner(Point2D position, ArrayList<BufferedImage> images) {
        this.position = position;
        this.angle = 0;
        this.speed = 90;
        this.target = position;
        this.direction = new Point2D.Double(0,0);
        this.sprites = images;
        this.panic = false;
    }

    public void update(double deltaTime, ArrayList<Prisoner> prisoners){
        double xDir = this.target.getX() - this.position.getX();
        double yDir = this.target.getY() - this.position.getY();
        if (Math.abs(xDir) < 4) xDir = 0;
        if (Math.abs(yDir) < 4) yDir = 0;
        this.direction = new Point2D.Double(
            Math.signum(xDir)*speed* Simulator.speed * deltaTime,
            Math.signum(yDir)*speed* Simulator.speed * deltaTime
        );

        Point2D newPosition = new Point2D.Double(
            this.position.getX()+this.direction.getX(),
            this.position.getY()+this.direction.getY()
        );

        int collisionCount = 1;
        for (Prisoner prisoner : prisoners){
            if (prisoner == this) continue;
            if (prisoner.position.distanceSq(newPosition) < 64*64) collisionCount++;
        }
        if (collisionCount > 1) {
            if (Math.random()>=0.91) this.panic = true;
            newPosition = new Point2D.Double(
                this.position.getX()+(this.direction.getX()/Math.pow(collisionCount, 2)),
                this.position.getY()+(this.direction.getY()/Math.pow(collisionCount, 2))
            );
        }
        if (this.panic) {
            if (collisionCount > 1) {
                newPosition = new Point2D.Double(
                    this.position.getX()+this.direction.getX(),
                    this.position.getY()+this.direction.getY()
                );
                this.panic = false;
            } else {
                this.panic = false;
            }
        }
        if (this.position.distanceSq(this.target) >= 64) this.position = newPosition;
    }

    public void draw(Graphics2D g2d){
        int centerX = sprites.get(0).getWidth()/2;
        int centerY = sprites.get(0).getHeight()/2;

        debugDraw(g2d);

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
