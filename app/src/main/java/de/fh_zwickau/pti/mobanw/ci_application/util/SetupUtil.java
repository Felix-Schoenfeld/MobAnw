package de.fh_zwickau.pti.mobanw.ci_application.util;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import de.fh_zwickau.pti.mobanw.ci_application.model.CI;

public class SetupUtil {

    // FIXME: Die meisten Dinge (Author, Sprache, etc) werden vom parser nicht richtig eingelesen
    public static ArrayList<CI> loadCIListFromJson(Context context) {
        ArrayList<CI> ciList = new ArrayList<>();
        String fileName = "CIs20-Simplified.json";

        try {
            InputStream inputStream = context.getAssets().open(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            Type listType = new TypeToken<ArrayList<CI>>() {}.getType();
            ArrayList<CI> tempList = new Gson().fromJson(inputStreamReader, listType);

            if (tempList != null) {
                ciList.addAll(tempList);
            }

            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            Log.e("JsonLoader", "Error reading JSON file: " + e.getMessage());
        }

        return ciList;
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
