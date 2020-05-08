
import Manager.FileManager;

import java.io.IOException;
import java.util.Timer;

import static java.lang.System.exit;

public class App {
    public static void main(String[] args) throws InterruptedException {
        try {
        Boolean isOk = FileManager.setDaysAtTray();
        }
        catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
            new Timer().wait(1000);
            exit(1);
        }
        catch (Exception e) {
            System.out.println("Unknown error" + e.getMessage());
            new Timer().wait(1000);
            exit(1);
        }
    }
}
