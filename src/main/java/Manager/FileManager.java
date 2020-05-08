package Manager;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.*;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.Date;

public class FileManager {
    private static TrayIcon trayIcon;

    private static String getFilePath() throws IOException {
        return  new File(".").getCanonicalPath() + "\\GreatData_tray.jar";
    }

    private static Date readFromFile(String attrName, Boolean needCreateAttr) throws IOException {
        Boolean wasCreatedAttr = false;
        UserDefinedFileAttributeView view = Files.getFileAttributeView(Paths.get(getFilePath()), UserDefinedFileAttributeView.class);
        if (needCreateAttr) {
            wasCreatedAttr = true;
            setAttribute(attrName, new Date());
        }
        try {
            ByteBuffer readBuffer = ByteBuffer.allocate(view.size(attrName));
            view.read(attrName, readBuffer);
            readBuffer.flip();
            String value = new String(readBuffer.array(), "UTF-8");
            return Service.getDateFormat().parse(value);
        } catch (Exception e) {
            // no such attribute - create them
            if (!wasCreatedAttr) {
                return readFromFile(attrName, true);
            } else
                return null;
        }
    }

    private static void writeToFile(String name, Date date) throws IOException {
        UserDefinedFileAttributeView view = Files.getFileAttributeView(Paths.get(getFilePath()), UserDefinedFileAttributeView.class);
        String strDate = Service.getDateFormat().format(date);
        byte[] bytes = strDate.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        view.write(name, writeBuffer);
    }

    private static void setAttribute(String name, Date date) throws IOException {
        writeToFile(name, date);
    }

    private static Date getAttribute(String name) throws IOException {
        Date value = readFromFile(name, false);
        if (value == null) return new Date();
        return value;
    }

    public static Date getDaysFromFile() throws IOException {
        Date attrValue = getAttribute("GreatDate_tray.Date");
        return attrValue;
    }

    public static void setDaysToFile(Date date) throws IOException {
        setAttribute("GreatDate_tray.Date", date);
    }

    public static Boolean setDaysAtTray() throws IOException {
        String descriptionInTray = Service.daysBetween(getDaysFromFile());
        trayIcon = TrayManager.initTray(descriptionInTray);
        if (trayIcon == null) return false;
        return true;
    }

}

