package Data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Schedule {
    public ArrayList<Activity> activities = new ArrayList<>();
    public ObservableList<String> activityNames = FXCollections.observableArrayList();
    public ObservableList<Guard> guards = FXCollections.observableArrayList();
    public ObservableList<Area> areas = FXCollections.observableArrayList();
    public ObservableList<Groep> prisonGroeps = FXCollections.observableArrayList();

    public Schedule(){
        {
            Guard Johan = new Guard("Johan Talboom");
            Guard etienne = new Guard("Ethiënne Goossens");

            guards.add(Johan);
            guards.add(etienne);

            activityNames.add("Sleep");
            activityNames.add("Eat");
            activityNames.add("Shower");
            activityNames.add("Free Time");
            activityNames.add("Work");
            activityNames.add("Yard");
            activityNames.add("Lock up");

            Area canteenA= new Area("Canteen A");
            Area canteenB = new Area("Canteen A");
            Area canteenC = new Area("Canteen A");
            Area cellBlockA = new Area("Cellblock A");
            Area workPlace = new Area("Workspace A");

            areas.addAll(canteenA,canteenB,canteenC);
            areas.add(cellBlockA);
            areas.add(workPlace);

            Groep lowSecurity = new Groep("low security");
            Groep mediumSecurity = new Groep("Medium security");
            Groep highSecurity = new Groep("High security");

            prisonGroeps.add(lowSecurity);
            prisonGroeps.add(mediumSecurity);
            prisonGroeps.add(highSecurity);
        }
    }

//    public ArrayList<Activity> getActivitiesForWarden(Guard guard){
//        ArrayList<Activity> act = new ArrayList<>();
//        for (Activity activity : activities) {
//            if (activity.getGuards().contains(guard)){
//                act.add(activity);
//            }
//            System.out.println(activities);
//        }
//        return act;
//    }
//
//    public ArrayList<Activity> getActivitiesForGroup(Groep groep){
//        ArrayList<Activity> act = new ArrayList<>();
//        for (Activity activity : activities) {
//            if (activity.getGroeps().contains(groep)){
//                act.add(activity);
//            }
//        }
//        return act;
//    }

    public ObservableList<Groep> getPrisonGroeps() {
        return prisonGroeps;
    }

    public void setPrisonGroeps(ObservableList<Groep> prisonGroeps) {
        this.prisonGroeps = prisonGroeps;
    }

}
