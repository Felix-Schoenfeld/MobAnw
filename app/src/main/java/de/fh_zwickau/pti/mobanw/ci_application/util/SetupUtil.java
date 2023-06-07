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

import de.fh_zwickau.pti.mobanw.ci_application.model.Author;
import de.fh_zwickau.pti.mobanw.ci_application.model.CI;
import de.fh_zwickau.pti.mobanw.ci_application.model.Gender;
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

        // CI

        int id;
        String title;
        String story;
        Date recordedDate;
        Language language;
        String place;
        Author author;
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
        String languageString = json.get("textStory").getAsJsonObject().get("languages").getAsJsonArray().get(0).getAsJsonObject().get("language").toString();
        languageString = stripQuotationMarks(languageString);
        language = stringToLanguage(languageString);


        place = json.get("location").getAsJsonObject().get("country").getAsJsonObject().get("label").toString();

        // Author

        JsonObject authorJson = json.get("textStory").getAsJsonObject().get("author").getAsJsonObject();
        int minAge;
        int maxAge;
        Gender gender;
        ArrayList<Language> languages = new ArrayList<>();

        Log.d("Author JSON",authorJson.toString());

        try {
            minAge = Integer.valueOf(stripQuotationMarks(authorJson.get("minAge").toString()));
            maxAge = Integer.valueOf(stripQuotationMarks(authorJson.get("maxAge").toString()));
        } catch(NumberFormatException ex) {
            minAge = 0;
            maxAge = 0;
        }

        String genderString = "";
        try {
            genderString = authorJson.get("gender").getAsJsonObject().get("label").toString();
            genderString = stripQuotationMarks(genderString);
            Log.d("gender",genderString);
        } catch (Exception e) {
            genderString = "Unknown";
        }

        gender = stringToGender(genderString);

        JsonArray nationalitiesArray = authorJson.get("nationalities").getAsJsonArray();
        for (int i=0; i < nationalitiesArray.size(); i++) {
            JsonObject nationality = nationalitiesArray.get(i).getAsJsonObject();
            languages.add(stringToLanguage(stripQuotationMarks(nationality.get("language").toString())));
        }



        author = new Author(minAge,maxAge,gender,languages);

        return new CI(id,title,story,recordedDate,language,place,author);
    }

    public static Language stringToLanguage(String languageString) {
        Language language;
        try {
            language = Language.valueOf(languageString.toUpperCase());
        } catch (Exception e) {
            switch (languageString.toLowerCase()) {
                case("de"):
                case("ger"):
                    language = Language.German;
                    break;
                case("en"):
                case("eng"):
                    language = Language.English;
                    break;
                case("sp"):
                case("spa"):
                    language = Language.Spanish;
                    break;
                default:
                    language = Language.Unknown;
            }
        }
        return language;
    }

    public static Gender stringToGender(String genderString) {
        Gender gender;
        try {
            gender = Gender.valueOf(genderString.toUpperCase());
        } catch (Exception e) {
            // FIXME: wahrscheinlich selbes problem hier wie bei language
            switch (genderString.toLowerCase()) {
                case ("female"):
                case("weiblich"):
                    gender = Gender.Female;
                    break;
                case("male"):
                case("männlich"):
                    gender = Gender.Male;
                    break;
                default:
                    gender = Gender.Unknown;
            }
        }
        return gender;
    }

    private static String stripQuotationMarks(String string) {
        if (string.startsWith("\"") && string.endsWith("\"")) {
            string = string.substring(1, string.length() - 1);
        }
        return string;
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
