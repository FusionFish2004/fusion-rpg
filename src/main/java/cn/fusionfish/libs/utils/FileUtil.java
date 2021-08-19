package cn.fusionfish.libs.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileUtil {

    public static @Nullable JsonObject getJson(@NotNull File file){
        if (!file.exists()){
            return null;
        }
        JsonObject json = new JsonObject();
        try {
            json = new JsonParser().parse(new JsonReader(new FileReader(file))).getAsJsonObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static void saveJson(File file, String json) {
        try {
            if (!file.exists()) {
                boolean result = file.createNewFile();
                if (!result) throw new RuntimeException();
            }

            Writer write = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            write.write(json);
            write.flush();
            write.close();

        } catch (Exception ignored) {

        }
    }

    public static @Nullable List<File> getFiles(@NotNull File dir){
        if(!dir.isDirectory() || !dir.exists()){
            return null;
        }

        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(dir.listFiles())));
    }
}
