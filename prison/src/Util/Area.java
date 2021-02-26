package Util;
import java.io.Serializable;

public class Area implements Serializable {
    private String area = "";

    Area(String area){
        this.area = area;
    }

    public String toString() {
        return this.area;
    }
}
