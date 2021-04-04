package Util;
import java.io.Serializable;

public class Activity implements Serializable {

    private int hourStart;
    private int hourEnd;
    private String name;
    private Guard guard;
    private Groep groep;

    /**
     * Constructs the activity object
     * @param hourStart the starthour for the item
     * @param hourEnd the endhour for the item
     * @param name the name of the item
     * @param guard the guard assigned to the item
     * @param groep the groep assigned to the item
     */
    public Activity(int hourStart, int hourEnd, String name, Guard guard, Groep groep) {
        this.hourStart = hourStart;
        this.hourEnd = hourEnd;
        this.name = name;
        this.guard = guard;
        this.groep = groep;
    }

    /**
     * Getters
     */
    public int getHourStart() {
        return hourStart;
    }
    public int getHourEnd() {
        return hourEnd;
    }
    public String getName() {
        return name;
    }
    public Guard getGuard() {
        return guard;
    }
    public Groep getGroep() {
        return groep;
    }

    /**
     * Setters
     */
    public void setHourStart(int hourStart) {
        this.hourStart = hourStart;
    }
    public void setHourEnd(int hourEnd) {
        this.hourEnd = hourEnd;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setGuard(Guard guard) {
        this.guard = guard;
    }
    public void setGroep(Groep groep) {
        this.groep = groep;
    }

    /**
     * Methods
     */
    public boolean isNow(int hour) {
        return hour>=this.hourStart && hour<=this.hourEnd;
    }
}
