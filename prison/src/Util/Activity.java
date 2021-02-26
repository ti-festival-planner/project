package Util;
import java.io.Serializable;

public class Activity implements Serializable {

    public int hourStart;
    public int hourEnd;
    public String name;
    public Guard guard;
    public Groep groep;
    public Area area;

    public Activity(String name) {
        this.name = name;
    }

    public Activity() {
        this.hourStart = hourStart;
        this.hourEnd = hourEnd;
        this.name = name;
        this.guard = guard;
        this.groep = groep;
        this.area = area;
    }

    public int getHourStart() {
        return hourStart;
    }

    public void setHourStart(int hourStart) {
        this.hourStart = hourStart;
    }

    public int getHourEnd() {
        return hourEnd;
    }

    public void setHourEnd(int hourEnd) {
        this.hourEnd = hourEnd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Guard getGuard() {
        return guard;
    }

    public void setGuard(Guard guard) {
        this.guard = guard;
    }

    public Groep getGroep() {
        return groep;
    }

    public void setGroep(Groep groep) {
        this.groep = groep;
    }


    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

}
