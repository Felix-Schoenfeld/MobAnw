package de.fh_zwickau.pti.mobanw.ci_application.util;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import de.fh_zwickau.pti.mobanw.ci_application.model.CI;
import de.fh_zwickau.pti.mobanw.ci_application.model.CIRepository;

public class CIFavStorage {

    private static final String favFileName = "ci-fav.json";

    public static void saveCIRepositoryFavListToJsonFile(Context context) {
        ArrayList<Integer> favIds = new ArrayList<>();
        for (CI ci : CIRepository.getFavCIList()) {
            favIds.add(ci.getId());
        }

        saveFavIdsToJsonFile(favIds, context);
    }
    public static ArrayList<Integer> getFavIdsFromJsonFile(Context context) {
        ArrayList<Integer> ids = new ArrayList<>();
        JsonArray idArray = loadFromFile(context);
        for (int i = 0; i < idArray.size(); i++) {
            try {
                Integer id = Integer.valueOf(idArray.get(i).toString());
                ids.add(id);
                Log.d("Fav Load", "Loaded Fav "+id);
            } catch (Exception e) {
                Log.e("Fav Load", "Failed to load "+idArray.get(i).toString());
            }
        }
        return ids;
    }

    private static JsonArray loadFromFile(Context context) {

        JsonArray idArray = new JsonArray();

        File file = new File(context.getFilesDir(), favFileName);

        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                Gson gson = new Gson();
                idArray = gson.fromJson(fileReader, JsonArray.class);
                fileReader.close();

                Log.d("Fav load", "JsonArray loaded successfully");
            } catch (IOException | JsonParseException e) {
                e.printStackTrace();
                Log.e("Fav load", "Failed to load the JsonArray");
            }
        } else {
            Log.d("Fav load", "File does not exist");
        }

        return idArray;
    }
    private static void writeToFile(JsonArray array, Context context) {

        File file = new File(context.getFilesDir(), favFileName);

        if (file.exists()) {
            if (file.delete()) {
                Log.d("Fav save", "File deleted successfully");
            } else {
                Log.d("Fav save", "Failed to delete the file");
            }
        } else {
            Log.d("Fav save", "File does not exist");
        }

        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(array.toString());
            fileWriter.flush();
            fileWriter.close();
            Log.d("Fav save", "JsonArray saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Fav save", "Failed to save the JsonArray");
        }
    }

    public static void saveFavIdsToJsonFile(ArrayList<Integer> ids, Context context) {

        JsonArray idArray = new JsonArray();
        for (Integer id : ids) {
            idArray.add(id);
        }

        writeToFile(idArray, context);

    }
}
