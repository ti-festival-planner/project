package Logic;

import Util.*;
import javafx.collections.ObservableList;

public class ScheduleController {

    private Schedule schedule = new Schedule();

    public Schedule getSchedule(){
        return schedule;
    }

    public void addActivity(Activity activity){
        schedule.activities.add(activity);
    }

    public ObservableList<Groep> getPrisonGroeps() {
        return schedule.prisonGroeps;
    }



    public ObservableList<String> getActivityNames(){
        return schedule.activityNames;
    }
    public ObservableList<Area> getAreas(){
        return schedule.areas;
    }
    public ObservableList<Guard> getGuards(){
        return schedule.guards;
    }

    public void clearActivities(){
        schedule.clearActivities();
    }

}
