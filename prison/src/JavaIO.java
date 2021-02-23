import Data.Schedule;

import java.io.*;

public class JavaIO {

    public static void writeData(String filename, Schedule schedule) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename))) {
            output.writeObject(schedule);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Schedule readData(File file) {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file.getName()))) {
            return (Schedule)(input.readObject());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
