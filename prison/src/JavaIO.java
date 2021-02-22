import Data.Schedule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class JavaIO {

    public static void writeData(File filename, String data) {

        try (PrintWriter printWriter = new PrintWriter(new FileOutputStream(filename, false))) {
            printWriter.println(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readData(File filename, Schedule schedule) {

        try (Scanner scanner = new Scanner(filename)) {

            while (scanner.hasNext()) {
                String text = scanner.next();

//                if (text.equals("Guard")) {
//                    schedule.addGuard(scanner.nextLine());
//                }
//                if (text.equals("Area")) {
//                    schedule.addArea(scanner.nextLine());
//                }
//                if (text.equals("Groep")) {
//                    schedule.addGroup(scanner.nextLine());
//                }
//                if (text.equals("Activity")) {
//                    schedule.addActivity(
//                            scanner.next(),
//                            scanner.next(),
//                            scanner.next(),
//                            scanner.next(),
//                            scanner.nextInt(),
//                            scanner.nextInt(),
//                            scanner.nextInt());
//                }
            }

        } catch(
                Exception e)

        {
            e.printStackTrace();
        }

    }


}
