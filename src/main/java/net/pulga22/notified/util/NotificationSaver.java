package net.pulga22.notified.util;

import com.google.gson.Gson;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;

public class NotificationSaver {

    private static final String dirPath = FabricLoader.getInstance().getConfigDir().toAbsolutePath().toString() + "\\notified";
    private static final String filePath = dirPath + "\\notification.json";

    public static void configFile() {
        System.out.println();
        File file = new File(dirPath);
        boolean mkdirDone = file.mkdir();
        if (mkdirDone){
            Gson gson = new Gson();
            NotificationObject notification = new NotificationObject("):", "No message");
            try (FileWriter writer = new FileWriter(filePath)) {
                gson.toJson(notification, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static String getTitle(){
        Gson gson = new Gson();

        try (Reader reader = new FileReader(filePath)) {
            NotificationObject notification = gson.fromJson(reader, NotificationObject.class);

            return notification.title;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getMessage(){
        Gson gson = new Gson();

        try (Reader reader = new FileReader(filePath)) {
            NotificationObject notification = gson.fromJson(reader, NotificationObject.class);

            return notification.message;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void setTitle(String title){
        Gson gson = new Gson();
        NotificationObject notification = null;

        try (Reader reader = new FileReader(filePath)) {
            notification = gson.fromJson(reader, NotificationObject.class);
            notification.title = title;
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(notification, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void setMessage(String message){
        Gson gson = new Gson();
        NotificationObject notification = null;

        try (Reader reader = new FileReader(filePath)) {
            notification = gson.fromJson(reader, NotificationObject.class);
            notification.message = message;
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(notification, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

class NotificationObject{
    public String title;
    public String message;

    public NotificationObject(String title, String message){
        this.title = title;
        this.message = message;
    }

}