package file;

import Util.Activity;
import Util.Schedule;

import java.io.*;
import java.util.ArrayList;

public class fileManager {


    public static void writeData(File file, Schedule schedule) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file.getAbsolutePath()))) {
            output.writeObject(schedule.getActivities());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
