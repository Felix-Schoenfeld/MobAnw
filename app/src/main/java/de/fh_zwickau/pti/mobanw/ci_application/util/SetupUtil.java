package de.fh_zwickau.pti.mobanw.ci_application.util;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import de.fh_zwickau.pti.mobanw.ci_application.model.CI;
import de.fh_zwickau.pti.mobanw.ci_application.model.Language;

public class SetupUtil {

    public static ArrayList<CI> loadCIListFromJson(Context context) {
        ArrayList<CI> ciList = new ArrayList<>();
        String fileName = "CIs20-Simplified.json";

        try {
            InputStream inputStream = context.getAssets().open(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            JsonArray json = (JsonArray) JsonParser.parseReader(inputStreamReader);

            ciList = ciListFromJsonArray(json);

            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            Log.e("JsonLoader", "Error reading JSON file: " + e.getMessage());
        }

        return ciList;
    }

    private static ArrayList<CI> ciListFromJsonArray(JsonArray json) {
        ArrayList<CI> ciList = new ArrayList<>();

        for (int i = 0; i < json.size(); i++) {
            JsonElement elem = json.get(i);
            Log.d("Found Element "+i,elem.toString());
            CI ci = ciFromJsonElement(elem);
            ciList.add(ci);
        }
        return  ciList;
    }

    private static CI ciFromJsonElement(JsonElement elem) {
        int id;
        String title;
        String story;
        Date recordedDate;
        Language language;
        String place;
        String authorName = "TODO";
        JsonObject json = elem.getAsJsonObject();

        id = Integer.valueOf(json.get("id").toString());
        title = json.get("title").toString();
        story = json.get("textStory").getAsJsonObject().get("story").toString();
        DateFormat formatter = new SimpleDateFormat("mm/dd/yy");
        try {
            recordedDate = formatter.parse(json.get("created").toString());
        } catch (Exception e) {
            recordedDate = new Date();
        }
        String languageString = json.get("textStory").getAsJsonObject().get("languages").getAsJsonArray().get(0).getAsJsonObject().get("label").toString();
        try {
            language = Language.valueOf(languageString.toUpperCase());
        } catch (Exception e) {
            switch (languageString.toLowerCase()) {
                case ("german"):
                case("deutsch"):
                    language = Language.German;
                    break;
                case("englisch"):
                case("english"):
                    language = Language.English;
                    break;
                case("spanish"):
                case("spanisch"):
                    language = Language.Spanish;
                default:
                    language = Language.Unknown;
            }
        }
        place = json.get("location").getAsJsonObject().get("country").getAsJsonObject().get("label").toString();

        return new CI(id,title,story,recordedDate,language,place,authorName);
    }

    public static void ciListDebugPrint(ArrayList<CI> ciList) {
        if (ciList.isEmpty()) {
            Log.d("JsonLoader", "No CI objects found in the JSON file.");
        } else {
            for (CI ci : ciList) {
                Log.d("CI JsonLoader", "CI ID: " + ci.getId());
            }
        }
    }
}
