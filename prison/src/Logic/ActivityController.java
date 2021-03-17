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


    public ActivityController(TableView table){
        this.table = table;
    }

    public void saveSelectedFile(FileChooser fileChooser) {
        //Set extension filter for text files


        //Show save file dialog
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            fileManager.writeData(file, this.schedule.getSchedule());
//            saveTextToFile(sampleText, file);
        } else {
            System.out.println("Cancelled");
        }
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

    private void clearSchedule(){
        table.getItems().clear();
        schedule.clearActivities();
    }

    public void deleteItem(Activity activity){
        table.getItems().remove(activity);
        schedule.removeActivity(activity);
    }

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

    public void addItem(Integer startHour, Integer endHour, String name, Guard guard, Groep groep){
        Activity activity = new Activity();
        activity.setHourStart(startHour);
        activity.setHourEnd(endHour);
        activity.setName(name);
        activity.setGuard(guard);
        activity.setGroep(groep);
        Activity check = checkOverlap(activity);
        if (name == null||guard== null||groep == null)
            throw new IllegalArgumentException("Gegevens missend");

        if (endHour < startHour)
            throw new IllegalArgumentException("Begin later dan einde");

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

    public Activity checkOverlap(Activity activity){
        ArrayList<Activity> activities = schedule.getSchedule().activities;
        for (Activity activity1 : activities){
            if (activity1.getGroep() == activity.getGroep()){
                if (activity1.getHourEnd() > activity.getHourStart())
                    return activity1;
            }

            if (activity.getGuard() == activity1.getGuard()){
                if (activity1.getHourEnd() > activity.getHourStart())
                    return activity1;
            }
        }
        return null;
    }
    public Activity checkOverlapEdit(Activity activity, Activity oldActivity){
        ArrayList<Activity> activities = schedule.getSchedule().activities;
        for (Activity activity1 : activities) {
            if (activity1 != oldActivity) {
                if (activity1.getGroep() == activity.getGroep()) {
                    if (activity1.getHourEnd() > activity.getHourStart())
                        return activity1;
                }

                if (activity.getGuard() == activity1.getGuard()) {
                    if (activity1.getHourEnd() > activity.getHourStart())
                        return activity1;
                }
            }
        }
        return null;
    }
}
