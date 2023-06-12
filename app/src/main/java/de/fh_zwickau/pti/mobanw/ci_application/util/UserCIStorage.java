package de.fh_zwickau.pti.mobanw.ci_application.util;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import de.fh_zwickau.pti.mobanw.ci_application.model.Author;
import de.fh_zwickau.pti.mobanw.ci_application.model.CI;
import de.fh_zwickau.pti.mobanw.ci_application.model.CIRepository;
import de.fh_zwickau.pti.mobanw.ci_application.model.Gender;
import de.fh_zwickau.pti.mobanw.ci_application.model.Language;

public class UserCIStorage {

    private static String userCiFile = "ci-user.json";

    private static JsonElement jsonElementFromCi(CI ci) {
        JsonObject ciJson = new JsonObject();

        // CI
        ciJson.addProperty("id", ci.getId());
        ciJson.addProperty("title", ci.getTitle());
        ciJson.addProperty("story", ci.getStory());
        ciJson.addProperty("recordedDate", ci.getRecordedDate().toString());
        ciJson.addProperty("language", ci.getLanguage().toString());
        ciJson.addProperty("place", ci.getPlace());

        // Author
        JsonObject authorJson = new JsonObject();
        authorJson.addProperty("minAge", ci.getAuthor().getMinAge());
        authorJson.addProperty("maxAge", ci.getAuthor().getMaxAge());
        authorJson.addProperty("gender", ci.getAuthor().getGender().toString());

        JsonArray nationalitiesArray = new JsonArray();
        for (Language nationality : ci.getAuthor().getLanguages()) {
            JsonObject nationalityJson = new JsonObject();
            nationalityJson.addProperty("language", nationality.toString());
            nationalitiesArray.add(nationalityJson);
        }
        authorJson.add("nationalities", nationalitiesArray);

        ciJson.add("author", authorJson);

        // Create and return the JsonElement
        Gson gson = new Gson();
        return gson.toJsonTree(ciJson);
    }

    private static CI ciFromJsonElement(JsonElement element) {
        JsonObject ciJson = element.getAsJsonObject();

        // CI properties
        int id = ciJson.get("id").getAsInt();
        String title = ciJson.get("title").getAsString();
        String story = ciJson.get("story").getAsString();
        Date recordedDate = new Date(ciJson.get("recordedDate").getAsString());
        Language language = Language.valueOf(ciJson.get("language").getAsString());
        String place = ciJson.get("place").getAsString();

        // Author properties
        JsonObject authorJson = ciJson.getAsJsonObject("author");
        int minAge = authorJson.get("minAge").getAsInt();
        int maxAge = authorJson.get("maxAge").getAsInt();
        Gender gender = Gender.valueOf(authorJson.get("gender").getAsString());

        JsonArray nationalitiesArray = authorJson.getAsJsonArray("nationalities");
        ArrayList<Language> languages = new ArrayList<>();
        for (JsonElement nationalityElement : nationalitiesArray) {
            JsonObject nationalityJson = nationalityElement.getAsJsonObject();
            Language nationality = Language.valueOf(nationalityJson.get("language").getAsString());
            languages.add(nationality);
        }

        // Create and return the CI object
        Author author = new Author(minAge, maxAge, gender, languages);
        return new CI(id, title, story, recordedDate, language, place, author);
    }

    private static void writeToFile(JsonArray array, Context context) {

        File file = new File(context.getFilesDir(), userCiFile);

        if (file.exists()) {
            if (file.delete()) {
                Log.d("UserCI save", "File deleted successfully");
            } else {
                Log.d("UserCI save", "Failed to delete the file");
            }
        } else {
            Log.d("UserCI save", "File does not exist");
        }

        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(array.toString());
            fileWriter.flush();
            fileWriter.close();
            Log.d("UserCI save", "JsonArray saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("UserCI save", "Failed to save the JsonArray");
        }
    }

    private static void saveUserCisToJsonFile(ArrayList<CI> cis, Context context) {

        JsonArray ciArray = new JsonArray();
        for (CI ci : cis) {
            ciArray.add(jsonElementFromCi(ci));
        }

        writeToFile(ciArray, context);

    }

    public static void saveCIRepositoryUserCIListToJsonFile(Context context) {
        saveUserCisToJsonFile(CIRepository.getUserCIList(), context);
    }

    public static ArrayList<CI> loadUserCIListFromJsonFile(Context context) {
        ArrayList<CI> cis = new ArrayList<>();
        JsonArray ciArray = loadFromFile(context);
        for (int i = 0; i < ciArray.size(); i++) {
            try {
                CI ci = ciFromJsonElement(ciArray.get(i));
                cis.add(ci);
                Log.d("UserCI Load", "Loaded CI "+ci.getId());
            } catch (Exception e) {
                Log.e("UserCI Load", "Failed to load "+ciArray.get(i).toString());
            }
        }
        return cis;
    }

    private static JsonArray loadFromFile(Context context) {

        JsonArray ciArray = new JsonArray();

        File file = new File(context.getFilesDir(), userCiFile);

        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                Gson gson = new Gson();
                ciArray = gson.fromJson(fileReader, JsonArray.class);
                fileReader.close();

                Log.d("Fav load", "JsonArray loaded successfully");
            } catch (IOException | JsonParseException e) {
                e.printStackTrace();
                Log.e("Fav load", "Failed to load the JsonArray");
            }
        } else {
            Log.d("Fav load", "File does not exist");
        }

        return ciArray;
    }

}
