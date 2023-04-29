package net.pulga22.notified.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationSaver {

    private static final String dirPath = FabricLoader.getInstance().getConfigDir().toAbsolutePath().toString() + "\\notified";

    private static String getFilePath(){
        return FabricLoader.getInstance().getConfigDir().toAbsolutePath() + "\\notified" + "\\notifications.json";
    }

    public static void configFile() {
        System.out.println();
        File file = new File(dirPath);
        boolean mkdirDone = file.mkdir();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        List<String> defaultTitles = new ArrayList<>();
        List<String> defaultMessages = new ArrayList<>();

        NotificationObject notification = new NotificationObject(defaultTitles, defaultMessages);
        try (FileWriter writer = new FileWriter(getFilePath())) {
            gson.toJson(notification, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<String> getTitles(){
        Gson gson = new Gson();

        try (Reader reader = new FileReader(getFilePath())) {
            NotificationObject notification = gson.fromJson(reader, NotificationObject.class);

            return notification.titles;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<String> getMessages(){
        Gson gson = new Gson();

        try (Reader reader = new FileReader(getFilePath())) {
            NotificationObject notification = gson.fromJson(reader, NotificationObject.class);

            return notification.messages;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void appendNotification(String title, String message){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        NotificationObject notification = null;

        try (Reader reader = new FileReader(getFilePath())) {

            notification = gson.fromJson(reader, NotificationObject.class);
            notification.appendNotification(title, message);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(getFilePath())) {
            gson.toJson(notification, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void lappendNotification(String title, String message){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        NotificationObject notification = null;

        try (Reader reader = new FileReader(getFilePath())) {

            notification = gson.fromJson(reader, NotificationObject.class);
            notification.lappendNotification(title, message);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(getFilePath())) {
            gson.toJson(notification, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void clear(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        NotificationObject notification = null;

        try (Reader reader = new FileReader(getFilePath())) {

            notification = gson.fromJson(reader, NotificationObject.class);
            notification.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(getFilePath())) {
            gson.toJson(notification, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

class NotificationObject{
    public List<String> titles;
    public List<String> messages;

    public NotificationObject(List<String> titles, List<String> messages){
        this.titles = titles;
        this.messages = messages;
    }

    public void appendNotification(String title, String message) {
        this.titles.add(0, title);
        this.messages.add(0, message);
    }

    public void lappendNotification(String title, String message){
        this.titles.add(title);
        this.messages.add(message);
    }

    public void clear(){
        this.titles.clear();
        this.messages.clear();
    }

}