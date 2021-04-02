package Util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Prisoner {

    private Point2D position;

    private double angle;
    private double speed;

    private ArrayList<BufferedImage> sprites;
    private Point2D target;
    public Prisoner(Point2D position, double angle) {
        this.position = position;
        this.angle = angle;
        this.speed = 3;
        this.target = position;
        this.sprites = new ArrayList<>();

        try {
            File image2 = new File("./resources/prisoner.png");
            BufferedImage image = ImageIO.read(image2);
            int w = image.getWidth()/3;
            int h = image.getHeight();
            for (int y = 0; y < 1; y++){
                for (int x = 0; x< 3; x++){
                    this.sprites.add(image.getSubimage(x*w,y* h,w,h));
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public  void update(){
        double targetAngle = Math.atan2(this.target.getY() - this.position.getY(),this.target.getX() - this.position.getX());
        double rotation = targetAngle - this.angle;
        while (rotation < -Math.PI){
            rotation += Math.PI;
        }
        while (rotation > Math.PI){
            rotation -= Math.PI;
        }
        if (rotation< 0){
            this.angle -= 0.1;
        } else if (rotation > 0){
            this.angle += 0.1;
        }

        this.position = new Point2D.Double(
                this.position.getX() + this.speed * Math.sin(this.angle),
                this.position.getY() + this.speed * Math.cos(this.angle));

    }

    public void draw(Graphics2D g2d){

        int centerX = sprites.get(0).getWidth()/2;
        int centerY = sprites.get(0).getHeight()/2;

        AffineTransform tx = new AffineTransform();
        tx.translate(position.getX() - centerX, position.getY()-centerY);

        tx.rotate(angle + Math.PI/2, centerX, centerY);
        g2d.drawImage(this.sprites.get(1), tx,null);
    }

    public void setTarget(Point2D newTarget) {
        this.target = newTarget;
    }
}
