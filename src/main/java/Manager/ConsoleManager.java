package Manager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ConsoleManager {
    public static Date getDate() {
        SimpleDateFormat sdf = Service.getDateFormat();
        System.out.println("Enter new date("+sdf.toPattern()+"):");
        Scanner scanner = new Scanner(System.in);
        String strDate = scanner.next();
        try {
            Date date = sdf.parse(strDate);
            String verificationString = sdf.format(date);
            if (!verificationString.equals(strDate)) {
                System.out.println("Incorrect date format");
                return null;
            }
            return date;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
