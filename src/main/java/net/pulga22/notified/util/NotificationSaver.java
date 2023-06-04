package net.pulga22.notified.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Data;
import net.fabricmc.loader.api.FabricLoader;
import net.pulga22.notified.handler.NotificationConfig;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Data
public class NotificationSaver {
    private NotificationConfig config;
    private Gson gson;

    private static final String folderPath = FabricLoader.getInstance().getConfigDir()
        .toAbsolutePath().toString() + "\\notified";

    private static final String filePath = FabricLoader.getInstance().getConfigDir()
            .toAbsolutePath().toString() + "\\notified\\notifications-2.json";

    public NotificationSaver() {
        this.gson = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();
        this.config = new NotificationConfig();
        loadConfig();
    }

    public void loadConfig() {
        File folder = new File(folderPath);
        System.out.println(folderPath);
        if (!folder.exists()){
            folder.mkdir();
        }
        File file = new File(filePath);
        if (!file.exists() && this.getConfig() != null){
            try {
                file.createNewFile();
                this.saveConfig();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(file.getPath()));
            NotificationConfig object = gson.fromJson(reader, NotificationConfig.class);
            this.setConfig(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        File file = new File(filePath);
        try {
            FileWriter fileWriter = new FileWriter(file);
            gson.toJson(this.getConfig(), fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}