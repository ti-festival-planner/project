package Logic;

import Util.Activity;
import Util.Groep;
import Util.Guard;
import file.fileManager;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.ArrayList;

public class ActivityController {

    private TableView<Activity> table;
    private ScheduleController schedule = new ScheduleController();

    /**
     * @param table the table to add and remove data from.
     */
    public ActivityController(TableView table){
        this.table = table;
    }

    /**
     * saveSelectedFile saves data to selected file.
     * @param fileChooser the filechooser to use
     */
    public void saveSelectedFile(FileChooser fileChooser) {
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            fileManager.writeData(file, this.schedule.getSchedule());
        } else {
            System.out.println("Cancelled");
        }
    }

    public ScheduleController getSchedule(){
        return schedule;
    }

    public void getSelectedFile(FileChooser fileChooser) {
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null){
            System.out.println(selectedFile);
            clearSchedule();
            for(Activity activity: fileManager.readData(selectedFile)){
                addItem(activity.getHourStart(),activity.getHourEnd(),activity.getName(),
                        activity.getGuard(),activity.getGroep());
            }
        } else {
            System.out.println("File is not valid");
        }
    }

    /**
     * clearSchedule clears the schedule.
     */
    private void clearSchedule(){
        table.getItems().clear();
        schedule.clearActivities();
    }

    /**
     * deleteItem deletes item from the table and schedule
     * @param activity activity to delete
     */
    public void deleteItem(Activity activity){
        table.getItems().remove(activity);
        schedule.removeActivity(activity);
    }

    /**
     * editItem edits an item in the table/schedule
     * @param activityOld old item to be replaced
     * @param activityNew new item to replace with
     */
    public void editItem(Activity activityOld, Activity activityNew) {
        Activity check = checkOverlapEdit(activityNew, activityOld);
        if (check == null) {
            schedule.editActivity(activityOld, activityNew);
            table.getItems().add(table.getItems().indexOf(activityOld), activityNew);
            table.getItems().remove(activityOld);
        } else {
            Alert alertOverlap = new Alert(Alert.AlertType.ERROR);
            alertOverlap.setTitle("Overlap");
            alertOverlap.setHeaderText("De activiteit overlapt met onderstaande activiteit:");
            alertOverlap.setContentText("Activiteit: "+check.getName()+"\n"+
                    "Groep: "+check.getGroep()+"\n"+
                    "Guard: "+check.getGuard()+"\n"+
                    "Start uur: "+check.getHourStart()+"\n"+
                    "Eind uur: "+check.getHourEnd()+"\n");
            alertOverlap.showAndWait();
        }
    }

    /**
     * addItem adds an item to the table/schedule
     * @param startHour the starthour for the item
     * @param endHour the endhour for the item
     * @param name the name of the item
     * @param guard the guard assigned to the item
     * @param groep the groep assigned to the item
     */
    public void addItem(Integer startHour, Integer endHour, String name, Guard guard, Groep groep){
        Activity activity = new Activity(startHour, endHour, name, guard, groep);
        Activity check = checkOverlap(activity);
        if (name == null||guard== null||groep == null)
            throw new IllegalArgumentException("Sorry, we missen wat gegevens bij het aanmaken van de activiteit, heb je alles ingevuld?");
        if (endHour > 23 || startHour > 23 ){
            throw  new IllegalArgumentException("Je kan maximaal een tijd gebruiken tot en met 23");
        }
        if (endHour < startHour) {
            throw new IllegalArgumentException("De activiteit die je aanmaakt is niet mogelijk, het begin ligt later dan het einde.");
        }
        if (check == null) {
            table.getItems().add(activity);
            schedule.addActivity(activity);
        } else {
            throw new IllegalArgumentException("De activiteit overlapt met onderstaande activiteit:" +"\nActiviteit: "+check.getName()+"\n"+
                    "Groep: "+check.getGroep()+"\n"+
                    "Guard: "+check.getGuard()+"\n"+
                    "Start uur: "+check.getHourStart()+"\n"+
                    "Eind uur: "+check.getHourEnd()+"\n");
        }
    }

    /**
     * checkOverlap checks the overlap between activity's
     * @param activity the activity to check
     * @return null if no conflict, the activity when there is a conflict
     */
    private Activity checkOverlap(Activity activity){
        ArrayList<Activity> activities = schedule.getSchedule().activities;
        for (Activity activity1 : activities){
            if (activity1.getGroep() == activity.getGroep()){
                if (activity1.getHourStart() >= activity.getHourEnd() || activity1.getHourEnd() >= activity.getHourStart())
                    return activity1;
            }

            if (activity.getGuard() == activity1.getGuard()){
                if (activity1.getHourStart() >= activity.getHourEnd() || activity1.getHourEnd() >= activity.getHourStart())
                    return activity1;
            }
        }
        return null;
    }

    /**
     * checkOverlapEdit checks overlap like checkOverlap but for edits
     */
    private Activity checkOverlapEdit(Activity activity, Activity oldActivity){
        ArrayList<Activity> activities = schedule.getSchedule().activities;
        for (Activity activity1 : activities) {
            if (activity1 != oldActivity) {
                if (activity1.getGroep() == activity.getGroep()) {
                    if (activity1.getHourStart() >= activity.getHourEnd() || activity1.getHourEnd() >= activity.getHourStart())
                        return activity1;
                }
                if (activity.getGuard() == activity1.getGuard()) {
                    if (activity1.getHourStart() >= activity.getHourEnd() || activity1.getHourEnd() >= activity.getHourStart())
                        return activity1;
                }
            }
        }
        return null;
    }
}
