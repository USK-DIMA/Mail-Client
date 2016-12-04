package uskov.mail.client;

import com.google.gson.Gson;

import java.io.*;

/**
 * Created by Dmitry on 04.12.2016.
 */
public class SettingsReader {

    public static Settings readSettings() throws IOException {
        File file = new File("settings");
        if(!file.exists() || !file.isFile()){
            return null;
        }

        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        String settingsJson = new String(data, "UTF-8");
        return new Gson().fromJson(settingsJson, Settings.class);
    }

    public static void saveSettings(Settings settings) throws IOException {
        File file = new File("settings");
        if(!file.exists() ){
            file.createNewFile();
        }
        FileOutputStream fout = new FileOutputStream(file);
        String settingsJson = new Gson().toJson(settings);
        byte[] b = settingsJson.getBytes("UTF-8");
        fout.write(b);
    }
}
