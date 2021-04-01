package file;

import Util.Activity;
import Util.Schedule;
import java.io.*;
import java.util.ArrayList;

public class fileManager {

    /**
     * writeData saves the schedule to a disk on the filesystem for later use.
     * @param file the file to write the data to
     * @param schedule the data to write to the filesystem
     */
    public static void writeData(File file, Schedule schedule) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file.getAbsolutePath()))) {
            output.writeObject(schedule.getActivities());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * readData reads the data from the filesystem.
     * @param file the file from which to read the data.
     * @return returns an arraylist with activity's
     */
    public static ArrayList<Activity> readData(File file) {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()))) {
            return (ArrayList<Activity>) (input.readObject());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
