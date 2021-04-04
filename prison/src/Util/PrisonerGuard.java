package Util;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PrisonerGuard extends Prisoner{
    private String name;
    public PrisonerGuard(Point2D position, ArrayList<BufferedImage> images, String name) {
        super(position, images);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        int centerX = sprites.get(0).getWidth()/2;
        int centerY = sprites.get(0).getHeight();

        AffineTransform tx = new AffineTransform();
        tx.translate(position.getX() - centerX, position.getY()-centerY);

        String firstname = name.split(" ")[0];

        g2d.setColor(Color.white);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2d.drawString(firstname, (int)position.getX() - centerX, (int)position.getY()+centerY);
    }
}
