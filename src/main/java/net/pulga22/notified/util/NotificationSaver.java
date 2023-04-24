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
            NotificationObject notification = new NotificationObject("):", "No message",
                    "):", "No message",
                    "):", "No message");
            try (FileWriter writer = new FileWriter(filePath)) {
                gson.toJson(notification, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static String filePath(){
        return filePath;
    }

    public static String getTitle(int index){
        Gson gson = new Gson();

        try (Reader reader = new FileReader(filePath)) {
            NotificationObject notification = gson.fromJson(reader, NotificationObject.class);

            switch (index) {
                case 1 -> {
                    return notification.title1;
                }
                case 2 -> {
                    return notification.title2;
                }
                case 3 -> {
                    return notification.title3;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getMessage(int index){
        Gson gson = new Gson();

        try (Reader reader = new FileReader(filePath)) {
            NotificationObject notification = gson.fromJson(reader, NotificationObject.class);

            switch (index) {
                case 1 -> {
                    return notification.message1;
                }
                case 2 -> {
                    return notification.message2;
                }
                case 3 -> {
                    return notification.message3;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void setTitle(int index, String title){
        Gson gson = new Gson();
        NotificationObject notification = null;

        try (Reader reader = new FileReader(filePath)) {
            notification = gson.fromJson(reader, NotificationObject.class);

            switch (index){
                case 1 -> {
                    notification.title1 = title;
                }
                case 2 -> {
                    notification.title2 = title;
                }
                case 3 -> {
                    notification.title3 = title;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(notification, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void setMessage(int index, String message){
        Gson gson = new Gson();
        NotificationObject notification = null;

        try (Reader reader = new FileReader(filePath)) {
            notification = gson.fromJson(reader, NotificationObject.class);

            switch (index){
                case 1 -> {
                    notification.message1 = message;
                }
                case 2 -> {
                    notification.message2 = message;
                }
                case 3 -> {
                    notification.message3 = message;
                }
            }

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
    public String title1;
    public String message1;
    public String title2;
    public String message2;
    public String title3;
    public String message3;


    public NotificationObject(String title1, String message1,String title2, String message2,String title3, String message3){
        this.title1 = title1;
        this.message1 = message1;
        this.title2 = title2;
        this.message2 = message2;
        this.title3 = title3;
        this.message3 = message3;
    }

}