package Logic;

import Util.Activity;
import Util.Groep;
import Util.Guard;
import Util.Schedule;
import javafx.collections.ObservableList;

public class ScheduleController {

    private Schedule schedule = new Schedule();

    /**
     * @return the schedule
     */
    public Schedule getSchedule(){
        return schedule;
    }

    /**
     * adds activity to schedule
     * @param activity activity to add
     */
    void addActivity(Activity activity){
        schedule.activities.add(activity);
    }

    /**
     * removes activity from schedule
     * @param activity activity to remove
     */
    void removeActivity(Activity activity){
        schedule.activities.remove(activity);
    }

    /**
     * edits the activity in the schedule
     * @param activityOld the old activity
     * @param activityNew the new activity
     */
    void editActivity(Activity activityOld, Activity activityNew) {
        int i = schedule.activities.indexOf(activityOld);
        schedule.activities.remove(i);
        schedule.activities.add(i, activityNew);
    }

    /**
     * @return the prison groups
     */
    public ObservableList<Groep> getPrisonGroeps() {
        return schedule.prisonGroeps;
    }

    /**
     * @return the activity names
     */
    public ObservableList<String> getActivityNames(){
        return schedule.activityNames;
    }

    /**
     * @return the guards
     */
    public ObservableList<Guard> getGuards(){
        return schedule.guards;
    }

    /**
     * clears the activity
     */
    void clearActivities(){
        schedule.clearActivities();
    }
}
