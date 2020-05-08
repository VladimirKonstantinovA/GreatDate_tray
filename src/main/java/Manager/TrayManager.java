package Manager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;

public class TrayManager {

    public static TrayIcon initTray(String daysLeft) {
        if (!SystemTray.isSupported()) return null;
        SystemTray systemTray = SystemTray.getSystemTray();
        TrayIcon trayIcon = configureTrayIcon(systemTray, daysLeft);
        if (trayIcon == null) return trayIcon;
        ImageManager.updateImage(daysLeft, trayIcon);
        trayIcon.displayMessage("Great date", "Great date started" , TrayIcon.MessageType.INFO);
        TimerManager.start(trayIcon);
        return trayIcon;
    }

    private static TrayIcon configureTrayIcon(SystemTray systemTray, String daysLeft) {
        MenuItem itemChangeDate = new MenuItem("Change date");
        MenuItem itemExit = new MenuItem("Exit");

        Image image = Toolkit.getDefaultToolkit().getImage("tray.gif");

        PopupMenu popupMenu = new PopupMenu("Great date");
        popupMenu.add(itemChangeDate);
        popupMenu.addSeparator();
        popupMenu.add(itemExit);

        try {
            TrayIcon trayIcon = new TrayIcon(image, getToolTip(daysLeft), popupMenu);
            trayIcon.setImageAutoSize(true);

            ActionListener changeDateActionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    trayIcon.displayMessage("Change date", "Input new date at terminal window" , TrayIcon.MessageType.INFO);
                    Date newDate = ConsoleManager.getDate();
                    if (newDate == null) {
                        trayIcon.displayMessage("Change date", "Operation canceled" , TrayIcon.MessageType.INFO);
                        return;
                    }

                    try {
                        FileManager.setDaysToFile(newDate);
                    } catch (IOException ex) {
                        trayIcon.displayMessage("Change date", "Error update date: " + ex.getMessage(), TrayIcon.MessageType.ERROR);
                        return;
                    }
                    try {
                        updateTrayIcon(trayIcon);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    trayIcon.displayMessage("Change date", "Update date: " + Service.getDateFormat().format(newDate), TrayIcon.MessageType.INFO);
                }
            };

            ActionListener exitActionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            };

            itemExit.addActionListener(exitActionListener);
            itemChangeDate.addActionListener(changeDateActionListener);

            systemTray.add(trayIcon);
            return trayIcon;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void updateTrayIcon(TrayIcon trayIcon) throws IOException {
        Date date = FileManager.getDaysFromFile();
        String descriptionInTray = Service.daysBetween(date);
        ImageManager.updateImage(descriptionInTray, trayIcon);
        trayIcon.setToolTip(getToolTip(descriptionInTray));
    }

    private static String getToolTip(String daysLeft) {
        return "Great date left: " + daysLeft;
    }
}
