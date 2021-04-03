package Util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Schedule implements Serializable {

    public ArrayList<Activity> activities = new ArrayList<>();
    public ObservableList<String> activityNames = FXCollections.observableArrayList();
    public ObservableList<Guard> guards = FXCollections.observableArrayList();
    public ObservableList<Groep> prisonGroeps = FXCollections.observableArrayList();

    /**
     * Constructs a schedule object
     */
    public Schedule() {
            guards.addAll(
                new Guard("Johan Talboom"),
                new Guard("Ethiënne Goossens"),
                new Guard("Edwin van der Geld")
            );

            activityNames.add("Sleep");
            activityNames.add("Eat");
            activityNames.add("Shower");
            activityNames.add("Free Time");
            activityNames.add("Work");
            activityNames.add("Yard");
            activityNames.add("Lock up");

            prisonGroeps.addAll(
                new Groep("low security"),
                new Groep("Medium security"),
                new Groep("High security")
            );

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(getActivities(), schedule.getActivities());
    }

    /**
     * @return the activity's
     */
    public ArrayList<Activity> getActivities (){
        return activities;
    }

    /**
     * clears the activity's in the schedule.
     */
    public void clearActivities(){
        activities.clear();
    }
}
