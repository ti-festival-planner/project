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
    private ArrayList<BufferedImage> sprites;

    public Prisoner(Point2D position, double angle) {
        this.position = position;
        this.angle = angle;
        this.sprites = new ArrayList<>();
        try {
            File image2 = new File("./resources/prisoner.png");
            BufferedImage image = ImageIO.read(image2);
            int w = image.getWidth()/3;
            int h = image.getHeight();
            for (int y = 0; y < 1; y++){
                for (int x = 0; x< 3; x++){
                    this.sprites.add(image.getSubimage(x*w,y*h,w,h));
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public  void update(){

    }

    public void draw(Graphics2D g2d){
        AffineTransform tx = new AffineTransform();
        g2d.drawImage(this.sprites.get(1), tx,null);
    }
}
