package Logic;

import Util.Activity;
import Util.Area;
import Util.Groep;
import Util.Guard;
import file.fileManager;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

import java.io.File;

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
                        activity.getGuard(),activity.getGroep(),activity.getArea());
            }
        } else {
            System.out.println("File is not valid");
        }
    }

    private void clearSchedule(){
        table.getItems().clear();
        schedule.clearActivities();
    }

    public void addItem(Integer startHour, Integer endHour, String name, Guard guard, Groep groep, Area area){
        Activity activity = new Activity();
        activity.setHourStart(startHour);
        activity.setHourEnd(endHour);
        activity.setName(name);
        activity.setGuard(guard);
        activity.setGroep(groep);
        activity.setArea(area);

        table.getItems().add(activity);
        schedule.addActivity(activity);
    }
}
