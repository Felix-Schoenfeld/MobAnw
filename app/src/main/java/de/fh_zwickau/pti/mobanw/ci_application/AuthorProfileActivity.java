package de.fh_zwickau.pti.mobanw.ci_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;

import de.fh_zwickau.pti.mobanw.ci_application.model.Author;
import de.fh_zwickau.pti.mobanw.ci_application.model.Language;
import de.fh_zwickau.pti.mobanw.ci_application.util.UserCIStorage;

public class AuthorProfileActivity extends AppCompatActivity {

    protected void addAuthorInfoToUI() {
        Author author = UserCIStorage.getUserAuthor();
        if (author == null) return;
        EditText editTextAgeMin = (EditText) findViewById(R.id.editTextAgeMin);
        editTextAgeMin.setText(""+author.getMinAge());

        EditText editTextAgeMax = (EditText) findViewById(R.id.editTextAgeMax);
        editTextAgeMin.setText(""+author.getMaxAge());

        Spinner spGender = (Spinner) findViewById(R.id.spGender);
        try {
            // TODO: gender Auswählen
        } catch (Exception e) {
            Log.e("Load Author", author.getGender().toString()+" not found in spGender");
        }

        CheckBox cbEnglish = (CheckBox) findViewById(R.id.cbEnglish);
        CheckBox cbGerman = (CheckBox) findViewById(R.id.cbGerman);
        CheckBox cbSpanish = (CheckBox) findViewById(R.id.cbSpanish);

        ArrayList<Language> langList = author.getLanguages();
        if (langList.contains(Language.English)) cbEnglish.setChecked(true);
        else cbEnglish.setChecked(false);
        if (langList.contains(Language.German)) cbGerman.setChecked(true);
        else cbGerman.setChecked(false);
        if (langList.contains(Language.Spanish)) cbSpanish.setChecked(true);
        else cbSpanish.setChecked(false);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_profile);

        addAuthorInfoToUI();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        addAuthorInfoToUI();
    }

    // TODO: Methoden und UI für das Ändern von UserCIStorage.setUserAuthor()
}