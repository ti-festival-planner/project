package Util;

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
}
